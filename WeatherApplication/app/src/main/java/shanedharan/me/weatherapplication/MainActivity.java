package shanedharan.me.weatherapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private static final String DEBUG = "DEBUG";

    private float[] tempValues;
    private SensorManager tempManager;
    private Sensor ambientTemp;
    private Temperature serverTemp;
    private ListType[] values;
    private String[] days;
    private ListAdapter dayAdapter;
    private ListView weatherList;
    private ToggleButton toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initalize the title to show ambient temp.
        setTitle("Qualcomm Coding Challenge ");

        tempValues = new float[6];
        serverTemp = new Temperature();
        days = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        values = new ListType[5];
        toggle = (ToggleButton) findViewById(R.id.toggleButton);

        //Sets up the array of values for Monday to Friday and Listeners.
        setup();
    }

    //Initializes the more crucial parts of the program.
    public void setup() {
        //0th position is made for ambient temp.
        tempValues[0] = 0;

        //Get values from "Server"
        for(int i = 1; i < 6; i++) {
            tempValues[i] = serverTemp.getValue();
            Log.d(DEBUG, "The values for server are: " + i + " and equals " + tempValues[i]);
        }

        for (int i = 0; i < 5; i++) {
            ListType temp = new ListType();
            temp.setTemp(tempValues[i + 1]);
            temp.setDate(days[i]);
            values[i] = temp;
            Log.d(DEBUG, "The values for days are: " + days[i] + " and equals " + tempValues[i + 1]);
        }

        dayAdapter = new ListAdapter(this, values);
        weatherList = (ListView) findViewById(R.id.listView);
        weatherList.setAdapter(dayAdapter);

        //Get instance of the sensor service
        tempManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(tempManager.getSensorList(Sensor.TYPE_AMBIENT_TEMPERATURE).size() != 0) {
            ambientTemp = tempManager.getSensorList(Sensor.TYPE_AMBIENT_TEMPERATURE).get(0);
            tempManager.registerListener(this, ambientTemp, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            //no temperature sensor available
            setTitle("No Temperature Sensor Available");
        }

        //Listener for the toggle button, does calculation depending on the unit of measurement
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //If Celsius convert to Fahrenheit
                if(isChecked) {
                    for(int i=0; i<5; i++) {
                        ListType day = values[i];
                        float temp = day.getTemp();
                        temp = TempCalc.ConvertToFahrenheit(temp);
                        day.setTemp(temp);
                        values[i] = day;
                    }
                    dayAdapter.notifyDataSetChanged();
                } else { //If Fahrenheit convert to Celsius
                    for(int i=0; i<5; i++) {
                        ListType day = values[i];
                        float temp = day.getTemp();
                        temp = TempCalc.ConvertToCelsius(temp);
                        day.setTemp(temp);
                        values[i] = day;
                    }
                    dayAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //if value changes, change the title...
        float temp = event.values[0];
        tempValues[0] = temp;
        setTitle("Current Temperature: " + temp);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
