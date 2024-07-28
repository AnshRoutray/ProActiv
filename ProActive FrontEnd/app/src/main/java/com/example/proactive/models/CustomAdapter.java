package com.example.proactive.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.proactive.R;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<ScheduleListData> {

    public CustomAdapter(@NonNull Context context, List<ScheduleListData> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ScheduleListData item = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_item, parent, false);
        }

        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView startTimeTextView = convertView.findViewById(R.id.startTimeTextView);
        TextView endTimeTextView = convertView.findViewById(R.id.endTimeTextView);

        if(convertView != null){
            nameTextView.setText(item.getName());
            startTimeTextView.setText("Start Time: " + item.getStartTime());
            endTimeTextView.setText("End Time: " + item.getEndTime());
        }

        return convertView;
    }
}
