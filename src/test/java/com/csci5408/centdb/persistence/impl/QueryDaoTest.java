package com.csci5408.centdb.persistence.impl;

import com.csci5408.centdb.persistence.IQueryDao;
import com.csci5408.centdb.services.queryimplementation.UseDatabase;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class QueryDaoTest {

    @Test
    public void test() throws IOException {
        UseDatabase useDatabase = new UseDatabase();
        useDatabase.use("use db");
        IQueryDao queryDao = new QueryDao();
        queryDao.getMetadata();
    }
}
