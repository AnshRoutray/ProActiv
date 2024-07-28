package com.example.proactive.models;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

public class ScheduleListData implements Serializable {
    private String name;
    private String startTime;
    private String endTime;
    private  ArrayList<String> videos;
    private ArrayList<String> quizQuestions;
    private ArrayList<Integer> quizAnswers;
    private ArrayList<String> quizOptions;
    private ArrayList<String> topics;
    public String getName(){ return name; }
    public String getStartTime(){
        return startTime;
    }
    public String getEndTime(){
        return endTime;
    }
    public ArrayList<String> getQuizQuestions(){
        return quizQuestions;
    }
    public ArrayList<String> getVideos() {
        return videos;
    }

    public ArrayList<String> getTopics() { return topics; }

    public void setTopics(ArrayList<String> topics) { this.topics = topics; }

    public ArrayList<Integer> getQuizAnswers(){
        return quizAnswers;
    }
    public ArrayList<String> getQuizOptions(){ return quizOptions; }

    public void setName(String name) {
        this.name = name;
    }
    public void setStartTime(String startTime){
        this.startTime = startTime;
    }
    public void setEndTime(String endTime){
        this.endTime = endTime;
    }
    public void setQuizQuestions(ArrayList<String> quizQuestions){ this.quizQuestions = quizQuestions; }
    public void setQuizOptions(ArrayList<String> quizOptions){ this.quizOptions = quizOptions; }
    public void setQuizAnswers(ArrayList<Integer> quizAnswers){ this.quizAnswers = quizAnswers; }
    public void setVideos(ArrayList<String> videos){ this.videos = videos; }
}
