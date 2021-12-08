package com.csci5408.centdb.persistence;

import java.io.IOException;
import java.util.List;

import com.csci5408.centdb.model.Metadata;
import com.csci5408.centdb.model.Query;

public interface IQueryDao {
    boolean dropTable(Query query);
    List<Metadata> getMetadata() throws IOException;
}
