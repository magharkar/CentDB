package com.csci5408.centdb.persistence.impl;

import com.csci5408.centdb.model.Column;
import com.csci5408.centdb.model.Metadata;
import com.csci5408.centdb.model.Query;
import com.csci5408.centdb.persistence.IQueryDao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

import static com.csci5408.centdb.model.util.Constants.DELIMITER;

public class QueryDao implements IQueryDao {
    final String databaseName = "CentDB";

    @Override
    public boolean dropTable(Query query) {
        try{
            File file = new File(String.format("resources/Databases/%s/%s.txt",databaseName,query.getTableName()));
            if(file.delete()){
                System.out.println(String.format("Table: %s has been removed from %s",query.getTableName(),databaseName));
                return true;
            }
            else{
                System.out.println("Failed to drop the table.");
                return false;
            }
        }
        catch (Exception exception){
            throw exception;
        }
    }




}
