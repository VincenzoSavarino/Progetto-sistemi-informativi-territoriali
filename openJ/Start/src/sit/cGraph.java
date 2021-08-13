package sit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Map.*;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.planargraph.PlanarGraph;
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
import com.vividsolutions.jump.workbench.ui.renderer.style.CircleLineStringEndpointStyle.Start;

import Utility.Select;
import sit.Graph;

public class cGraph extends AbstractPlugIn{
	// Graph graph = new Graph();
	// HashMap<Coordinate,Integer> hm=new HashMap<>();
	// HashMap<Coordinate,Node> map_nodi=new HashMap<>();
    @Override
	public void initialize(PlugInContext arg0) throws Exception {
		FeatureInstaller featureInstaller = new FeatureInstaller(arg0.getWorkbenchContext());
		featureInstaller.addMainMenuPlugin(
			this, //the plugin to execute on click
			new String[] {"Esame" }, //menu path
			"cGraph", //name
			false, //checkbox
			null, //icon
			null );
	}
	// @Override
	// public boolean execute(PlugInContext arg0) throws Exception {
	// 	//newLayer(arg0);
	// 	Node nodeA = new Node(0);
	// 	Node nodeB = new Node(1);
	// 	Node nodeC = new Node(2);
	// 	Node nodeD = new Node(3); 
	// 	Node nodeE = new Node(4);
	// 	Node nodeF = new Node(5);

	// 	nodeA.addDestination(nodeB, 10);
	// 	nodeA.addDestination(nodeC, 15);

	// 	nodeB.addDestination(nodeD, 12);
	// 	nodeB.addDestination(nodeF, 15);

	// 	nodeC.addDestination(nodeE, 10);

	// 	nodeD.addDestination(nodeE, 2);
	// 	nodeD.addDestination(nodeF, 1);

	// 	nodeF.addDestination(nodeE, 5);

	// 	Graph graph = new Graph();

	// 	graph.addNode(nodeA);
	// 	graph.addNode(nodeB);
	// 	graph.addNode(nodeC);
	// 	graph.addNode(nodeD);
	// 	graph.addNode(nodeE);
	// 	graph.addNode(nodeF);


	// 	graph = Dijkstra.calculateShortestPathFromSource(graph, nodeA);
    //     for (Node n : graph.getNodes()) {
			
	// 		if(n.getName()==4){
	// 			System.out.println(n.getName()+" dist: "+n.getDistance());
	// 			for (Node sp : n.getShortestPath()) {
	// 				System.out.println("sp:"+ sp.getName());
	// 			}
	// 		}

	// 	}
	// 	//LineString(arg0);
	// 	return false;
	// }
	@Override
	public boolean execute(PlugInContext arg0) throws Exception {
		//newLayer(arg0);
		
		Layer strade=arg0.getLayerManager().getLayer("Strade3857");
		creaPunti(arg0, strade);

		return false;
	}

	public void creaPunti(PlugInContext context, Layer layer) {
		HashMap<Node,List<Feature>> map_nodi_feature=new HashMap<>();
		HashMap<Coordinate,Node> map_nodi=new HashMap<>();
		Graph graph = new Graph();
		//HashMap<Coordinate,List<Coordinate>> map_distanze=new HashMap<>();
		int id=0;
		FeatureSchema fs= new FeatureSchema();
		fs.addAttribute("geometry", AttributeType.GEOMETRY);
		fs.addAttribute("id", AttributeType.INTEGER);
		fs.addAttribute("dist", AttributeType.DOUBLE);
		GeometryFactory gf=new GeometryFactory();
		FeatureCollection fc=new FeatureDataset(fs);

		for (Feature f : layer.getFeatureCollectionWrapper().getFeatures()) {
			Coordinate[] coordinate=f.getGeometry().getCoordinates();
			Coordinate sV=new Coordinate(coordinate[0].x,coordinate[0].y);
			Coordinate eV=new Coordinate(coordinate[coordinate.length-1].x,coordinate[coordinate.length-1].y);
			List<Feature> lista_feature=new ArrayList<>();
			Double d=f.getGeometry().getLength();

			if(map_nodi.containsKey(sV)){
					if(map_nodi.containsKey(eV)){
						Node start=map_nodi.get(sV);
						Node end=map_nodi.get(eV);
						graph.removeNode(start);
						graph.removeNode(end);
						start.addDestination(end, d);
						end.addDestination(start, d);
						graph.addNode(start);
						graph.addNode(end);
						if(map_nodi_feature.get(start) != null){
							lista_feature=map_nodi_feature.get(start);
							// System.out.println("size1: "+map_nodi_feature.get(start).size());
							lista_feature.add(f);
							map_nodi_feature.replace(start, lista_feature);
						}else{
							lista_feature.add(f);
							map_nodi_feature.put(start, lista_feature);
						}
						if(map_nodi_feature.get(end) != null){
							lista_feature=map_nodi_feature.get(end);
							// System.out.println("size1: "+map_nodi_feature.get(start).size());
							lista_feature.add(f);
						map_nodi_feature.replace(end, lista_feature);
						}else{
							lista_feature.add(f);
						map_nodi_feature.put(end, lista_feature);
						}
						
					}
					else{
						Node start=map_nodi.get(sV);
						id++;
						Node end=new Node(id);
						map_nodi.put(eV,end);
						graph.removeNode(start);
						graph.removeNode(end);
						start.addDestination(end, d);
						end.addDestination(start, d);
						graph.addNode(start);
						graph.addNode(end);
						Feature ff=new BasicFeature(fs);
						ff.setAttribute(0, gf.createPoint(eV));
						ff.setAttribute(1, end.getName());
						ff.setAttribute(2, d);
						fc.add(ff);
						ff=new BasicFeature(fs);
						ff.setAttribute(0, gf.createPoint(sV));
						ff.setAttribute(1, start.getName());
						ff.setAttribute(2, d);
						fc.add(ff);
						if(map_nodi_feature.get(start) != null){
							lista_feature=map_nodi_feature.get(start);
							// System.out.println("size1: "+map_nodi_feature.get(start).size());
							lista_feature.add(f);
						map_nodi_feature.replace(start, lista_feature);
						}else{
							lista_feature.add(f);
						map_nodi_feature.put(start, lista_feature);
						}
						if(map_nodi_feature.get(end) != null){
							lista_feature=map_nodi_feature.get(end);
							// System.out.println("size1: "+map_nodi_feature.get(start).size());
							lista_feature.add(f);
						map_nodi_feature.replace(end, lista_feature);
						}else{
							lista_feature.add(f);
						map_nodi_feature.put(end, lista_feature);
						}
					}
					
			}
			else if(map_nodi.containsKey(eV)){
				if(map_nodi.containsKey(sV)){
					Node start=map_nodi.get(eV);
					Node end=map_nodi.get(sV);
					graph.removeNode(start);
					graph.removeNode(end);
					start.addDestination(end, d);
					end.addDestination(start, d);
					graph.addNode(start);
					graph.addNode(end);
					if(map_nodi_feature.get(start) != null){
						lista_feature=map_nodi_feature.get(start);
						// System.out.println("size1: "+map_nodi_feature.get(start).size());
						lista_feature.add(f);
					map_nodi_feature.replace(start, lista_feature);
					}else{
						lista_feature.add(f);
					map_nodi_feature.put(start, lista_feature);
					}
					if(map_nodi_feature.get(end) != null){
						lista_feature=map_nodi_feature.get(end);
						// System.out.println("size1: "+map_nodi_feature.get(start).size());
						lista_feature.add(f);
					map_nodi_feature.replace(end, lista_feature);
					}else{
						lista_feature.add(f);
					map_nodi_feature.put(end, lista_feature);
					}
				}
				else{
					id++;
					Node start=new Node(id);
					Node end=map_nodi.get(eV);
					map_nodi.put(sV,start);
					graph.removeNode(end);
					start.addDestination(end, d);
					end.addDestination(start, d);
					graph.addNode(start);
					graph.addNode(end);
					Feature ff=new BasicFeature(fs);
					ff.setAttribute(0, gf.createPoint(eV));
					ff.setAttribute(1, end.getName());
					ff.setAttribute(2, d);
					fc.add(ff);
					ff=new BasicFeature(fs);
					ff.setAttribute(0, gf.createPoint(sV));
					ff.setAttribute(1, start.getName());
					ff.setAttribute(2, d);
					fc.add(ff);
					if(map_nodi_feature.get(start) != null){
						lista_feature=map_nodi_feature.get(start);
						// System.out.println("size1: "+map_nodi_feature.get(start).size());
						lista_feature.add(f);
					map_nodi_feature.replace(start, lista_feature);
					}else{
						lista_feature.add(f);
						map_nodi_feature.put(start, lista_feature);
					}
					if(map_nodi_feature.get(end) != null){
						lista_feature=map_nodi_feature.get(end);
						// System.out.println("size1: "+map_nodi_feature.get(start).size());
						lista_feature.add(f);
					map_nodi_feature.replace(end, lista_feature);
					}else{
						lista_feature.add(f);
					map_nodi_feature.put(end, lista_feature);
					}
				}
			}
			else{
				id++;
				Node start=new Node(id);
				map_nodi.put(sV,start);
				id++;
				Node end=new Node(id);
				map_nodi.put(eV,end);
				start.addDestination(end, d);
				end.addDestination(start, d);
				graph.addNode(start);
				graph.addNode(end);
				Feature ff=new BasicFeature(fs);
				ff.setAttribute(0, gf.createPoint(sV));
				ff.setAttribute(1, start.getName());
				ff.setAttribute(2, d);
				fc.add(ff);
				ff=new BasicFeature(fs);
				ff.setAttribute(0, gf.createPoint(eV));
				ff.setAttribute(1, end.getName());
				ff.setAttribute(2, d);
				fc.add(ff);
				lista_feature.add(f);
				map_nodi_feature.put(start, lista_feature);
				map_nodi_feature.put(end, lista_feature);
				
			}
        }

		// for (Entry<Coordinate, Node> pair : map_nodi.entrySet()) {
		// 	Node node=pair.getValue();
		// 	Coordinate key=pair.getKey();

		// 	System.out.println("map: "+"key: "+key+" value: "+node.getName());
		// }
		// for (Node n : graph.getNodes()) {
		// 	System.out.println("graph: "+"node: "+n.getName()+" adj:"+n.getAdjacentNodes());
		// }
		Layer start_layer=context.getLayerManager().getLayer("start");
		Layer segna_layer=context.getLayerManager().getLayer("segna");
		Feature start_geom = start_layer.getFeatureCollectionWrapper().getFeatures().get(0);
		Feature end_geom = segna_layer.getFeatureCollectionWrapper().getFeatures().get(0);
		
		Coordinate[] coordinate=start_geom.getGeometry().getCoordinates();
		Coordinate eV2=new Coordinate(coordinate[coordinate.length-1].x,coordinate[coordinate.length-1].y);
		Node s_p=map_nodi.get(eV2);
		Coordinate[] coordinate_e=end_geom.getGeometry().getCoordinates();
		Coordinate eV3=new Coordinate(coordinate_e[coordinate_e.length-1].x,coordinate_e[coordinate_e.length-1].y);
		Node e_p=map_nodi.get(eV3);
		// Node s_p=new Node(0);
		// s_p.addDestination(s_p_l, 1);
		// graph.addNode(s_p);
		// System.out.println("Coordinate:" + eV2);
		
		// System.out.println("Node get adj:" + s_p.getAdjacentNodes());
		Dijkstra.calculateShortestPathFromSource(graph, s_p);
		System.out.println("Node source :" + s_p.getName());
		System.out.println("Nodo destinazione: "+e_p.getName()+" dis: "+e_p.getDistance());
		List<Feature> comune=new ArrayList<>();

        for (Node n : graph.getNodes()) {
			// System.out.println("nome node: "+n.getName()+" distanza: "+n.getDistance());	
			// for (Node short_pt : n.getShortestPath()) {
			// 	if(e_p.getName()==n.getName()){
			// 		System.out.println("Percorso minimo: "+short_pt.getName());
			// 		List<Feature> pp=map_nodi_feature.get(short_pt);
			// 		for (Feature f_stampa : pp) {
			// 			Feature ff=new BasicFeature(fs);
			// 			ff.setAttribute(0, gf.createLineString(f_stampa.getGeometry().getCoordinates()));
			// 			fc.add(ff);
			// 		}
			// 		// for (Feature fa : pp) {
			// 		// 	System.out.println(fa.getGeometry());
			// 		// }
			// 	}
			// 		// System.out.println("p: "+n.getName()+" sp :"+ short_pt.getName()+"dist: "+n.getDistance());
			// }
			if(e_p.getName()==n.getName()){
				for (Node nn : n.getShortestPath()) {
					System.out.println("Percorso minimo: "+nn.getName());
					for (Feature f_stampa : map_nodi_feature.get(nn)) {
									Feature ff=new BasicFeature(fs);
									ff.setAttribute(0, gf.createLineString(f_stampa.getGeometry().getCoordinates()));
									fc.add(ff);
					}
				}
			}

		}

		// for (Entry<Node,List<Feature>> pair : map_nodi_feature.entrySet()) {
		// 	System.out.println("Node: "+pair.getKey().getName()+" value"+pair.getValue());
		// }


		context.addLayer("Result", "puntiCreati", fc);
	}

}


