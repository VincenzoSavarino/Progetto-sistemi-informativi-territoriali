package sit;

import Utility.Alert;
import Utility.DbConnect;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jump.feature.AttributeType;
import com.vividsolutions.jump.feature.BasicFeature;
import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.feature.FeatureCollection;
import com.vividsolutions.jump.feature.FeatureDataset;
import com.vividsolutions.jump.feature.FeatureSchema;

import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.plugin.FeatureInstaller;



public class lett extends AbstractPlugIn{
	public double r = 50;
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Start generatore punti random";
	}

	public void caricamentoSegnalazioni(PlugInContext arg0) {
		DbConnect DbManager=new DbConnect();
		Alert alert=new Alert();
		FeatureSchema fs = new FeatureSchema();
		fs.addAttribute( "id", AttributeType.INTEGER );
		fs.addAttribute( "geometry", AttributeType.GEOMETRY );
		fs.addAttribute( "latitudine", AttributeType.DOUBLE );
		fs.addAttribute( "longitudine", AttributeType.DOUBLE );
		fs.addAttribute( "email", AttributeType.STRING );
		fs.addAttribute( "dimensione", AttributeType.STRING );
		fs.addAttribute( "descrizione", AttributeType.STRING );
		fs.addAttribute( "data", AttributeType.DATE );
		fs.addAttribute( "foto", AttributeType.BOOLEAN);


		FeatureCollection fc = new FeatureDataset( fs );
		GeometryFactory gf=new GeometryFactory();
		// double LAT=5685266.04888587;
		// double LON=1323613.56023002;
		String sql = "SELECT *, st_asText(geometry) as geom FROM segnalazioni";
		//id, ST_AsText(geometry), lat,lon
		ResultSet res;
		WKTReader wkr = new WKTReader();
		int count=0;
		try {
			res = DbManager.MyExecute(DbManager.MyConnectStmt(),sql);
			
			while(res.next()) {
				count++;
				int id = res.getInt( "id" );
				String wkt=res.getString("geom");
				double lat=res.getDouble("lat");
				double lon=res.getDouble("lon");
				String email=res.getString("email");
				String dim=res.getString("dim");
				String descrizione=res.getString("descrizione");
				Date data=res.getDate("data");
				Boolean foto=res.getBoolean("foto");
				Feature f = new BasicFeature( fs );
				Geometry g = wkr.read( wkt );
				if(g.getGeometryType()=="Polygon"){
					f.setAttribute( 0, id );
					f.setAttribute( 1, g.getCentroid());
					fc.add(f);
					
				}
				else{
					f.setAttribute( 0, id );
					f.setAttribute( 1, g );
					fc.add(f);
				}
				f.setAttribute( 2, lat );
				f.setAttribute( 3, lon );
				f.setAttribute( 4, email );
				f.setAttribute( 5, dim );
				f.setAttribute( 6, descrizione );
				f.setAttribute( 7, data );
				f.setAttribute( 8, foto );
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(count!=0){
			arg0.addLayer( "Segnalazioni", "Segnalazioni", fc );
		}
		else{
			alert.pannello("Nessuna segnalazione presente nel DB", "Caricamento Segnalazioni", arg0);
		}
		
		
		// Coordinate c = new Coordinate(LON,LAT);
		// 		Geometry g = gf.createPoint( c );
		// 		Feature f = new BasicFeature( fs );
		// 		f.setAttribute( 0, 10 );
		// 		f.setAttribute( 1, g );
		// 		fc.add(f);
		// arg0.addLayer( "Segnalazioni", "id: "+f.getAttribute(0), fc );
		
		// System.err.println("geom: "+f.getGeometry().getCoordinates()[0]);
	}
	
	// WKTReader wkt_r =new WKTReader();
		// try {
		// 	Geometry g1 = wkt_r.read("POINT(1321938.77917143 5685305.9365846)");
		// 	System.out.println("g1: "+ g1.getCoordinates()[0]);

		// } catch (ParseException e) {
		// 	// TODO Auto-generated catch block
		// 	e.printStackTrace();
		// }
		
		// double x = r * Math.cos( LAT ) * Math.sin( LON );
		// double y = r * Math.sin( LAT );
		// Coordinate c = new Coordinate(x,y);
	
	
	
	
	@Override
	public void initialize(PlugInContext arg0) throws Exception {
		FeatureInstaller featureInstaller = new FeatureInstaller(arg0.getWorkbenchContext());
		featureInstaller.addMainMenuPlugin(
			this, //the plugin to execute on click
			new String[] {"Esame" }, //menu path
			"Carica Segnalazioni", //name
			false, //checkbox
			null, //icon
			null );
	}
	@Override
	public boolean execute(PlugInContext arg0) throws Exception {
		//newLayer(arg0);
		caricamentoSegnalazioni(arg0);
		//LineString(arg0);
		return false;
	}	
	
}
