package com.csci5408.centdb.persistence;

import java.io.IOException;
import java.util.List;

import com.csci5408.centdb.model.Metadata;

public interface IFileReader {
    List<Metadata> getMetadata() throws IOException;
    List<String> getColumnValues(String tablePath) throws IOException;
    boolean createFile(String path) throws IOException;

}
