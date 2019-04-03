const express = require('express');
const router = express.Router();
const request = require('request');

router.get('/:anartist',(req,response,next)=>{
	
	var anartist = req.params.anartist;
	var gcustomsearchUrl = "https://www.googleapis.com/customsearch/v1?q="+ anartist +"&cx=016543536561506243794:6rhzsll1bws&imgSize=huge&imgType=news&num=8&searchType=image&key=AIzaSyCXEvzmA4Ru2TerfA7CFmDMoqPn0TP4yj8";
	
	request(gcustomsearchUrl,{ json: true }, (err, res, body) => {
		response.status(200).json({
			body
			});
		});
	});

module.exports = router;
