package cbonoan.project3;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class EnemySpawner {
    private ArrayList<GameObject> enemies;
    float x,y = 0;
    int screenWidth;
    int wave = 1;
    int frameTick = 0, spawnTick, waveTick = 0;
    Resources res;
    private Paint paint = new Paint();

    public EnemySpawner(Resources res) {
        screenWidth = res.getDisplayMetrics().widthPixels;
        this.res = res;
        enemies = new ArrayList<>();
        spawnTick = new Random().nextInt(120-60) + 60;
        paint.setColor(Color.WHITE);
        paint.setTextSize(screenWidth * 0.05f);
    }

    public void update() {
        frameTick++;
        if(frameTick >= spawnTick) {
            frameTick = 0;
            spawnTick = new Random().nextInt(120-60)+60;

            x = new Random().nextInt((int) (screenWidth * 0.75f - screenWidth * 0.05f)) + screenWidth * 0.05f;

            enemies.add(new Enemy(res,x,y));
        }

        for(Iterator<GameObject> iterator = enemies.iterator(); iterator.hasNext();) {
            GameObject go = iterator.next();
            go.update();

            // Check if game object should be destroyed
            if(!go.isAlive()) {
                iterator.remove();
            }
        }
    }

    public void draw(Canvas canvas) {
        for(GameObject go : enemies) {
            go.draw(canvas);
        }
    }

    public ArrayList<GameObject> getEnemies() {
        return enemies;
    }
}
