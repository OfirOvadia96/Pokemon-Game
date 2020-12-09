package ex2;

import java.io.Serializable;

public class NodeData implements node_data , Comparable<NodeData>, Serializable{ //*****Node class
	// Property
	private int key;
	private String info = "white";
	private double weight;
	private int tag;
	private geo_location location;	
	
	public NodeData(int key) { //constructor
	this.key = key;
	this.info = "white";
	this.weight = 0;
	this.tag = 0;
	this.location= new GeoLocation(0,0,0)  ;
	}
	
	public NodeData(int key, String info, double weight, int tag, geo_location location) { //copy constructor
		this.key = key;
		this.info = info;
		this.weight = weight;
		this.tag = tag;
		this.location = location;
	}
	
	public NodeData(node_data copy) {
		this(copy.getKey(),
			 copy.getInfo(),
			 copy.getWeight(),
			 copy.getTag(),
			 copy.getLocation());
	}
	
	@Override
	public int getKey() {
		return this.key;
	}

	@Override
	public geo_location getLocation() {
		// TODO Auto-generated method stub
		return location;
	}

	@Override
	public void setLocation(geo_location p) {
		location = p;	
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

	@Override
	public int compareTo(NodeData o) {
        int ans = (int) (o.getTag() - this.getTag());
        
        if(ans < 0)
        	ans = 1;
        else if (ans > 0)
        	ans = -1;
        return ans;		
	}
	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + key;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + tag;
		long temp;
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		NodeData other = (NodeData) obj;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (key != other.key)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (tag != other.tag)
			return false;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NodeData [key=" + key + ", info=" + info + ", weight=" + weight + ", tag=" + tag + ", location="
				+ location + "]";
	}
}