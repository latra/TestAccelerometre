package udl.eps.testaccelerometre.listeners;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Console;

import udl.eps.testaccelerometre.R;

public class LightSensorListener implements SensorEventListener {
    private Activity mainActivity;
    private TextView listener_text;
    private long lastUpdate;

    private static float LIGHT_MIN = 1000;
    private static float LIGHT_MAX = 3000;
    private String current_status;
    public LightSensorListener(Activity activity) {
        this.mainActivity = activity;
        this.listener_text = this.mainActivity.findViewById(R.id.textViewBot);
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
            listener_text.setText(status);
            lastUpdate = System.currentTimeMillis();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
