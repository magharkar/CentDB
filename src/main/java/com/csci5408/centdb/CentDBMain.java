package com.csci5408.centdb;

import com.csci5408.centdb.services.UserService;

import java.io.IOException;
import java.util.Scanner;

public class CentDBMain {

	public static void main(String[] args) throws IOException {
		userAccessControl();
	}
	private static void userAccessControl() throws IOException {
		Scanner sc = new Scanner(System.in);
		UserService userService = new UserService();
		ACCESS: do {
			System.out.println("Please enter an option to continue\n1. Register\n2. Login\n3. Exit");
			int choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {

				case 1:
					if (!userService.userRegistration()) {
						continue ACCESS;
					}
					else{
						System.out.println("User registered successfully!");
					}
					break;

				case 2:
					if(!userService.userLogin()){
						continue ACCESS;
					}
					else{
						System.out.println("User logged in successfully!");
					}
					databaseOperations();
					break;

				case 3:
					System.out.println("Thank you!");
					System.exit(0);
			}
		} while (true);
	}
	private static void databaseOperations() {
		Scanner sc = new Scanner(System.in);
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
					System.out.println("Logging out. Thank you!");
					System.exit(0);
					break;
			}
		} while (true);
	}
}
