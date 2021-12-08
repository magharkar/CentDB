package com.csci5408.centdb.services;

import java.io.IOException;

import com.csci5408.centdb.services.queryimplementation.CreateDatabase;
import com.csci5408.centdb.services.queryimplementation.CreateTable;
import com.csci5408.centdb.services.queryimplementation.DeleteQuery;
import com.csci5408.centdb.services.queryimplementation.InsertQuery;
import com.csci5408.centdb.services.queryimplementation.SelectQuery;
import com.csci5408.centdb.services.queryimplementation.UpdateQuery;
import com.csci5408.centdb.services.queryimplementation.UseDatabase;
import com.csci5408.centdb.services.transactions.Transactions;

public class CheckTypeOfQuery {
	String databaseName;

	public void checkTypeOfQuery(String query) throws Exception {
		String querySplitArray[] = query.split(" ");
		if (querySplitArray[0].equalsIgnoreCase("update")) {
			UpdateQuery updateQuery = new UpdateQuery();
			updateQuery.updateQuery(query, "resources\\Databases\\" + UseDatabase.getDatabaseName(), true);
		} else if (querySplitArray[0].equalsIgnoreCase("delete") && querySplitArray[1].equalsIgnoreCase("from")) {
			DeleteQuery deleteQuery = new DeleteQuery();
			deleteQuery.deleteQuery(query, "resources\\Databases\\" + UseDatabase.getDatabaseName(), true);
		} else if (querySplitArray[0].equalsIgnoreCase("use")) {
			UseDatabase useDatabase = new UseDatabase();
			useDatabase.use(query);
			System.out.println(databaseName);
		} else if (querySplitArray[0].equalsIgnoreCase("create") && querySplitArray[1].equalsIgnoreCase("database")) {
			System.out.println("inside create");
			CreateDatabase createDatabase = new CreateDatabase();
			createDatabase.createDb(query);
			System.out.println(databaseName);
		} else if (querySplitArray[0].equalsIgnoreCase("create") && querySplitArray[1].equalsIgnoreCase("table")) {
			System.out.println("inside create table");
			CreateTable createTable = new CreateTable();
			createTable.createTable(query);
		} else if (querySplitArray[0].equalsIgnoreCase("insert") && querySplitArray[1].equalsIgnoreCase("into")) {
			System.out.println("inside insert table");
			InsertQuery.insert(query, "resources\\Databases\\"+UseDatabase.getDatabaseName(),UseDatabase.getDatabaseName());
		} else if (querySplitArray[0].equalsIgnoreCase("drop")) {
			System.out.println("inside drop table");
			//drop
		} else if (querySplitArray[0].equalsIgnoreCase("select")) {
			System.out.println("inside select table");
			SelectQuery.select(query, "resources\\Databases\\" + UseDatabase.getDatabaseName());
		} else if (querySplitArray[0].equalsIgnoreCase("begin") || (querySplitArray[0].equalsIgnoreCase("start"))) {
			Transactions transaction = new Transactions();
			transaction.processTransaction(query, "resources\\Databases\\" + UseDatabase.getDatabaseName());
		}
	}
}
