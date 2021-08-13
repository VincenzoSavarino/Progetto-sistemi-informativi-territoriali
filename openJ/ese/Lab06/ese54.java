package Lab06;

import java.util.ArrayList;

import com.vividsolutions.jts.awt.PointShapeFactory.Point;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.plugin.FeatureInstaller;

import org.openjump.core.graph.delauneySimplexInsert.DTriangulationForJTS;
import org.openjump.core.graph.delauneySimplexInsert.DelaunayTriangulation;

public class ese54 extends AbstractPlugIn {

    @Override
	public void initialize(PlugInContext context) throws Exception {
		
		FeatureInstaller featureInstaller = new FeatureInstaller( context.getWorkbenchContext() ); 
		featureInstaller.addMainMenuPlugin( 
				this,    //the plugin to execute on click 
				new String[] {"Corso GIS", "Lab06"},   //menu path 
				"Esercizio54",   //name   
				false,    //checkbox 
				null,    //icon 
				null    );   //enable check	
		
	}
    
    @Override
    public boolean execute(PlugInContext arg0) throws Exception {
        
        return false;

    }
}
