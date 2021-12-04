package com.csci5408.centdb;

import com.csci5408.centdb.services.UserService;

import java.io.IOException;
public class CentDBMain {

	public static void main(String[] args) throws IOException {

		UserService userInterface = new UserService();
		userInterface.userAccessControl();
	}
}
