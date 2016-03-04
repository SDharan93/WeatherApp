package shanedharan.me.weatherapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by shane on 03/03/16.
 */
public class ListAdapter extends ArrayAdapter{

    public ListAdapter(Context context, ListType[] days) {
        super(context, R.layout.list_row, days);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater contectInflater = LayoutInflater.from(getContext());
        View customView = contectInflater.inflate(R.layout.list_row, parent, false);

        ListType selectDay = (ListType) getItem(position);
        TextView day = (TextView) customView.findViewById(R.id.Day);
        TextView temp = (TextView) customView.findViewById(R.id.Temp);

        //Rounding the temperature value
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.CEILING);
        float num = selectDay.getTemp();

        day.setText(selectDay.getDate());
        temp.setText("Temperature: " + df.format(num) + selectDay.getUnit());

        return customView;
    }
}
