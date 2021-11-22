package com.csci5408.centdb;
import java.util.regex.*;

public class QueryValidator {

	Pattern pattern = Pattern.compile("(select (.*?) from(.*?)(where.*)?)|"
			+ "(delete (.*?)from(.*?)(where.*)?)|"
			+ "(insert into(.*?) \\((.*?)\\) values \\((.*?))\\)|"
			+ "(update (.*?) set (.*?) where (.*?))|"
			+"(show dbs)|"
			+ "(create db (.*?))|"
			+ "(use (.*?))|"
			+ "(create table (.*?) \\((.*?)\\))|"
			+ "(begin)|"
			+ "(commit)|"
			+ "(rollback)", Pattern.CASE_INSENSITIVE);
    
	public boolean validateQuery(String query) {
		Matcher matcher = pattern.matcher(query);
	    boolean matchFound = matcher.find();
	    return matchFound;
		}
	
	public static void main(String[] args) {
		QueryValidator x=new QueryValidator();
		System.out.println(x.validateQuery("select * from A where ab=1"));
		System.out.println(x.validateQuery("select * from A"));
		System.out.println(x.validateQuery("delete * from A"));
		System.out.println(x.validateQuery("delete from A where ab=1"));
		System.out.println(x.validateQuery("INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) VALUES ('Cardinal', 'Tom B. Erichsen', 'Skagen 21', 'Stavanger', '4006', 'Norway')"));
	    System.out.println(x.validateQuery("UPDATE Customers SET ContactName='Juan' WHERE Country='Mexico';"));
	    System.out.println(x.validateQuery("show dbs"));
		System.out.println(x.validateQuery("create db A"));
		System.out.println(x.validateQuery("use db A"));
		System.out.println(x.validateQuery("CREATE TABLE Persons (PersonID int,LastName varchar(255),FirstName varchar(255),Address varchar(255),City varchar(255))"));
		System.out.println(x.validateQuery("begin"));
		System.out.println(x.validateQuery("commit"));
		System.out.println(x.validateQuery("rollback"));
	

	}

}
