package com.csci5408.centdb;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UseDatabase {
    static final String USE_DATABASE_COMMAND = "use";

    public void use(String input) {
        CreateDatabase db = new CreateDatabase();
        String[] inputWords = input.split(" ");
        // use database syntax will have length 2
        String initializerString = inputWords[0];
        System.out.println(initializerString);
        if(initializerString.equalsIgnoreCase(USE_DATABASE_COMMAND)) {
            // set path of the db with name array[1]. if it doesn't exist, throw error
            Path path = Paths.get("Databases//" + inputWords[1]);
            if(!Files.exists(path)) {
                System.out.println("This database does not exist");
            }
            else {
                System.out.println("Setting database name");
                db.setDatabaseName(inputWords[1]);
            }
        }  else  {
            System.out.println("Wrong Syntax");
        }
    }
}
