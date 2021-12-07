package com.csci5408.centdb.model.util;

import java.util.Arrays;
import java.util.List;

public final class Constants {
    public static final String DELIMITER = "|";
    public static final String TRANSACTION_LOG_HEADER = "Transaction Id| Transaction Number|Previous Pointer| Next Pointer| Operation| Table Name| Row Id| Column Name| Before Value| After Value";
    public static final List<String> SECURITY_QUESTIONS = Arrays.asList("What elementary school did you attend?","Who was your childhood hero?");
    public static final String CRASH_REPORT_PATH = "resources/CrashReports.txt";
    public static final String EVENT_LOG_PATH = "resources/EventLogs.txt";
    public static final String TRANSACTION_LOG_PATH = "resources/%s/TransactionLogs.txt";
}
