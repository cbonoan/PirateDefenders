package cbonoan.project3;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Enemy implements GameObject{
    private float x, y, ySpeed;
    private float health = 100f;
    private Bitmap enemy;
    private final int screenWidth, screenHeight, dpi;
    private final int width, height;
    private Paint paint = new Paint();
    private int frameTick;

    public Enemy(Resources res, float x, float y) {
        dpi = res.getDisplayMetrics().densityDpi;
        screenHeight = res.getDisplayMetrics().heightPixels;
        screenWidth = res.getDisplayMetrics().widthPixels;

        enemy = BitmapFactory.decodeResource(res, R.mipmap.enemy1);
        width = enemy.getWidth();
        height = enemy.getHeight();

        this.x = x;
        this.y = y;

        ySpeed = 0.1f *dpi;
    }

    @Override
    public void update() {
        y += ySpeed;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(enemy, x, y, paint);
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
}
