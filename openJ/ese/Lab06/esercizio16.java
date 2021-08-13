package Lab06;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
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


import Utility.SpatialQ;

// Creare un nuovo layer con un punto (la bomba)
// • Creare un plug-in che:
// – Chiede all’utente di scegliere due layer (A,B)
// – Legge la posizione della bomba come le
// coordinate della prima geometria del layer B
// – Trova gli elementi del layer A entro 250 metri
// dalla bomba usando la JTS

public class esercizio16 extends AbstractPlugIn{

    @Override
	public void initialize(PlugInContext context) throws Exception {
		
		FeatureInstaller featureInstaller = new FeatureInstaller( context.getWorkbenchContext() ); 
		featureInstaller.addMainMenuPlugin( 
				this,    //the plugin to execute on click 
				new String[] {"Corso GIS", "Lab06"},   //menu path 
				"Esercizio16",   //name   
				false,    //checkbox 
				null,    //icon 
				null    );   //enable check	
		
	}


    @Override
    public boolean execute(PlugInContext context) throws Exception {
        ArrayList<Object> layers=TwoReadUserLayerChoice(context);
        Layer layer_bomba=(Layer) layers.get(1);
        Layer layer_A=(Layer) layers.get(0);
        Geometry g_bomba=layer_bomba.getFeatureCollectionWrapper().getFeatures().get(0).getGeometry();
        Coordinate c_bomba=g_bomba.getCoordinate();
        double distanza_da_eva=(Double)layers.get(2);
        System.out.println("coordinate bomba: x= "+ c_bomba.x+" y= "+c_bomba.y);
        System.out.println("distanza letta: "+distanza_da_eva);
        ResSpatialQ(g_bomba, layer_A,context,distanza_da_eva);

        //ResConIntersect(g_bomba, layer_A, context, distanza_da_eva);
        return false;
    }


    public ArrayList<Object> TwoReadUserLayerChoice(PlugInContext context) {
        MultiInputDialog mid = new MultiInputDialog( context.getWorkbenchFrame(), this.getName(), true );
        String _nomelayerA = "Scegli il Layer A";
        String _nomelayerB = "Scegli il Layer B (Bomba)";
        String _nomedist="inserisci la dis";
        mid.addLayerComboBox( _nomelayerA, null, context.getLayerManager());
        mid.addLayerComboBox( _nomelayerB, null, context.getLayerManager());
        mid.addDoubleField(_nomedist, 2 , 10);
        mid.setVisible( true );
        if( mid.wasOKPressed() == false ) return null;
        // se arrivo qua, l'utente ha cliccato ok:
        String layernameA = mid.getText( _nomelayerA );
        String layernameB = mid.getText( _nomelayerB );
        double dist=mid.getDouble(_nomedist);
        Layer layerA = context.getLayerManager().getLayer(layernameA);
        ArrayList<Object> layerSelezionati=new ArrayList<>();
        layerSelezionati.add(layerA);
        Layer layerB = context.getLayerManager().getLayer(layernameB);
        layerSelezionati.add(layerB);
        layerSelezionati.add(dist);
        return layerSelezionati;   
    }
    public void ResSpatialQ(Geometry g_bomba,Layer layer_A,PlugInContext context,double distanza){
        Geometry buffer_bomba=g_bomba.buffer(distanza);
        SpatialQ sp=new SpatialQ();
        ArrayList<Feature> res=sp.SpatialQuery(buffer_bomba, layer_A.getFeatureCollectionWrapper());
        System.out.println("res: "+res.size());
        
        //add layer
        FeatureSchema fs=layer_A.getFeatureCollectionWrapper().getFeatureSchema();
        FeatureCollection fc_res=new FeatureDataset(fs);
        fc_res.addAll(res);
		context.addLayer("Result", "risultato_"+Double.toString(distanza) , fc_res);
        VisEnv(g_bomba, distanza, context);
    }
   


    public void ResConIntersect(Geometry g_bomba,Layer layer_A,PlugInContext context,double distanza) {
        Geometry buffer_bomba=g_bomba.buffer(distanza);
        FeatureSchema fs= layer_A.getFeatureCollectionWrapper().getFeatureSchema();
        FeatureCollection fc=new FeatureDataset(fs);
        List<Feature> lista=new ArrayList<>();
        for (Feature f : layer_A.getFeatureCollectionWrapper().getFeatures()) {
            if(f.getGeometry().intersects(buffer_bomba)){
                lista.add(f);
            }
        }
        fc.addAll(lista);
        context.addLayer("Result", "risultato_"+Double.toString(distanza) , fc);
        VisBuffer(g_bomba,distanza,context);



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

    public void VisEnv(Geometry g_bomba,double distanza,PlugInContext context){
        Geometry enve_bomba=g_bomba.buffer(distanza).getEnvelope();
        FeatureSchema fs= new FeatureSchema();
        fs.addAttribute("id", AttributeType.INTEGER);
        fs.addAttribute("geometry", AttributeType.GEOMETRY);
        FeatureCollection fc=new FeatureDataset(fs);
        Feature f=new BasicFeature(fs);
        f.setAttribute(0, 1);
        f.setAttribute(1, enve_bomba);
        fc.add(f);
        context.addLayer("Result", "EnvBomba_"+Double.toString(distanza),fc);
    }

}
