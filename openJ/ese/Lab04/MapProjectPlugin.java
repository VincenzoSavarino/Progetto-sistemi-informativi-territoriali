package Lab04;



import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;

import com.vividsolutions.jts.geom.util.GeometryEditor;
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

public class MapProjectPlugin extends AbstractPlugIn{

	public void initialize(PlugInContext context) throws Exception {
		FeatureInstaller featureInstaller = new FeatureInstaller(context.getWorkbenchContext());
		featureInstaller.addMainMenuPlugin(
			this,		//the plugin to execute on click
			new String[] {"Corso GIS", "Cap04"}, 	//menu path
			this.getName(), 	//name  
			false,		//checkbox
			null,		//icon
			null    );	
	}
	
	
	/**
	 * Funzione che crea un MID che mostra all'utente un pannello per scegliere un layer
	 * e ritorna il layer scelto
	 * @param context
	 * @return Layer o null
	 */
	public Layer readUserLayerChoice( PlugInContext context ) {
		
		MultiInputDialog mid = new MultiInputDialog( context.getWorkbenchFrame(), 
				this.getName(), true );		

		String _nomelayer = "Scegli il layer da elaborare";
		mid.addLayerComboBox( _nomelayer, null, context.getLayerManager() );
	
		mid.setVisible( true );	// dialog modale
		if( mid.wasOKPressed() == false )	return null;
				
		String layername = mid.getText( _nomelayer );
		System.out.println( "Hai selezionato il layer: "+layername );
		Layer layer = context.getLayerManager().getLayer(layername);
		return layer;
	}	
	
	public void createGrid(PlugInContext ctx) {
		FeatureSchema fs =new FeatureSchema();
		fs.addAttribute("ID", AttributeType.INTEGER);
		fs.addAttribute("TYPE", AttributeType.STRING);
		fs.addAttribute("GEOMETRY", AttributeType.GEOMETRY);
		FeatureCollection fc=new FeatureDataset(fs);
		GeometryFactory gf=new GeometryFactory();
		//meridiano
		for(int i=-180;i<=180;i+=20) {
			Feature f=new BasicFeature(fs);
			LineString line = gf.createLineString(new Coordinate[] {
					new Coordinate(i,-90),
					new Coordinate(i,90)
			});
			f.setAttribute("ID", i);
			f.setAttribute("TYPE", "meridiano");
			f.setGeometry(line);
			fc.add(f);
			
		}
		//paralleli
		for(int i=-90;i<=90;i+=20) {
			Feature f=new BasicFeature(fs);
			LineString line = gf.createLineString(new Coordinate[] {
					new Coordinate(-180,i),
					new Coordinate(180,i)
			});
			f.setAttribute("ID", i);
			f.setAttribute("TYPE", "parallelo");
			f.setGeometry(line);
			fc.add(f);
			
		}
		ctx.addLayer("GRID", "GRID", fc);
		
	} 
	
	@Override
	public String getName() {
		return "Map Proection";
	}

	public boolean execute(PlugInContext context) throws Exception {		
		
		System.out.println( this.getName() + " started!" );
		
		// faccio scegliere un layer e il valore del parametro
		MultiInputDialog mid = new MultiInputDialog( context.getWorkbenchFrame(), 
				this.getName(), true );		

		String _nomelayer = "Scegli il layer da elaborare";
		mid.addLayerComboBox( _nomelayer, null, context.getLayerManager() );
	
		String _parametro = "Valore parametro:";
		mid.addDoubleField( _parametro, 1, 3 );
		
		mid.setVisible( true );	// dialog modale
		if( mid.wasOKPressed() == false )	return false;
				
		double param = mid.getDouble( _parametro );
		String layername = mid.getText( _nomelayer );
		Layer layer = context.getLayerManager().getLayer(layername);
		
		FeatureSchema fs = new FeatureSchema();
		fs.addAttribute( "id", AttributeType.INTEGER );
		fs.addAttribute( "geometry", AttributeType.GEOMETRY );
		FeatureCollection fc = new FeatureDataset( fs );
		GeometryFactory gf = new GeometryFactory();
		
		
		createGrid(context);
		
		if(true)
			return true; 

		int i = 0;
		int r = 1;
		for( Feature f : layer.getFeatureCollectionWrapper().getFeatures() ) {
			
/*			
 * Codice iniziale per trasformare le linestring
 * Sostituito dall'uso della classe GeometryEditor (v.sotto)
			Coordinate[] arc = f.getGeometry().getCoordinates();
			if( f.getGeometry() instanceof Polygon ) {
				Polygon p = (Polygon) f.getGeometry();
				arc = p.getExteriorRing().getCoordinates();
			}
			
			
			Coordinate[] nc = new Coordinate[ arc.length ];
			for( int ic=0; ic<arc.length; ic++ ) {
				
				// leggo le coordinate importate da Google Earth
				double LON = arc[ic].x;
				double LAT = arc[ic].y;
				
				// trasformazione plate carre
//				double x = LON;				
//				double y = LAT;
				
				// formula di trasformazione
				LON = Math.toRadians( LON );
				LAT = Math.toRadians( LAT );
				double x = r * Math.cos( LAT ) * Math.sin( LON );
				double y = r * Math.sin( LAT );			
				
				// inserisco le coordinate nel nuovo array
				Coordinate c = new Coordinate( x,y );
				nc[ic] = c;
			}
			
			// uso il nuovo array per fare la geometria proiettata
			Geometry g = gf.createLineString(nc);
*/			
			
			GeometryEditor ge = new GeometryEditor( gf );
			CoordinateProjectOperation cpo = new CoordinateProjectOperation(  r );
			Geometry g = ge.edit( f.getGeometry(), cpo );
			
			Feature ff = new BasicFeature( fs );
			ff.setAttribute( 0, i++ );
			ff.setAttribute( 1, g );
			fc.add( ff );			
		}
			
		context.addLayer( "Output", "Proiezione"+layer.getName(), fc );			
		return false;
	}	
	
	
	/**
	 * Classe CoordinateProjectOperation da usare con GeometryEditor
	 * De-commentate le varie parti per attivare una o l'altra proiezione.
	 * @author Sandro
	 * @date 22/apr/2020 17:25:02
	 *
	 */
	public static class CoordinateProjectOperation extends GeometryEditor.CoordinateOperation {
		
		public double r = 1;
		public CoordinateProjectOperation( double radius ) {
			this.r = radius;
		}
		@Override
		public Coordinate[] edit(Coordinate[] coordinates, Geometry geometry) {

			Coordinate[] nc = new Coordinate[ coordinates.length ];
			for( int ic=0; ic<coordinates.length; ic++ ) {
				
				// leggo le coordinate importate da Google Earth
				double LON = coordinates[ic].x;
				double LAT = coordinates[ic].y;
				
				// trasformazione plate carree
				// piano-quadrata
				double x = LON;				
				double y = LAT;
	

				// per tutte le proiezioni successive, che usano la trigonometria,
				// dobbiamo convertire LON e LAT in radianti
				// anche se usiamo formule trigonometriche solo su uno dei due parametri,
				// covnertiamo entrambi per mantenere la proporzione
				
				
				// usando la funzione java
//				LON = Math.toRadians( LON );
//				LAT = Math.toRadians( LAT );
				
				// metodo manuale
//				LON = Math.PI * LON / 180;
//				LAT = Math.PI * LAT / 180;
				
/*				
				// proiezione ortografica equatoriale
				double x = r * Math.cos( LAT ) * Math.sin( LON );
				double y = r * Math.sin( LAT );
*/
	
					
				
/*				
				// Labert 1772
				// cilindrica equatoriale ortografica
				// convertiamo anche LON in radianti per avere lo stesso fattore di scala
				LON = Math.toRadians( LON );
				LAT = Math.toRadians( LAT );
				double x = r * LON ;
				double y = r * Math.sin( LAT ) ;
*/
				
/*						
				// proiezione di Gall-Peters 1855 / 1974
				LON = Math.toRadians( LON );
				LAT = Math.toRadians( LAT );			
				double x = r * LON;
				double y = r * 2 * Math.sin( LAT );
*/	
				
/*
				// Tobler quadrata (carta quadrata: altezza = larghezza
				LON = Math.toRadians( LON );
				LAT = Math.toRadians( LAT );
				double x = r * LON ;
				double y = r * Math.sin( LAT )  *180;
*/				
/*				
				// cilindrica equatoriale centrografica
				LON = Math.toRadians( LON );
				LAT = Math.toRadians( LAT );
				double x = r * LON;
				double y = r * Math.tan( LAT );
*/				
/*				
				// mercatore 1569
				LON = Math.toRadians( LON );
				LAT = Math.toRadians( LAT );
				double x = r * LON;
				double y = r * Math.log( ( 1+Math.sin(LAT)) / ( 1-Math.sin(LAT)) ) /2;
//				double y = r * Math.log( Math.tan( Math.PI/4 + LAT/2 )); // versione alternativa: 
																		// provatela e vedete che la formula diversa
																		// porta ad un trattamento numerico diverso

*/	
/*
				// mercatore trasversa
				LON = Math.toRadians( LON );
				LAT = Math.toRadians( LAT );
				
				// la formula di mercatore per la latitudine si aspetta un angolo da -90 a +90
				// se la applico alla longitudine (che fa da -180 a +180) devo dividerla per 2
				// divido per lo stesso fattore anche la latitudine

				LON /=2;
				LAT /=2;
			
				double x = r * Math.log(  ( 1+ Math.sin( LON ))/( 1- Math.sin( LON )) ) /2;
				double y = r * LAT;		
				
				// per visualizzare con la stessa dimensione Mercatore Normale e Trasversa
				// mettete r = 50 nel primo e r = 100 nel secondo (o altro numero in rapporto 1:2)
*/				

				
				// inserisco le coordinate nel nuovo array
				Coordinate c = new Coordinate( x,y );
				nc[ic] = c;
			}
			
			return nc;
		}
		
		
	}
	
	
	
	
}