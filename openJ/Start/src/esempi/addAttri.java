package esempi;

import java.util.Arrays;
import java.util.Date;

import com.vividsolutions.jump.feature.AttributeType;
import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.workbench.model.Layer;
import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.plugin.FeatureInstaller;

public class addAttri extends AbstractPlugIn{
	
	public void initialize(PlugInContext context) throws Exception {
		// TODO Auto-generated method stub
		FeatureInstaller featureInstaller = new FeatureInstaller(context.getWorkbenchContext());
		featureInstaller.addMainMenuPlugin(
			this, //the plugin to execute on click
			new String[] {"Corso GIS","Layer"}, //menu path
			"add attri", //name
			false, //checkbox
			null, //icon
			null );
	}

	@Override
	public boolean execute(PlugInContext context) throws Exception {
		String layername = "Nuovo";
		Layer layer = context.getLayerManager().getLayer(layername);
		layer.getFeatureCollectionWrapper().getFeatureSchema().addAttribute("id", AttributeType.DATE);
		int att_num = layer.getFeatureCollectionWrapper().getFeatureSchema().getAttributeCount();
		int att_idx = att_num -1;
		Date data = new Date();
		for( Feature f : layer.getFeatureCollectionWrapper().getFeatures() ) {
			Object[] ar = Arrays.copyOf( f.getAttributes(), att_num );
			f.setAttributes( ar );
			f.setAttribute( att_idx, data );
		}
		
		
		
		return false;
		// TODO Auto-generated method stub
		
	}
	
	

}
