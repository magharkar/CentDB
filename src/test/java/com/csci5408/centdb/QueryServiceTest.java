package com.csci5408.centdb;

import com.csci5408.centdb.services.QueryService;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class QueryServiceTest {
    @Test
    public void dropTableTest() throws IOException {
        QueryService queryService = new QueryService();
        assertTrue(queryService.dropTable("drop table orders"));
    }
}
