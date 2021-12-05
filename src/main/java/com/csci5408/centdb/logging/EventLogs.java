package com.csci5408.centdb.logging;

import com.csci5408.centdb.model.Transaction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.csci5408.centdb.model.util.Constants.*;

public class EventLogs {

    public static void createTransactionLog(Transaction transaction, String databaseName) throws IOException {
        FileWriter fileWriter;
        File file = new File(String.format("resources/%s/TransactionLogs.txt",databaseName));
        if(!file.exists()){
            file.createNewFile();
        }
        fileWriter = new FileWriter(String.format("resources/%s/TransactionLogs.txt",databaseName),true);
        fileWriter.write(String.format(transactionLogHeader));
        fileWriter.write(formTransactionLogRow(transaction));
        fileWriter.close();
    }
    private static String formTransactionLogRow(Transaction transaction){
        return String.format("\n"+transaction.getTransactionId()+DELIMITER
                            + transaction.getTransactionNumber()+DELIMITER
                + transaction.getPreviousPtr()+DELIMITER
                + transaction.getNextPtr()+DELIMITER
                + transaction.getTableName()+DELIMITER
                + transaction.getRowId()+DELIMITER
                + transaction.getBeforeVal()+DELIMITER
                + transaction.getAfterVal()
        );
    }
}
