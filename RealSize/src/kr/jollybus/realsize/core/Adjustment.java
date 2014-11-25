package kr.jollybus.realsize.core;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;


public class Adjustment extends Path{
	
	private int logicalLength;

	public Adjustment(int x1, int y1, int x2, int y2, int logicalLength) {
		super(x1, y1, x2, y2);
		this.logicalLength = logicalLength;
	}
	
	public int getLogicalLength() {
		return logicalLength;
	}
	
	public float getLength(float lengthInPixel) {
		return lengthInPixel*logicalLength/getLength();
	}
	
	public float getArea(float areaInPixel) {
		return areaInPixel*(logicalLength/getLength())*(logicalLength/getLength());
	}
	
	public Adjustment clone() {
		return new Adjustment((int)getStart().getX(), (int)getStart().getY(), (int)getEnd().getX(), (int)getEnd().getY(), logicalLength);
	}
	
	public static Adjustment createDefaultAdjustment(Display display, int orientation) {
		int x,y;
		Rect rect = new Rect();
		display.getRectSize(rect);
		if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
			x = rect.height()/10;
			y = rect.width()/10;
		} else {
			x = rect.width()/10;
			y = rect.height()/10;
		}
		
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		int length = (int) (metrics.xdpi/2.54); // 최대한 1cm 에 해당하는 길이를 찾음. 1(inch) = 2.54(cm)
		return new Adjustment(x, y, (int)(x + length), y, 1);
	}
}
