package udl.eps.testaccelerometre;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.Toast;

import udl.eps.testaccelerometre.listeners.AccelerometerSensorListener;
import udl.eps.testaccelerometre.listeners.LightSensorListener;

public class TestAccelerometreActivity extends Activity {
    private SensorManager sensorManager;
    private TextView topView;
    private TextView midView;
    private SensorEventListener lightListener;
    private SensorEventListener accelerometerListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("test! START");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        topView = findViewById(R.id.textViewTop);
        midView = findViewById(R.id.textViewMid);
        topView.setBackgroundColor(Color.GREEN);
        lightListener = new LightSensorListener(this);
        accelerometerListener = new AccelerometerSensorListener(this);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager != null && sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null)
            sensorManager.registerListener(lightListener,
                    sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                    SensorManager.SENSOR_DELAY_NORMAL);

        if (sensorManager != null && sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            midView.setText(getResources().getString(R.string.sensor_ok));
            sensorManager.registerListener(accelerometerListener,
                    sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_NORMAL);

        } else
            midView.setText(getResources().getString(R.string.sensor_ko));

        // register this class as a listener for the accelerometer sensor


    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(lightListener);
        sensorManager.unregisterListener(accelerometerListener);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (sensorManager != null && sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null)
            sensorManager.registerListener(lightListener,
                    sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                    SensorManager.SENSOR_DELAY_NORMAL);

        if (sensorManager != null && sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
            sensorManager.registerListener(accelerometerListener,
                    sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(lightListener);
        sensorManager.unregisterListener(accelerometerListener);
    }
}