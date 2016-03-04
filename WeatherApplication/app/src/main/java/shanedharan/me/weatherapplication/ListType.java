package shanedharan.me.weatherapplication;

/**
 * Created by shane on 03/03/16.
 *
 * Custom Class for data storage per row of the list.
 */
public class ListType {

    private String Date;
    private float temp;
    private char unit;

    public ListType() {
        this.Date = "Monday";
        this.temp = 0;
        this.unit = '\u2103';
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public void setUnit(char unit) {
        this.unit = unit;
    }

    public char getUnit() {
        return this.unit;
    }
}
