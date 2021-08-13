package Lab06;


import java.util.ArrayList;
import java.util.List;


import com.vividsolutions.jts.geom.Geometry;

import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.feature.FeatureCollection;
import com.vividsolutions.jump.feature.FeatureDataset;
import com.vividsolutions.jump.feature.FeatureSchema;
import com.vividsolutions.jump.workbench.model.Layer;
import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.plugin.FeatureInstaller;



import Utility.SpatialQ;





public class esercizio11 extends AbstractPlugIn{
	

	@Override
	public void initialize(PlugInContext context) throws Exception {
		
		FeatureInstaller featureInstaller = new FeatureInstaller( context.getWorkbenchContext() ); 
		featureInstaller.addMainMenuPlugin( 
				this,    //the plugin to execute on click 
				new String[] {"Corso GIS", "Lab06"},   //menu path 
				"Esercizio11",   //name   
				false,    //checkbox 
				null,    //icon 
				null    );   //enable check	
		
	}

	@Override
	public boolean execute(PlugInContext arg0) throws Exception {
		//Ese11ConQuery(arg0);
		provaMetodo(arg0);
		return false;
	}

	public void Ese11SenzaQuery(PlugInContext context) {
		int numero_ponti=0;
		Layer layer_idro=context.getLayerManager().getLayer("IDROGR_A");
		Layer layer_strade=context.getLayerManager().getLayer("ASTEVIA");
		for (Feature idro : layer_idro.getFeatureCollectionWrapper().getFeatures()){
			Geometry g_idro=idro.getGeometry();
			for (Feature strada : layer_strade.getFeatureCollectionWrapper().getFeatures()) {

				if (g_idro.intersects(strada.getGeometry())) {
					numero_ponti++;
				}
			}
		}
		System.out.println("numero di stade: "+numero_ponti);
	}

	public void Ese11ConQuery(PlugInContext context) {
		Layer layer_fiumi=context.getLayerManager().getLayer("IDROGR_A");
		Layer layer_strade=context.getLayerManager().getLayer("ASTEVIA");

		System.out.println("fiumi: "+layer_fiumi.getFeatureCollectionWrapper().size());
		//geom del fiume
		Feature fiume=layer_fiumi.getFeatureCollectionWrapper().getFeatures().get(7);

			Geometry g_fiume=fiume.getGeometry();

			FeatureCollection fc=layer_strade.getFeatureCollectionWrapper();
			System.out.println("strade: "+fc.size());
			
			//facciamo una copia 
			FeatureSchema fs=layer_strade.getFeatureCollectionWrapper().getFeatureSchema();
			FeatureCollection fc_res=new FeatureDataset(fs);
			List<Feature> lista=fc.query(g_fiume.getEnvelopeInternal());
			System.out.println("dimlist: "+lista.size());
			fc_res.addAll(lista);
			ArrayList<Feature> res = new ArrayList<>();
			for (Feature ff : lista) {
				if( fiume.getGeometry().disjoint(ff.getGeometry()) )
						continue;
					res.add( ff );
			}
			FeatureCollection new_fc_res=new FeatureDataset(fs);
			new_fc_res.addAll(res);
			context.addLayer("Result", "risultato", new_fc_res);

	}

	public void provaMetodo(PlugInContext context) {
		Layer layer_fiumi=context.getLayerManager().getLayer("IDROGR_A");
		Layer layer_strade=context.getLayerManager().getLayer("ASTEVIA");

		System.out.println("fiumi: "+layer_fiumi.getFeatureCollectionWrapper().size());
		//geom del fiume
		Feature fiume=layer_fiumi.getFeatureCollectionWrapper().getFeatures().get(7);

		Geometry g_fiume=fiume.getGeometry();
		FeatureCollection fc=layer_strade.getFeatureCollectionWrapper();
		SpatialQ sq=new SpatialQ();
		ArrayList<Feature> res= sq.SpatialQuery(g_fiume, fc);
		FeatureSchema fs=layer_strade.getFeatureCollectionWrapper().getFeatureSchema();
		FeatureCollection fc_res=new FeatureDataset(fs);
		fc_res.addAll(res);
		context.addLayer("Result", "risultato", fc_res);
	}
}

