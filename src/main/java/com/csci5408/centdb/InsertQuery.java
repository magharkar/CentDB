
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InsertQuery {

	public static void insert(String inputQuery, String database) {
		String query = inputQuery;
		String db = database;
		String table = "";
		List<String> columns = new ArrayList<String>();
		List<String> columnNames = new ArrayList<String>();
		HashMap<String, String> rowData = new HashMap<String, String>();
		String values = "";
		String[] s = null;
		try {

			String regex = "into(.*?)values(.*?)";
			Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(query);
			while (matcher.find()) {
				table = (matcher.group(1).trim());
				System.out.println("111" + " " + table);
			}

			regex = "values\\((.*?)\\);";
			pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(query);
			while (matcher.find()) {
				values = (matcher.group(1).trim());
			}
			System.out.println("222" + " " + values);

			File f = new File(table + ".txt");
			if (f.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(table + ".txt"));
				String columnName = br.readLine();
				if (columnName != null) {
					System.out.println("333" + " " + columnName);

					s = columnName.split("\\|");
					for (String column : s) {
						columnNames.add(column);
					}
					s = values.split(",");
					for (int i = 0; i < s.length; i++) {
						rowData.put(columnNames.get(i), s[i]);
					}
					br.close();
					System.out.println(rowData);

				}
				BufferedWriter bw = new BufferedWriter(new FileWriter(table + ".txt", true));
				bw.append(System.lineSeparator());
				String newRow = "";
				for (String col : columnNames) {
					newRow = newRow + rowData.get(col) + "|";
				}
				newRow = newRow.substring(0, newRow.length() - 1);
				System.out.println(newRow);
				bw.append(newRow);
				bw.close();

			} else {
				System.out.println(table + ": Table doesn't exist");
			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void main(String[] args) {
		insert("insert into a values(l,b,r,t);", "db");
	}
}
