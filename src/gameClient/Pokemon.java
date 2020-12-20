package gameClient;

import api.edge_data;
import gameClient.util.Point3D;

public class Pokemon {
	private double min_dist;
	private double _value;
	private edge_data edge;
	private Point3D _pos;
	private int _type;


	public Pokemon(Point3D p, int t, double v, edge_data e) {
		_type = t;
		_value = v;
		_pos = p;
		this.edge = e;
	}


	public void setDistance(double dis) {
		min_dist = dis;
	}


	public void setEdges(edge_data edges) {
		this.edge = edges;
	}


	public int getType() {
		return _type;
	}


	public double getValue() {
		return _value;
	}


	public Point3D getLocation() {
		return _pos;
	}


	public int getDest() {
		return edge.getDest();
	}


	public Double getDistance() {
		return min_dist;
	}


	public edge_data getEdges() {
		return edge;
	}

}
