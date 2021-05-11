package cbonoan.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity implements SensorEventListener {
    private GameView gameView;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magneticField;
    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];

    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

         // Get x and y coords of player
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameView = new GameView(this, point.x, point.y);

        // Create sensor service
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(GameActivity.this, accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);

        setContentView(gameView);
    }

    public void gameOver() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 6000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_NORMAL);
        gameView.resume();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(sensorEvent.values, 0, accelerometerReading,
                    0, accelerometerReading.length);
            //Log.d("accelerometer3: ", String.valueOf(accelerometerReading[0]));
        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(sensorEvent.values, 0, magnetometerReading,
                    0, magnetometerReading.length);
            Log.d("magnetometerX: ", String.valueOf(magnetometerReading[0]));
            Log.d("magnetometerY: ", String.valueOf(magnetometerReading[1]));
            Log.d("magnetometerZ: ", String.valueOf(magnetometerReading[2]));
        }
        //Log.d("GAMEACTIVITY", "X: " + sensorEvent.values[0]);
        updateOrientationAngles();
        //Log.d("orientation: ", String.valueOf(orientationAngles[0]));

        gameView.setTiltX(magnetometerReading[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    public void updateOrientationAngles() {
        // Update rotation matrix, which is needed to update orientation angles.
        SensorManager.getRotationMatrix(rotationMatrix, null,
                accelerometerReading, magnetometerReading);

        // "rotationMatrix" now has up-to-date information.

        SensorManager.getOrientation(rotationMatrix, orientationAngles);
        //Log.d("orientationX: ", String.valueOf(orientationAngles[0]));
        //Log.d("orientationY: ", String.valueOf(orientationAngles[1]));
        //Log.d("orientationZ: ", String.valueOf(orientationAngles[2]));
        // "orientationAngles" now has up-to-date information.
    }
}