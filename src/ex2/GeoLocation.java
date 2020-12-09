package ex2;

import java.io.Serializable;

public class GeoLocation implements geo_location,Serializable{
   private double x;
   private double y;
   private double z;
   public	GeoLocation () {
		this.x=0;
		this.y=0;
		this.z=0;
	 
   }
 
	public	GeoLocation (double x,double y,double z) {
		this.x=x;
		this.y=y;
		this.z=z;
	}
	@Override
	public double x() {
		
		return x;
	}

	@Override
	public double y() {
	
		return y;
	}

	@Override
	public double z() {
		
		return z;
	}
	
	//d=sprt((x1-x2)^2 + (y1-y2)^2 + (z1-z2)^2)
	@Override
	public double distance(geo_location g) {
		
		return Math.sqrt(Math.pow(x-g.x(), 2)+ Math.pow(y-g.y(),2) + Math.pow(z-g.z(),2));
	}

	@Override
	public String toString() {
		return "GeoLocation [x=" + x + ", y=" + y + ", z=" + z + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
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
		GeoLocation other = (GeoLocation) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}
	
	

}
