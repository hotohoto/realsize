package kr.jollybus.realsize;

import kr.jollybus.realsize.core.Adjustment;
import kr.jollybus.realsize.core.Boundary;
import kr.jollybus.realsize.core.ChangingBoundary;
import kr.jollybus.realsize.core.Vector;
import android.content.res.Configuration;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

public class CanvasController {
	private Status status = Status.BASIC;

	private RealSizeActivity realSizeActivity;
	private CanvasView canvasView;
	private Boundary boundary;
	private ChangingBoundary changingBoundary;
	private Adjustment adjustment;
	private Adjustment tempAdjustment;
	private int orientation;

	public CanvasController(CanvasView canvasView, RealSizeActivity activity) {
		this.canvasView = canvasView;
		this.realSizeActivity = activity;
		Configuration conf = activity.getResources().getConfiguration();
		this.orientation = conf.orientation;
		this.adjustment = Adjustment.createDefaultAdjustment(
				activity.getWindowManager().getDefaultDisplay(), orientation);
		
		canvasView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//Log.d("CanvasController","onTouch(): " + event.toString());
				return touch(event);
			}
		});
		
        canvasView.getHolder().addCallback(new SurfaceHolder.Callback() {
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				Log.d("canvasView","surfaceDestroyed()");
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				Log.d("canvasView","surfaceCreated()");
				requestUpdate();
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
				Log.d("canvasView","surfaceChanged()");
				requestUpdate();
			}
		});
	}
	
	public void configurationChanged(Configuration newConfig) {
		orientation =  newConfig.orientation;
		requestUpdate();
	}

	/**
	 * @return True if the event was handled, False otherwise.
	 */
	public boolean touch(MotionEvent evt) {
		float x = evt.getX();
		float y = evt.getY();

		if (status == Status.BASIC) {
			// got the first input point for drawing.
			changingBoundary = new ChangingBoundary();
			changingBoundary.addLastPoint(createRotatedVector(x,y,orientation));
			status = Status.DRAWING;
			requestUpdate();
			requestMenuVisible(false);
		} else if (status == Status.DRAWING) {
			if (evt.getAction() == MotionEvent.ACTION_UP){
				changingBoundary = null;
				status = Status.BASIC;
				requestMenuVisible(true);
			} else {
				// got an additional point for drawing
				boolean ret = changingBoundary.addLastPoint(createRotatedVector(x,y,orientation));
				if (ret) {
					// when it collides
					status = Status.DRAWING_DONE;
					boundary = new Boundary(changingBoundary.trim());
					changingBoundary = null;
				}
			}
			requestUpdate();
		} else if (status == Status.DRAWING_DONE) {
			if (evt.getAction() == MotionEvent.ACTION_UP) {
				status = Status.BASIC;
				requestMenuVisible(true);
			}
		} else if (status == Status.ADJUSTING_READY) {
			// got the first adjustment point
			
		} else if (status == Status.ADJUSTING) {
			// got the second adjustment point 
		} else {
			return false;
		}
		return true;
	}
	
	private void requestUpdate() {
		switch(status) {
		case INIT:
			canvasView.requestUpdate(new CanvasData(status, orientation)
					.setAdjustment(adjustment));
			break;
		case DRAWING:
			canvasView.requestUpdate(new CanvasData(status, orientation)
					.setChangingBoundary(changingBoundary));
			break;
		case BASIC:
		case DRAWING_DONE:
			canvasView.requestUpdate(new CanvasData(status, orientation)
					.setBoundary(boundary).setAdjustment(adjustment));
			break;
		case ADJUSTING_READY:
		case ADJUSTING:
		case ADJUSTING_DONE:
			canvasView.requestUpdate(new CanvasData(status, orientation)
					.setBoundary(boundary).setAdjustment(tempAdjustment));
			break;
		}
	}
	
	private void requestMenuVisible(boolean visible) {
		if (visible) {
			realSizeActivity.findViewById(R.id.fullscreen_content_controls).setVisibility(View.VISIBLE);
		} else {
			realSizeActivity.findViewById(R.id.fullscreen_content_controls).setVisibility(View.INVISIBLE);
		}
	}
	
	private Vector createRotatedVector(float x, float y, int orientation) {
		if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
			float c = canvasView.getHeight() - y;
			y = x;
			x = c;
		}
		return new Vector(x,y);
	}
}
