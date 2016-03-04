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

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private static final String DEBUG = "DEBUG";
    private static final char CELSIUS = '\u2103';
    private static final char FAHRENHEIT = '\u2109';

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
        tempValues[0] = 1000; //Given a value it should never reach

        //Get values from "Server"
        for(int i = 1; i < 6; i++) {
            tempValues[i] = serverTemp.getValue();
        }

        //Sets up the day and temperature per value
        for (int i = 0; i < 5; i++) {
            ListType temp = new ListType();
            temp.setTemp(tempValues[i + 1]);
            temp.setDate(days[i]);
            //Will default at Celsius
            temp.setUnit(CELSIUS);
            values[i] = temp;
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
                DecimalFormat df = new DecimalFormat("#");
                df.setRoundingMode(RoundingMode.CEILING);
                //If Celsius convert to Fahrenheit
                if(isChecked) {
                    for(int i=0; i<5; i++) {
                        ListType day = values[i];
                        float temp = day.getTemp();
                        temp = TempCalc.ConvertToFahrenheit(temp);
                        day.setTemp(temp);
                        day.setUnit(FAHRENHEIT);
                        values[i] = day;
                    }
                    if(tempValues[0] < 200) {
                        float convert = TempCalc.ConvertToFahrenheit(tempValues[0]);
                        setTitle("Ambient Temperature: " + df.format(convert) + FAHRENHEIT);
                    }
                    dayAdapter.notifyDataSetChanged();

                } else { //If Fahrenheit convert to Celsius
                    for(int i=0; i<5; i++) {
                        ListType day = values[i];
                        float temp = day.getTemp();
                        temp = TempCalc.ConvertToCelsius(temp);
                        day.setTemp(temp);
                        day.setUnit(CELSIUS);
                        values[i] = day;
                    }
                    if(tempValues[0] < 200) {
                        setTitle("Ambient Temperature: " + df.format(tempValues[0]) + CELSIUS);
                    }
                    dayAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //if value changes, change the title...
        tempValues[0] = event.values[0];

        //grab the value of Monday, all days should go by same unit convention
        ListType holder = values[0];
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.CEILING);

        //If Celsius no calculations are needed.
        if(holder.getUnit() == CELSIUS) {
            setTitle("Ambient Temperature: " + df.format(tempValues[0]) + holder.getUnit());
        } else {
            //Need to convert to Fahreinhgit
            float convert = TempCalc.ConvertToFahrenheit(tempValues[0]);
            setTitle("Ambient Temperature: " + df.format(convert) + holder.getUnit());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
