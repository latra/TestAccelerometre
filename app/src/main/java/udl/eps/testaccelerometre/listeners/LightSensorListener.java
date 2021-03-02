package udl.eps.testaccelerometre.listeners;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

import udl.eps.testaccelerometre.R;

public class LightSensorListener implements SensorEventListener {
    private final Activity mainActivity;
    private final TextView sensorData;
    private long lastUpdate;

    private static final float LIGHT_MIN = 1000;
    private static final float LIGHT_MAX = 3000;
    private String current_status;
    public LightSensorListener(Activity activity) {
        this.mainActivity = activity;
        this.sensorData = this.mainActivity.findViewById(R.id.lightSensorData);
        lastUpdate = System.currentTimeMillis();


    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        getLight(event);
    }

    private void getLight(SensorEvent event) {
        long actualTime = System.currentTimeMillis();
        if (actualTime - lastUpdate < 200) {
            return;
        }
        float[] values = event.values;
        float light = values[0];
        String status = light < LIGHT_MIN ? "LOW" :
                light >= LIGHT_MIN && light < LIGHT_MAX ? "MEDIUM" : "HIGH";

        if (!status.equals(current_status)) {
            sensorData.setText(String.format(mainActivity.getString(R.string.light_sensor_data), light, status));
            lastUpdate = System.currentTimeMillis();
            current_status = status;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
