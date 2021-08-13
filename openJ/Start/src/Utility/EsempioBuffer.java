package Utility;



import java.util.ArrayList;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.operation.buffer.BufferOp;
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

/*
 *  Facciamo un esempio: un plugin che chiede all’utente di selezionare un layer, definire un valore
 *  e calcola un buffer di tale larghezza su tutte le geometrie presenti nel layer.
 *  Usiamolo in successione al plugin che crea geometrie random per un effetto più vario!
Poiché dobbiamo chiedere all’utente un parametro, modifichiamo il codice della funzione 
readUserLayerChoice e inseriamolo nel metodo execute() che sarà così:*/

public class EsempioBuffer extends AbstractPlugIn{

	@Override
	public void initialize(PlugInContext arg0) throws Exception {
		FeatureInstaller featureInstaller = new FeatureInstaller(arg0.getWorkbenchContext());
		featureInstaller.addMainMenuPlugin(
			this, //the plugin to execute on click
			new String[] {"Lab 3","Geometrie"}, //menu path
			"Buffer", //name
			false, //checkbox
			null, //icon
			null );
	}

	@Override
	public boolean execute(PlugInContext context) throws Exception {
		// TODO Auto-generated method stub
		System.out.println( this.getName() + " started!" );
		// faccio scegliere un layer e il valore del parametro
		MultiInputDialog mid = new MultiInputDialog( context.getWorkbenchFrame(),
		this.getName(), true );
		String _nomelayer = "Scegli il layer da elaborare";
		mid.addLayerComboBox( _nomelayer, null, context.getLayerManager() );
		String _parametro = "Valore parametro:";
		mid.addDoubleField( _parametro, 1, 3 );
		mid.setVisible( true ); // dialog modale
		if( mid.wasOKPressed() == false ) return false;
		double param = mid.getDouble( _parametro );
		String layername = mid.getText( _nomelayer );
		Layer layer = context.getLayerManager().getLayer(layername);
		FeatureSchema fs = new FeatureSchema();
		fs.addAttribute( "id", AttributeType.INTEGER );
		fs.addAttribute( "geometry", AttributeType.GEOMETRY );
		FeatureCollection fc = new FeatureDataset( fs );
		GeometryFactory gf = new GeometryFactory();
		int i = 0;
		/*for( Feature f : layer.getFeatureCollectionWrapper().getFeatures() ) {
			Geometry g = f.getGeometry().buffer( param );
			Feature ff = new BasicFeature( fs );
			ff.setAttribute( 0, i++ );
			ff.setAttribute( 1, g );
			fc.add( ff );
		}*/
		for( Feature f : layer.getFeatureCollectionWrapper().getFeatures() ) {
			Geometry g = f.getGeometry().buffer( param );
		
			Feature ff = new BasicFeature( fs );
			ff.setAttribute( 0, i++ );
			ff.setAttribute( 1, g );
			fc.add( ff );
		}
		context.addLayer( "Output", "Buffer"+layer.getName(), fc );
		return false;
	}
	
	
	

}
