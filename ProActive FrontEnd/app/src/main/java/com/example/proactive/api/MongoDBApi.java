package com.example.proactive.api;

import com.example.proactive.models.ScheduleListData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface MongoDBApi {
    @GET("/api/TutorialData") // Node.js API endpoint for retrieving data
    Call<List<ScheduleListData>> getData();

    @POST("/api/TutorialData") // Node.js API endpoint for adding data
    Call<Void> addData(@Body ScheduleListData data);

    @GET("/api/AIData/{tutorialName}")
    Call<ScheduleListData> getAIData(@Path("tutorialName") String tutorialName);

    // Define other CRUD operations as needed
}

