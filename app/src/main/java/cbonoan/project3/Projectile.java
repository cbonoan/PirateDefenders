package cbonoan.project3;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Projectile implements GameObject{

    private float x, y;
    private Bitmap projectile;
    private float dpi;
    private Paint paint = new Paint();
    private float health = 100f;

    public Projectile(Resources res) {
        projectile = BitmapFactory.decodeResource(res, R.mipmap.canonball);
        dpi = res.getDisplayMetrics().densityDpi;
    }
    
    public boolean isOnScreen() {
        return !(y < getHeight());
    }

    public void update() {
        y -= 0.1f * dpi;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(projectile, this.x, this.y, paint);
    }

    public float getMidX() {
        return projectile.getWidth() / 2f;
    }

    @Override
    public float getHeight() {
        return projectile.getHeight();
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

    @Override
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getWidth() {
        return projectile.getWidth();
    }

    public void setY(float y) {
        this.y = y;
    }
}
