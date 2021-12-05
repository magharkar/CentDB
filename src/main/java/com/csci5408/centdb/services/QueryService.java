package com.csci5408.centdb.services;

import com.csci5408.centdb.persistence.impl.QueryDao;

import java.util.Scanner;

public class QueryService {
    private QueryDao queryDao;
    static Scanner sc;
    public QueryService() {
        this.queryDao = new QueryDao();
    }

    public void queryProcessor(String query){
        do{

        }while(true);
    }

}
