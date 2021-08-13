package esempi;

import com.vividsolutions.jump.feature.AttributeType;

import com.vividsolutions.jump.feature.FeatureCollection;
import com.vividsolutions.jump.feature.FeatureSchema;
import com.vividsolutions.jump.workbench.model.Layer;
import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.plugin.FeatureInstaller;

public class DescribeLayerPlugin extends AbstractPlugIn{

	@Override
	public void initialize(PlugInContext context) throws Exception {
		// TODO Auto-generated method stub
		FeatureInstaller featureInstaller = new FeatureInstaller(context.getWorkbenchContext());
		featureInstaller.addMainMenuPlugin(
		this, //the plugin to execute on click
		new String[] {"Corso GIS","Layer"}, //menu path
		"Describe Layer", //name
		false, //checkbox
		null, //icon
		null );
	}

	
	@Override
	public boolean execute(PlugInContext arg0) throws Exception {
		// TODO Auto-generated method stub
		System.out.println( "Describe Layer!" );
		String layername = "case";
		Layer layer = arg0.getLayerManager().getLayer( layername );
		FeatureCollection fc = layer.getFeatureCollectionWrapper();
		FeatureSchema fs = fc.getFeatureSchema();
		System.out.println( "Il layer ha "+fc.size()+" righe" ); 
		System.out.println( "Lo schema ha "+fs.getAttributeCount()+" colonne" );
		for( int i=0; i<fs.getAttributeCount(); i++ ) {
				String att_name = fs.getAttributeName(i);
				AttributeType att_type = fs.getAttributeType(i);
				System.out.println( "Colonna "+i+"\t"+att_name+"\t"+att_type+"\t"+att_type.toJavaClass() );
		}
	
		/*for(Feature str:fc.getFeatures()){
			
			System.out.println("geom "+str.getGeometry());
			
		}*/
		
		return false;
	}
}
