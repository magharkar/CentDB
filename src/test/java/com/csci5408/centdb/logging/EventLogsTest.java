package com.csci5408.centdb.logging;

import com.csci5408.centdb.model.Transaction;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventLogsTest {
    @Test
    public void transcationLogTest() throws IOException {
        Transaction transaction = new Transaction("341","101","Null","352","START","**Start transaction**","","","","");
        Transaction transaction2 = new Transaction("352","101","341","363","UPDATE","PRODUCT","1551-QC","prodcuctName","tampons","pads");
        EventLogs.createTransactionLog(transaction2,"CentDB");
        File file = new File("resources/CentDB/TransactionLogs.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader("resources/CentDB/TransactionLogs.txt"));
        assertTrue(file.exists());
    }
}
