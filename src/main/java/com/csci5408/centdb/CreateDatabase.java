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
        System.out.println("setter " + newName);
        this.name = newName;
    }

    public void createDb(String inputString) throws IOException {
        String[] inputWords = inputString.split(" ");

        String initializerString = inputWords[0] + " " + inputWords[1] + " ";
        if(initializerString.equalsIgnoreCase(CREATE_DATABASE_COMMAND)) {
            // create databases folder if it doesn't exist
            File databasesDirectory = new File(DATABASES);
            databasesDirectory.mkdir();

            // create database.txt if it doesnt exist
            File databasesMeta = new File("databases.txt");
            boolean isNewlyCreated = databasesMeta.createNewFile();
            FileWriter metaFileWriter = new FileWriter("databases.txt", true);
            if(isNewlyCreated) {
                metaFileWriter.write("Databases\n");
                metaFileWriter.flush();
            }

            //create folder with array[2] as name. if it exists, throw error
            String databasePathDir = DATABASES + "//" + inputWords[2];
            File databaseDirectory = new File(databasePathDir);
            boolean folderCreated = databaseDirectory.mkdir();
            if(!folderCreated) {
                System.out.println("This database already exists");
            } else {
                metaFileWriter.write(inputWords[2] + "\n");
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
