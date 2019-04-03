const express = require('express');
const router = express.Router();
const request = require('request');

router.get('/:eventid',(req,response,next)=>{
	
	var eventid = req.params.eventid;
	var eventdetailurl = 'https://app.ticketmaster.com/discovery/v2/events/'+ eventid +'?apikey=M0hmh9XA1IImpyriVwfIpJDzF6ANmoUn';
	console.log(eventdetailurl);
	request(eventdetailurl,{ json: true }, (err, res, body) => {
		response.status(200).json({
			body
			});
		});
	});

module.exports = router;
