package cbonoan.project3;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.Iterator;

public class Player {
    private float x, y;
    private final Bitmap playerImg;
    private Paint paint = new Paint();
    private final float dpi;
    private int shotTicks = 0;
    private final Resources res;

    ArrayList<Projectile> projectiles = new ArrayList<>();

    public Player(Resources res) {
        this.res = res;
        playerImg = BitmapFactory.decodeResource(res, R.mipmap.player);

        DisplayMetrics dm = res.getDisplayMetrics();
        dpi = dm.densityDpi;

        x = (dm.widthPixels / 2f) - (playerImg.getWidth() / 2f);
        y = (dm.heightPixels * 0.75f);
    }

    public void update(int touchX, int touchY) {
        if(touchX > 0 && touchY > 0) {
            this.x = touchX;
            this.y = touchY;
        }

        // Shooting logic
        shotTicks++;

        if(shotTicks >= 20) {
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
}
