const express = require('express');
const router = express.Router();
const request = require('request');

router.get('/:venuename',(req,response,next)=>{
	
	var venuename = req.params.venuename;
	var venuedetailurl = "https://app.ticketmaster.com/discovery/v2/venues?apikey=M0hmh9XA1IImpyriVwfIpJDzF6ANmoUn&keyword="+venuename;;
	request(venuedetailurl,{ json: true }, (err, res, body) => {
		response.status(200).json({
			body
			});
		});
	});

module.exports = router;
