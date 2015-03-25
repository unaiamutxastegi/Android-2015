package com.unaiamutxastegi.earthquakes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unaiamutxastegi.earthquakes.R;
import com.unaiamutxastegi.earthquakes.model.EarthQuake;

import java.util.List;

/**
 * Created by cursomovil on 25/03/15.
 */
public class EarthquakeAdapter extends ArrayAdapter<EarthQuake> {

    private int resource;

    public EarthquakeAdapter(Context context, int resource, List<EarthQuake> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;

        if (convertView == null){
            //Si no existe la vista, la creamos
            layout = new LinearLayout(getContext());

            LayoutInflater li;
            String inflater = Context.LAYOUT_INFLATER_SERVICE;

            li = (LayoutInflater) getContext().getSystemService(inflater);
            li.inflate(resource,layout,true);
        }else {
            layout = (LinearLayout) convertView;
        }

        EarthQuake earthQuake = getItem(position);

        TextView txtMagnitude = (TextView) layout.findViewById(R.id.txtMagnitud);
        TextView txtInfo = (TextView) layout.findViewById(R.id.txtInfo);

        String info = "ID: " + earthQuake.get_id()+ " PLACE: " + earthQuake.getPlace()+ " COORDS: " + earthQuake.getCoords()
                +" TIME: "+ earthQuake.getTime();
        txtMagnitude.setText(Double.toString(earthQuake.getMagnitude()));

        txtInfo.setText(info);

        return layout;
    }
}
