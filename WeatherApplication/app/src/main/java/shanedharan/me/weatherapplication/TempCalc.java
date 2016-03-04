package shanedharan.me.weatherapplication;

import android.util.Log;

/**
 * Created by shane on 03/03/16.
 *
 * A class made for calculating the new unit of temperature using the native C functions
 */
public class TempCalc {

    private static final String DEBUG = "DEBUG";

    private native static float ConvertToCelsiusNative(float temp);
    private native static float ConvertToFahrenheitNative(float temp);

    static {
        try {
            System.loadLibrary("tempConv");
        } catch (UnsatisfiedLinkError e) {
            Log.d(DEBUG, "Native code library failed to load " + e);
            System.exit(1);
        }
    }

    public static float ConvertToCelsius(float temp) {

        Log.d(DEBUG, "Entered convert to Celsius");
        float newTemp = ConvertToCelsiusNative(temp);
        Log.d(DEBUG, "Returning Celsius");
        return newTemp;
    }

    public static float ConvertToFahrenheit(float temp) {

        Log.d(DEBUG, "Entered convert to Fahrenheit");
        float newTemp = ConvertToFahrenheitNative(temp);
        Log.d(DEBUG, "Returning Fahrenheit");
        return newTemp;
    }

}
