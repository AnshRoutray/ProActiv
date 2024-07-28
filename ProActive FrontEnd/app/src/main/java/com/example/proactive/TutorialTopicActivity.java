package com.example.proactive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

public class TutorialTopicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_topic);
        TextView name = findViewById(R.id.topicName);
        Intent intent = getIntent();
        String topicName = "";
        String videoId = "";
        if(intent != null){
            topicName = intent.getStringExtra("topicName");
            videoId = intent.getStringExtra("videoId");
        }
        else{
            System.out.println("Intent is null");
        }
        WebView youtubeView = findViewById(R.id.youtubeView);
        String video = "<iframe width=\"350\" height=\"300\" src=\"https://www.youtube.com/embed/" + videoId + "\"" +
                " title=\"YouTube video player\" frameborder=\"0\" allow=\"" +
                "accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" " +
                "allowfullscreen></iframe>";
        name.setText(topicName);
        youtubeView.loadData(video, "text/html", "utf-8");
        youtubeView.getSettings().setJavaScriptEnabled(true);
        youtubeView.setWebChromeClient(new WebChromeClient());
    }
}