package com.csci5408.centdb;

import com.csci5408.centdb.encryption.Aes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInterface {

	static Scanner sc = new Scanner(System.in);

	public void userAccessControl() throws IOException {
		String securityAnswer;
		String secretKey = System.getenv("SECRET_KEY");
		FileWriter userProfileWriter = new FileWriter("UserPersistence/User_Profile.txt", true);
		List<String> securityQuestions = new ArrayList<>();

		securityQuestions.add("What elementary school did you attend?");
		securityQuestions.add("Who was your childhood hero?");

		ACCESS: do {
			System.out.println("Please enter an option to continue\n1. Register\n2. Login\n3. Exit");
			int choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {

			case 1:
				try {
					System.out.println("Enter a userId");
					String userId = sc.nextLine();
					if (checkIfUserExists(userId)) {
						System.out.println("User Id already exists.. please register with new user Id or login");
						continue ACCESS;
					}
					// Use string format
					int lengthValidation = 8;

					System.out.println("Enter password(min length : 8 characters)");
					String password = sc.nextLine();
					while (password.length() < 8) {
						System.out.println("please enter a password with minimum 8 characters");
						password = sc.nextLine();
					}
					userProfileWriter.write("\n");
					userProfileWriter.write(userId + "|" + Aes.encrypt(password,secretKey));
					System.out.println("Please answer the following security questions...");

					for (String securityQuestion : securityQuestions) {
						System.out.println(securityQuestion);
						securityAnswer = sc.nextLine();
						userProfileWriter.write("|" + securityAnswer);
					}
					System.out.println("User Registered Succesfully!!!!");
				} finally {
					userProfileWriter.close();
				}
				break;

			case 2:
				BufferedReader br = new BufferedReader(new FileReader("UserPersistence/User_Profile.txt"));
				// isUserPresent - Recommendation for better naming for local variables.
				boolean found = false;
				String[] userDetails = null;
				USERID: while (true) {
					System.out.println("User Id:");
					String loginId = sc.nextLine();
					String line;
					while ((line = br.readLine()) != null) {
						String[] str = line.split("\\|");
						// Check the length of str before accessing first index - Prevent Index out of bounce exception.
						if ("userId".equals(str[0]))
							continue;
						if (str[0].equals(loginId)) {
							userDetails = str;
							found = true;
							break;
						}
					}
					if (!found) {
						System.out.println("User Id doesn't exist.. please enter a valid User Id");
						continue USERID;
					} else {
						break USERID;
					}
				}
				// Have initialization of local variables at the top
				// Use isPasswordValid instead of weird names for local variables.
				boolean isNotValid = false;
				do {
					System.out.println("Password: ");
					String loginPassword = sc.nextLine();
					// Might result in an NPE, check ofr the size of the list and a null check before accessing the index 1
					final String decryptedPassword = Aes.decrypt(userDetails[1], secretKey);
					if (!loginPassword.equals(decryptedPassword)) {
						System.out.println("Invalid password.. please enter a valid password");
						isNotValid = true;
					}
				} while (isNotValid);

				boolean checkNotValid = false;
				int i = 1;
				// for (int i = 0; i < securityQuestions.length(); i++) { // usage: securityQuestions.get(i) }
				for (String securityQuestion : securityQuestions) {
					checkNotValid = false;
					do {
						System.out.println(securityQuestion);
						String securityAnswercheck = sc.nextLine();
						if (i == 1) {
							checkNotValid = userDetails[2].equals(securityAnswercheck) ? false : true;
						} else {
							checkNotValid = userDetails[3].equals(securityAnswercheck) ? false : true;
						}
						if (checkNotValid) {
							System.out.println("The answer is incorrect..please enter the correct answer");
						}
					} while (checkNotValid);
					i++;
				}
				System.out.println("Successfully logged in !!!!");
				userOperations();
				break;

			case 3:
				System.out.println("Thank you!!!");
				System.exit(0);
			}
		} while (true);
	}
	private static boolean checkIfUserExists(String userId) throws IOException {

		try (BufferedReader br = new BufferedReader(new FileReader("UserPersistence/User_Profile.txt"))) {
			boolean exists = false;
			String line;
			while ((line = br.readLine()) != null) {
				String[] str = line.split("\\|");
				if (userId.equals(str[0])) {
					exists = true;
					break;
				}
			}
			return exists;
		}
	}

	private static void userOperations() {

		do {
			System.out.println(
					"Please select an operation to perform\n1. Write Queries\r\n2. Export\r\n3. Data Model\r\n4. Analytics\r\n5. Logout");
			int ch = sc.nextInt();
			switch (ch) {
			case 1:
				// code for writing queries
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
				System.out.println("Logging out..........Thank you!!!");
				System.exit(0);
				break;
			}
		} while (true);
	}
}
