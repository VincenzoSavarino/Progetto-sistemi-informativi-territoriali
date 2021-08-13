package lab3;

import Utility.Select;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jump.feature.AttributeType;
import com.vividsolutions.jump.feature.BasicFeature;
import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.feature.FeatureCollection;
import com.vividsolutions.jump.feature.FeatureDataset;
import com.vividsolutions.jump.feature.FeatureSchema;
import com.vividsolutions.jump.workbench.model.Layer;
import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.plugin.FeatureInstaller;

public class RandomPlugin extends AbstractPlugIn{
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Start generatore punti random";
	}
	public void newLayer(PlugInContext arg0) {
		int n = 40; // num geom
		int maxval = 100; // max valore x o y
		FeatureSchema fs = new FeatureSchema();
		fs.addAttribute( "id", AttributeType.INTEGER );
		fs.addAttribute( "geometry", AttributeType.GEOMETRY );
		FeatureCollection fc = new FeatureDataset( fs );
		GeometryFactory gf=new GeometryFactory();
		for( int i=0; i<n; i++ ) {
				double x = Math.random() * maxval;
				double y = Math.random() * maxval;
				Coordinate c = new Coordinate( x,y );
				Geometry g = gf.createPoint( c );
				Feature f = new BasicFeature( fs );
				f.setAttribute( 0, i );
				f.setAttribute( 1, g );
				fc.add( f );
			}
		arg0.addLayer( "Output", "GeomRandom", fc );
	}
	
	public void randomPoints(PlugInContext context) {
		int n = 40; // num geom
		int maxval = 100; // max valore x o y
		Select select=new Select();
		Layer layer = select.readUserLayerChoice(context);
		layer.getFeatureCollectionWrapper().getFeatureSchema().addAttribute("id", AttributeType.INTEGER);
		FeatureSchema fs =layer.getFeatureCollectionWrapper().getFeatureSchema();
		GeometryFactory gf=new GeometryFactory();
		for( int i=0; i<n; i++ ) {
				double x = Math.random() * maxval;
				double y = Math.random() * maxval;
				Coordinate c = new Coordinate( x,y );
				Geometry g = gf.createPoint( c );
				Feature f = new BasicFeature( fs );
				f.setAttribute( 0, g );
				f.setAttribute( 1, i );
				layer.getFeatureCollectionWrapper().add(f);
			}
	}
	
	public void LineString(PlugInContext context) {
		int n = 40; // num geom
		int maxval = 100; // max valore x o y
		
		Select select=new Select();
		Layer layer = select.readUserLayerChoice(context);
		layer.getFeatureCollectionWrapper().getFeatureSchema().addAttribute("id", AttributeType.INTEGER);
		FeatureSchema fs =layer.getFeatureCollectionWrapper().getFeatureSchema();
		GeometryFactory gf=new GeometryFactory();
		for( int i=0; i<n; i++ ) {
				int m = (int)( Math.random() * n ) +2;
				Coordinate[] pts = new Coordinate[m];
				for( int ii=0; ii<m; ii++ ) {
				double x = Math.random() * maxval;
				double y = Math.random() * maxval;
				Coordinate c = new Coordinate( x,y );
				pts[ii] = c;
				}
				Geometry g = gf.createLineString( pts );
				Feature f = new BasicFeature( fs );
				f.setAttribute( 0, g );
				f.setAttribute( 1, i );
				layer.getFeatureCollectionWrapper().add( f );
			}
		
	}
	
	
	
	
	
	
	
	@Override
	public void initialize(PlugInContext arg0) throws Exception {
		FeatureInstaller featureInstaller = new FeatureInstaller(arg0.getWorkbenchContext());
		featureInstaller.addMainMenuPlugin(
			this, //the plugin to execute on click
			new String[] {"Lab 3","Geometrie"}, //menu path
			"Punti random", //name
			false, //checkbox
			null, //icon
			null );
	}
	@Override
	public boolean execute(PlugInContext arg0) throws Exception {
		//newLayer(arg0);
		randomPoints(arg0);
		//LineString(arg0);
		return false;
	}
	
	

	
	
	
}
