package kr.jollybus.realsize;

import java.util.List;

import kr.jollybus.realsize.core.Adjustment;
import kr.jollybus.realsize.core.Boundary;
import kr.jollybus.realsize.core.Path;
import kr.jollybus.realsize.core.Vector;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;

public class CanvasView extends SurfaceView{
	private static final int COLOR_SHADOW = Color.BLACK;
	private static final int COLOR_STRONG = Color.rgb(90, 164, 255);
	private static final int COLOR_DEFAULT = Color.WHITE;
	private static final int BACKGROUND_COLOR = Color.DKGRAY;
	
	private static final Paint PAINT_BOUNDARY = new Paint();
	private static final Paint PAINT_RULER = new Paint();
	private static final Paint PAINT_SCALE_TEXT = new Paint();
	
	
	static {
		PAINT_SCALE_TEXT.setColor(COLOR_DEFAULT);
		PAINT_SCALE_TEXT.setTextSize(70);
		PAINT_SCALE_TEXT.setTextAlign(Align.RIGHT);
		
		PAINT_BOUNDARY.setColor(COLOR_DEFAULT);
		PAINT_BOUNDARY.setStrokeWidth(3);
		
		PAINT_RULER.setColor(COLOR_DEFAULT);
		PAINT_RULER.setStrokeWidth(3);
	}

	private Handler uiHandler = new Handler();

	/**
	 * number of updating requests to draw which has not been processed.
	 */
	private int nUpdateRequest = 0;
	private CanvasData canvasData;

	public CanvasView(Context context) {
		super(context);
	}

	public CanvasView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CanvasView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void requestUpdate(CanvasData data) {
		synchronized (uiHandler) {
			canvasData = data;
			nUpdateRequest++;
		}
		uiHandler.post(new Runnable() {
			@Override
			public void run() {
				synchronized (uiHandler) {
					if (nUpdateRequest == 0) {
						return;
					}
					draw();
					if (nUpdateRequest>1) {
						Log.d("draw", nUpdateRequest + " requests cleared.");
					}
					nUpdateRequest = 0;
				}
			}
		});
	}
	
	public void draw() {
		Status s = canvasData.getStatus();
		switch (s) {
		case INIT:
			clear();
			break;
		case BASIC:
		case DRAWING_DONE:
			drawAll();
			break;
		case DRAWING:
			drawGrowingBoundary();
			break;
		case ADJUSTING_READY:
		case ADJUSTING:
		case ADJUSTING_DONE:
			break;
		}
	}
	
	private void drawAll() {
		float canvasW; // 원래 좌표계에서 너비.
		Boundary boundary = canvasData.getBoundary();

		Canvas canvas = getHolder().lockCanvas();
		canvas.drawColor(BACKGROUND_COLOR);
		
		// 영역,기준거리 그리기 준비
		if (canvasData.getOrientation() == Configuration.ORIENTATION_LANDSCAPE) {
			canvasW = canvas.getHeight();
			canvas.rotate(-90,canvasW/2,canvasW/2);
		} else {
			canvasW = canvas.getWidth();
		}
		
		//영역 그리기
		if (boundary != null) {
			List<Path> allPath = canvasData.getBoundary().getAllPath();
			for (int i=0; i<allPath.size(); i++) {
				Path p = allPath.get(i);
				Vector s = p.getStart();
				Vector e = p.getEnd();
				canvas.drawLine(s.getX(), s.getY(), e.getX(), e.getY(), PAINT_BOUNDARY);
			}
		}
		//축척 자 그리기
		Adjustment adj = canvasData.getAdjustment();
		drawRuler(canvas, adj);

		getHolder().unlockCanvasAndPost(canvas);
	}
	
	private void drawGrowingBoundary() {
		Canvas canvas = getHolder().lockCanvas();
		if (canvasData.getOrientation() == Configuration.ORIENTATION_LANDSCAPE) {
			canvas.rotate(-90,canvas.getHeight()/2,canvas.getHeight()/2);
		}
		canvas.drawColor(BACKGROUND_COLOR);
		List<Path> allPath = canvasData.getChangingBoundary().getAllPath();
		for (int i=0; i<allPath.size(); i++) {
			Path p = allPath.get(i);
			Vector s = p.getStart();
			Vector e = p.getEnd();
			canvas.drawLine(s.getX(), s.getY(), e.getX(), e.getY(), PAINT_BOUNDARY);
		}
		getHolder().unlockCanvasAndPost(canvas);
	}
	
	private void clear() {
		Canvas canvas = getHolder().lockCanvas();
		canvas.drawColor(BACKGROUND_COLOR);
		getHolder().unlockCanvasAndPost(canvas);
	}

	/**
	 * 축척 자 그리기.
	 */
	private void drawRuler(Canvas canvas, Adjustment adj) {
		canvas.drawLine(
				adj.getStart().getX(),adj.getStart().getY(),
				adj.getEnd().getX(),adj.getEnd().getY(),
				PAINT_RULER);
	}
}
