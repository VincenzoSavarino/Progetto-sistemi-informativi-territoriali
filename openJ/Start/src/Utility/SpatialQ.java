package Utility;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jump.feature.AttributeType;
import com.vividsolutions.jump.feature.BasicFeature;
import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.feature.FeatureCollection;
import com.vividsolutions.jump.feature.FeatureDataset;
import com.vividsolutions.jump.feature.FeatureSchema;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;

public class SpatialQ {
    
    public ArrayList<Feature> SpatialQuery (Geometry g,FeatureCollection fc){
        // primary filter (usando envelope)
        List list = fc.query( g.getEnvelopeInternal() );
        ArrayList<Feature> res = new ArrayList<>();
        // secondary filter (controllo ogni candidato)
        for( Object o : list ) {
            Feature f = (Feature) o;
            Geometry g2 = f.getGeometry();
            if( g2.disjoint(g) )
            continue;
            res.add( f );
        }
        return res;
    }
    

    public void VisBuffer(Geometry geometry,double distanza,PlugInContext context){
        Geometry buffeGeometry=geometry.buffer(distanza);
        FeatureSchema fs= new FeatureSchema();
        fs.addAttribute("id", AttributeType.INTEGER);
        fs.addAttribute("geometry", AttributeType.GEOMETRY);
        FeatureCollection fc=new FeatureDataset(fs);
        Feature f=new BasicFeature(fs);
        f.setAttribute(0, 1);
        f.setAttribute(1, buffeGeometry);
        fc.add(f);
        context.addLayer("Result", "Buffer_"+Double.toString(distanza),fc);
    }

    public void VisEnv(Geometry geometry,double distanza,PlugInContext context){
        Geometry envelopeGeometry=geometry.buffer(distanza).getEnvelope();
        FeatureSchema fs= new FeatureSchema();
        fs.addAttribute("id", AttributeType.INTEGER);
        fs.addAttribute("geometry", AttributeType.GEOMETRY);
        FeatureCollection fc=new FeatureDataset(fs);
        Feature f=new BasicFeature(fs);
        f.setAttribute(0, 1);
        f.setAttribute(1, envelopeGeometry);
        fc.add(f);
        context.addLayer("Result", "Envelope_"+Double.toString(distanza),fc);
    }
    
    
}
