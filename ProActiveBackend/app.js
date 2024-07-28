const express = require('express');
const bodyParser = require('body-parser');
const db = require('./db');
const apiRoutes = require('./routes/api');

const app = express();

app.use(bodyParser.json());

app.use('/api', apiRoutes);

const PORT = 3000;
app.listen(PORT, '0.0.0.0', () => {
    console.log(`Server is running on port ${PORT}`);
});
