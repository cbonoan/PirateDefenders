package cbonoan.project3;

import android.graphics.Canvas;

public interface GameObject {
    void update();
    void draw(Canvas canvas);

    float getX();
    float getY();
    float getWidth();
    float getHeight();

    boolean isAlive();
    float getHealth();
    float takeDamage(float damage);
}
