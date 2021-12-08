package com.csci5408.centdb.services;


import static com.csci5408.centdb.model.util.Constants.SECURITY_QUESTIONS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import com.csci5408.centdb.persistence.implementation.FileSystemUserDao;
import com.csci5408.centdb.model.User;

public class UserService {
	FileSystemUserDao userDao;
	static Scanner sc = new Scanner(System.in);
	private static String name;
	public static String getUserName() {
		return name;
	}
	public void setUserName(String userName) {
		name = userName;
	}

	public UserService() throws IOException {
		userDao = new FileSystemUserDao();
	}
	public void userAccessControl() throws IOException {
		List<String> securityAnswers = new ArrayList<>();
		ACCESS: do {
			System.out.println("Please enter an option to continue\n1. Register\n2. Login\n3. Exit");
			int choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {

			case 1:
				try {
					User user = new User();
					System.out.println("Enter a userId");
					final String userId = sc.nextLine();
					User userDetails = userDao.getUserDetails(userId);
					if (!Objects.isNull(userDetails)) {
						System.out.println("User Id already exists. Please enter another User ID or Login.");
						continue ACCESS;
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
					System.out.println("User registered successfully!");
				} finally {
				}
				break;

			case 2:
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
					continue ACCESS;
				}
				setUserName(registerUser.getUserId());
				System.out.println("Successfully logged in!");
				userOperations();
				break;

			case 3:
				System.out.println("Thank you!");
				System.exit(0);
			}
		} while (true);
	}

	private static boolean isPasswordValid(String password){
		return password.length() > 8;
	}
	private static void userOperations() throws IOException {
		CheckTypeOfQuery checkTypeOfQuery = new CheckTypeOfQuery();
		boolean validateQuery = false;
		do {
			System.out.println(
					"Please select an operation to perform\n1. Write Queries\r\n2. Export\r\n3. Data Model\r\n4. Analytics\r\n5. Logout");
			int ch = sc.nextInt();
			switch (ch) {
			case 1:
				Scanner scanner = new Scanner(System.in);
				System.out.println("inside case1");
				String query;
				System.out.println("Enter valid query");
				query = scanner.nextLine();
				QueryValidator queryValidator = new QueryValidator();
				validateQuery = queryValidator.validateQuery(query);
				System.out.println("Query Validation"+validateQuery);
				if(validateQuery) {
					checkTypeOfQuery.checkTypeOfQuery(query);
				}
				break;
			case 2:
				// code for exporting the dumps
				break;
			case 3:
				// code for returning the data model
				break;
			case 4:
				// code for analytics
				break;
			case 5:
				System.out.println("Logging out. Thank you!");
				System.exit(0);
				break;
			}
		} while (true);
	}
}
