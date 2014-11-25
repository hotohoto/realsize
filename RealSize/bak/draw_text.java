import android.content.res.Configuration;
import android.graphics.Rect;

		//글자 쓰기 준비
		if (canvasData.getOrientation() == Configuration.ORIENTATION_LANDSCAPE) {
			//좌표계 원복
			canvas.rotate(90,canvasW/2,canvasW/2);
		}
		
		//글자 쓰기
		int marginX = (int) (canvas.getWidth()/10);
		int marginY = (int) (canvas.getHeight()/10);
		int marginLine = (int) (canvas.getHeight()/30);
		
		float x = canvas.getWidth()-marginX;
		float y = marginY - marginLine;
		Rect bounds = new Rect();
		String text;
		
		text = "U="+((float)Math.round(adj.getLogicalLength()*10))/10;
		PAINT_SCALE_TEXT.getTextBounds(text, 0, text.length(), bounds);
		y = y + marginLine - bounds.top;
		canvas.drawText(text, x, y, PAINT_SCALE_TEXT);
		
		//너비, 길이 출력
		if (boundary != null) {
			text = "L="+((float)Math.round(adj.getLength(boundary.getLength())*10))/10;
			PAINT_LENGTH_TEXT.getTextBounds(text, 0, text.length(), bounds);
			y = y + marginLine - bounds.top;
			canvas.drawText(text, x, y, PAINT_LENGTH_TEXT);
			
			text = "S="+((float)Math.round(adj.getArea(boundary.getArea())*10))/10;
			PAINT_AREA_TEXT.getTextBounds(text, 0, text.length(), bounds);
			y = y + marginLine - bounds.top;
			canvas.drawText(text, x, y, PAINT_AREA_TEXT);
		}