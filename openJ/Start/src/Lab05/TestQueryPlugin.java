package Lab05;

import java.sql.Connection;
import Utility.DbConnect;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;
import com.vividsolutions.jump.workbench.plugin.*;
import com.vividsolutions.jump.workbench.ui.plugin.FeatureInstaller;

public class TestQueryPlugin extends AbstractPlugIn {

	@Override
	public void initialize(PlugInContext context) throws Exception {
		
		FeatureInstaller featureInstaller = new FeatureInstaller( context.getWorkbenchContext() ); 
		featureInstaller.addMainMenuPlugin( 
				this,    //the plugin to execute on click 
				new String[] {"Corso GIS", "Lab05"},   //menu path 
				"Test Query Plugin",   //name   
				false,    //checkbox 
				null,    //icon 
				null    );   //enable check	
		
	}


	@Override
	public boolean execute(PlugInContext context) throws Exception {
		
//		String conn_str =  "jdbc:postgresql://localhost:5432/gis2021?user=postgres&password=306090120";
//		Connection conn = DriverManager.getConnection( conn_str );
//		String sql = "SELECT * FROM tabella_spaziale";
//		Statement stmt = conn.createStatement();
//		ResultSet rset = stmt.executeQuery( sql );
//		while( rset.next() ) {
//			int id = rset.getInt( "id" );
//			
//			System.out.println( "Ho letto id: "+id );
//			
//		}
//		rset.close();
//		stmt.close();
		DbConnect DbManager=new DbConnect();
//		String query="create table test(id serial PRIMARY KEY, testo varchar(255), geometry GEOMETRY)";
//		DbManager.Execute(DbManager.MyConnect(), query);
		//DbManager.creaTabella(DbManager.MyConnect(), "ciao123");
		String sql = "SELECT id, ST_AsText(geom) FROM tabella_spaziale";
		ResultSet res= DbManager.MyExecute(DbManager.MyConnectStmt(),sql);
		WKTReader wkr = new WKTReader();
		while(res.next()) {
			int id = res.getInt( "id" );
			String wkt=res.getString(2);
			System.out.println( "Ho letto id: "+id );
			System.out.println( "Ho letto wkt: "+wkt );
			Geometry g = wkr.read( wkt );
		}
		Geometry g1 = new GeometryFactory().createPoint( new Coordinate( 10,10 ));
		WKTWriter wkw = new WKTWriter();
		String wkt = wkw.write( g1 );
		String sql1 = "INSERT INTO tabella_spaziale (id, testo, geom) values ( 10, 'Geom JTS', ST_GeomFromText( '"+wkt+"' ) )";
		DbManager.MyUpdate(DbManager.MyConnectStmt(), sql1);
		res.close();
		return false;
	}
	
	
	
}
