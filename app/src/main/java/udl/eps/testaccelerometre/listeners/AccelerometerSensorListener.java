package udl.eps.testaccelerometre.listeners;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import udl.eps.testaccelerometre.R;

public class AccelerometerSensorListener implements SensorEventListener {
    private final Activity mainActivity;
    private long lastUpdate;
    private boolean color = false;
    private final TextView colorView;

    public AccelerometerSensorListener(Activity activity) {
        this.mainActivity = activity;
        colorView = this.mainActivity.findViewById(R.id.topView);
        lastUpdate = System.currentTimeMillis();

    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        getAccelerometer(event);
    }

    private void getAccelerometer(SensorEvent event) {
        long actualTime = System.currentTimeMillis();
        if (actualTime - lastUpdate < 200) {
            return;
        }
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        if (accelationSquareRoot >= 1.7) {
            lastUpdate = actualTime;
            Toast.makeText(this.mainActivity, R.string.shuffed, Toast.LENGTH_SHORT).show();
            if (color) {
                colorView.setBackgroundColor(Color.GREEN);

            } else {
                colorView.setBackgroundColor(Color.RED);
            }
            color = !color;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }


}
