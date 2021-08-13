package Lab05;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;
import com.vividsolutions.jump.feature.AttributeType;
import com.vividsolutions.jump.feature.BasicFeature;
import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.feature.FeatureCollection;
import com.vividsolutions.jump.feature.FeatureDataset;
import com.vividsolutions.jump.feature.FeatureSchema;
import com.vividsolutions.jump.workbench.model.Layer;
import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.MultiInputDialog;
import com.vividsolutions.jump.workbench.ui.plugin.FeatureInstaller;

import Utility.*;

public class Ese54 extends AbstractPlugIn{
	DbConnect DbManager=new DbConnect();
	@Override
	public void initialize(PlugInContext context) throws Exception {
		
		FeatureInstaller featureInstaller = new FeatureInstaller( context.getWorkbenchContext() ); 
		featureInstaller.addMainMenuPlugin( 
				this,    //the plugin to execute on click 
				new String[] {"Corso GIS", "Lab05"},   //menu path 
				"Esercizi",   //name   
				false,    //checkbox 
				null,    //icon 
				null    );   //enable check	
		
	}
	
	
	@Override
	public boolean execute(PlugInContext arg0) throws Exception {
		

		//String nome_tabella="tabella_spaziale";
//		String query="insert into "+nome_tabella+" (testo, geom) values ('insert eclipse', 'POINT(1 1)')";
//		DbManager.UpdateQuery(DbManager.MyConnectStmt(), query);

		//TODO
//		• Creare un plugin che:
//			– Si collega a PostGIS
//			– Chiede a PostGIS l’area dell’oggetto con ID 3981 della tabella FABBRIC
//			– Stampa il valore
//		String nome_tabella="tabella_spaziale";
//		int id = 3981;
//		String query ="select ST_area(geom) from "+nome_tabella+" where id="+id;
//		ResultSet area = DbManager.Execute3(DbManager.MyConnectStmt(), query);
//		while(area.next()) {
//			System.out.println("area= "+area.getInt("st_area"));
//		}
		
		//TODO
//		Realizziamo un plugin che esegue una
//		query su PostGIS e scrive in console il
//		risultato
//		• Nel metodo execute facciamo apparire un
//		Dialog che chiede all’utente di scrivere la
//		query
//		• Creiamo un metodo che esegue la query e
//		stampa le righe (e le colonne) del risultato
//		SimpleQuery(arg0);
		
		
		//AreaF1(arg0);
		//Ese62(arg0);
//		Ese64(arg0);
		Ese64Bonus(arg0);
		
		
		
		return false;
	}
	
//	Facciamo calcolare a PostGIS l’area del
//	primo elemento letto del layer EDIFC
//	caricato in OpenJUMP
	
	public void AreaF1(PlugInContext context) {
		WKTWriter wktw=new WKTWriter(3);
		Select select=new Select();
		Layer layer = select.readUserLayerChoice(context);
		//Layer layer=context.getLayerManager().getLayer("Nuovo");
		Feature f= layer.getFeatureCollectionWrapper().getFeatures().get(0);
		System.out.println("normal "+f.getGeometry());
		String geom_wkt=wktw.write(f.getGeometry());
		System.out.println("WKT "+geom_wkt);
		//String query="select ST_AREA(st_GeomFromText('"+geom_wkt+"'))";
		String query = "SELECT ST_Area( ST_GeomFromText( '"+f.getGeometry()+"' ))";
		try {
			ResultSet res=DbManager.MyExecute(DbManager.MyConnectStmt(), query);
			while(res.next()) {
				System.out.println("area= "+ res.getInt("st_area"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	Creare un plug-in che:
//		– Mostra in un nuovo layer di OpenJUMP la
//		geometria di tutti gli oggetti della tabella
//		fabbric
	public void Ese62(PlugInContext context) {
		
		WKTReader wkt_r =new WKTReader();
		FeatureSchema fs=new FeatureSchema();
		fs.addAttribute( "GEOMETRY", AttributeType.GEOMETRY );
		FeatureCollection fc = new FeatureDataset( fs );
		//trova la colonna geom 
		String nome_tabella="tabella_spaziale";
		String nome_colonna_geometry = null;
		String query="select f_geometry_column from geometry_columns where f_table_name='"+nome_tabella+"'";
		try {
			ResultSet res =DbManager.MyExecute(DbManager.MyConnectStmt(), query);
			while(res.next()) {
				//System.out.println(res.getString(1));
				nome_colonna_geometry=res.getString(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("nome della colonna GEOMETRY: "+nome_colonna_geometry );
		//inserisco la geometria
		query="select ST_asText("+nome_colonna_geometry+") from "+ nome_tabella;
		try {
			ResultSet res =DbManager.MyExecute(DbManager.MyConnectStmt(), query);
			while(res.next()) {
				System.out.println(res.getString(1));
				Feature f = new BasicFeature( fs );
				Geometry g;
				try {
					g = wkt_r.read(res.getString(1));
					f.setAttribute(0, g);
					fc.add( f );
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if(nome_colonna_geometry!=null)
//			System.out.println(nome_colonna_geometry);
//		else {
//			System.out.println("No colonna geometry");
//		}
		context.addLayer( "Output", nome_tabella, fc );
	}
	
	public void Ese62Bonus1(PlugInContext context) {
		WKTReader wkt_r =new WKTReader();
		FeatureSchema fs=new FeatureSchema();
		fs.addAttribute( "GEOMETRY", AttributeType.GEOMETRY );
		FeatureCollection fc = new FeatureDataset( fs );
		//inserisci nome tabella
		String nome_tabella = (String)JOptionPane.showInputDialog(
				context.getWorkbenchFrame(),
				"Inserisci il nome della tabella",
				"PG Query Dialog",
				JOptionPane.PLAIN_MESSAGE,
				null, null, "");
		//controllo ins
				if ((nome_tabella != null) && (nome_tabella.length() > 0)) {
					System.out.println( "nome tabella: "+nome_tabella );
					//trovo la colonna geom 
					String nome_colonna_geometry = null;
					String query="select f_geometry_column from geometry_columns where f_table_name='"+nome_tabella+"'";
					try {
						ResultSet res =DbManager.MyExecute(DbManager.MyConnectStmt(), query);
						while(res.next()) {
							nome_colonna_geometry=res.getString(1);
							System.out.println("nome della colonna GEOMETRY: "+nome_colonna_geometry );
						}
						//todo exception null geom 
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//inserisco la geometria
					query="select ST_asText("+nome_colonna_geometry+") from "+ nome_tabella;
					try {
						ResultSet res =DbManager.MyExecute(DbManager.MyConnectStmt(), query);
						while(res.next()) {
							System.out.println(res.getString(1));
							Feature f = new BasicFeature( fs );
							Geometry g;
							try {
								g = wkt_r.read(res.getString(1));
								f.setAttribute(0, g);
								fc.add( f );
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//					if(nome_colonna_geometry!=null)
//						System.out.println(nome_colonna_geometry);
//					else {
//						System.out.println("No colonna geometry");
//					}
					context.addLayer( "Output", nome_tabella, fc );
					
				
				}
				else {
					System.out.println( "inserisci il nome della tabella" );

				}
		//trova la colonna geom 
		//String nome_tabella="tabella_spaziale";
		
	}
	
	public void Ese62Bonus2(PlugInContext context) {
		List<String> scelte=new ArrayList<String>();
		String query1="select f_table_name from geometry_columns";
		
		try {
			ResultSet res =DbManager.MyExecute(DbManager.MyConnectStmt(), query1);
			while(res.next()) {
				System.out.println(res.getString(1));
				scelte.add(res.getString(1));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String nome_tabella = (String)JOptionPane.showInputDialog(
				context.getWorkbenchFrame(),
				"Scegli la tabella",
				"PG Query Dialog",
				JOptionPane.PLAIN_MESSAGE,
				null, scelte.toArray(), "");
		System.out.println("hai scelto la tabella: "+nome_tabella);
		
		//copia es62
		WKTReader wkt_r =new WKTReader();
		FeatureSchema fs=new FeatureSchema();
		fs.addAttribute( "GEOMETRY", AttributeType.GEOMETRY );
		FeatureCollection fc = new FeatureDataset( fs );
		//trova la colonna geom 
		String nome_colonna_geometry = null;
		String query="select f_geometry_column from geometry_columns where f_table_name='"+nome_tabella+"'";
		try {
			ResultSet res =DbManager.MyExecute(DbManager.MyConnectStmt(), query);
			while(res.next()) {
				//System.out.println(res.getString(1));
				nome_colonna_geometry=res.getString(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("nome della colonna GEOMETRY: "+nome_colonna_geometry );
		//inserisco la geometria
		query="select ST_asText("+nome_colonna_geometry+") from "+ nome_tabella;
		try {
			ResultSet res =DbManager.MyExecute(DbManager.MyConnectStmt(), query);
			while(res.next()) {
				System.out.println(res.getString(1));
				Feature f = new BasicFeature( fs );
				Geometry g;
				try {
					g = wkt_r.read(res.getString(1));
					f.setAttribute(0, g);
					fc.add( f );
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if(nome_colonna_geometry!=null)
//			System.out.println(nome_colonna_geometry);
//		else {
//			System.out.println("No colonna geometry");
//		}
		context.addLayer( "Output", nome_tabella, fc );
		
	}
	
	
	
	public void SimpleQuery(PlugInContext context) {
		String s = (String)JOptionPane.showInputDialog(
				context.getWorkbenchFrame(),
				"Type the query",
				"PG Query Dialog",
				JOptionPane.PLAIN_MESSAGE,
				null, null, "");
				if ((s != null) && (s.length() > 0)) {
				System.out.println( "The query is: "+s );
				try {
					ResultSet res= DbManager.MyExecute(DbManager.MyConnectStmt(), s);// funzione che esegue la query
					System.out.println( "Risultati della query: " );
					int count=0;
					while(res.next()){
						count++;
						for( int ic=0; ic< res.getMetaData().getColumnCount(); ic++ )
						System.out.println( "\t "+
						res.getMetaData().getColumnName(ic+1)+
						" : "
						+res.getString( ic+1 ));
						
					}
					System.out.println( " "+count+" righe" );
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					} 
				}
				else {
				System.out.println( "No query to exec" );
				}
	}

//	• Creare un plug-in che:
//		– Carica in una tabella PostGIS tutte le
//		geometrie di layer di OpenJUMP (il plugin
//		deve far scegliere il layer e chiedere il nome
//		della tabella)
//		Bonus: importare in PostGIS anche gli altri attributi
//		Per semplificare l’esercizio:
//		• Considerare esistente la tabella
//		• Accodare i dati senza fare un controllo di esistenza
	
	public void Ese64(PlugInContext context) {
		//Select  layer
		Select select =new Select();
		Layer layer_selezionato=select.readUserLayerChoice(context);
		System.out.println("hai scelto il layer tabella: "+layer_selezionato);
		//Select table	
		String nome_tabella=select.readUserTableChoice(DbManager, context);
		System.out.println("hai scelto la tabella: "+nome_tabella);
		//per comodit
//		Layer layer_selezionato=context.getLayerManager().getLayer("Nuovo");
		List<Feature> fl=layer_selezionato.getFeatureCollectionWrapper().getFeatures();
		
		//prendo la geom
		Geometry g;
		for(Feature f1:fl) {
			g=f1.getGeometry();
			System.out.println("geom letta: "+g);
			String query="insert into test (geometry) values (st_GeomFromText('"+g+"'))";
			try {
				DbManager.MyUpdate(DbManager.MyConnectStmt(), query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void Ese64Bonus(PlugInContext context) {
		//Select  layer
		Select select =new Select();
//		Layer layer_selezionato=select.readUserLayerChoice(context);
		Layer layer_selezionato=context.getLayerManager().getLayer("Nuovo");
		System.out.println("hai scelto il layer tabella: "+layer_selezionato);
		//Select table	
		//String nome_tabella=select.readUserTableChoice(DbManager, context);
		String nome_tabella="test";
		System.out.println("hai scelto la tabella: "+nome_tabella);
		//per comodit
//		Layer layer_selezionato=context.getLayerManager().getLayer("Nuovo");
		//FeatureSchema fs= layer_selezionato.getFeatureCollectionWrapper().getFeatureSchema();
		List<Feature> fl=layer_selezionato.getFeatureCollectionWrapper().getFeatures();
//		ESE BONUS TODO
//		System.out.println("number of feature: "+fl.size());
//		System.out.println("number of attribute: "+fs.getAttributeCount());
//		for(Feature f:fl) {
//			for(String attri_name:fs.getAttributeNames()) {
//				System.out.println(attri_name+": "+ f.getAttribute(attri_name)+" tipo:"+fs.getAttributeType(attri_name));
//				//insert into test (id) values ()
////				String query="insert into "+nome_tabella+"("+attri_name+") values ("+f.getAttribute(attri_name)+")";
////				try {
////					DbManager.MyUpdate(DbManager.MyConnectStmt(), query);
////				} catch (SQLException e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
//			}
//		}

		
		//prendo la geom
//		Geometry g;
//		for(Feature f1:fl) {
//			g=f1.getGeometry();
//			System.out.println("geom letta: "+g);
//			String query="insert into test (geometry) values (st_GeomFromText('"+g+"'))";
//			try {
//				DbManager.MyUpdate(DbManager.MyConnectStmt(), query);
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
	}
	

}
