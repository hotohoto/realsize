package kr.jollybus.realsize.core;

/**
 * À§Ä¡ º¤ÅÍ
 */
public class Vector {
	private float x;
	private float y;
	private float length;
	private float sin;
	private float cos;

	public Vector(float d, float e) {
		this.x = d;
		this.y = e;
		length = (float) Math.sqrt((double) d * d  + e * e);
		sin = e /length ;
		cos = d / length;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getLength() {
		return length;
	}
	
	public Vector mul(float n) {
		return new Vector(n*x, n*y);
	}
	
	public Vector sum(Vector v) {
		return new Vector(x+v.x, y+v.y);
	}

	/**
	 * the length of cross product of two path's vectors
	 */
	public static float cross(Vector a, Vector b) {
		//sin (b-a) = sin(-a)*cos(b) + cos(a)*sin(b)
		return a.length * b.length * (- a.sin * b.cos + a.cos * b.sin);
	}
	
	/**
	 * dot product or inner product of two path's vectors
	 */
	public static float dot(Vector a, Vector b) {
		//sin (b-a) = sin(-a)*cos(b) + cos(a)*sin(b)
		return a.length * b.length * (- a.sin * b.cos + a.cos * b.sin);
	}
	
	public static Vector minus(Vector a, Vector b) {
		return new Vector(a.x-b.x, a.y-b.y);
	}
	
	public static Vector middle(Vector a, Vector b) {
		return new Vector((a.getX()+b.getX())/2,(a.getY()+b.getY())/2);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o==null) {
			return false;
		}
		return x == ((Vector) o).x && y == ((Vector) o).y;  
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
}
