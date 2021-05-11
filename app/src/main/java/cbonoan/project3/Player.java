package cbonoan.project3;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.Iterator;

public class Player implements GameObject{
    private float x, y, xSpeed;
    private final Bitmap playerImg;
    private Paint paint = new Paint();
    private final float dpi;
    private int shotTicks = 0;
    private final Resources res;
    private final int width, height;
    private final int screenWidth;

    ArrayList<Projectile> projectiles = new ArrayList<>();
    private float health = 100f;

    public Player(Resources res) {
        this.res = res;
        playerImg = BitmapFactory.decodeResource(res, R.mipmap.player);
        width = playerImg.getWidth();
        height = playerImg.getHeight();

        DisplayMetrics dm = res.getDisplayMetrics();
        dpi = dm.densityDpi;
        screenWidth = res.getDisplayMetrics().widthPixels;

        x = (dm.widthPixels / 2f) - (playerImg.getWidth() / 2f);
        y = (dm.heightPixels * 0.75f);
        xSpeed = 0.1f *dpi;
    }

    public void updatePos(float tiltX) {
        // TODO
        // Get sensor data and update x position of player
        // based on tilt of phone

        if(tiltX > 30.0f && x <= screenWidth * 0.8) {
            x += xSpeed;
        } else if(tiltX < -30.0f && x >= 0) {
            x -= xSpeed;
        }
    }

    public void update() {

        // Shooting logic
        shotTicks++;

        if(shotTicks >= 12) {
            Projectile tmp = new Projectile(this.res);
            tmp.setX(x + (playerImg.getWidth() / 2) - tmp.getMidX());
            tmp.setY(y - tmp.getHeight() / 2f);

            projectiles.add(tmp);

            shotTicks = 0;
        }

        for(Iterator<Projectile> iterator = projectiles.iterator(); iterator.hasNext(); ) {
            Projectile projectile = iterator.next();
            if(!projectile.isOnScreen()) {
                iterator.remove();
            }
        }

        for(Projectile projectile : projectiles) {
            projectile.update();
        }

    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(playerImg, this.x, this.y, paint);

        for(Projectile projectile : projectiles) {
            projectile.draw(canvas);
        }
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public boolean isAlive() {
        return health > 0f;
    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public float takeDamage(float damage) {
        return health -= damage;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }
}
