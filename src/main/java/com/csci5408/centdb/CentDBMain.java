package com.csci5408.centdb;

import java.io.IOException;
public class CentDBMain {

	public static void main(String[] args) throws IOException {

		UserInterface userInterface = new UserInterface();
		userInterface.userAccessControl();
	}
}
