const express = require('express');
const router = express.Router();
const request = require('request');
var geohash = require('ngeohash');

router.get('/:keyword/:segmentId/:radius/:unit/:latitude/:longitude/:locationtext',(req,response,next)=>{
	var keyword = req.params.keyword;
	var segmentId = req.params.segmentId;
	if(segmentId == "All"){
		segmentId="";
	}
	var radius = req.params.radius;
	var unit = req.params.unit;
	var latitude = req.params.latitude;
	var longitude = req.params.longitude;

	if(req.params.locationtext != "Nolocation"){
		var geocodeUrl = 'https://maps.googleapis.com/maps/api/geocode/json?address='+ req.params.locationtext +'&key=AIzaSyByqc69cN16Nwsa8SviTmGd08bf-VHzsJ8';
		request(geocodeUrl,{ json: true }, (err, res, body) => {
				latitude = body.results[0].geometry.location.lat;
				longitude = body.results[0].geometry.location.lng;
				var geoPoint = geohash.encode(latitude, longitude);
				var eventlisturl = "https://app.ticketmaster.com/discovery/v2/events.json?apikey=M0hmh9XA1IImpyriVwfIpJDzF6ANmoUn&keyword="+ 
				keyword +"&segmentId="+ segmentId +"&radius="+ radius +"&unit="+ unit +"&geoPoint="+geoPoint+"&sort=date,asc";
				request(eventlisturl,{ json: true }, (err, res, body) => {
					response.status(200).json({
						body
						}); //End of response
					}); // end of req eventlistURL
			});
	}else{
		var geoPoint = geohash.encode(latitude, longitude);
		console.log(geoPoint);
		var eventlisturl = "https://app.ticketmaster.com/discovery/v2/events.json?apikey=M0hmh9XA1IImpyriVwfIpJDzF6ANmoUn&keyword="+ 
		keyword +"&segmentId="+ segmentId +"&radius="+ radius +"&unit="+ unit +"&geoPoint="+geoPoint+"&sort=date,asc";
		console.log(eventlisturl);
		request(eventlisturl,{ json: true }, (err, res, body) => {
			response.status(200).json({
				body
				}); //End of response
			}); // end of req eventlistURL	
		}
	
});  //End of get

module.exports = router;


