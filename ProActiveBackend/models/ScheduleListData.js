const mongoose = require('mongoose');

const scheduleListDataSchema = new mongoose.Schema({
    name: String,
    startTime: String,
    endTime: String,
    videos: [String],
    quizQuestions: [String],
    quizAnswers: [Number],
    quizOptions: [String],
    topics: [String]
});

const ScheduleListData = mongoose.model('ScheduleListData', scheduleListDataSchema);

module.exports = ScheduleListData;
