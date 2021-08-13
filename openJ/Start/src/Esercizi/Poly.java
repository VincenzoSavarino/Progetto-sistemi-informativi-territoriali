package Esercizi;


/*
 * creare un plugin che calcola un numero N di posizioni random e per ognuna di queste generare
  * un poligono regolare di M lati.
 * */


import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jump.feature.AttributeType;
import com.vividsolutions.jump.feature.BasicFeature;
import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.feature.FeatureCollection;
import com.vividsolutions.jump.feature.FeatureDataset;
import com.vividsolutions.jump.feature.FeatureSchema;
import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.plugin.FeatureInstaller;

public class Poly extends AbstractPlugIn{
	
	public void polyFisso(PlugInContext arg0) {
		GeometryFactory gf = new GeometryFactory();
		Coordinate[] pts=new Coordinate[4];
		pts[0]=new Coordinate( -10, -10 );
		
		pts[1]=new Coordinate(10, 10);
		pts[2]=new Coordinate(10, 20);
		pts[3]=new Coordinate(-10, -10);
		final Polygon polygon1=gf.createPolygon(pts);
		/*final Polygon polygon = gf.createPolygon(new LinearRing(new CoordinateArraySequence(points
		  .toArray(new Coordinate[points.size()])), gf), null);*/
		FeatureSchema fs = new FeatureSchema();
		fs.addAttribute( "id", AttributeType.INTEGER );
		fs.addAttribute( "geometry", AttributeType.GEOMETRY );
		FeatureCollection fc = new FeatureDataset( fs );
		Feature f = new BasicFeature( fs );
		f.setAttribute( 0, 1 );
		f.setAttribute( 1, polygon1);
		fc.add( f );
		arg0.addLayer( "Output", "poly", fc );
	}
	
	public void polyProva(PlugInContext arg0) {
		GeometryFactory gf = new GeometryFactory();
		Coordinate[] pts=new Coordinate[5];
		Coordinate centro=new Coordinate(10,10);
		pts[0]=new Coordinate(centro.x+20,centro.y+20);
		pts[1]=new Coordinate(-(centro.x+20),centro.y+20);
		
		pts[2]=new Coordinate(-(centro.x+20),-(centro.y+20));
		pts[3]=new Coordinate(centro.x+20,-(centro.y+20));
		pts[4]=new Coordinate(centro.x+20,centro.y+20);
		final Polygon polygon1=gf.createPolygon(pts);
		final Point p=gf.createPoint(centro);
		FeatureSchema fs = new FeatureSchema();
		fs.addAttribute( "id", AttributeType.INTEGER );
		fs.addAttribute( "geometry", AttributeType.GEOMETRY );
		FeatureCollection fc = new FeatureDataset( fs );
		Feature f = new BasicFeature( fs );
		Feature fd = new BasicFeature( fs );
		f.setAttribute( 0, 1 );
		f.setAttribute( 1, polygon1);
		fd.setAttribute( 0, 1 );
		fd.setAttribute( 1, p);
		fc.add( f );
		fc.add( fd );
		arg0.addLayer( "Output", "poly", fc );
	}
	
	@Override
	public void initialize(PlugInContext arg0) throws Exception {
		FeatureInstaller featureInstaller = new FeatureInstaller(arg0.getWorkbenchContext());
		featureInstaller.addMainMenuPlugin(
			this, //the plugin to execute on click
			new String[] {"Lab 3","Geometrie"}, //menu path
			"crea Poly", //name
			false, //checkbox
			null, //icon
			null );
	}
	
	public void prova1(PlugInContext arg0) {
		int m=5;
		GeometryFactory gf = new GeometryFactory();
		Coordinate[] pts=new Coordinate[m];
		Coordinate punto=new Coordinate(10,10);
		FeatureSchema fs = new FeatureSchema();
		fs.addAttribute( "id", AttributeType.INTEGER );
		fs.addAttribute( "geometry", AttributeType.GEOMETRY );
		FeatureCollection fc = new FeatureDataset( fs );
		Feature f = new BasicFeature( fs );
		System.out.println("len pts: "+pts.length);
		pts[0]=punto;
		int maxval = 100; // max valore x o y
		
		pts[4]=punto;
		for( int i=1; i<m-1; i++ ) {
			
			double x = Math.random() * maxval;
			double y = Math.random() * maxval;
			pts[i]=new Coordinate(x,y);
		}
		Geometry g = gf.createPolygon( pts );
		System.out.println("aaa");
		f.setAttribute( 0, 1 );
		f.setAttribute( 1, g );
		fc.add( f );
		/*for(int i=0;i<n;i++) {
			centro[i]=new Coordinate(10,10);
		}
		for(int i=0;i<n;i++) {
			for(int j=0;j<(m-1)/2;j++) {
				pts[j]=new Coordinate(centro[i].x+j, centro[i].y+j);
				pts[j+(m-1)/2]=new Coordinate(-(centro[i].x+j+(m-1)/2), centro[i].y+j+(m-1)/2);
				System.out.println("m "+j+" x "+pts[j].x+" y "+pts[j].y);
				System.out.println("m "+(j+(m-1)/2)+" x "+pts[j+(m-1)/2].x+" y "+pts[j+(m-1)/2].y);
			}
			pts[pts.length-1]=pts[0];
			final Polygon polygon1=gf.createPolygon(pts);
			f.setAttribute( 0, 1 );
			f.setAttribute( 1, polygon1);
			fc.add( f );
		}*/
		

		arg0.addLayer( "Output", "poly", fc );
	}

	@Override
	public boolean execute(PlugInContext arg0) throws Exception {
		// TODO Auto-generated method stub
		int m=5;
		int n=3; //centro
		
		
		Coordinate[] punto=new Coordinate[n];
		FeatureSchema fs = new FeatureSchema();
		fs.addAttribute( "id", AttributeType.INTEGER );
		fs.addAttribute( "geometry", AttributeType.GEOMETRY );
		FeatureCollection fc = new FeatureDataset( fs );
		GeometryFactory gf = new GeometryFactory();
		
		int maxval = 100; // max valore x o y
		for(int i=0;i<n;i++) {
			double x = Math.random() * maxval;
			double y = Math.random() * maxval;
			punto[i]=new Coordinate(x,y);
			
		}
	
		
			
		/*pts[0]=punto[0];
		pts[1]=new Coordinate(pts[0].x,-(pts[0].y));
		pts[2]=new Coordinate(-(pts[1].x),pts[1].y);
		pts[3]=new Coordinate(pts[2].x,-(pts[2].y));
		pts[4]=punto[0];*/
		
		
		/*for(int i=0;i<n;i++) {
			Coordinate[] pts=new Coordinate[m+1];
			pts[0]=punto[i];
			pts[pts.length-1]=punto[i];
			System.out.println("i: "+i+"vettore punti"+" x: "+punto[i].x+" y: "+punto[i].y);
			for(int j=1;j<m;j++) {
				if(j%2==0)
				{
					pts[j]=new Coordinate(pts[j-1].x,-(pts[j-1].y));
				}
				else {
					pts[j]=new Coordinate(-(pts[j-1].x),pts[j-1].y);
				}
				System.out.println("j: "+j+" x: "+pts[j].x+" y: "+pts[j].y);
			}
			System.out.println(" x: "+pts[1].x+" y: "+pts[1].y);
			for(Coordinate cc:pts) {
				System.out.println("cc: "+cc.toString());
			}
			Feature f = new BasicFeature( fs );
			Geometry poly = gf.createPolygon( pts );
			
			f.setAttribute( 0, i );
			f.setAttribute( 1, poly);
			fc.add( f );
		}*/
		
		
		arg0.addLayer( "Output", "poly", fc );
		return false;
	}

	
	
	

}
