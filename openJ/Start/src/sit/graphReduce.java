package sit;

import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import Utility.Select;
import Utility.Alert;
import Utility.DbConnect;
import Utility.SpatialQ;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
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

public class graphReduce extends AbstractPlugIn {
    public double distanzaMassima(PlugInContext arg0,Layer layer_rimessa, Layer layer_segnalazioni ){
		double max_dist=0;
		double dist;
		Geometry g_rimessa=layer_rimessa.getFeatureCollectionWrapper().getFeatures().get(0).getGeometry();
		for (Feature f_segnalazioni : layer_segnalazioni.getFeatureCollectionWrapper().getFeatures()) {
			dist=g_rimessa.distance(f_segnalazioni.getGeometry());
			if(dist>max_dist){
				max_dist=dist;
			}
		}
		return max_dist;
	}
    public void VisBuffer(Geometry g_bomba,double distanza,PlugInContext context){
        Geometry buffer_bomba=g_bomba.buffer(distanza);
        FeatureSchema fs= new FeatureSchema();
        fs.addAttribute("id", AttributeType.INTEGER);
        fs.addAttribute("geometry", AttributeType.GEOMETRY);
        FeatureCollection fc=new FeatureDataset(fs);
        Feature f=new BasicFeature(fs);
        f.setAttribute(0, 1);
        f.setAttribute(1, buffer_bomba);
        fc.add(f);
        context.addLayer("Result", "BufferBomba_"+Double.toString(distanza),fc);
    }

    public Layer ResSpatialQ(Geometry g_bomba,Layer layer_A,PlugInContext context,double distanza){
        Geometry buffer_bomba=g_bomba.buffer(distanza);
        SpatialQ sp=new SpatialQ();
        ArrayList<Feature> res=sp.SpatialQuery(buffer_bomba, layer_A.getFeatureCollectionWrapper());
        System.out.println("res: "+res.size());
        
        //add layer
        FeatureSchema fs=layer_A.getFeatureCollectionWrapper().getFeatureSchema();
        FeatureCollection fc_res=new FeatureDataset(fs);
        fc_res.addAll(res);
		context.addLayer("Result", "risultato_SpatialQuery" , fc_res);
        // VisBuffer(layer_A.getFeatureCollectionWrapper().getFeatures().get(0).getGeometry(), distanzaMassima(arg0, layer_rimessa, layer_segnalazioni), arg0);
		return context.getLayerManager().getLayer("risultato_SpatialQuery");
    }

    public void addPointStrade(PlugInContext context, Layer layer) {
		HashMap<Coordinate,Integer> hm=new HashMap<>();
        for (Feature f : layer.getFeatureCollectionWrapper().getFeatures()) {
			Coordinate[] coordinate=f.getGeometry().getCoordinates();
			Coordinate sV=new Coordinate(coordinate[0].x,coordinate[0].y);
			Coordinate eV=new Coordinate(coordinate[coordinate.length-1].x,coordinate[coordinate.length-1].y);
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
		context.addLayer("Result", "punti",fc);
	}


    @Override
	public void initialize(PlugInContext arg0) throws Exception {
		FeatureInstaller featureInstaller = new FeatureInstaller(arg0.getWorkbenchContext());
		featureInstaller.addMainMenuPlugin(
			this, //the plugin to execute on click
			new String[] {"Esame" }, //menu path
			"riduzione", //name
			false, //checkbox
			null, //icon
			null );
	}
	@Override
	public boolean execute(PlugInContext arg0) throws Exception { 
        Select selected= new Select();
		List<Layer> s=selected.readUserLayerChoice(arg0);
		Layer layer_rimessa=s.get(0);
		Layer layer_strade=s.get(1);
		Layer layer_segnalazioni=s.get(2);
		VisBuffer(layer_rimessa.getFeatureCollectionWrapper().getFeatures().get(0).getGeometry(), distanzaMassima(arg0, layer_rimessa, layer_segnalazioni), arg0);
		ResSpatialQ(layer_rimessa.getFeatureCollectionWrapper().getFeatures().get(0).getGeometry(), layer_strade, arg0, distanzaMassima(arg0, layer_rimessa, layer_segnalazioni));
		// addPointStrade(arg0, ResSpatialQ(layer_rimessa.getFeatureCollectionWrapper().getFeatures().get(0).getGeometry(), layer_strade, arg0, distanzaMassima(arg0, layer_rimessa, layer_segnalazioni)));
		ResSpatialQ(layer_rimessa.getFeatureCollectionWrapper().getFeatures().get(0).getGeometry(), layer_strade, arg0, distanzaMassima(arg0, layer_rimessa, layer_segnalazioni));
		return false;
	}
    
}
