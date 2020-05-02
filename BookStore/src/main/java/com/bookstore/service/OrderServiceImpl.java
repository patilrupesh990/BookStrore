package com.bookstore.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.bookstore.dao.BookDaoImpl;
import com.bookstore.dao.IOrderDAO;
import com.bookstore.dto.MailResponse;
import com.bookstore.exception.InvalidTokenOrExpiredException;
import com.bookstore.exception.UserDoesNotExistException;
import com.bookstore.model.Book;
import com.bookstore.model.Order;
import com.bookstore.model.UserData;
import com.bookstore.response.OrderListResponse;
import com.bookstore.response.OrderResponse;
import com.bookstore.util.JwtTokenUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements IOrderservice {

	@Autowired
	IOrderDAO orderDao;
	@Autowired
	BookDaoImpl bookDao;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	private JwtTokenUtil generateToken;
	static UserData userData;
	 double finalAmount=0;
	@Autowired
	private JavaMailSender sender;
	@Autowired
	Configuration config;

	/**
	 *
	 */
	@Override
	public ResponseEntity<Object> makeOrder(String token, int id, int quantity) {
		if (verifyUser(token)) {
			Book book = bookDao.getBookByBookId(id);
			Order order = new Order();
			order.setBookId(id);
			order.setUserId(userData.getUId());
			order.setQuantity(quantity);
			order.setBookName(book.getBookName());
			order.setPrice(book.getPrice());
			order.setCustomerName(userData.getFirstName());
			order.setEmail(userData.getEmail());
			order.setPhNo(userData.getPhNo());
			order.setTotal(order.getPrice() * order.getQuantity());
			order.setBookImage(book.getBookImage());
			if (orderDao.addOrder(order) > 0) {
				System.out.println("Added successfully");
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(new OrderResponse(202, "Order Added to cart"));
			}
		} else {
			throw new UserDoesNotExistException("User Does Not Exist", HttpStatus.BAD_REQUEST);
		}
		return null;
	}

	@Override
	public ResponseEntity<Object> getCartList(String token) {
		if (verifyUser(token)) {
			Optional<List<Order>> orders = Optional.ofNullable(orderDao.getOrderList(userData.getUId()));
			if (orders.isPresent()) {
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new OrderListResponse(202, "total books in cart" + orders.get().size(), orders.get()));
			} else {
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new OrderResponse(202, "No any Books Added to cart"));
			}
		}
		throw new UserDoesNotExistException("User Does Not Exist", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<Object> updateQuantity(String token, Order order) {
		if (verifyUser(token)) {
			if (orderDao.updateQuantity(order) > 0) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(new OrderResponse(202, "Quantity Updatd"));
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new OrderResponse(500, "Error in Updated Quantity"));
			}
		} else {
			throw new UserDoesNotExistException("User Does Not Exist", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<Object> cancelOrder(String token, int bookId) {
		if (verifyUser(token)) {
			if (orderDao.deleteOrder(bookId) > 0) {
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new OrderResponse(202, "Order Deleted SuccessFully"));
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new OrderResponse(500, "Error in Delete Order"));
			}
		} else {
			throw new UserDoesNotExistException("User Does Not Exist", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<Object> confirmOrder(String token, List<Order> order) {
		if (verifyUser(token)) {
			MimeMessage message = sender.createMimeMessage();
			Map<String, Object> model = new HashMap<String, Object>();
			order.forEach(s->{
				double temp=0;
				temp =s.getTotal();
				finalAmount +=temp;
			});
			
			model.put("name",userData.getFirstName());
			model.put("total", finalAmount);
			try {
				MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED,
						StandardCharsets.UTF_8.name());
				Template template = config.getTemplate("order-summery.ftl");
				String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
				helper.setTo(userData.getEmail());
				helper.setText(html, true);
				helper.setSubject("BookStore Order Summery");
				helper.setFrom("pati.rupesh990@gmail.com");
				sender.send(message);
				orderDao.removeAllOrder(userData.getUId());
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MailResponse("Mail Sent", "202"));
			} catch (MessagingException | IOException | TemplateException e) {
				System.out.println("Error in message sending");
				e.printStackTrace();
			}
		} else {
			throw new UserDoesNotExistException("User Does Not Exist", HttpStatus.BAD_REQUEST);
		}
		return null;
	}

	public boolean verifyUser(String token) {
		log.info("-------->>>>>>>>>>>>>Calling USerApi From NotesApi<<<<<<<<<<<<<<<<--------------------");
		userData = restTemplate.getForObject("http://localhost:8092/users/" + token, UserData.class);
		log.info("--------->>>>>>>>>>>>Accessing DataFrom UserApi<<<<<<<<<<<---------------------");
		try {
			log.info("verifyUserApi Using RestTemplate From UserApi Success--------->:"
					+ (userData.getUId() == generateToken.parseToken(token)));
			log.info("erererererererererererererereererereereerererhsghgghsghgsd" + userData.getPhNo());
			return (userData.getUId() == generateToken.parseToken(token));
		} catch (SignatureVerificationException | JWTDecodeException | AlgorithmMismatchException e) {
			throw new InvalidTokenOrExpiredException("Invalid Token or Token Expired", HttpStatus.BAD_REQUEST);
		}
	}
}
