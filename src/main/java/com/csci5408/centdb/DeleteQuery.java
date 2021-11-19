package com.csci5408.centdb;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class DeleteQuery {
    public void deleteQuery(String query, String folder) {
        String table_name="";
        ArrayList<String> columns = new ArrayList<>();
        ArrayList<String> data = new ArrayList<>();
        int count = 0;
        String where_condition;
        int position=0;
        String[] s = null;
        String line;

        if(query.toLowerCase().contains("delete from ")) {
            System.out.println("Delete query identified!");

            try {
                String regex = "delete from(.*?)where(.*?)";
                Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(query);
                while (matcher.find()) {
                    table_name = (matcher.group(1).trim());
                    table_name = folder+table_name.replaceAll("\\.", "\\\\") + ".txt";
                }

                String[] string_where = query.split("where");
                where_condition = string_where[1].trim();
                String where_value = where_condition.split("=")[1].trim();
                where_value = where_value.substring(1, where_value.length() - 1);
                String where_column = where_condition.split("=")[0].trim();

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
                            if (s[i].trim().equals(where_column)) {
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
            }catch (Exception e){
                System.out.println(e);
            }
        }
        else{
            System.out.println("Delete query unidentified!");
        }
        System.out.println("Delete Query Completed!");
    }
}
