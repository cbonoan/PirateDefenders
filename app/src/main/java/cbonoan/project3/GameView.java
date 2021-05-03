package cbonoan.project3;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {
    private Thread thread;

    private final Background background1;
    private final Background background2;
    private boolean isPlaying = true;
    private int touchX;
    private int touchY;

    private final Player player;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        Resources res = getResources();

        // Gives off the imitation of an endless background
        background1 = new Background(screenX, screenY, res);
        background2 = new Background(screenX, screenY, res);
        background2.setY(screenY);

        player=  new Player(res);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("EVENT:", String.valueOf(event));
        touchX = (int)event.getX();
        touchY = (int)event.getY();
        return true;
    }

    @Override
    public void run() {
        while(isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update() {
        background1.update();
        background2.update();

        player.update(touchX, touchY);
    }

    private void draw() {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();

            background1.draw(canvas);
            background2.draw(canvas);

            player.draw(canvas);

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        isPlaying = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
