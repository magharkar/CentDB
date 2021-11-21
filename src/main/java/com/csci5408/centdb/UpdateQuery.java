package com.csci5408.centdb;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class UpdateQuery {
    public void updateQuery(String query, String folder) {
        ArrayList<String> columns = new ArrayList<>();
        ArrayList<String> data = new ArrayList<>();
        String line;
        String[] s = null;
        String table_name = "";
        String constraint = "";
        String where_condition;
        int count = 0;
        int position=0;
        int position_where = 0;

        if(query.toLowerCase().contains("update ")) {
            System.out.println("An update query identified!");

            try {

                String regex = "update(.*?)set(.*?)";
                Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(query);
                while (matcher.find()) {
                    table_name = (matcher.group(1).trim());
                    table_name = folder+table_name.replaceAll("\\.", "\\\\") + ".txt";
                }

                String regex1 = "set(.*?)where(.*?)";
                Pattern pattern1 = Pattern.compile(regex1, Pattern.CASE_INSENSITIVE);
                Matcher matcher1 = pattern1.matcher(query);
                while (matcher1.find()) {
                    constraint = (matcher1.group(1).trim());
                }

                String[] string_where = query.split("where");
                where_condition = string_where[1].trim();

                File f = new File(table_name);
                if(f.exists()) {
                    BufferedReader br = new BufferedReader(new FileReader(table_name));
                    if(br.readLine()!=null) {
                        StringTokenizer st1 = new StringTokenizer(br.readLine(), "\t");
                        while (st1.hasMoreTokens()) {
                            columns.add(st1.nextToken());
                            count++;
                        }
                    }

                    if(columns.size()>0) {
                        for (String column : columns) {
                            s = column.split("\\|");
                        }
                        for (int i = 0; i < Objects.requireNonNull(s).length; i++) {
                            if (s[i].trim().equals(constraint.split("=")[0].trim())) {
                                position = i;
                            }
                            if (s[i].trim().equals(where_condition.split("=")[0].trim())) {
                                position_where = i;
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

                        String where_value = where_condition.split("=")[1].trim();
                        where_value = where_value.substring(1, where_value.length() - 1);
                        String set_value = constraint.split("=")[1].trim();
                        set_value = set_value.substring(1, set_value.length() - 1);

                        for (int i = 0; i < data.size(); i++) {
                            if (data.get(i).split("\\|")[position_where].trim().equals(where_value)) {
                                data.set(i, data.get(i).replaceAll(data.get(i).split("\\|")[position], set_value));
                            }
                        }

                        FileWriter writer = new FileWriter(table_name);
                        writer.write(columns.remove(0) + "\n");
                        for (String datum : data) {
                            writer.write(datum + "\n");
                        }
                        writer.close();
                    }else{
                        System.out.println("No data in Table: "+table_name);
                    }
                }else{
                    System.out.println(table_name+": Table doesn't exist");
                }

            }catch(Exception e){
                System.out.println(e);
            }
        }
        else{
            System.out.println("An update query unidentified!");
        }
        System.out.println("Update Query Completed!");
    }
}
