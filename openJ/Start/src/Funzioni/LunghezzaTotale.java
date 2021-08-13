package Funzioni;
import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.workbench.model.Layer;
import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.plugin.FeatureInstaller;

import Utility.Select;
import Utility.Alert;

public class LunghezzaTotale extends AbstractPlugIn{
	
	public void initialize(PlugInContext arg0) throws Exception {
		FeatureInstaller featureInstaller = new FeatureInstaller(arg0.getWorkbenchContext());
		featureInstaller.addMainMenuPlugin(
			this, //the plugin to execute on click
			new String[] {"Lab 3","Geometrie"}, //menu path
			"Lunghezza totale", //name
			false, //checkbox
			null, //icon
			null );
	}
	@Override
	public boolean execute(PlugInContext arg0) throws Exception {
		// TODO Auto-generated method stub
		Select selectLayer =new Select();
		Layer layer =selectLayer.readUserLayerChoice(arg0);
		if( layer == null ) return false;
		double len=0;
		for(Feature f:layer.getFeatureCollectionWrapper().getFeatures())
		{
			len += f.getGeometry().getLength();
		}
		Alert al=new Alert();
		al.pannello(len,"nome", arg0);
		return false;
	}
}
