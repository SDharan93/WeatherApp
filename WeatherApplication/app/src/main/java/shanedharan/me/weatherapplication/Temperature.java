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
    private static final float MIN_TEMP = -14f;

    private Random tempValue;

    public Temperature() {
        tempValue = new Random();
    }

    public float getValue() {
        return tempValue.nextFloat() + MIN_TEMP;
    }
}
