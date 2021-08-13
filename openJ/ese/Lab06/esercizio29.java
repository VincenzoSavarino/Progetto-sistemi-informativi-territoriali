package Lab06;


import java.util.ArrayList;
import java.util.HashMap;

import com.vividsolutions.jts.geom.Coordinate;

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

import Utility.SpatialQ;

public class esercizio29 extends AbstractPlugIn{
    @Override
	public void initialize(PlugInContext context) throws Exception {
		
		FeatureInstaller featureInstaller = new FeatureInstaller( context.getWorkbenchContext() ); 
		featureInstaller.addMainMenuPlugin( 
				this,    //the plugin to execute on click 
				new String[] {"Corso GIS", "Lab06"},   //menu path 
				"Esercizio29",   //name   
				false,    //checkbox 
				null,    //icon 
				null    );   //enable check	
		
	}

	@Override
	public boolean execute(PlugInContext context) throws Exception {
        //Select select=new Select();
		Layer layer=context.getLayerManager().getLayer("Nuovo");
		//EseConHash(context,layer);
        //Layer layer=select.readUserLayerChoice(context);
		EseConSpatialQuery(layer,context);
		
		
		return false;
	}

	public void EseConHash(PlugInContext context,Layer layer) {
		HashMap<Coordinate,Integer> hm=new HashMap<>();
        for (Feature f : layer.getFeatureCollectionWrapper().getFeatures()) {
			Coordinate[] coordinate=f.getGeometry().getCoordinates();
			Coordinate sV=new Coordinate(coordinate[0].x,coordinate[0].y);
			Coordinate eV=new Coordinate(coordinate[coordinate.length-1].x,coordinate[coordinate.length-1].y);
			System.out.println("vertice iniziale: "+sV+" vertice finale: "+eV);
			// if(hm.containsKey(eV)){
			// 	int incre=hm.get(eV);
			// 	incre++;
			// 	hm.replace(eV, incre);
			// }
			// else{
			// 	hm.put(eV, 1);
			// 	hm.put(sV, 1);
			// }
			//controllo null
			if(hm.get(sV) != null){
				int incre=hm.get(sV);
				incre++;
				hm.replace(sV, incre);
			}else{
				hm.put(sV, 1);
			}
			if (hm.get(eV) != null) {
				int incre=hm.get(eV);
				incre++;
				hm.replace(eV, incre);
			}else{
				hm.put(eV, 1);
			}
        }
		
		System.out.println("hm: "+hm);

		

		//prova
		// FeatureSchema fs= new FeatureSchema();
        // fs.addAttribute("id", AttributeType.INTEGER);
        // fs.addAttribute("geometry", AttributeType.GEOMETRY);
		// FeatureCollection fc=new FeatureDataset(fs);
		// Feature ff=new BasicFeature(fs);
		// Coordinate[] p=layer.getFeatureCollectionWrapper().getFeatures().get(0).getGeometry().getCoordinates();
		// ff.setAttribute(0, 1);
		// GeometryFactory gf=new GeometryFactory();
		// Geometry poi= gf.createPoint((p[0]));
		// ff.setAttribute(1, poi);
		// fc.add(ff);
		// System.out.println("hm get: "+hm.keySet());
		// context.addLayer("Result", "prova",fc);


		//prova 
		FeatureSchema fs= new FeatureSchema();
        fs.addAttribute("geometry", AttributeType.GEOMETRY);
		fs.addAttribute("id", AttributeType.INTEGER);
		fs.addAttribute("grado", AttributeType.INTEGER);
		GeometryFactory gf=new GeometryFactory();
		FeatureCollection fc=new FeatureDataset(fs);

		int id=0;
		for (Coordinate c : hm.keySet()) {
			Feature f=new BasicFeature(fs);
			id++;
			f.setAttribute(0, gf.createPoint(c));
			f.setAttribute(1, id);
			f.setAttribute(2, hm.get(c));
			fc.add(f);
		}
		context.addLayer("Result", "prova",fc);		
	}
public void EseConSpatialQuery(Layer layer,PlugInContext context){
	GeometryFactory gf=new GeometryFactory();
	int id=0;
	SpatialQ sp =new SpatialQ();
	FeatureSchema fs_point =new FeatureSchema();
	fs_point.addAttribute("id", AttributeType.INTEGER);
	fs_point.addAttribute("geometry", AttributeType.GEOMETRY);
	FeatureCollection fc_point= new FeatureDataset(fs_point);
	//ADD POINT IN LAYER
	for (Feature f : layer.getFeatureCollectionWrapper().getFeatures()){
		id++;
		Coordinate[] v=f.getGeometry().getCoordinates();
		System.out.println("Coordinate 1: "+ v[0]);
		System.out.println("Coordinate 2: "+ v[v.length-1]);
		Feature f_point =new BasicFeature(fs_point);
		f_point.setAttribute(0, id);
		f_point.setAttribute(1, gf.createPoint(v[0]));
		fc_point.add(f_point);
		f_point =new BasicFeature(fs_point);
		f_point.setAttribute(0, id);
		f_point.setAttribute(1, gf.createPoint(v[v.length-1]));
		fc_point.add(f_point);
	}
	//posso continuare ad uti fc_point
	context.addLayer("Result", "Layer punti", fc_point);
	//prova con selezione del nuovo layer creato 
	FeatureCollection fc_punti=context.getLayerManager().getLayer("Layer punti").getFeatureCollectionWrapper();
	ArrayList<Feature> res_punti=new ArrayList<>();
	
	
	for (Feature f : layer.getFeatureCollectionWrapper().getFeatures()) {
		res_punti=sp.SpatialQuery(f.getGeometry(),fc_punti);
		System.out.println("size: "+ res_punti.size());
	}
}
	
    
}
