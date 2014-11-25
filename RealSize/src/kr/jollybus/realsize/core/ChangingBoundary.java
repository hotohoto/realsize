package kr.jollybus.realsize.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.util.Log;

public class ChangingBoundary extends Boundary {

	private static final int N_GRID_X = 30;
	private static final int N_GRID_Y = 45;
	
	private HashMap<Integer,ArrayList<Path>> grid = new HashMap<Integer, ArrayList<Path>>();
	private Integer firstValidPathIndex = null; //inclusive
	private Vector lastPoint;
	
	public static int getGridKey(int x, int y) {
		return x + y*N_GRID_X;
	}
	
	public Vector getLastPoint() {
		return lastPoint;
	}
	
	/**
	 * path 의 마지막 점에 새로운 점을 연결시켜 path를 길게 함.
	 * 충돌한 경우 true 리턴.
	 */
	public boolean addLastPoint(Vector v) {
		if (lastPoint != null) {
			Path newPath = new Path(lastPoint, v);
			//Log.d("distance",""+newPath.getLength());
			
			//collision check
			for (int i=0; i<allPath.size()-1; i++) {
				Path p = allPath.get(i);
				Vector intersection = p.getIntersectionPoint(newPath);
				
				if (intersection != null) {
					Log.d("collision", i+ ": " + p + "and " + newPath + "have intersection at :" + intersection);
					Path p1 = new Path(p.getStart(), intersection);
					Path p2 = new Path(intersection, p.getEnd());
					allPath.set(i, p2);
					allPath.add(i, p1);
					firstValidPathIndex = i+1;

					newPath = new Path(lastPoint,intersection);
					allPath.add(newPath);
					
					return true;
				}
			}
			allPath.add(newPath);
		}
		
		lastPoint = v;
		return false;
	}
	
	/**
	 * 충돌 체크 성공 후에 유의미한 path 영역을 추출.  
	 * @return
	 */
	public List<Path> trim() {
		return allPath.subList(firstValidPathIndex, allPath.size());
	}
}
