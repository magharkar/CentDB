package com.csci5408.centdb.services;

import com.csci5408.centdb.services.queryimplementation.CreateDatabase;
import com.csci5408.centdb.services.queryimplementation.DeleteQuery;
import com.csci5408.centdb.services.queryimplementation.UpdateQuery;
import com.csci5408.centdb.services.queryimplementation.UseDatabase;

import java.io.IOException;

public class CheckTypeOfQuery {
	String databaseName;
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
		else if(querySplitArray[0].equalsIgnoreCase("create") && querySplitArray[1].equalsIgnoreCase("database")){
			CreateDatabase createDatabase = new CreateDatabase();
			createDatabase.createDb(query);
			CreateDatabase.getDatabaseName();
		}
		else if(querySplitArray[0].equalsIgnoreCase("use")) {
			UseDatabase useDatabase = new UseDatabase();
			databaseName = useDatabase.use(query);
			System.out.println(databaseName);
			
		}
	}
}
