package com.csci5408.centdb.services;

import java.io.IOException;

public class CheckTypeOfQuery {
	String databaseName = "CantDb";
	public void checkTypeOfQuery(String query) throws IOException {
		String querySplitArray[] = query.split(" ");
		if(querySplitArray[0].equalsIgnoreCase("update")) {
			UpdateQuery updateQuery = new UpdateQuery();
			updateQuery.updateQuery(query, databaseName, true);
		}
		else if(querySplitArray[0].equalsIgnoreCase("delete") && querySplitArray[1].equalsIgnoreCase("from")) {
			DeleteQuery deleteQuery = new DeleteQuery();
			deleteQuery.deleteQuery(query, databaseName, true);
		}
	}
}
