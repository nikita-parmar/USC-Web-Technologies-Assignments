const express = require('express');
const app = express();
var cors = require('cors');


const eventListRoutes = require('./routes/eventlist');
const eventDetailRoute = require('./routes/eventdetail');
const venueDetailtRoute = require('./routes/venuedetail');
const songkickRoute = require('./routes/songkick');
const spotifyRoute = require('./routes/spotify');
const gcustomsearchRoute = require('./routes/gcustomsearch');
const autocompRoute = require('./routes/autocomp');

app.use(cors());
app.use('/eventlist', eventListRoutes);
app.use('/eventdetail', eventDetailRoute);
app.use('/venuedetail', venueDetailtRoute);
app.use('/songkick', songkickRoute);
app.use('/spotify', spotifyRoute);
app.use('/gcustomsearch', gcustomsearchRoute);
app.use('/autocomp', autocompRoute);

module.exports = app;
