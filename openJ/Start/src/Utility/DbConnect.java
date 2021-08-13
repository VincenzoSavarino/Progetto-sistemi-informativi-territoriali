package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vividsolutions.jts.geom.Geometry;

public class DbConnect {
	
	//ok 
//	public void MyConnection() throws SQLException{
//		String conn_str =  "jdbc:postgresql://localhost:5432/gis2021?user=postgres&password=306090120";
//		Connection conn = null;
//		try {
//			conn = DriverManager.getConnection( conn_str );
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String sql = "SELECT * FROM tabella_spaziale";
//		Statement stmt = null;
//		try {
//			stmt = conn.createStatement();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		ResultSet rset = null;
//		try {
//			rset = stmt.executeQuery( sql );
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		while( rset.next() ) {
//			int id = rset.getInt( "id" );
//			
//			System.out.println( "Ho letto id: "+id );
//			
//		}
//		rset.close();
//		stmt.close();
//		
//	}

	
	// public Connection  MyConnect() {
//	 Connection conn= null;	
//	 String conn_str =  "jdbc:postgresql://localhost:5432/gis2021?user=postgres&password=306090120";
//		try {
//			conn = DriverManager.getConnection( conn_str );
//			return conn;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return conn;
// }	
// 

	
// public void Execute(Connection conn, String query) throws SQLException {
//	 Statement stmt = null;
//	try {
//			stmt = conn.createStatement();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		ResultSet rset = null;
//		try {
//			rset = stmt.executeQuery( query );
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			while( rset.next() ) {
//				int id = rset.getInt( "id" );
//				System.out.println( "Ho letto id: "+id );
//				
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		rset.close();
//		stmt.close();
//	 
// }
 
 public void MyUpdate(Statement stmt, String query) throws SQLException {
	
	stmt.executeUpdate( query );
	 
 }
 

 
// public void creaTabella(Connection conn, String nomeTabella) {
//	 Statement stmt=null;
//	 
//	 	try {
//			stmt = conn.createStatement();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	 	try {
//			stmt.executeQuery("create table "+nomeTabella+"(id serial PRIMARY KEY, testo varchar(255), geometry Geometry);");
//	 		//stmt.executeQuery("create table test2(id serial PRIMARY KEY, testo varchar(255), geometry Geometry);");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
// }
 
 public void creaTabella(Statement stmt, String nomeTabella) {
	 //%TODO for
			try {
				stmt.executeQuery("create table "+nomeTabella+"(id serial PRIMARY KEY, testo varchar(255), geometry Geometry);");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 		//stmt.executeQuery("create table test2(id serial PRIMARY KEY, testo varchar(255), geometry Geometry);");
		
 }
 /////PROVA
 public Statement  MyConnectStmt() {
	 Statement stmt= null;	
	 String conn_str =  "jdbc:postgresql://localhost:5432/gis2021?user=postgres&password=306090120";
		try {
			Connection conn = DriverManager.getConnection( conn_str );
			stmt = conn.createStatement();
			return stmt;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stmt;
 }	
 
// public void Execute2(Statement stmt, String query) throws SQLException {
//
//	
//		ResultSet rset = null;
//		try {
//			rset = stmt.executeQuery( query );
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			while( rset.next() ) {
//				int id = rset.getInt( "id" );
//				System.out.println( "Ho letto id: "+id );
//				
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		rset.close();
//		stmt.close();
//	 
// }
 
 
 public ResultSet MyExecute(Statement stmt, String query) throws SQLException {
		ResultSet rset = null;
		rset = stmt.executeQuery( query );
		return rset;
}
 

 
 

 
 
}
