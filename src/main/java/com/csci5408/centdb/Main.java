package com.csci5408.centdb;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.csci5408.centdb.services.CreateDatabase;
import com.csci5408.centdb.services.UseDatabase;

public class Main {
    public static void main(String args[]) throws IOException {
        CreateDatabase database = new CreateDatabase();
        CreateTable table = new CreateTable();
        UseDatabase useDb = new UseDatabase();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Query 1:");
        String input = scanner.nextLine();

        String[] inputWords = input.split(" ");
        if(inputWords.length == 3 ) {
            database.createDb(input);
        } else if( inputWords.length == 2) {
            useDb.use(input);
        } else if (inputWords.length > 3) {
            table.createTable(input);
        } else {
            System.out.println("Wrong Syntaxxxx");
        }

        System.out.println("Query 2:");
        String input1 = scanner.nextLine();

        String[] inputWords1 = input1.split(" ");
        System.out.println(inputWords1.length);
        if(inputWords1.length == 3 ) {
            database.createDb(input1);
        } else if( inputWords1.length == 2) {
            useDb.use(input1);
        } else if (inputWords1.length > 3) {
            table.createTable(input1);
        } else {
            System.out.println("Wrong Syntaxooooo");
        }
        System.out.println(database.getDatabaseName());
        System.out.println("Query 3:");

        String input2 = scanner.nextLine();

        String[] inputWords2 = input2.split(" ");
        if(inputWords2.length == 3 ) {
            database.createDb(input2);
        } else if( inputWords2.length == 2) {
            useDb.use(input2);
        } else if (inputWords2.length > 3) {
            table.createTable(input2);
        } else {
            System.out.println("Wrong Syntax");
        }

    }
}
