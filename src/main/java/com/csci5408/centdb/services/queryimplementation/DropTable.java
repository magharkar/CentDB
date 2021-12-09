package com.csci5408.centdb.services.queryimplementation;

import com.csci5408.centdb.model.Metadata;
import com.csci5408.centdb.model.Query;
import com.csci5408.centdb.persistence.IFileReader;
import com.csci5408.centdb.persistence.IQueryDao;
import com.csci5408.centdb.persistence.impl.FileReader;
import com.csci5408.centdb.persistence.impl.QueryDao;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DropTable {
    static IFileReader fileReader;
    static IQueryDao queryDao;

    public DropTable() {
        this.fileReader = new FileReader();
        this.queryDao = new QueryDao();
    }

    public static boolean dropTable(String query) throws IOException {
        Pattern pattern = Pattern.compile("drop\\ *table\\s.*");
        Matcher matcher = pattern.matcher(query);
        if(matcher.matches()){

            String[] querySplit = query.split("table");
            if(querySplit.length >1){
                Query dropQuery = new Query.QueryBuilder()
                        .tableName(querySplit[1].trim())
                        .build();
                //check if table exists
                List<Metadata> metadataList = fileReader.getMetadata();
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
