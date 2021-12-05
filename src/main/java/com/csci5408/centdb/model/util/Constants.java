package com.csci5408.centdb.model.util;

import java.util.Arrays;
import java.util.List;

public final class Constants {
    public static final String DELIMITER = "|";
    public static final String transactionLogHeader = "Transaction Id| Transaction Number|Previous Pointer| Next Pointer| Operation| Table Name| Row Id| Column Name| Before Value| After Value";
    public static final List<String> securityQuestions = Arrays.asList("What elementary school did you attend?","Who was your childhood hero?");
    
}
