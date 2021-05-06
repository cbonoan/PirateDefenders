package cbonoan.project3;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {
    private Thread thread;

    private final Background background1;
    private final Background background2;
    private boolean isPlaying = true;
    private float tiltX;
    private ArrayList<Projectile> projectiles;
    private ArrayList<GameObject> enemies;
    private GameActivity gameActivity;

    private final Player player;

    private EnemySpawner spawner;
    private int projectileDmg = 50;
    private final float screenWidth, screenHeight;
    private Paint textPaint = new Paint();

    public GameView(GameActivity context, int screenX, int screenY) {
        super(context);

        Resources res = getResources();
        screenWidth = res.getDisplayMetrics().widthPixels;
        screenHeight = res.getDisplayMetrics().heightPixels;

        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(screenWidth * 0.1f);

        // Gives off the imitation of an endless background
        background1 = new Background(screenX, screenY, res);
        background2 = new Background(screenX, screenY, res);
        background2.setY(screenY);

        player=  new Player(res);

        spawner = new EnemySpawner(res);

        projectiles = player.getProjectiles();
        enemies = spawner.getEnemies();

        gameActivity = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("EVENT:", String.valueOf(event));
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

    public void setTiltX(float x) {
        this.tiltX = x;
    }

    private void update() {
        background1.update();
        background2.update();

        player.updatePos(tiltX);
        player.update();

        spawner.update();

        checkAllCollisions();
        checkEnemiesOffScreen();
    }

    private void checkAllCollisions() {
        // Projectile collision
        for(Projectile projectile : projectiles) {
            for(GameObject go : enemies) {
                if(checkCollision(projectile, go)) {
                    projectile.takeDamage(100);
                    go.takeDamage(projectileDmg);
                }
            }
        }

        //Check if player collides with enemy
        for(GameObject go : enemies) {
            if(checkCollision(player, go)) {
                player.takeDamage(100);
                go.takeDamage(100);
                gameActivity.gameOver();
            }
        }
    }

    private boolean checkCollision(GameObject g1, GameObject g2) {
        return g1.getX() < g2.getX() + g2.getWidth() &&
                g1.getX() + g1.getWidth() > g2.getX() &&
                g1.getY() < g2.getY() + g2.getHeight() &&
                g1.getY() + g1.getHeight() > g2.getY();
    }

    private void checkEnemiesOffScreen() {
        for(GameObject go : enemies) {
            if(go.getY() > screenHeight) {
                player.takeDamage(100);
                go.takeDamage(100);
                gameActivity.gameOver();
            }
        }
    }

    private void draw() {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();

            background1.draw(canvas);
            background2.draw(canvas);

            if(!player.isAlive()) {
                canvas.drawText("GAME OVER", screenWidth/4f, screenHeight/2f, textPaint);
            }

            player.draw(canvas);

            spawner.draw(canvas);

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
