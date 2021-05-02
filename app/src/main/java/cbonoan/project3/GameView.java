package cbonoan.project3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {
    private Thread thread;

    private final Background background1;
    private final Background background2;
    private boolean isPlaying = true;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        // Gives off the imitation of an endless background
        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());
        background2.setY(screenY);
    }

    @Override
    public void run() {
        while(isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void draw() {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            background1.draw(canvas);
            background2.draw(canvas);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void update() {
        background1.update();
        background2.update();
    }

    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
}
