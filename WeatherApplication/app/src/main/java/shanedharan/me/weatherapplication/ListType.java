package shanedharan.me.weatherapplication;

/**
 * Created by shane on 03/03/16.
 *
 * Custom Class for data storage per row of the list.
 */
public class ListType {

    private String Date;
    private float temp;

    public ListType() {
        this.Date = "Monday";
        this.temp = 0;
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
}
