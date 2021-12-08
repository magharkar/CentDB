package com.csci5408.centdb;

import java.io.IOException;

import com.csci5408.centdb.services.UserService;
public class CentDBMain {

	public static void main(String[] args) throws IOException {

		UserService userInterface = new UserService();
		userInterface.userAccessControl();
	}
}
