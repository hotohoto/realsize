package kr.jollybus.realsize.core;

import java.util.ArrayList;
import java.util.List;

/**
 * 교차하지 않는 경계선.
 */
public class Boundary {
	List<Path> allPath;
	
	public Boundary() {
		allPath = new ArrayList<Path>();
	}
	
	public Boundary(List<Path> allPath) {
		this.allPath = allPath;
	}
	
	public List<Path> getAllPath() {
		return allPath;
	}
	
	/**
	 * 닫혀진 경계선 내부 너비 구하기. 단위는 dp^2
	 */
	public float getArea() {
		int nPath = allPath.size();
		
		if (nPath < 3) {
			return 0;
		}
		
		Vector first = allPath.get(0).getStart();
		Vector last = allPath.get(nPath-1).getEnd();
		
		if (!first.equals(last)) {
			return 0;
		}
		
		float sum = 0;
		for (int i=0; i<nPath; i++) {
			Path p = allPath.get(i);
			Vector s = p.getStart();
			Vector e = p.getEnd();
			sum+=s.getX()*e.getY();
			sum-=e.getX()*s.getY();
		}
		return Math.abs(sum/2);
	}
	
	/**
	 * 경로 길이 구하기. 단위는 dp
	 */
	public float getLength() {
		int nPath = allPath.size(); 
		float sum = 0;
		
		for (int i=0; i<nPath; i++) {
			Path p = allPath.get(i);
			sum+=p.getLength();
		}
		return sum;
	}
}
