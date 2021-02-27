package udl.eps.testaccelerometre;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import udl.eps.testaccelerometre.listeners.AccelerometerSensorListener;
import udl.eps.testaccelerometre.listeners.LightSensorListener;

public class TestAccelerometreActivity extends Activity {
    private SensorManager sensorManager;
    private SensorEventListener lightListener;
    private SensorEventListener accelerometerListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        TextView topView = findViewById(R.id.topView);
        LinearLayout midView = findViewById(R.id.midLayout);
        ScrollView botView = findViewById(R.id.botView);
        TextView accStatus = findViewById(R.id.accSensorStatus);
        TextView lightStatus = findViewById(R.id.lightSensorStatus);
        topView.setBackgroundColor(Color.GREEN);
        botView.setBackgroundColor(Color.YELLOW);


        lightListener = new LightSensorListener(this);
        accelerometerListener = new AccelerometerSensorListener(this);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager != null && sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            lightStatus.setText(String.format(getString(R.string.light_sensor_ok),
                    sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT).getMaximumRange()));
            sensorManager.registerListener(lightListener,
                    sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else
            lightStatus.setText(getResources().getString(R.string.light_sensor_ko));


        if (sensorManager != null && sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accStatus.setText(getResources().getString(R.string.acc_sensor_ok));
            showSensorData(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
            sensorManager.registerListener(accelerometerListener,
                    sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_NORMAL);

        } else
            accStatus.setText(getResources().getString(R.string.acc_sensor_ko));



    }

    private void showSensorData(Sensor sensor) {
        showSensorData(sensor.getResolution(), sensor.getMaximumRange(), sensor.getPower());
    }

    private void showSensorData(float resolution, float rank, float power) {
        TextView sensorData = findViewById(R.id.accSensorData);
        sensorData.setText(String.format(getString(R.string.acc_sensor_data),
                resolution, rank, power));

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