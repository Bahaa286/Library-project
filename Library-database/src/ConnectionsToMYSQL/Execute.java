package ConnectionsToMYSQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Execute {

	public static Connection connection;
	public static ResultSet resultSet;
	public static Statement statement;
	private static ConnectToDatabase connectToDatabase = new ConnectToDatabase(ConnectionsText.URL,
			ConnectionsText.port, ConnectionsText.dbName, ConnectionsText.dbUsername, ConnectionsText.dbPassword);;
	private static ConnectionsText connectionsText;

	// execute query
	public static void executeQuery(String query) throws ClassNotFoundException, SQLException {

		connection = connectToDatabase.connect();
		statement = connection.createStatement();
		statement.executeUpdate(query);

		connection.close();
		statement.close();
	}

	// execute SELECT query
	public static void setResultSELECTQuery(String query) throws ClassNotFoundException, SQLException {

		connection = connectToDatabase.connect();
		statement = connection.createStatement();
		resultSet = statement.executeQuery(query);

	}
	
	// close
	public static void close() throws SQLException {
		
		connection.close();
		statement.close();
		resultSet.close();
		
	}	
}
