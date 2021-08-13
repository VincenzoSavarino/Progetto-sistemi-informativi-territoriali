package esempi;

import com.vividsolutions.jump.feature.AttributeType;

import com.vividsolutions.jump.feature.FeatureCollection;
import com.vividsolutions.jump.feature.FeatureDataset;
import com.vividsolutions.jump.feature.FeatureSchema;
import com.vividsolutions.jump.workbench.model.Layer;
import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.plugin.FeatureInstaller;

public class copyLayerPlugin extends AbstractPlugIn {

	@Override
	public void initialize(PlugInContext context) throws Exception {
		// TODO Auto-generated method stub
		FeatureInstaller featureInstaller = new FeatureInstaller(context.getWorkbenchContext());
		featureInstaller.addMainMenuPlugin(
		this, //the plugin to execute on click
		new String[] {"Corso GIS","Layer"}, //menu path
		"Copy Layer", //name
		false, //checkbox
		null, //icon
		null );
	}

	
	@Override
	public boolean execute(PlugInContext arg0) throws Exception {
		// TODO Auto-generated method stub
		System.out.println( "Copy Layer!" );
		String layername = "Nuovo";
		Layer layer = arg0.getLayerManager().getLayer(layername);
		FeatureCollection layer_fc = layer.getFeatureCollectionWrapper(); //righe
		FeatureSchema fs = new FeatureSchema(); //colonne
		System.out.println("copia");
		for( int i=0; i<layer_fc.getFeatureSchema().getAttributeCount(); i++ ) {
				String att_name = layer_fc.getFeatureSchema().getAttributeName(i);
				AttributeType att_type = layer_fc.getFeatureSchema().getAttributeType(i);
				fs.addAttribute( att_name, att_type );
		}//schema 
			FeatureCollection fc = new FeatureDataset( fs );
			
			for( int i=0; i<layer_fc.getFeatures().size(); i++ ) {
				fc.add(layer_fc.getFeatures().get(i).clone( true ) );
			}
			
			/*for(Feature f:layer_fc.getFeatures()) {
				fc.add(f.clone(true));
			}*/
			arg0.getLayerManager().addLayer("Output",  layername+"_case", fc );
			
		return false;
		
		
	}
	

}
