
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectQuery {

	public static void select(String inputQuery, String database) {
		String query = inputQuery;
		String db = database;
		String columnString = "";
		String table = "";
		List<String> columns = new ArrayList<String>();
		List<String> columnNames = new ArrayList<String>();
		List<HashMap<String, String>> tableData = new ArrayList<HashMap<String, String>>();
		String condition = "";
		String[] s = null;
		try {

			String regex = "select(.*?)from(.*?)";
			Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(query);
			while (matcher.find()) {
				columnString = (matcher.group(1).trim());
				System.out.println("111" + " " + columnString);
			}

			regex = "from(.*?)where(.*?)";
			pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(query);
			while (matcher.find()) {
				table = (matcher.group(1).trim());
			}
			System.out.println("222" + " " + table);

			String[] string_where = query.split("where");
			condition = string_where[1].trim();
			System.out.println("333" + " " + condition);

			File f = new File(table + ".txt");
			if (f.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(table + ".txt"));
				String columnName = br.readLine();
				String row = br.readLine();

				while (row != null) {
					columns.add(row);
					row = br.readLine();
					;
				}
				if (columnName != null) {
					s = columnName.split("\\|");
					for (String column : s) {
						columnNames.add(column);
					}
				}
				if (columns.size() > 0) {
					for (String column : columns) {
						HashMap<String, String> rowData = new HashMap<String, String>();
						s = column.split("\\|");
						for (int i = 0; i < s.length; i++) {
							rowData.put(columnNames.get(i), s[i]);
						}
						tableData.add(rowData);
					}

				} else {
					System.out.println("No data in Table: " + table);
				}
				br.close();
			} else {
				System.out.println(table + ": Table doesn't exist");
			}
			for (int i = 0; i < tableData.size(); i++) {
				for (String key : columnNames) {
					System.out.print(tableData.get(i).get(key) + "       ");
				}
				System.out.println();
			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void main(String[] args) {
		select("Select * from a where condition;", "db");
	}
}
