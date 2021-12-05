


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
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

        File f = new File(table+".txt");
        if(f.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(table+".txt"));
            if(br.readLine()!=null) {
                StringTokenizer st = new StringTokenizer(br.readLine(), "\t");
                int count = 0;
                while (st.hasMoreTokens()) {
                    columns.add(st.nextToken());
                    count++;
                }
            }

            if(columns.size()>0) {
                for (String column : columns) {
                	System.out.println("555" + " " + column);
                    s = column.split("\\|");
                	System.out.println("6666" + " " + s);
                	System.out.println(s[0]);
                	System.out.println(s[1]);
                	System.out.println(s[2]);
                	System.out.println(s[3]);
                }

            }else{
                System.out.println("No data in Table: "+table);
            }
        }else{
            System.out.println(table+": Table doesn't exist");
        }

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void main(String[] args) {
		select("Select * from a where condition;", "db");
	}
}
