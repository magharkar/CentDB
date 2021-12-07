package com.csci5408.centdb.services;


import com.csci5408.centdb.model.User;
import com.csci5408.centdb.persistence.IUserDao;
import com.csci5408.centdb.persistence.impl.FileSystemUserDao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static com.csci5408.centdb.model.util.Constants.*;

public class UserService {
	IUserDao userDao;
	static Scanner sc = new Scanner(System.in);

	public UserService() throws IOException {
		userDao = new FileSystemUserDao();
	}

	public boolean userRegistration() throws IOException {
		List<String> securityAnswers = new ArrayList<>();
		User user = new User();
		String Message = "";
		System.out.println("Enter a userId");
		final String userId = sc.nextLine();
		User userDetails = userDao.getUserDetails(userId);
		if (!Objects.isNull(userDetails)) {
			System.out.println("\nUser Id already exists. Please enter another User ID or Login.");
			return false;
		}
		user.setUserId(userId);
		System.out.println("Enter password (min length : 8 characters)");
		String password = sc.nextLine();
		while (!isPasswordValid(password)){
			System.out.println("Please enter a password with minimum 8 characters");
			password = sc.nextLine();
		}
		user.setPassword(password);

		System.out.println("Please answer the following security questions:");

		for (String securityQuestion : SECURITY_QUESTIONS) {
			System.out.println(securityQuestion);
			String securityAnswer = sc.nextLine();
			securityAnswers.add(securityAnswer);
		}
		user.setSecurityAnswers(securityAnswers);
		userDao.addUser(user);
		return true;
	}
	public boolean userLogin() throws IOException {
		List<String> securityAnswers = new ArrayList<>();
		User registerUser = new User();
		securityAnswers = new ArrayList<>();
		System.out.println("Enter User Id: ");
		String userInput = sc.nextLine();
		registerUser.setUserId(userInput);
		System.out.println("Enter your password: ");
		userInput = sc.nextLine();
		registerUser.setPassword(userInput);
		System.out.println("");
		for (String securityQuestion: SECURITY_QUESTIONS) {
			System.out.println(securityQuestion);
			userInput = sc.nextLine();
			securityAnswers.add(userInput);
		}
		registerUser.setSecurityAnswers(securityAnswers);
		String errorMessage = userDao.userValidation(registerUser);
		if(!errorMessage.equals("")){
			System.out.println(errorMessage);
			return false;
		}
		return true;
	}


	private static boolean isPasswordValid(String password){
		if(password.length() > 8){
			return true;
		}
		return false;
	}

}
