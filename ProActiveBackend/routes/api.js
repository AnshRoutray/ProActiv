const express = require('express');
const router = express.Router();
const ScheduleListData = require('../models/ScheduleListData');
const app = express();
const axios = require('axios');
const {google} = require('googleapis');
const OpenAI = require("openai");
const openai = new OpenAI({ apiKey: /*OPENAI KEY*/ });

router.get('/TutorialData', async (req, res) => {
    try {
        console.log('Received a GET request to /api/data');
        const data = await ScheduleListData.find();
        res.json(data);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});

router.post('/TutorialData', async (req, res) => {
    const newData = new ScheduleListData(req.body);
    try {
        console.log("Received a POST request to /api/data");
        const savedData = await newData.save();
        res.status(201).json(savedData);
    } catch (error) {
        res.status(400).json({ message: error.message });
    }
});

//OpenAI data request

router.get('/AIData/:tutorialName', async (req, res) => {
    try {
        res.setTimeout(5 * 60 * 1000);
        console.log("Received OpenAI API request1");
        const tutorialName = req.params.tutorialName;

        // Make a request to OpenAI API to generate information based on the tutorialName
        console.log("Beginning");
        const prompt = "Generate all subtopics for " + tutorialName + " no extra words, just the subtopics in number form.";
        const openAIPromise = new Promise(async (resolve, reject) => {
            try{
                const response = await openai.chat.completions.create({
                    messages: [{ role: "system", content: prompt }],
                    model: "gpt-3.5-turbo",
                });
                console.log("OpenAI successful");
                resolve(response.choices[0].message.content);
            }
            catch(error){
                console.log("Problem OpenAI");
                reject(error);
            }
        });
        // Extract relevant information from the OpenAI response
        const generatedInfo = await openAIPromise;
        // Edit the response to match ScheduleListData schema
        const headers = extractHeaders(generatedInfo);
        // Send the generated information as the response
        const ytPromise = new Promise(async (resolve, reject) => {
            try{
                const newTutorial = new ScheduleListData();
                for (const header of headers) {
                    const query = `${header} tutorial`; // Header for the YouTube search
        
                    // Search for videos or playlists related to the subtopic
                    const response = await axios.get('https://www.googleapis.com/youtube/v3/search', {
                        params: {
                          key: /*GOOGLE API KEY*/,
                          q: query,
                          part: 'snippet',
                          type: 'video',
                          maxResults: 1,
                        },
                    });
                    let video = response.data.items[0].id.videoId;
                    newTutorial.videos.push(video);
                }
                resolve(newTutorial);
            }
            catch(error){
                console.log("Youtube error ");
                console.log(error);
                reject(error);
            }
        });
        let newTutorial = await ytPromise;
        newTutorial.topics = headers;
        console.log("Youtube Successful");
        res.json(newTutorial);
    } 
    catch (error) {
        console.error('Error in OpenAI request or Youtube API:', error.message);
        res.status(500).json({ message: 'Internal server error' });
    }
});

module.exports = router;

function extractHeaders(generatedInfo) {
    const headers = [];
  
    // Regular expression to match numbered topics
    const regex = /^(\d+\.\s*([^\n]+))/gm;
  
    let match;
    while ((match = regex.exec(generatedInfo)) !== null) {
      const header = match[2].trim();
      headers.push(header);
    }
  
    return headers;
}
