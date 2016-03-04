package shanedharan.me.weatherapplication;

import java.util.Random;

/**
 * Created by shane on 03/03/16.
 *
 * Currently this class only generates random numbers, however if this were a real weather
 * application I would use this class for server communication to get the next 5 day forecast.
 */
public class Temperature {

    //Canada's temperature sucks
    private static final int MIN_TEMP = -14;
    private static final int MAX_TEMP = 9;

    private Random tempValue;

    public Temperature() {
        tempValue = new Random();
    }

    public float getValue() {
        //dividing my 1f forces a floating point variable
        float realTemp = tempValue.nextInt(MAX_TEMP - MIN_TEMP) + MIN_TEMP / 1f;
        return realTemp;
    }
}
