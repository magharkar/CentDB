package com.csci5408.centdb.persistence;

import com.csci5408.centdb.model.Metadata;

import java.io.IOException;
import java.util.List;

public interface IFileReader {
    List<Metadata> getMetadata() throws IOException;

}
