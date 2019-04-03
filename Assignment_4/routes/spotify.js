const express = require('express');
const router = express.Router();
const request = require('request');

var SpotifyWebApi = require('spotify-web-api-node');

// credentials are optional
var spotifyApi = new SpotifyWebApi({
  clientId: 'de5f15339a9d41468a986ac42844e58c',
  clientSecret: 'f9d79c0e8ac24471a64956ffb715eae7',
  redirectUri: 'http://www.example.com/callback'
});

router.get('/:artistname',(req,response,next)=>{
	
	var artistname = req.params.artistname;
	var message = '';
	
	spotifyApi.searchArtists(artistname)
	  .then(function(data) {
	    console.log('Search artists by 1 : ', data.body);
	    message = data.body;
		response.status(200).json({
			message
		});

	  }, // end of found 1   
	  function(err) {
	    console.error(err);
 
 		spotifyApi.clientCredentialsGrant().then(
		  function(data) {

		    console.log('The access token is ' + data.body['access_token']);
		    spotifyApi.setAccessToken(data.body['access_token']);

			spotifyApi.searchArtists('Love')
			  .then(function(data) {
			    console.log('Search artists by 2 :', data.body);
			    message = data.body;
		
				response.status(200).json({
					message
				});

			  }, function(err) {
			    console.error(err);
			  });


		  }, // token reset done
		  function(err) {
		    console.log('Something went wrong!', err);
		  }// token reset error
		);



	  }); // end of searchartist 1 error

});

module.exports = router;
