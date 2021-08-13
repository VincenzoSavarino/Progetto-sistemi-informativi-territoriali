package Utility;

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

public class EsempioEnvelope extends AbstractPlugIn {
	
	@Override
	public void initialize(PlugInContext arg0) throws Exception {
		FeatureInstaller featureInstaller = new FeatureInstaller(arg0.getWorkbenchContext());
		featureInstaller.addMainMenuPlugin(
			this, //the plugin to execute on click
			new String[] {"Lab 3","Geometrie"}, //menu path
			"Envelope", //name
			false, //checkbox
			null, //icon
			null );
	}
	
	
	@Override
	public boolean execute(PlugInContext arg0) throws Exception {
		// TODO Auto-generated method stub
		Select select=new Select();
		Layer layer=select.readUserLayerChoice(arg0);
		
		FeatureSchema fs = new FeatureSchema();
		fs.addAttribute( "id", AttributeType.INTEGER );
		fs.addAttribute( "geometry", AttributeType.GEOMETRY );
		FeatureCollection fc = new FeatureDataset( fs );
		GeometryFactory gf = new GeometryFactory();
		int i = 0;
		for( Feature f : layer.getFeatureCollectionWrapper().getFeatures() ) {
			Geometry g = f.getGeometry().getEnvelope();
			Feature ff = new BasicFeature( fs );
			ff.setAttribute( 0, i++ );
			ff.setAttribute( 1, g );
			fc.add( ff );
		}
		arg0.addLayer( "Output", "Envelope"+layer.getName(), fc );
		return false;
	}

	
	

}
