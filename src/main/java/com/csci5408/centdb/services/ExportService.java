package com.csci5408.centdb.services;

import com.csci5408.centdb.model.Column;
import com.csci5408.centdb.model.Metadata;
import com.csci5408.centdb.model.util.Database;
import com.csci5408.centdb.persistence.IFileReader;
import com.csci5408.centdb.persistence.impl.FileReader;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ExportService {
    IFileReader fileReader;

    public ExportService() {
        this.fileReader = new FileReader();
    }
    private String buildCreateQuery(Metadata tableInfo){
        StringBuilder createQuery = new StringBuilder(String.format("--\n" +
                "-- Table structure for table %s\n" +
                "--\n"+
                "DROP TABLE IF EXISTS %s;\n"+
                "CREATE TABLE %s (",tableInfo.getTableName(),tableInfo.getTableName(),tableInfo.getTableName()));
        List<Column> columns = tableInfo.getColumns();
        for (int i=0;i<columns.size();i++) {
            createQuery.append("\n"+columns.get(i).getName()+" "+columns.get(i).getType());
            if(Objects.nonNull(columns.get(i).getConstraint())){
                createQuery.append(" "+columns.get(i).getConstraint());
            }
            if(i!=columns.size()-1){
                createQuery.append(String.format(","));
            }
        }
        createQuery.append(String.format(");\n"));
        return createQuery.toString();
    }
    private String buildInsertQuery(Metadata tableInfo){
        StringBuilder insertQuery = new StringBuilder(String.format("--\n" +
                "-- Dumping data for table %s\n" +
                "--\n"+
                "LOCK TABLES %s WRITE;\n"+
                "INSERT INTO %s VALUES (", tableInfo.getTableName(),tableInfo.getTableName(),tableInfo.getTableName()));
        List<Column> columns = tableInfo.getColumns();
        String tablePath = "";
        for (int i=0;i<columns.size();i++){
            insertQuery.append(String.format("'%s'",columns.get(i)));
            if(i!=columns.size()-1){
                insertQuery.append(String.format(","));
            }
        }
        insertQuery.append(String.format("),"));
        //add

        return insertQuery.toString();
    }
    public void createExport() throws IOException {
        List<Metadata> metadataList = fileReader.getMetadata();
        StringBuilder exportText = new StringBuilder(String.format("-- Host: localhost    Database: %s\n" +
                "---------------------------------------------------------" +
                "", Database.getDatabaseName()));
        for (Metadata metadata: metadataList) {
            exportText.append(buildCreateQuery(metadata));
            exportText.append(buildInsertQuery(metadata));
        }
    }
}
