package lab3;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.workbench.model.Layer;
import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.MultiInputDialog;
import com.vividsolutions.jump.workbench.ui.plugin.FeatureInstaller;

public class GetGeometryTypePlugin extends AbstractPlugIn {
	
	public Layer readUserLayerChoice(PlugInContext context) { //return il layer selezionato
		MultiInputDialog mid = new MultiInputDialog( context.getWorkbenchFrame(), this.getName(), true );
		String _nomelayer = "Scegli il layer da elaborare";
		mid.addLayerComboBox( _nomelayer, null, context.getLayerManager() );
		mid.setVisible( true );
		if( mid.wasOKPressed() == false ) return null;
		// se arrivo qua, l'utente ha cliccato ok:
		String layername = mid.getText( _nomelayer );
		//System.out.println( "readUserLayerChoice= "+layername );
		Layer layerSelezionato = context.getLayerManager().getLayer(layername);
		return layerSelezionato;
	}
	
	@Override
	public String getName() {
		return "Read Geometry Type";
	}

	@Override
	public void initialize(PlugInContext arg0) throws Exception {
		// TODO Auto-generated method stub
		FeatureInstaller featureInstaller = new FeatureInstaller(arg0.getWorkbenchContext());
		featureInstaller.addMainMenuPlugin(
			this, //the plugin to execute on click
			new String[] {"Lab 3","Geometrie"}, //menu path
			"GetGeometryType", //name
			false, //checkbox
			null, //icon
			null );
		
	}
	

	@Override
	public boolean execute(PlugInContext arg0) throws Exception {
		// TODO Auto-generated method stub
		Layer selezione= readUserLayerChoice(arg0); //l'utente sel il Layer
		System.out.println( "Hai selezionato il layer: "+selezione.getName());
		System.out.println( this.getName());
		int n=0;
		int[] numeri=new int[4];
		for(Feature f:selezione.getFeatureCollectionWrapper().getFeatures()) {
			n++;
			Geometry g=f.getGeometry();
			/*System.out.println("Geometry Type: "+g.getGeometryType()+" "+"Vector: "+g.getNumPoints());*/
			//conto il numero di elementi per ogni tipo
			
			/*switch(g.getGeometryType()) {
			case "Point":
					numeri[0]++;
				break;
			case "LineString":
				numeri[1]++;
			break;
			case "Polygon":
				numeri[2]++;
			break;
			default: numeri[3]++;
			
			}*/
			if( g instanceof Point ) numeri[0]++;
			else if( g instanceof LineString ) numeri[1]++;
			else if( g instanceof Polygon ) numeri[2]++;
			else numeri[3]++;
		}
			
		System.out.println("il layer contiene "+n+" geometrie");
		System.out.println("il layer contiene "+numeri[0]+" Point");
		System.out.println("il layer contiene "+numeri[1]+" LineString");
		System.out.println("il layer contiene "+numeri[2]+" Polygon");
		System.out.println("il layer contiene "+numeri[3]+" Altro");
		
		return true;
	}
	

}
