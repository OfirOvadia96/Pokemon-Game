package ex2;

public class EdgeData implements edge_data {
	private int src;
	private int dest;
	private double weight;
	private String info;
	private int tag;


	public EdgeData () {
		src=0;
		dest=0;
		weight=0;
		info=null;
		tag=0;
	}
	
	public EdgeData (int src,int dest,double weight,String info,int tag) {
		this.src=src;
		this.dest=dest;
		this.weight=weight;
		this.info=info;
		this.tag=tag;
	}
	public EdgeData (int src,int dest,double weight){
		this.src=src;
		this.dest=dest;
		this.weight=weight;
	}
	
	
	@Override
	public int getSrc() {
		return src;
	}

	@Override
	public int getDest() {
		return dest;
	}

	@Override
	public double getWeight() {
		return weight;
	}

	@Override
	public String getInfo() {
		return info;
	}

	@Override
	public void setInfo(String s) {
		info=s;		
	}

	@Override
	public int getTag() {
		return tag;
	}

	@Override
	public void setTag(int t) {
		tag=t;		
	}

}
