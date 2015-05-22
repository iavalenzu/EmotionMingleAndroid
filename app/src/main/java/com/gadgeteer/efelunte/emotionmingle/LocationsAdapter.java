package com.gadgeteer.efelunte.emotionmingle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gadgeteer.efelunte.emotionmingle.model.Location;

import java.util.List;

/**
 * Created by ismaelvalenzuela on 19-05-15.
 */
public class LocationsAdapter extends ArrayAdapter<Location> {

    // View lookup cache
    private static class ViewHolder {
        TextView description;
        TextView type;
    }


    public LocationsAdapter(Context context, List<Location> users) {
        super(context, android.R.layout.simple_list_item_2, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Get the data item for this position
        Location location = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
            viewHolder.description = (TextView) convertView.findViewById(android.R.id.text1);
            viewHolder.type = (TextView) convertView.findViewById(android.R.id.text2);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        String description = location.getDescription();
        String type = location.getType();

        // Populate the data into the template view using the data object
        viewHolder.description.setText(description);
        viewHolder.type.setText(type);

        if(location.isSelected())
        {
            convertView.setBackgroundColor(getContext().getResources().getColor(android.R.color.holo_green_light));
        }
        else
        {
            convertView.setBackgroundColor(getContext().getResources().getColor(android.R.color.white));
        }


        // Return the completed view to render on screen
        return convertView;
    }


}
