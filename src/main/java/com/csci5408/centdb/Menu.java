package com.csci5408.centdb;

import com.csci5408.centdb.model.util.Database;
import com.csci5408.centdb.services.ExportService;
import com.csci5408.centdb.services.QueryService;
import com.csci5408.centdb.services.UserService;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Menu {
    static Scanner sc;

    public Menu() {
        sc = new Scanner(System.in);
    }
    public static void userAccessControl() throws IOException {
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
    public static void databaseOperations() throws IOException {
        QueryService queryService = new QueryService();
        ExportService exportService = new ExportService();
        Database.setDatabaseName("CentDB");
        do {
            System.out.println(
                    "Please select an operation to perform\n1. Write Queries\r\n2. Export\r\n3. Data Model\r\n4. Analytics\r\n5. Logout");
            int ch = sc.nextInt();
            switch (ch) {
                case 1:
                    checkDatabase();
                    queryService.queryProcessor();
                    // code for writing queries
                    break;
                case 2:
                    checkDatabase();
                    exportService.createExport();
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
                    return;
            }
        } while (true);
    }
    private static void checkDatabase(){
        if(Objects.nonNull(Database.getDatabaseName())){
            System.out.println("Database selected: "+ Database.getDatabaseName());
        }
        else{
            System.out.println("Database not selected!");
        }
    }
}
