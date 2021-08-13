package Funzioni;

import java.util.ArrayList;
import java.util.List;

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

import Utility.Select;

public class IntersezioneG1_2 extends AbstractPlugIn{
	
	public void initialize(PlugInContext arg0) throws Exception {
		FeatureInstaller featureInstaller = new FeatureInstaller(arg0.getWorkbenchContext());
		featureInstaller.addMainMenuPlugin(
			this, //the plugin to execute on click
			new String[] {"Lab 3","Geometrie"}, //menu path
			"Inter due geom", //name
			false, //checkbox
			null, //icon
			null );
	}

	@Override
	public boolean execute(PlugInContext arg0) throws Exception {
		// TODO Auto-generated method stub
		Select selectedlayer= new Select();
		Layer layer =selectedlayer.readUserLayerChoice(arg0);
		if( layer == null ) return false;
		FeatureSchema fs = new FeatureSchema();
		fs.addAttribute( "id", AttributeType.INTEGER );
		fs.addAttribute( "geometry", AttributeType.GEOMETRY );
		FeatureCollection fc = new FeatureDataset( fs );
		//GeometryFactory gf = new GeometryFactory();
		
		Geometry g1 = layer.getFeatureCollectionWrapper().getFeatures().get(0).getGeometry();
		Geometry g2 = layer.getFeatureCollectionWrapper().getFeatures().get(1).getGeometry();
		Geometry diff = g1.intersection(g2);
		Feature f = new BasicFeature( fs );
		f.setAttribute( 0, 1 );
		f.setAttribute( 1, diff );
		fc.add( f );
		arg0.addLayer( "Output", "Inter", fc );
		
		
		return false;
		
	}
	
	
	

}
