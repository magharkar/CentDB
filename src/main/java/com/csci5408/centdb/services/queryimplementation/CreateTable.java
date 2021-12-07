package com.csci5408.centdb.services.queryimplementation;
import com.csci5408.enums.ColumnConstraints;
import com.csci5408.enums.ColumnDataTypes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateTable {
    static  String CREATE_TABLE_COMMAND = "Create table ";
    static  String SPACE = " ";
    static  String DELIMITER = "|";

    ArrayList<String> columnNames = new ArrayList<>();
    ArrayList<String[]> columns = new ArrayList<>();


    public void create(String currentDatabase, String tableName, String[] allInputWords,
                       String inputString) throws IOException {
        // 1. Create table file with name array[2]. throw error if it exists
        String databaseMetaPath = "Databases" + "//" + currentDatabase + "//" + currentDatabase + "-meta.txt";
        String tableFilePath = "Databases" + "//" + currentDatabase + "//" + tableName + ".txt";
        File tableFile = new File(tableFilePath);
        //boolean isFileCreated = tableFile.createNewFile();
        if(tableFile.exists()) {
            System.out.println("This table already exists\n");
        }
        else {
            System.out.println(inputString);
            boolean success = validateColumns(inputString, tableName);
            if(success) {
                FileWriter databaseMetaFileWriter = new FileWriter(databaseMetaPath, true);
                FileWriter tableFileWriter = new FileWriter(tableFilePath, true);
                databaseMetaFileWriter.write("Table" + DELIMITER + tableName + "\n" );
                databaseMetaFileWriter.flush();
                for(int i = 0; i< columns.size(); i++) {
                    String[] column = columns.get(i);
                    tableFileWriter.write(column[0] + "|");
                    databaseMetaFileWriter.write(column[0] + "|" + column[1] + "|" + column[2] + "\n");
                }
                tableFileWriter.close();
                databaseMetaFileWriter.close();
            }

            //   validateAndSetColumns(inputString, tableFilePath, databaseMetaPath, tableName);

        }

    }

    public boolean validateColumns(String inputString, String table) {

        int startParanthesisIndex = inputString.indexOf('(');
        int endParanthesisIndex = inputString.lastIndexOf(')');
        String columnData = inputString.substring(startParanthesisIndex + 1, endParanthesisIndex).trim();
        System.out.println(columnData);
        String[] individualColumns = columnData.split(",");
        ArrayList<String> individualColumnArray = new ArrayList<>(Arrays.asList(individualColumns));
        for(int i = 0; i < individualColumnArray.size(); i++) {
            String test = individualColumnArray.get(i).trim();
            String[] columnWords = test.split(" ");
            if(columnWords.length < 2) {
                System.out.println("Column syntax is incorrect");
                return false;
            } else {
                boolean isColumnNameCorrect = false;
                boolean isColumnTypeCorrect = false;
                boolean isColumnConstraintCorrect = true;
                String columnConstraint = "";

                String columnName = columnWords[0];
                //write columnwords[0] to table file
                if(columnNames.contains(columnName)) {
                    System.out.println("This column name already exists");
                    return false;
                } else {
                    columnNames.add(columnName);
                    isColumnNameCorrect = true;
                }

                String columnDataType = columnWords[1];
                //validate columnwords[1]
                if(!columnDataType.contains("(") && !columnDataType.contains(")")) {
                    boolean isTypeMatched = false;
                    for (ColumnDataTypes dataType : ColumnDataTypes.values()) {
                        if(columnDataType.equalsIgnoreCase(dataType.toString())) {
                            isTypeMatched = true;
                        }
                    }
                    if(isTypeMatched) {
                        //store type data in database-meta file
                        isColumnTypeCorrect = true;
                    } else {
                        System.out.println("Data type is wrong");
                        return false;
                    }
                } else if(columnDataType.contains("(") && columnDataType.indexOf(")") == columnDataType.length() - 1) {
                    //validate varchar, varbinary, enum, set
                    String VARCHAR = "varchar";
                    String VARBINARY = "varbinary";

                    int startParanthesis = columnDataType.indexOf("(");
                    String type = columnDataType.substring(0,startParanthesis);
                    String size = columnDataType.substring(startParanthesis + 1, columnDataType.length() - 1);
                    if(type.equalsIgnoreCase(VARBINARY) || type.equalsIgnoreCase(VARCHAR)) {
                        int integerSize = Integer.parseInt(size);
                        if(integerSize >= 0 && integerSize <= 255) {
                            //store type data in database-meta file
                            isColumnTypeCorrect = true;
                        } else {
                            System.out.println("error");
                            return false;
                        }
                    }
                } else {
                    System.out.println("error");
                    return false;
                }


                if(columnWords.length == 3)  {
                    columnConstraint = columnWords[2];
                    boolean isConstraintMatched = false;
                    for (ColumnConstraints columnConstraintEnum : ColumnConstraints.values()) {
                        if(columnConstraint.equalsIgnoreCase(columnConstraintEnum.toString())) {
                            isConstraintMatched = true;
                        }
                    }
                    if (!isConstraintMatched) {
                        isColumnConstraintCorrect = false;
                        return false;
                    }
                    //validate columnwords[2]
                }
                if(isColumnNameCorrect && isColumnTypeCorrect && isColumnConstraintCorrect) {
                    String[] column = {columnName, columnDataType, columnConstraint};
                    columns.add(column);
//                    tableFileWriter.write(columnName + "|");
//                    databaseMetaFileWriter.write(columnName + "|" + columnDataType + "" +
//                            "|" + columnConstraint + "\n");
                }


            }
        }
        return individualColumnArray.size() == columns.size();

    }

    public void createTable(String input, String currentDatabase) throws IOException {
        String[] inputWords = input.split(" ");
        String createSyntax = inputWords[0] + SPACE + inputWords[1] + SPACE;
        //  CreateDatabase db = new CreateDatabase();

        if(currentDatabase == null) {
            System.out.println("Please perform use database query first");
        } else {
            if(createSyntax.equalsIgnoreCase(CREATE_TABLE_COMMAND) &&
                    input.lastIndexOf(')') == input.length() - 1) {
                CreateTable createTable = new CreateTable();
                String tableName = inputWords[2];
                if(tableName.indexOf('(') != -1) {
                    tableName = tableName.substring(0, tableName.indexOf('('));
                }
                createTable.create(currentDatabase, tableName, inputWords, input);
            }  else  {
                System.out.println("Wrong Syntax");
            }
        }
    }
}
