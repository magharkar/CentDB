package com.csci5408.centdb;

import com.csci5408.centdb.services.UserService;

import java.io.IOException;
import java.util.Scanner;

public class CentDBMain {

	public static void main(String[] args) throws IOException {
		Menu menu = new Menu();
		menu.userAccessControl();
	}

}
