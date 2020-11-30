package ex2;

public class NodeData implements node_data , Comparable<NodeData>{ //*****Node class
	// Property
	private int key;
	private String info;
	private double weight;
	private int tag;
	private geo_location location;
	
	public NodeData() { //constructor
	this.key = Integer.MIN_VALUE;
	this.info = "white";
	this.weight = 0;
	this.tag = 0;
	this.location= new GeoLocation(0,0,0)  ;
	}
	
	public NodeData(node_data copy) { //copy constructor
		this.key = copy.getKey();
		this.info = copy.getInfo();
		this.weight = copy.getWeight();
		this.tag = copy.getTag();
		this.location=copy.getLocation();
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
}