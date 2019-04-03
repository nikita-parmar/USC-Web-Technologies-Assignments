const express = require('express');
const router = express.Router();
const request = require('request');

router.get('/:keyword',(req,response,next)=>{
	
	var keyword = req.params.keyword;
	var autocompUrl = "https://app.ticketmaster.com/discovery/v2/suggest?apikey=M0hmh9XA1IImpyriVwfIpJDzF6ANmoUn&keyword="+keyword;
	
	request(autocompUrl,{ json: true }, (err, res, body) => {
		response.status(200).json({
			body
			});
		});
	});

module.exports = router;
