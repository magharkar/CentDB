package com.csci5408.centdb.services;

import com.csci5408.centdb.persistence.impl.QueryDao;


public class QueryService {
    private QueryDao queryDao;

    public QueryService() {
        this.queryDao = new QueryDao();
    }


}
