package ex2;

import java.io.Serializable;

public class EdgeData implements edge_data, Serializable {
	private int src;
	private int dest;
	private double weight;
	private String info = "white";
	private int tag;


	public EdgeData () {
		src=0;
		dest=0;
		weight=0;
		info="white";
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

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dest;
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + src;
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
		EdgeData other = (EdgeData) obj;
		if (dest != other.dest)
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (src != other.src)
			return false;
		if (tag != other.tag)
			return false;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EdgeData [src=" + src + ", dest=" + dest + ", weight=" + weight + ", info=" + info + ", tag=" + tag
				+ "]";
	}
	
	

}
