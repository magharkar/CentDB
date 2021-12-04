package com.csci5408.centdb;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class UpdateQuery {
	public ArrayList<String> updateQuery(String query, String folder, boolean persistentFileUpdate) throws IOException {
		ArrayList<String> columns = new ArrayList<>();
		ArrayList<String> data = new ArrayList<>();
		String line;
		String[] columnSplit = null;
		String tableName = "";
		String constraint = "";
		String whereCondition = "";
		String databaseName = "";
		String tableNameLog = "";
		int count = 0;
		int position = 0;
		int positionWhere = 0;

		if (query.toLowerCase().contains("update ")) {
			System.out.println("An update query identified!");

			try {

				String regex = "update(.*?)set(.*?)";
				Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(query);
				while (matcher.find()) {
					tableName = (matcher.group(1).trim());
					databaseName = tableName.split("\\.")[0];
					tableNameLog = tableName.split("\\.")[1];
					tableName = folder + tableName.replaceAll("\\.", "\\\\") + ".txt";
				}

				String regex1 = "set(.*?)where(.*?)";
				Pattern pattern1 = Pattern.compile(regex1, Pattern.CASE_INSENSITIVE);
				Matcher matcher1 = pattern1.matcher(query);
				while (matcher1.find()) {
					constraint = (matcher1.group(1).trim());
				}

				String[] string_where = query.split("where");
				whereCondition = string_where[1].trim();

				File f = new File(tableName);
				if (f.exists()) {
					BufferedReader br = new BufferedReader(new FileReader(tableName));
					StringTokenizer st1 = new StringTokenizer(br.readLine(), "\t");
					while (st1.hasMoreTokens()) {
						columns.add(st1.nextToken());
						count++;
					}

					if (columns.size() > 0) {
						for (String column : columns) {
							columnSplit = column.split("\\|");
						}
						for (int i = 0; i < Objects.requireNonNull(columnSplit).length; i++) {
							if (columnSplit[i].trim().equals(constraint.split("=")[0].trim())) {
								position = i;
							}
							if (columnSplit[i].trim().equals(whereCondition.split("=")[0].trim())) {
								positionWhere = i;
							}
						}

						while ((line = br.readLine()) != null) {
							StringTokenizer st2 = new StringTokenizer(line, "\t");
							for (int i = 0; i < count; i++) {
								if (st2.hasMoreTokens()) {
									data.add(st2.nextToken());
								} else {
									data.add("");
								}
							}
						}

						String where_value = whereCondition.split("=")[1].trim();
						where_value = where_value.substring(1, where_value.length() - 1);
						String set_value = constraint.split("=")[1].trim();
						set_value = set_value.substring(1, set_value.length() - 1);

						for (int i = 0; i < data.size(); i++) {
							if (data.get(i).split("\\|")[positionWhere].trim().equals(where_value)) {
								data.set(i, data.get(i).replaceAll(data.get(i).split("\\|")[position], set_value));
							}
						}

						if (persistentFileUpdate) {
							FileWriter writer = new FileWriter(tableName);
							writer.write(columns.remove(0) + "\n");
							for (String datum : data) {
								writer.write(datum + "\n");
							}
							writer.close();
						}
					} else {
						System.out.println("No data in Table: " + tableName);
					}
					br.close();
				} else {
					System.out.println(tableName + ": Table doesn't exist");
				}

				QueryLogs queryLogs = new QueryLogs();
				queryLogs.createQueryLog(folder, "Update","Sucess", databaseName, tableNameLog, constraint.split("=")[0],
						constraint, "where " + whereCondition);

			} catch (Exception e) {
				QueryLogs queryLogs = new QueryLogs();
				queryLogs.createQueryLog(folder, "Update","Failure", databaseName, tableNameLog, constraint.split("=")[0],
						constraint, "where " + whereCondition);
				System.out.println(e);
			}
		} else {
			System.out.println("An update query unidentified!");
		}
		System.out.println("Update Query Completed!");
		return data;
	}

}
