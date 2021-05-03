package cbonoan.project3;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Player {
    private int x, y;
    private final Bitmap playerImg;
    private Paint paint = new Paint();

    public Player(Resources res) {
        playerImg = BitmapFactory.decodeResource(res, R.mipmap.player);
    }

    public void update(int touchX, int touchY) {
        if(touchX > 0 && touchY > 0) {
            this.x = touchX;
            this.y = touchY;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(playerImg, this.x, this.y, paint);
    }
}
