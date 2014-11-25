package kr.jollybus.realsize.core;


/**
 * 위치 벡터에서 위치 벡터로의 선분
 */
public class Path extends Vector{
	private Vector start; // start point vector
	private Vector end; // end point vector
	
	public Path(Vector start, Vector end) {
		super(end.getX() - start.getX(), end.getY() - start.getY());
		this.start = start;
		this.end = end;
	}

	public Path(int x1, int y1, int x2, int y2) {
		super(x2 - x1, y2 - y1);
		this.start = new Vector(x1,y1);
		this.end = new Vector(x2,y2);
	}
	
	public Vector getStart() {
		return start;
	}
	
	public Vector getEnd() {
		return end;
	}
	
	/**
	 * http://stackoverflow.com/questions/563198/how-do-you-detect-where-two-line-segments-intersect
	 * @param path
	 * @return the vector of unique intersection or the middle point vector in overlapping points
	 */
	public Vector getIntersectionPoint(Path path) {
		Vector p = this.start;
		Vector q = path.start;
		Vector r = minus(this.end,this.start);
		Vector s = minus(path.end,path.start);
		Vector m = minus(q, p);
		
		float crs = cross(r,s);
		float cmr = cross(m,r);
		
		if (crs == 0) {
			if(cmr == 0) {
				//collinear
				float d1 = dot(minus(q,p),r)/r.getLength();
				float d2 = dot(minus(p,q),s)/s.getLength();
				
				if(0<=d1 && d1 <= 1){ //overlapping #1: p,q,r 의 순서인 경우.  
					if (d2>=1) { //s,p,q,r
						return middle(p, q);
					} else if (d2>=0) { //p,s,q,r
						return middle(s, q);
					} else {
						if (minus(r,q).getLength() >= minus(s,q).getLength()) { //p,q,s,r => q,s 의 중점
							return middle(p,s);
						} else { //p,q,r,s => q,r의 중점
							return middle(q,r);
						}
					}
				} else if (0<=d2 && d2 <= 1) { //overlapping #2: q,p,s 의 순서인 경우.
					if (d1>=1) { //r,q,p,s
						return middle(p, q);
					} else if (d1>=0) { //q,r,p,s
						return middle(s, q);
					} else {
						if (minus(s,p).getLength() >= minus(r,p).getLength()) { //q,p,r,s
							return middle(p,r);
						} else { //q,p,s,r
							return middle(p,s);
						}
					}
				} else {
					//disjoint
					return null;
				}
			} else {
				//parallel
				return null;
			}
		} else {
			float cms = cross(m,s);
			float t = cms/crs;
			float u = cmr/crs;
			if (0<=t && t<=1 && 0<=u && u<=1) {
				return new Vector(p.getX()+t*r.getX(), p.getY() + t*r.getY());
			} else {
				//r 또는 s가 특정 방향으로 좀 더 길었다면 만날번 했던 경우.
				return null;
			}
		}
	}
	
	@Override
	public boolean equals(Object o) {
		return this.start.equals(((Path) o).start) &&
				this.end.equals(((Path) o).end);  
	}
	
	@Override
	public String toString() {
		return "[" + start + ", " + end + "]";
	}
}
