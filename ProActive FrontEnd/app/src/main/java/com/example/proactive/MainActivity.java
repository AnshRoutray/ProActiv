package com.example.proactive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.example.proactive.api.MongoDBApi;
import com.example.proactive.api.MongoDBService;
import com.example.proactive.models.CustomAdapter;
import com.example.proactive.models.ScheduleListData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private List<ScheduleListData> dataList = new ArrayList<>();
    private CustomAdapter adapter;
    private ListView scheduleList;
    private TextView noScheduleLabel;

    private static int REQUEST_CODE = 123;
    private void fetchDataFromServer(){
        MongoDBService mongoDbService = new MongoDBService();
        MongoDBApi apiInterface = mongoDbService.getApi();
        Call<List<ScheduleListData>> call = apiInterface.getData();
        call.enqueue(new Callback<List<ScheduleListData>>() {
            @Override
            public void onResponse(Call<List<ScheduleListData>> call, Response<List<ScheduleListData>> response) {
                if(response.isSuccessful()){
                    System.out.println("BOOM BABY, IT WORKS!!");
                    List<ScheduleListData> schedule = response.body();
                    if(schedule.isEmpty()){
                        scheduleList.setVisibility(View.INVISIBLE);
                        noScheduleLabel.setVisibility(View.VISIBLE);
                    }
                    else{
                    dataList.addAll(schedule);
                    adapter.notifyDataSetChanged();
                }
            }
                else {
                System.out.println("Response code: " + response.code()); // Log the response code
                System.out.println("Response message: " + response.message()); // Log the response message
            }
        }

        @Override
        public void onFailure(Call<List<ScheduleListData>> call, Throwable t) {
            t.printStackTrace();
            System.out.println("Network Failure to call data from MongoDB Database.");
            scheduleList.setVisibility(View.INVISIBLE);
                noScheduleLabel.setVisibility(View.VISIBLE);
                noScheduleLabel.setText("Failed to load data");
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addTutorialButton = findViewById(R.id.AddTutorialButton);
        scheduleList = findViewById(R.id.ActivityList);
        noScheduleLabel = findViewById(R.id.NoScheduleLabel);
        addTutorialButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTutorialActivity.class);
            intent.putExtra("dataList", (Serializable) dataList);
            startActivityForResult(intent, REQUEST_CODE);
        });
        scheduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ScheduleListData selectedTutorial = dataList.get(position);
                Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
                intent.putExtra("SelectedTutorial", selectedTutorial);
                startActivity(intent);
            }
        });
        adapter = new CustomAdapter(this, dataList);
        scheduleList.setAdapter(adapter);
        fetchDataFromServer();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // Retrieve the modified dataList from the result intent
            Serializable serializableData = data.getSerializableExtra("TutorialData");

            if (serializableData != null && serializableData instanceof ScheduleListData) {
                // Update your dataList and notify the adapter
                dataList.add((ScheduleListData)serializableData);
                adapter.notifyDataSetChanged();
                noScheduleLabel.setVisibility(View.INVISIBLE);
                scheduleList.setVisibility(View.VISIBLE);
            }
        }
    }
}