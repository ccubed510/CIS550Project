package edu.upenn.cis.cis550.hw3.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.upenn.cis.cis550.hw3.client.QueryTables;
import edu.upenn.cis.cis550.hw3.shared.Relation;

/**
 * This class operates on the server (not the browser) and does a computation.
 * Its output will be returned to the browser.
 * 
 * @author zives
 *
 */
public class QueryTablesImpl extends RemoteServiceServlet implements QueryTables {
	Connection connection;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor -- initialize our source tables and the object
	 */
	public QueryTablesImpl() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String serverName = "fling.seas.upenn.edu";
			String database = "yannie";
			String url = "jdbc:mysql://" + serverName + "/" + database;
			String user = "yannie";
			String password = "abcd";
			
			connection = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Returns a relation output by computing a query
	 */
	@Override
	public Relation getRelation() throws IllegalArgumentException {
		
		try {
			Statement statement = connection.createStatement();
			
			if (statement.execute("SELECT * FROM User")) {
			
				ResultSet rs = statement.getResultSet();
				
				return RelationBuilder.createRelation("R", rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Relation getJoin(String movie) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Relation getImdb() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
