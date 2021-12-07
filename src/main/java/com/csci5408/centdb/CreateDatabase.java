package com.csci5408.centdb;

import com.csci5408.enums.ColumnConstraints;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class CreateDatabase {

    static final String CREATE_DATABASE_COMMAND = "Create database ";
    static final String DATABASES = "Databases";
    private static String name;

    public static String getDatabaseName() {
        return name;
    }
    public void setDatabaseName(String newName) {
        name = newName;
    }

    public void createDb(String inputString) throws IOException {
        String[] inputWords = inputString.split(" ");

        String createCommandString = inputWords[0] + " " + inputWords[1] + " ";
        if(createCommandString.equalsIgnoreCase(CREATE_DATABASE_COMMAND)) {
            // create databases folder if it doesn't exist
            File databasesDirectory = new File(DATABASES);
            if(!databasesDirectory.exists())
            {
                databasesDirectory.mkdir();
            }

            // create database.txt if it doesn't exist
            File databasesMeta = new File("resources/databases.txt");
            boolean isNewlyCreated = !databasesMeta.exists();
            FileWriter metaFileWriter = new FileWriter("resources/databases.txt", true);
            if(isNewlyCreated) {
                metaFileWriter.write("Databases\n");
                metaFileWriter.flush();
            }

            //create folder with array[2] as name. if it exists, throw error
            String databasePathDir = "resources/" + DATABASES + "//" + inputWords[2];
            System.out.println(databasePathDir);
            File databaseDirectory = new File(databasePathDir);
            boolean folderCreated = databaseDirectory.mkdir();
            if(!folderCreated) {
                System.out.println("This database already exists");
            } else {
                metaFileWriter.write("Database|" + inputWords[2] + "\n");
                metaFileWriter.close();
                // create db log file
                File databaseLogFile = new File(databasePathDir + "//" + inputWords[2] + "-log.txt");
                databaseLogFile.createNewFile();
                // create db metadata file
                File databaseMetaFile = new File(databasePathDir + "//" + inputWords[2] + "-meta.txt");
                databaseMetaFile.createNewFile();
                name = inputWords[2];
            }
        } else  {
            System.out.println("Wrong Syntax");
        }
    }
}
