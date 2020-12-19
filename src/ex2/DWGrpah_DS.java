package ex2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class DWGrpah_DS implements directed_weighted_graph ,Serializable{
	private HashMap<Integer,node_data>nodes;

	// Src -> Dest -> edge_data
	private HashMap<Integer,HashMap<Integer,edge_data>>edges;

	// Dest -> Src -> edge_data
	private HashMap<Integer,HashMap<Integer,edge_data>>destEdges;

	private int edgesSize = 0;
	private int mcSize = 0;

	public DWGrpah_DS( HashMap<Integer,node_data>nodes,HashMap<Integer,
			HashMap<Integer,edge_data>>edges,
			HashMap<Integer,HashMap<Integer,edge_data>>destEdges,int edgesSize,int mcSize) {
		this.nodes=nodes;
		this.edges=edges;
		this.destEdges=destEdges;
		this.mcSize = mcSize;
		this.edgesSize = 0;
		
	}
	private void init() {
		nodes = new HashMap<Integer,node_data>();
		edges = new HashMap<Integer,HashMap<Integer,edge_data>>();
		destEdges = new HashMap<Integer,HashMap<Integer,edge_data>>();
	}

	public DWGrpah_DS() {
		init();
	}

	public DWGrpah_DS(directed_weighted_graph graph) {
		init();

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
		return nodes.get(key);
	}

	@Override
	public edge_data getEdge(int src, int dest) {
		if (! edges.containsKey(src)) return null;
		HashMap<Integer, edge_data> neighbors = edges.get(src);
		if(!neighbors.containsKey(dest)) return null;
		return neighbors.get(dest);

	}

	@Override
	public void addNode(node_data n) {
		if(!nodes.containsKey(n.getKey())) {
			NodeData node = new NodeData(n);
			nodes.put(n.getKey(),node);
			HashMap<Integer, edge_data> neighborsSrc = new HashMap<Integer, edge_data>();
			HashMap<Integer, edge_data> neighborsDest = new HashMap<Integer, edge_data>();

			edges.put(n.getKey(),  neighborsSrc );
			destEdges.put(n.getKey(),  neighborsDest );
			mcSize++;
		}
	}

	@Override
	public void connect(int src, int dest, double w) {

		if(!edges.containsKey(src) ||!edges.containsKey(dest)||src == dest) return;
		
		HashMap<Integer, edge_data> neighborsSrc = edges.get(src);
		HashMap<Integer, edge_data> neighborsDest = destEdges.get(dest);

		if(!hasEdges(src,dest)) {
			edgesSize++;
		}
		
		neighborsSrc.put(dest, new EdgeData(src,dest,w));
		neighborsDest.put(src, new EdgeData(src,dest,w));
		mcSize++;
	}

	@Override
	public Collection<node_data> getV() {
		return nodes.values();
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		return edges.get(node_id).values();

	}

	private Collection<edge_data> getDestE(int destKey) {
		return destEdges.get(destKey).values();
	}

	@Override
	public node_data removeNode(int key) {
		if(!nodes.containsKey(key))return null;
		for(edge_data node : new ArrayList<edge_data>(getE(key))) {
			removeEdge(node.getSrc(),node.getDest());
		}

		for(edge_data node:new ArrayList<edge_data>(getDestE(key))) {
			removeEdge(node.getSrc(),node.getDest());
		}

		mcSize++;
		return nodes.remove(key);
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		if(!hasEdges(src, dest))return null;
		
		edgesSize--;
		mcSize++;
		destEdges.get(dest).remove(src);
		return 	edges.get(src).remove(dest);
	}

	
	@Override
	public int nodeSize() {
		return nodes.size();
	}

	@Override
	public int edgeSize() {
		return edgesSize;
	}

	@Override
	public int getMC() {
		return mcSize;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destEdges == null) ? 0 : destEdges.hashCode());
		result = prime * result + ((edges == null) ? 0 : edges.hashCode());
		result = prime * result + edgesSize;
		result = prime * result + mcSize;
		result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DWGrpah_DS other = (DWGrpah_DS) obj;
		if (destEdges == null) {
			if (other.destEdges != null)
				return false;
		} else if (!destEdges.equals(other.destEdges))
			return false;
		if (edges == null) {
			if (other.edges != null)
				return false;
		} else if (!edges.equals(other.edges))
			return false;
		if (edgesSize != other.edgesSize)
			return false;
		if (mcSize != other.mcSize)
			return false;
		if (nodes == null) {
			if (other.nodes != null)
				return false;
		} else if (!nodes.equals(other.nodes))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "DWGrpah_DS [nodes=" + nodes + ", edges=" + edges + ", destEdges=" + destEdges + ", edgesSize="
				+ edgesSize + ", mcSize=" + mcSize + "]";
	}

}