package com.bookstore.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookstore.user.configuration.PasswordEncoderConfiguration;
import com.bookstore.user.dao.IUserDAO;
import com.bookstore.user.model.User;
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
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserResponse(400, "User Already Registered"));
		}
	}

	@Override
	public ResponseEntity<UserResponse> activateUser(String token) {
		int id = generateToken.parseToken(token);
		if (userDao.activateUSer(id) > 0) {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new UserResponse(208, "Your Account Activate Successfully"));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new UserResponse(400, "User Not Registered Or Token Expired"));
		}
	}

	public ResponseEntity<UserResponse> loginUser(String email, String password) {
			Optional<User> user = Optional.ofNullable(userDao.getUser(email));
			if(user.isPresent()&& passwordEncryption.passwordEncoder().matches(password, user.get().getPassword())) {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new UserResponse(202, "User Login Successfull WellCome Mr." + user.get().getFirstName() + " "
							+ user.get().getLastName()));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new UserResponse(400, "Invalid UserName Or Password"));
		}
	}
	
	
}
