package shanedharan.me.weatherapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private int[] tempValues;
    private SensorManager tempManager;
    private Sensor ambientTemp;
    private Temperature serverTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initalize the title to show ambient temp.
        setTitle("Current Temperature: ");

        tempValues = new int[5];
        serverTemp = new Temperature();

        setup();
    }

    public void setup() {
        //0th position is made for ambient temp.
        tempValues[0] = 0;

        //Get values from "Server"
        for(int i = 1; i < 5; i++) {
            tempValues[i] = serverTemp.getValue();
        }

        //Get instance of the sensor service
        tempManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(tempManager.getSensorList(Sensor.TYPE_AMBIENT_TEMPERATURE).size() != 0) {
            ambientTemp = tempManager.getSensorList(Sensor.TYPE_AMBIENT_TEMPERATURE).get(0);
            tempManager.registerListener(this, ambientTemp, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            //no temperature sensor available
            setTitle("No Temperature Sensor Available");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //if value changes, change the title...
        float temp = event.values[0];
        setTitle("Current Temperature: " + temp);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
