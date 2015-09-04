package world_cities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CitiesFromDatabase {
	
	/**
	 * Zadatak koji treba riješiti je sledeći:
	 * Napisati program koji dopušta krajnjem korisniku da pretražuje 
	 * Attachment-ovanu (world.sql) bazu po imenu države, broju stanovnika ili 
	 * po gradu iz te države. 
	 * Vaš program treba da ispiše što više podataka iz baze za svaki 
	 * rezultat - ALI da to bude fino i čitko predstavljeno.
	 */

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		Scanner input = new Scanner(System.in);
		
		/** Load the JDBC driver */
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println(">Driver loaded.");
		
		/** Connect to a database */
		Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/world", "root", "");
		System.out.println(">Database connected!\n");
		
		/** Create a statement */
		Statement statement = connect.createStatement();
		
		/** Execute statement */
		ResultSet resultSet = statement.executeQuery(" SELECT * FROM `city` ");
		
		/** Create ResultSetMetaData to retrieve information from the Database
		 * such as: length of the column, column names, etc. etc. */
	//	ResultSetMetaData rsMetaData = resultSet.getMetaData();
		
		/** Prompt the user to enter a search query */
		System.out.println("Enter a search query: ");
		String query = input.nextLine();
		
		/** Create boolean and initially set it to false */
		boolean isFound = false;
		
		System.out.println("Results for the query: " + query);
		
		/** Keep looping while table contains some data */
		while(resultSet.next()) {
			/** If query corresponds to the data from the database, retrieve that data and display results */
			if(query.equals(resultSet.getString("Name")) || query.equals(resultSet.getString("CountryCode")) || query.equals(resultSet.getString("Population"))) {
				/**
				System.out.print("ID       Name      Country     District      Population\n");
				System.out.println("---------------------------------------------------------");
				for(int i = 1; i <= rsMetaData.getColumnCount(); i++) {
					System.out.print(resultSet.getObject(i) + "       ");
				}
				*/
				
				/** Retrieve information from the database */
				System.out.println("\nID: " + resultSet.getString("ID")); 
				System.out.println("City Name: " + resultSet.getString("Name"));
				System.out.println("Country Code: " + resultSet.getString("CountryCode"));
				System.out.println("District: " + resultSet.getString("District"));
				System.out.println("Population: " + resultSet.getString("Population"));
				System.out.println();
				
				/** Query is found in the database, set boolean to true */
				isFound = true;
			} 
		}
		
		/** If query is not found, display not found */
		if(!isFound) {
			System.out.println("\nThe query \"" + query + "\" was not found in the Database.");
		}
		
		input.close(); // Close the scanner
		connect.close(); // Close connection with the database
	}

}
