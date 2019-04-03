const express = require('express');
const router = express.Router();
const request = require('request');


router.get('/:venuename',(req,response,next)=>{
	
	var venuename = req.params.venuename;
	console.log(venuename);
	var songkick1url = 'https://api.songkick.com/api/3.0/search/venues.json?query='+ venuename +'&apikey=BGKbfFmzXkpQrTYb';
		request(songkick1url,{ json: true }, (err, res, body) => {
			if(body && body.resultsPage && body.resultsPage.results && body.resultsPage.results.venue[0].id){
				console.log("in if");
				var venueid = body.resultsPage.results.venue[0].id; 			
				songkick2(venueid,response);				
			}else{
				console.log("in else");
				response;
			}		

	});
});

module.exports = router;

function songkick2(venueid, response) {
	var songkick2url = "https://api.songkick.com/api/3.0/venues/"+ venueid +"/calendar.json?apikey=BGKbfFmzXkpQrTYb";			
		request(songkick2url,{ json: true }, (err, res, body) => {
			response.status(200).json({
				body
			});
		});	
}
