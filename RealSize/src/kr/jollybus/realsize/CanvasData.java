package kr.jollybus.realsize;

import kr.jollybus.realsize.core.Adjustment;
import kr.jollybus.realsize.core.Boundary;
import kr.jollybus.realsize.core.ChangingBoundary;

public class CanvasData {
	
	private Status status;
	private int orientation;
	private Boundary boundary;
	private ChangingBoundary changingBoundary;
	private Adjustment adjustment;
	
	public CanvasData(Status status, int orientation) {
		this.status = status;
		this.orientation = orientation;
	}

	public Status getStatus() {
		return status;
	}
	
	public CanvasData setStatus(Status status) {
		this.status = status;
		return this;
	}
	
	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public Boundary getBoundary() {
		return boundary;
	}
	
	public CanvasData setBoundary(Boundary boundary) {
		this.boundary = boundary;
		return this;
	}

	public ChangingBoundary getChangingBoundary() {
		return changingBoundary;
	}
	
	public CanvasData setChangingBoundary(ChangingBoundary changingBoundary) {
		this.changingBoundary = changingBoundary;
		return this;
	}

	public Adjustment getAdjustment() {
		return adjustment;
	}

	public CanvasData setAdjustment(Adjustment adjustment) {
		this.adjustment = adjustment;
		return this;
	}
}
