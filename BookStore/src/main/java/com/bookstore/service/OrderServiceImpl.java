package com.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.bookstore.dao.BookDaoImpl;
import com.bookstore.dao.IOrderDAO;
import com.bookstore.exception.InvalidTokenOrExpiredException;
import com.bookstore.exception.UserDoesNotExistException;
import com.bookstore.model.Book;
import com.bookstore.model.Order;
import com.bookstore.model.UserData;
import com.bookstore.response.OrderResponse;
import com.bookstore.util.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements IOrderservice{
	
	@Autowired  
	IOrderDAO orderDao;
	@Autowired
	BookDaoImpl bookDao;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	private JwtTokenUtil generateToken;
	static UserData userData;
	
	/**
	 *
	 */
	@Override
	public ResponseEntity<Object> makeOrder(String token,int id,int quantity) {
		if(verifyUser(token)) {
			Book book=bookDao.getBookByBookId(id);
			Order order=new Order();
			order.setBookId(id);
			order.setUserId(userData.getUId());
			order.setQuantity(quantity);
			order.setBookName(book.getBookName());
			order.setPrice(book.getPrice());
			order.setCustomerName(userData.getFirstName());
			order.setEmail(userData.getEmail());
			order.setPhNo(userData.getPhNo());
			order.setTotal(order.getPrice()*order.getQuantity());
			
			
			if(orderDao.addOrder(order)>0) {
				System.out.println("Added successfully");
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new OrderResponse(202,"Order Added to cart"));
			}
		}
		else {
			throw new UserDoesNotExistException("User Does Not Exist", HttpStatus.BAD_REQUEST);		}
		return null;
	}

	@Override
	public ResponseEntity<Object> cancelOrder() {
		
		return null;
	}
	
	public boolean verifyUser(String token) {
		log.info("-------->>>>>>>>>>>>>Calling USerApi From NotesApi<<<<<<<<<<<<<<<<--------------------");
		userData = restTemplate.getForObject("http://localhost:8092/users/" + token, UserData.class);
		log.info("--------->>>>>>>>>>>>Accessing DataFrom UserApi<<<<<<<<<<<---------------------");
		try {
		log.info("verifyUserApi Using RestTemplate From UserApi Success--------->:"
				+ (userData.getUId() == generateToken.parseToken(token)));	
		log.info("erererererererererererererereererereereerererhsghgghsghgsd"+userData.getPhNo());
		return (userData.getUId() == generateToken.parseToken(token));
		}catch (SignatureVerificationException|JWTDecodeException|AlgorithmMismatchException e) {
			throw new InvalidTokenOrExpiredException("Invalid Token or Token Expired", HttpStatus.BAD_REQUEST);
		}
	}
}
