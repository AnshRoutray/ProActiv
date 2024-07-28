package com.example.proactive;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.proactive.models.ScheduleListData;
import com.example.proactive.models.TopicViewAdapter;

public class TutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        Intent intent = getIntent();
        ScheduleListData topic = new ScheduleListData();
        ListView list = findViewById(R.id.topicList);
        if (intent != null) {
            topic = (ScheduleListData) intent.getSerializableExtra("SelectedTutorial");
        } else {
            System.out.println("Tutorial failed to load in Intent");
        }
        TextView tutorialName = findViewById(R.id.tutorialNameTextView);
        tutorialName.setText(topic.getName());
        TopicViewAdapter adapter = new TopicViewAdapter(this, topic.getTopics());
        list.setAdapter(adapter);
        ScheduleListData finalTopic = topic;
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TutorialActivity.this, TutorialTopicActivity.class);
                intent.putExtra("topicName", finalTopic.getTopics().get(position));
                intent.putExtra("videoId", finalTopic.getVideos().get(position));
                startActivity(intent);
            }
        });
    }
}