package ex2;

import java.util.Collection;

public class DWGrpah_DS implements directed_weighted_graph {

	

	public class NodeData implements node_data { //*****Node class
		
		private int key;
		private String info;
		private double weight;
		private int tag;
		
		public NodeData() { //constructor
		this.key = Integer.MIN_VALUE;
		this.info = "white";
		this.weight = 0;
		this.tag = 0;
		}
		
		public NodeData(NodeData copy) { //copy constructor
			this.key = copy.key;
			this.info = copy.info;
			this.weight = copy.weight;
			this.tag = copy.tag;
		}
		@Override
		public int getKey() {
			return this.key;
		}

		@Override
		public geo_location getLocation() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setLocation(geo_location p) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public double getWeight() {
			return this.weight;
		}

		@Override
		public void setWeight(double w) {
			this.weight = w;
			
		}

		@Override
		public String getInfo() {
			return this.info;
		}

		@Override
		public void setInfo(String s) {
			this.info = s;
		}

		@Override
		public int getTag() {
			return this.tag;
		}

		@Override
		public void setTag(int t) {
			this.tag = t;
			
		}
	}
	//******************end Node*****************
	
	
	@Override
	public node_data getNode(int key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public edge_data getEdge(int src, int dest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addNode(node_data n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connect(int src, int dest, double w) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<node_data> getV() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public node_data removeNode(int key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int nodeSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edgeSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMC() {
		// TODO Auto-generated method stub
		return 0;
	}

}
