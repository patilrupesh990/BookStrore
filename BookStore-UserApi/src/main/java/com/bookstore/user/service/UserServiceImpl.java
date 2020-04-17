package com.bookstore.user.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.bookstore.user.configuration.PasswordEncoderConfiguration;
import com.bookstore.user.dao.IUserDAO;
import com.bookstore.user.exception.AuthenticationFailedException;
import com.bookstore.user.exception.InvalidTokenOrExpiredException;
import com.bookstore.user.exception.UserAlradyRegisterException;
import com.bookstore.user.model.User;
import com.bookstore.user.response.LoginSuccessResponse;
import com.bookstore.user.response.UserData;
import com.bookstore.user.response.UserResponse;
import com.bookstore.user.util.DateValidator;
import com.bookstore.user.util.EmailGenerator;
import com.bookstore.user.util.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {
	@Autowired
	IUserDAO userDao;
	@Autowired
	PasswordEncoderConfiguration passwordEncryption;
	@Autowired
	private EmailGenerator emailGenerate;
	@Autowired
	private JwtTokenUtil generateToken;
	@Autowired
	private UserData userresponce;

	@Override
	public ResponseEntity<UserResponse> register(User user) {
		Optional<User> userExist = Optional.ofNullable(userDao.isUserExist(user));
		if (!userExist.isPresent()) {
			log.info("Password Original::" + user.getPassword());
			log.info("Encrypted Password::" + passwordEncryption.passwordEncoder().encode(user.getPassword()));
			user.setPassword(passwordEncryption.passwordEncoder().encode(user.getPassword()));
			log.info(user.getPassword());
			user.setActivate(false);
			user.setCreationTime(DateValidator.getCurrentDate());
			userDao.register(user);

			String link = "http://localhost:8092/users/activ/" + generateToken.generateToken(user.getUId());
			emailGenerate.sendEmail(user.getEmail(), "Foondu Notes Varification", link);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new UserResponse(208, "Verification Link Sent to your email ===>" + user.getEmail()
							+ "<=== please verify your email first"));
		} else {
			throw new UserAlradyRegisterException("User Already Registered", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<UserResponse> activateUser(String token) {
		int id = 0;
		try {
			id = generateToken.parseToken(token);
		} catch (SignatureVerificationException | AlgorithmMismatchException e) {
			throw new InvalidTokenOrExpiredException("Invalid Token or Token Expired", HttpStatus.BAD_REQUEST);
		}
		if (userDao.activateUSer(id) > 0) {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new UserResponse(208, "Your Account Activate Successfully"));
		} else {
			throw new InvalidTokenOrExpiredException("User Not Registered Or Token Expired", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<Object> loginUser(String email, String password) {
		Optional<User> user = Optional.ofNullable(userDao.getUser(email));
		if (user.isPresent() && passwordEncryption.passwordEncoder().matches(password, user.get().getPassword())) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new LoginSuccessResponse(user.get().getFirstName(),
					user.get().getLastName(), generateToken.generateToken(user.get().getUId()), "Login Success", 202));
		} else {
			throw new AuthenticationFailedException("Invalid UserName Or Password", HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<Object> loginAdmin(String email, String password) {
		Optional<User> user = Optional.ofNullable(userDao.getUser(email));
		if (user.isPresent() && user.get().isSeller()) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new LoginSuccessResponse(user.get().getFirstName(),
					user.get().getLastName(), generateToken.generateToken(user.get().getUId()), "Login Success", 202));
		} else {
			throw new AuthenticationFailedException("Invalid UserName Or Password", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<UserData> getUserByID(String token) {
		try {
			if (userDao.getUserById(generateToken.parseToken(token)) != null) {
				User user = userDao.getUserById(generateToken.parseToken(token));
				BeanUtils.copyProperties(user, userresponce);
				userresponce.setStatus(302);
				userresponce.setResponse("User Data Found");
				return ResponseEntity.status(HttpStatus.FOUND).body(userresponce);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UserData(404, "User Not Found"));
			}
		} catch (SignatureVerificationException | JWTDecodeException | AlgorithmMismatchException e) {
			throw new InvalidTokenOrExpiredException("Invalid Token or Token Expired", HttpStatus.BAD_REQUEST);
		}
	}
}
