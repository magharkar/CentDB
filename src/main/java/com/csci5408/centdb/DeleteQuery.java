package com.csci5408.centdb;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class DeleteQuery {
	public void deleteQuery(String query, String folder, boolean persistentFileUpdate) throws IOException {
		ArrayList<String> columns = new ArrayList<>();
		ArrayList<String> data = new ArrayList<>();
		String tableName = "";
		String whereCondition = "";
		String[] columnSplit = null;
		String line;
		String databaseName = "";
		String tableNameLog = "";
		int position = 0;
		int count = 0;

		if (query.toLowerCase().contains("delete from ")) {
			System.out.println("Delete query identified!");

			try {
				String regex = "delete from(.*?)where(.*?)";
				Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(query);
				while (matcher.find()) {
					tableName = (matcher.group(1).trim());
					databaseName = tableName.split("\\.")[0];
					tableNameLog = tableName.split("\\.")[1];
					tableName = folder + tableName.replaceAll("\\.", "\\\\") + ".txt";
				}

				String[] string_where = query.split("where");
				whereCondition = string_where[1].trim();
				String where_value = whereCondition.split("=")[1].trim();
				where_value = where_value.substring(1, where_value.length() - 1);
				String where_column = whereCondition.split("=")[0].trim();

				File file = new File(tableName);
				if (file.exists()) {
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
							if (columnSplit[i].trim().equals(where_column)) {
								position = i;
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

						for (int i = 0; i < data.size(); i++) {
							if (data.get(i).split("\\|")[position].trim().equals(where_value)) {
								data.remove(i);
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
				queryLogs.createQueryLog(folder, "Delete Row","Success", databaseName, tableNameLog, "NA", "NA",
						"where " + whereCondition);

			} catch (Exception e) {
				QueryLogs queryLogs = new QueryLogs();
				queryLogs.createQueryLog(folder, "Delete Row","Failure", databaseName, tableNameLog, "NA", "NA",
						"where " + whereCondition);
				System.out.println(e);
			}
		} else {
			System.out.println("Delete query unidentified!");
		}
		System.out.println("Delete Query Completed!");
	}
}
