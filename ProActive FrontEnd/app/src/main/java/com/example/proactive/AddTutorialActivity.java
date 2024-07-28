package com.example.proactive;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.example.proactive.api.MongoDBApi;
import com.example.proactive.api.MongoDBService;
import com.example.proactive.models.ScheduleListData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tutorial);

        Button enterButton = findViewById(R.id.enterButton);
        EditText nameText = findViewById(R.id.editTextName);
        EditText startTime = findViewById(R.id.editTextStartTime);
        EditText endTime = findViewById(R.id.editTextEndTime);

        enterButton.setOnClickListener(v -> {
            ProgressDialog progressDialog = new ProgressDialog(AddTutorialActivity.this);
            progressDialog.setMessage("Creating tutorial...");
            progressDialog.show();

            ScheduleListData newTutorial = new ScheduleListData();
            newTutorial.setStartTime(startTime.getText().toString());
            newTutorial.setEndTime(endTime.getText().toString());

            MongoDBService mongoDbService = new MongoDBService();
            MongoDBApi apiInterface = mongoDbService.getApi();

            // Display progress bar while fetching data from OpenAI
            progressDialog.setMessage("Preparing Tutorial...");
            progressDialog.show();

            Call<ScheduleListData> call1 = apiInterface.getAIData(nameText.getText().toString());
            call1.enqueue(new Callback<ScheduleListData>() {
                @Override
                public void onResponse(Call<ScheduleListData> call, Response<ScheduleListData> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ScheduleListData body = response.body();
                        newTutorial.setVideos(body.getVideos());
                        newTutorial.setQuizQuestions(body.getQuizQuestions());
                        newTutorial.setQuizOptions(body.getQuizOptions());
                        newTutorial.setQuizAnswers(body.getQuizAnswers());
                        newTutorial.setTopics(body.getTopics());
                        System.out.println("OpenAI response successful");
                    } else {
                        System.out.println("OpenAI response is not successful");
                    }
                    newTutorial.setName(nameText.getText().toString());
                    System.out.println("Response: " + newTutorial.getVideos().get(0));

                    // Proceed with adding data to MongoDB
                    Call<Void> call2 = apiInterface.addData(newTutorial);
                    call2.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                System.out.println("POST to MongoDB successful");
                            } else {
                                System.out.println("POST to MongoDB is not successful");
                            }
                            progressDialog.dismiss(); // Dismiss the progress dialog after both calls are completed

                            // Set the result and finish the activity
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("TutorialData", newTutorial);
                            setResult(RESULT_OK, resultIntent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            System.out.println("POST to MongoDB failed");
                            progressDialog.dismiss(); // Dismiss the progress dialog even if MongoDB call fails
                        }
                    });
                }

                @Override
                public void onFailure(Call<ScheduleListData> call, Throwable t) {
                    System.out.println("OpenAI API call failed");
                    progressDialog.dismiss(); // Dismiss the progress dialog if OpenAI call fails
                }
            });
        });
    }
}
