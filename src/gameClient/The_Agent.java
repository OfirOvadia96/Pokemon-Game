package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import org.json.JSONObject;

public class The_Agent {

	private directed_weighted_graph Graph;
	private int key;
	private double speedMove;
	private geo_location loc;
	private double points;
	private edge_data Edge;
	private node_data Node;

	
	public The_Agent(directed_weighted_graph graph, int start) {
		Graph = graph;
		setPoints(0);
		Node = Graph.getNode(start);
		key = -1;
		loc = Node.getLocation();
		setSpeed(0);
	}


	public void update(String Json) {
		JSONObject line;
		
		try {
			line = new JSONObject(Json);
			JSONObject ttt = line.getJSONObject("Agent");
			int id = ttt.getInt("id");
			if (id == this.getID() || this.getID() == -1) {
				if (this.getID() == -1) {
					key = id;
				}
				
				double speed = ttt.getDouble("speed");
				String p = ttt.getString("pos");
				Point3D pp = new Point3D(p);
				int src = ttt.getInt("src");
				int dest = ttt.getInt("dest");
				double value = ttt.getDouble("value");
				this.loc = pp;
				this.setSpeed(speed);
				this.setCurrNode(src);
				this.setNextNode(dest);
				this.setPoints(value);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getSrcNode() {
		return Node.getKey();
	}
	public void setCurrNode(int src) {
		this.Node = Graph.getNode(src);
	}
	

	private void setPoints(double p) {
		points = p;
	}
	
	public int getID() {
		return this.key;
	}
	
	public void setNextNode(int dest) {
		int src = this.Node.getKey();
		
		if(Graph.getEdge(src, dest) != null) {
			Edge = Graph.getEdge(src, dest);
		}
	}


	public void setSpeed(double v) {
		this.speedMove = v;
	}

	public geo_location getLocation() {
		return loc;
	}


	public double getPoints() {
		return this.points;
	}


	public int getNextNode() {
		if(this.Edge == null) {
			return -1;
		}
		return this.Edge.getDest();
	}


	public double getSpeed() {
		return this.speedMove;
	}

}