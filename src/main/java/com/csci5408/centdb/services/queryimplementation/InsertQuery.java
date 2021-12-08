package com.csci5408.centdb.services.queryimplementation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InsertQuery {

	public static Object insert(String inputQuery, String database, String dbName) {
		String query = inputQuery;
		int columnCount = 0;
		String primaryKey = "";
		String table = "";
		List<String> columnNames = new ArrayList<String>();
		HashMap<String, String> newRowData = new HashMap<String, String>();
		HashMap<String, String> dataTypes = new HashMap<String, String>();
		String values = "";
		String[] s = null;
		Boolean flag = true;
		List<HashMap<String, String>> tableData = new ArrayList<HashMap<String, String>>();
		try {

			String regex = "into(.*?)values(.*?)";
			Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(query);
			while (matcher.find()) {
				table = (matcher.group(1).trim());
			}

			regex = "values \\((.*?)\\)";
			pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(query);
			while (matcher.find()) {
				values = (matcher.group(1).trim());
			}
			File f = new File(database + "\\" + table + ".txt");
			if (f.exists()) {
				// primary key checking:
				BufferedReader br1 = new BufferedReader(new FileReader(database + "\\" + dbName + "-meta.txt"));
				String data = br1.readLine();
				String metaRow[];
				while (data != null) {
					metaRow = data.split("\\|");
					if (table.equals(metaRow[1])) {
						while (data != null) {
							data = br1.readLine();
							if (data != null)
								metaRow = data.split("\\|");
							dataTypes.put(metaRow[0], metaRow[1]);
							if (metaRow[0].equals("Table"))
								break;
							if (metaRow.length > 2) {
								if (metaRow[2].equals("primary_key")) {
									primaryKey = metaRow[0];

								}
							}
						}
					}
					data = br1.readLine();
				}
				br1.close();
				BufferedReader br = new BufferedReader(new FileReader(database + "\\" + table + ".txt"));
				String columnName = br.readLine();
				if (columnName != null) {

					s = columnName.split("\\|");
					for (String column : s) {
						columnNames.add(column);
						columnCount++;
					}
					s = values.split(",");
					if (s.length == columnCount) {
						for (int i = 0; i < s.length; i++) {
							newRowData.put(columnNames.get(i), s[i]);
						}
					} else {
						System.out.println("Input values count and Column count  mismatch");
						flag = false;
					}

					String rows = br.readLine();
					while (rows != null) {
						HashMap<String, String> rowsData = new HashMap<String, String>();
						s = rows.split("\\|");
						for (int i = 0; i < s.length; i++) {
							rowsData.put(columnNames.get(i), s[i]);
						}
						tableData.add(rowsData);
						rows = br.readLine();

					}
					br.close();

				}

				if (!primaryKey.isEmpty()) {

					for (int i = 0; i < tableData.size(); i++) {

						if (tableData.get(i).get(primaryKey).equals(newRowData.get(primaryKey))) {
							System.out.println("Duplicate Primary Key" + tableData.get(i) + newRowData.get(primaryKey));
							flag = false;
							break;
						}
					}
				}

				if (flag) {
					BufferedWriter bw = new BufferedWriter(new FileWriter(database + "\\" + table + ".txt", true));
					bw.append(System.lineSeparator());
					String newRow = "";
					for (String col : columnNames) {
						try {
							if (dataTypes.get(col).equals("int")) {
								Integer.parseInt(newRowData.get(col));
							}
							if (dataTypes.get(col).equals("float")) {
								Float.parseFloat(newRowData.get(col));
							}
						} catch (Exception e) {
							flag = false;
							System.out.println("Incorrect Datatype");
						}

						newRow = newRow + newRowData.get(col) + "|";
					}
					newRow = newRow.substring(0, newRow.length() - 1);
					if (flag)
						bw.append(newRow);
					bw.close();
				}

			} else {
				System.out.println(table + ": Table doesn't exist");
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}

}
