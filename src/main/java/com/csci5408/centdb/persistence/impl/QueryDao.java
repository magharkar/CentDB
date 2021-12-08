package com.csci5408.centdb.persistence.impl;

import com.csci5408.centdb.model.Column;
import com.csci5408.centdb.model.Metadata;
import com.csci5408.centdb.model.Query;
import com.csci5408.centdb.persistence.IQueryDao;
import com.csci5408.centdb.services.queryimplementation.UseDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

import static com.csci5408.centdb.model.util.Constants.DELIMITER;
import static com.csci5408.centdb.model.util.Constants.METADATA_PATH;

public class QueryDao implements IQueryDao {

    @Override
    public boolean dropTable(Query query) {
        try{
            File file = new File(String.format("resources/Databases/%s/%s.txt",UseDatabase.getDatabaseName(),query.getTableName()));
            if(file.delete()){
                System.out.println(String.format("Table: %s has been removed from %s",query.getTableName(),UseDatabase.getDatabaseName()));
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


    @Override
    public List<Metadata> getMetadata() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(String.format(METADATA_PATH, UseDatabase.getDatabaseName(),UseDatabase.getDatabaseName())));

        List<Metadata> metadataList = new ArrayList<>();
        List<Column> columns = new ArrayList<>();
        String tableName = null;

        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
            List<String> strings = Arrays.asList(line.split(Pattern.quote(DELIMITER)));
            if (strings.get(0).trim().equalsIgnoreCase("table")) {
                if (Objects.nonNull(tableName) && columns.size() > 0) {
                    add(metadataList, tableName, columns);
                    columns = new ArrayList<>();
                }
                tableName = strings.get(1);
            } else {
                Column column = new Column();
                column.setName(strings.get(0).trim());
                column.setType(strings.get(1).trim());
                if(strings.size()>2){
                    column.setConstraint(strings.get(2).trim());
                }
                columns.add(column);
            }
        }
        add(metadataList, tableName, columns);
        return metadataList;
    }

    private void add(List<Metadata> metadataList, String tableName, List<Column> columns) {
        Metadata tableMetadata = new Metadata();
        tableMetadata.setTableName(tableName);
        tableMetadata.setColumns(columns);
        metadataList.add(tableMetadata);
    }
}
