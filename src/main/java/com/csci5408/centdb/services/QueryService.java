package com.csci5408.centdb.services;

import com.csci5408.centdb.model.Metadata;
import com.csci5408.centdb.model.Query;
import com.csci5408.centdb.model.util.Operation;
import com.csci5408.centdb.persistence.IQueryDao;
import com.csci5408.centdb.persistence.impl.QueryDao;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryService {
    private IQueryDao queryDao;
    static Scanner sc;
    public QueryService() {
        sc = new Scanner(System.in);
        this.queryDao = new QueryDao();
    }

    public void queryProcessor() throws IOException {
        do{
            System.out.print("MySQL>");
            String query = sc.nextLine();
            //Add logic for query distinguish
            //Switch case for query operation
            Operation operation = Operation.DROP;
            if(query.trim().toLowerCase(Locale.ROOT).equals("exit")){
                operation = Operation.EXIT;
            }
            switch (operation){
                case DROP:
                    dropTable(query);
                    break;
                case DELETE:
                    //logic
                    break;
                case INSERT:
                    //logic
                    break;
                case SELECT:
                    //logic
                    break;
                case UPDATE:
                    //logic
                    break;
                case EXIT:
                    return;
                default:
                    System.out.println("Unrecognised command!");
                    break;
            }
        }while(true);
    }
    public boolean dropTable(String query) throws IOException {
        Pattern pattern = Pattern.compile("drop\\ *table\\s.*");
        Matcher matcher = pattern.matcher(query);
        if(matcher.matches()){

           String[] querySplit = query.split("table");
           if(querySplit.length >1){
               Query dropQuery = new Query.QueryBuilder()
                       .tableName(querySplit[1].trim())
                       .build();
               //check if table exists
               List<Metadata> metadataList = queryDao.getMetadata();
               for (Metadata metadata: metadataList) {
                   if(metadata.getTableName().equals(dropQuery.getTableName())){
                       return queryDao.dropTable(dropQuery);
                   }
               }
               System.out.println("Table not found!");
               return false;
           }
        }
        return false;
    }

}
