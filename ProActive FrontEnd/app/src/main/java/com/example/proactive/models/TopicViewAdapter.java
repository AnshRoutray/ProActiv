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

public class TopicViewAdapter extends ArrayAdapter<String> {

    public TopicViewAdapter(@NonNull Context context, List<String> list) {
        super(context, 0, list);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        String item = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.topic_item, parent, false);
        }

        TextView name = convertView.findViewById(R.id.topicTextViewItem);
        if(convertView != null){
            name.setText(item);
        }
        return convertView;
    }
}
