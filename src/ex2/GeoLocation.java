package ex2;

public class GeoLocation implements geo_location{
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

}
