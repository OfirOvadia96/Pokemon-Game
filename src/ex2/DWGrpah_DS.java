package ex2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;





public class DWGrpah_DS implements directed_weighted_graph {
private HashMap<Integer,node_data>nodes;

// Src -> Dest -> edge_data
private HashMap<Integer,HashMap<Integer,edge_data>>edges;

// Dest -> Src -> edge_data
private HashMap<Integer,HashMap<Integer,edge_data>>destEdges;

private int edgesSize = 0;
private int mcSize = 0;


	public  DWGrpah_DS() {
		nodes=new HashMap<Integer,node_data>();
		edges=new HashMap<Integer,HashMap<Integer,edge_data>>();
	}
	public  DWGrpah_DS(directed_weighted_graph graph) {
		nodes=new HashMap<Integer,node_data>();
		edges=new HashMap<Integer,HashMap<Integer,edge_data>>();
		for(node_data node : graph.getV()) {
			node_data newNode = new NodeData(node);
			addNode(newNode);
		}
	for(node_data node : graph.getV()) {
			
			// run over all neighbors of specific node
			for(edge_data neighbor : graph.getE(node.getKey())) {
				
				//create neighbor
				double weight = neighbor.getWeight(); 
				
				// add neighbor
				connect(neighbor.getSrc(), neighbor.getDest(), weight);
			}
		}
		
		this.edgesSize = graph.edgeSize();
		this.mcSize = graph.getMC();
		
	
	}
	
	private boolean hasEdges (int src, int dest) 
	 {
		HashMap<Integer, edge_data> neighbors = edges.get(src);
		if(neighbors.containsKey(dest)) {
			return true;
		}
		return false;
	}
	
	@Override
	public node_data getNode(int key) {
		// TODO Auto-generated method stub
		return nodes.get(key);
	}

	@Override
	public edge_data getEdge(int src, int dest) {
		if (! edges.containsKey(src)) return null;
		HashMap<Integer, edge_data> neighbors = edges.get(src);
		if(neighbors.containsKey(dest)) return null;
		return neighbors.get(dest);
	
	}

	@Override
	public void addNode(node_data n) {
		if(!nodes.containsKey(n.getKey())) {
			nodes.put(n.getKey(),new NodeData( n));
			HashMap<Integer, edge_data> neighborsSrc = new HashMap<Integer, edge_data>();
			HashMap<Integer, edge_data> neighborsDest = new HashMap<Integer, edge_data>();

			edges.put(n.getKey(),  neighborsSrc );
			destEdges.put(n.getKey(),  neighborsDest );
	          mcSize++;
		}
	}

	@Override
	public void connect(int src, int dest, double w) {
		
		HashMap<Integer, edge_data> neighborsSrc = edges.get(src);
		HashMap<Integer, edge_data> neighborsDest = destEdges.get(dest);

		
		if(hasEdges(src,dest)) {
			neighborsSrc.put(dest, new EdgeData(src,dest,w));
			neighborsDest.put(src, new EdgeData(src,dest,w));
		}
	
	}

	@Override
	public Collection<node_data> getV() {
		return nodes.values();
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		// TODO Auto-generated method stub
		return edges.get(node_id).values();

	}
	

	private Collection<edge_data> getDestE(int destKey) {
		// TODO Auto-generated method stub
		return destEdges.get(destKey).values();
	}
	
	@Override
	public node_data removeNode(int key) {
		if(!nodes.containsKey(key))return null;
		for(edge_data node:getE(key)) {
			removeEdge(node.getSrc(),node.getDest());
		}
		
		for(edge_data node:getDestE(key)) {
			removeEdge(node.getSrc(),node.getDest());
		}
		
		mcSize++;
		return nodes.remove(key);
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		if(!hasEdges(src, dest))return null;
	
		destEdges.get(dest).remove(src);
		return 	edges.get(src).remove(dest);
	}

	@Override
	public int nodeSize() {
		// TODO Auto-generated method stub
		return nodes.size();
	}

	@Override
	public int edgeSize() {
		// TODO Auto-generated method stub
		return edgesSize;
	}

	@Override
	public int getMC() {
		// TODO Auto-generated method stub
		return mcSize;
	}

}
