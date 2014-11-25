package kr.jollybus.realsize;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class RealSizeActivity extends Activity {
	
	CanvasController canvasController;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Window window = getWindow();
        window.requestFeature(Window.FEATURE_ACTION_BAR);
        window.requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_canvas);
        final CanvasView canvasView = (CanvasView) findViewById(R.id.canvas_view);
        canvasController = new CanvasController(canvasView, this);
    }
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		canvasController.configurationChanged(newConfig);
	}
}
