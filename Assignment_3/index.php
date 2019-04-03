<?php
	include 'geoHash.php';
	
	$jsonObject="";
	$category = "";
	$startpoint = "";
	$latitude = "";
	$longitude = "";
	$distance = "10";	
	  		
	//If any button is clicked
	if ($_SERVER["REQUEST_METHOD"] == "POST") {
		//if all form fields are set 
		if(isset($_POST["keyword"]) && isset($_POST["category"]) && isset($_POST["distance"]) && isset($_POST["startpoint"])){
			$keyword = urlencode($_POST["keyword"]);
	  		$category = $_POST["category"];
	  		if ($_POST["distance"] != ""){
		  		$distance = $_POST["distance"];
	  		}
     		$startpoint = $_POST["startpoint"];
     		$latitude = $_POST['latitude'];
     		$longitude = $_POST['longitude'];

     		if($startpoint == "location"){     			
     			//Use Google API for mentioned startpoint location
     			$location = $_POST["locationText"];
				$url = 'https://maps.googleapis.com/maps/api/geocode/json?address='. urlencode($location) . '&key=AIzaSyByqc69cN16Nwsa8SviTmGd08bf-VHzsJ8'; // path to your JSON file
				$jsonString = file_get_contents($url); // put the contents of the file into a variable
				$jsonObject = json_decode($jsonString, true); // decode the JSON feed
				$latitude = $jsonObject['results'][0]['geometry']['location']['lat'];
				$longitude = $jsonObject['results'][0]['geometry']['location']['lng'];
	   		}
     		$geohashLength = 5;
     		$geohash = encode($latitude, $longitude, $geohashLength);
     		
     		$segmentid = "";

     		//Ticketmaster API
     		if($category == "Music"){
				$segmentid = "KZFzniwnSyZfZ7v7nJ"; 
     		}elseif ($category == "Sports") {
     			$segmentid = "KZFzniwnSyZfZ7v7nE";
     		}elseif ($category == "ArtsandTheatre") {
     			$segmentid = "KZFzniwnSyZfZ7v7na";
     		}elseif($category == "Film"){
				$segmentid = "KZFzniwnSyZfZ7v7nn";
     		}elseif($category == "Miscellaneous"){
				$segmentid = "KZFzniwnSyZfZ7v7n1";
     		}
		}

		if(!isset($_POST["eventIdname"])){
			$ticketmaster_url =	"https://app.ticketmaster.com/discovery/v2/events.json?apikey=M0hmh9XA1IImpyriVwfIpJDzF6ANmoUn&keyword=".$keyword."&segmentId=".$segmentid."&radius=". $distance ."&unit=miles&geoPoint=".$geohash;

			$jsonString2 = file_get_contents($ticketmaster_url); // put the contents of the file into a variable
			$jsonObject2 = json_decode($jsonString2,true); // decode the JSON feed
			$jsonObject3 = json_encode($jsonObject2);			
		}
	
		if(isset($_POST["eventIdname"])){
			$eventID =  $_POST["eventIdname"];
			$eventdetailURL = 'https://app.ticketmaster.com/discovery/v2/events/'.$eventID.'?apikey=M0hmh9XA1IImpyriVwfIpJDzF6ANmoUn';

			$jsonStringED = file_get_contents($eventdetailURL); // put the contents of the file into a variable
			$jsonObjectED = json_decode($jsonStringED,true); // decode the JSON feed
			$jsonObjectED2 = json_encode($jsonObjectED);			

			$venueurl = "https://app.ticketmaster.com/discovery/v2/venues?apikey=M0hmh9XA1IImpyriVwfIpJDzF6ANmoUn&keyword=". urlencode($jsonObjectED["_embedded"]["venues"][0]["name"]);

			$jsonStringVD = file_get_contents($eventdetailURL); // put the contents of the file into a variable
			$jsonObjectVD = json_decode($jsonStringVD,true); // decode the JSON feed
			$jsonObjectVD2 = json_encode($jsonObjectVD);			
		}
	}	
?>

<!DOCTYPE html>
<html>
<head>
	<title>Events</title>
	<head>
	<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyByqc69cN16Nwsa8SviTmGd08bf-VHzsJ8&callback=initMap">
    </script>
	
	<script type="text/javascript">
		destlat=0;
		destlon=0;
		function enabletext(){
				document.getElementById("locationText").disabled = false;
		}		
		function disabletext(){
			document.getElementById("locationText").disabled = true;
		}
		function getuserlocation(url) {
			var jsonDoc;
			var xmlhttp = new XMLHttpRequest();
			xmlhttp.open("GET",url,false); //open, send, responseText are
			xmlhttp.send(); //properties of XMLHTTPRequest
			jsonDoc=xmlhttp.responseText;
			apijsonObject = JSON.parse(jsonDoc);
			document.getElementById("searchbutton").disabled = false;
			
			document.getElementById("latId").value = apijsonObject.lat;
			document.getElementById("lonId").value = apijsonObject.lon;

			document.getElementById('latId2').value = apijsonObject.lat;
			document.getElementById('lonId2').value = apijsonObject.lon;
			
			<?php if(isset($_POST['startpoint'])){ ?>
				<?php	if($_POST['startpoint'] == "location"){ ?>	
					<?php 
						$url = 'https://maps.googleapis.com/maps/api/geocode/json?address='. urlencode($_POST["locationText"]) . '&key=AIzaSyByqc69cN16Nwsa8SviTmGd08bf-VHzsJ8'; // path to your JSON file
						$jsonString = file_get_contents($url); // put the contents of the file into a variable
						$jsonObject = json_decode($jsonString, true); // decode the JSON feed
						$latitude = $jsonObject['results'][0]['geometry']['location']['lat'];
						$longitude = $jsonObject['results'][0]['geometry']['location']['lng'];
					?>
					document.getElementById('latId').value = "<?php echo $latitude ?>";
					document.getElementById('lonId').value = "<?php echo $longitude ?>";
			
					document.getElementById('latId2').value = "<?php echo $latitude ?>";
					document.getElementById('lonId2').value = "<?php echo $longitude ?>";
				<?php } ?>
			<?php } ?>

			<?php if(isset($_POST['startpoint2'])){ ?>
				<?php	if($_POST['startpoint2'] == "location"){ ?>	
					<?php 
						$url = 'https://maps.googleapis.com/maps/api/geocode/json?address='. urlencode($_POST["locationText"]) . '&key=AIzaSyByqc69cN16Nwsa8SviTmGd08bf-VHzsJ8'; // path to your JSON file
						$jsonString = file_get_contents($url); // put the contents of the file into a variable
						$jsonObject = json_decode($jsonString, true); // decode the JSON feed
						$latitude = $jsonObject['results'][0]['geometry']['location']['lat'];
						$longitude = $jsonObject['results'][0]['geometry']['location']['lng'];
					?>
					document.getElementById('latId').value = "<?php echo $latitude ?>";
					document.getElementById('lonId').value = "<?php echo $longitude ?>";
			
					document.getElementById('latId2').value = "<?php echo $latitude ?>";
					document.getElementById('lonId2').value = "<?php echo $longitude ?>";
				<?php } ?>
			<?php } ?>

			return false;
		}		
		function resetbutton(){
			//First Form
			document.forms['eventform']['keyword'].value = "";
			document.forms['eventform']['distance'].value = "";	
			document.forms['eventform']['category'].value = "Default";	
			document.getElementsByName("startpoint")[1].checked = false;			
			document.getElementsByName("startpoint")[0].checked = true;			
			document.forms['eventform']['locationText'].value = "";
			document.getElementById("locationText").disabled = true;

			//Hidden Form
			document.forms['hiddeneventform']['keyword'].value = "";
			document.forms['hiddeneventform']['distance'].value = "";	
			document.forms['hiddeneventform']['category'].value = "Default";	
			document.getElementsByName("startpoint2")[1].checked = false;			
			document.getElementsByName("startpoint2")[0].checked = true;
			document.forms['hiddeneventform']['locationText2'].value = "";
			document.getElementById("locationText2").disabled = true;
			
			var jsonDoc;
			var xmlhttp = new XMLHttpRequest();
			xmlhttp.open("GET","http://ip-api.com/json",false); //open, send, responseText are
			xmlhttp.send(); //properties of XMLHTTPRequest
			jsonDoc=xmlhttp.responseText;
			apijsonObject = JSON.parse(jsonDoc);
			document.getElementById("latId").value = apijsonObject.lat;
			document.getElementById("lonId").value = apijsonObject.lon;

			document.getElementById('latId2').value = apijsonObject.lat;
			document.getElementById('lonId2').value = apijsonObject.lon;

			//All data and lists
			<?php if(isset($_POST['keyword']) && !isset($_POST['eventIdname'])){ ?>
				var parent = document.getElementById("parent_eventlist");
				var child = document.getElementById("child_eventlist");
				parent.removeChild(child);
			<?php }?>

			<?php if(isset($_POST['eventIdname'])){ ?>
				var parent = document.getElementById("eventnvenue");
				var child1 = document.getElementById("eventlist");
				var child2 = document.getElementById("venueAll");
				parent.removeChild(child1);
				parent.removeChild(child2);
			<?php }?>
				
		}
		function initMap() {
			var destLatLng = { lat: parseFloat(destlat), lng: parseFloat(destlon) };
			var map = new google.maps.Map(document.getElementById('map'), {
	          zoom: 12,
	          center: destLatLng
	        });

	        var marker = new google.maps.Marker({
	          position: destLatLng,
	          map: map,
	          title: 'Destination point'
	        });		   
		}

     	function calculateAndDisplayRoute(selectedMode, destlat, destlon) {
			var directionsDisplay = new google.maps.DirectionsRenderer;
	        var directionsService = new google.maps.DirectionsService;

	       	mylat = document.getElementById('latId').value;
	       	mylon = document.getElementById('lonId').value;
       	   	var myLatLng = { lat: parseFloat(mylat), lng: parseFloat(mylon) };
			
	        var destLatLng = { lat: parseFloat(destlat), lng: parseFloat(destlon) };
	        var map = new google.maps.Map(document.getElementById('map'), {
	          zoom: 14,
	          center: destLatLng
	        });
	        directionsDisplay.setMap(map);

		        directionsService.route({
		          origin: myLatLng,  
		          destination: destLatLng,  
		          // Note that Javascript allows us to access the constant
		          // using square brackets and a string value as its
		          // "property."
		          travelMode: google.maps.TravelMode[selectedMode]
		        }, function(response, status) {
		          if (status == 'OK') {
		            directionsDisplay.setDirections(response);
		          } else {
		            window.alert('Directions request failed due to ' + status);
		          }
	        }); 			
 		}
	
		function img_text_toggle(imgid, titleid, dataid) {
			imgtoggle(imgid);
			titletoggle(titleid);
			datatoggle(dataid);
			initMap();
		}
	    function imgtoggle(imgid){
		    var img1 = "http://csci571.com/hw/hw6/images/arrow_down.png",
	        img2 = "http://csci571.com/hw/hw6/images/arrow_up.png";
		    var imgElement = document.getElementById(imgid);
		    imgElement.src = (imgElement.src == img1)? img2 : img1;
	    }
		function titletoggle(titleid){
			var y = document.getElementById(titleid);
    		if(y.innerHTML == "click to show venue info"){
    			y.innerHTML = "click to hide venue info";
    		}else{
    			y.innerHTML = "click to show venue info";
    		}
	    }
	    function datatoggle(dataid){
		    var x = document.getElementById(dataid);
		    if (x.style.display === "none") {
		        x.style.display = "block";
		    }else {
		        x.style.display = "none";
		    }
	    }

	    function maptoggle(i){
	    	var x = document.getElementById(mapndir[i]);
	    	if (x.style.display == "none") {
		        for (var j = 0; j < mapndir.length; j++) {

					if(document.getElementById(mapndir[j]).style.display == "block"){
						document.getElementById(mapndir[j]).style.display = "none";
					} 
		    	}
		        x.style.display = "block";        
				myinitMap(mapsid[i],destlat2[i],destlon2[i]);	    	
		    }else {
		        x.style.display = "none";
		    }
	    }
		function myinitMap(mapid,dlat,dlon){
			var destLatLng = { lat: parseFloat(dlat), lng: parseFloat(dlon) };
			var map = new google.maps.Map(document.getElementById(mapid), {
	          zoom: 12,
	          center: destLatLng
	        });

	        var marker = new google.maps.Marker({
	          position: destLatLng,
	          map: map,
	          title: 'Destination point'
	        });
		}

		function mycalculateAndDisplayRoute(selectedMode, destlat, destlon,i) {
			var directionsDisplay = new google.maps.DirectionsRenderer;
	        var directionsService = new google.maps.DirectionsService;

	       	mylat = document.getElementById('latId').value;
	       	mylon = document.getElementById('lonId').value;
       	   	var myLatLng = { lat: parseFloat(mylat), lng: parseFloat(mylon) };
			
	        var destLatLng = { lat: parseFloat(destlat), lng: parseFloat(destlon) };
	        var map = new google.maps.Map(document.getElementById(mapsid[i]), {
	          zoom: 14,
	          center: destLatLng
	        });
	        directionsDisplay.setMap(map);

		        directionsService.route({
		          origin: myLatLng,  
		          destination: destLatLng,  
		          // Note that Javascript allows us to access the constant
		          // using square brackets and a string value as its
		          // "property."
		          travelMode: google.maps.TravelMode[selectedMode]
		        }, function(response, status) {
		          if (status == 'OK') {
		            directionsDisplay.setDirections(response);
		          } else {
		            window.alert('Directions request failed due to ' + status);
		          }
	        }); 			
 		}
	</script>

	<style type="text/css">
		div.formdivbox{
			border: 2px;
			border-style: solid;
			background-color: #e6e6e6;
			border-color: #999999;
			margin: auto;
			width: 40%;
			padding: 20px;
		}
		div.title {
			text-align: center;
			font-size: 30px;
		}
		hr{
		  	display: block;
		    height: 2px;
		    border: 0;
		    border-top: 2px solid #999999;
		    margin: 1em 0;
		    padding: 0;
		}
		.link{
			text-decoration: none;
		}
		.link:hover{
			color: #bfbfbf;
		}
		.link{
			color: black;
		}
		#titleid1,#titleid2{
			padding-top: 10px;
			text-align: center;
		}
		#imgid1,#imgid2{
			display: block;
			margin: auto;
			padding-top: 10px;
			padding-bottom:10px; 
		}
		#td1{
			font-weight: bold;
			text-align: right;
		}
		#td2{
			text-align: center;
			padding: 3px;
		}

		.norecordid{
			width: 60%;
			margin: auto;
			text-align: center;
			border: 2px;
			border-style: solid;
			background-color: #e6e6e6;
			border-color: #999999;			
		}
		.mode{
			background-color: #e6e6e6;
			padding-right: 10px;
		}
		.venuename{
			position: relative;
			z-index: 0;
			color: black;
		}
		.venuename:hover{
			color: #bfbfbf;
		}
		#tableheading{
			font-weight: bold;
		}
		#bfont{
			font-size: 19px;
			font-weight: bold;
			padding-top: 10px;
		}
	</style>
</head>

<body onload="getuserlocation('http://ip-api.com/json')">
	<div class="formdivbox">
		<div class="title"><i>Events Search</i></div>
		<hr>
		<form name='eventform' method="POST" action="index.php">
			<label><b>Keyword:</b></label> 
			<input type="text" name="keyword" required="true" value="<?php echo isset($_POST['keyword']) ? $_POST['keyword'] : '' ?>" id="keywordId" /><br>

			<label><b>Category:</b></label>
			<select name="category" id="category">
			    <option value="Default">Default</option>
			    <option value="Music">Music</option>
			    <option value="Sports">Sports</option>
			    <option value="ArtsandTheatre">Arts & Theatre</option>
			    <option value="Film">Film</option>
			    <option value="Miscellaneous">Miscellaneous</option>    
			</select><br>

			<label><b>Distance (miles):</b></label>
			<input type="text" name="distance" placeholder="10" value="<?php echo isset($_POST['distance']) ? $_POST['distance'] : '' ?>" id="distance" />
			
			<label><b>from:</b></label>	
			    <input type="radio" name="startpoint" value="here" checked="checked" onclick="disabletext()" id="hereId" />Here<br>
			    <input style="margin-left: 300px;" type="radio" name="startpoint" value="location" onclick="enabletext()" id="locationId" />
			    <input type="text" name="locationText" id="locationText" placeholder="Location" disabled="true" value="<?php echo isset($_POST['locationText']) ? $_POST['locationText'] : '' ?>" required /><br> 

			 <input type="text" name="latitude" id="latId" style="display:none;" />
			 <input type="text" name="longitude" id="lonId" style="display:none;" />

			<input type="submit" value="Search" disabled="true" id="searchbutton" />
			<button value="Reset" onclick="resetbutton()">Clear</button>

		</form>	
	</div>

	<!-- Hidden Form --> 
	<form name='hiddeneventform' id="hiddenformId" method="POST" action="index.php" style="display:none;">
		<label><b>Keyword:</b></label> 
		<input type="text" name="keyword" required="true" value="<?php echo isset($_POST['keyword']) ? $_POST['keyword'] : '' ?>" id="keywordId" /><br>

		<label><b>Category:</b></label>
		<select name="category" id="category2">
		    <option value="Default">Default</option>
		    <option value="Music">Music</option>
		    <option value="Sports">Sports</option>
		    <option value="ArtsandTheatre">Arts & Theatre</option>
		    <option value="Film">Film</option>
		    <option value="Miscellaneous">Miscellaneous</option>    
		</select><br>

		<label><b>Distance (miles):</b></label>
		<input type="text" name="distance" placeholder="10" value="<?php echo isset($_POST['distance']) ? $_POST['distance'] : '' ?>" id="distance" />
		
		<label><b>from:</b></label>	
		    <input type="radio" name="startpoint2" value="here" checked="checked" onclick="disabletext()" id="hereId2" />Here<br>
		    <input style="margin-left: 300px;" type="radio" name="startpoint2" value="location" onclick="enabletext()" id="locationId2" />
		    <input type="text" name="locationText" id="locationText2" placeholder="Location" disabled="true" value="<?php echo isset($_POST['locationText']) ? $_POST['locationText'] : '' ?>" required /><br> 

		 <input type="text" name="latitude" id="latId2" />
		 <input type="text" name="longitude" id="lonId2" />

		<input type="text" name="eventIdname" id="eventId" />
		<input type="submit" value="Search" disabled="true" id="searchbutton" />
		<button value="Reset" onclick="resetbutton()">Reset</button>
	</form>	
	<div id ="eventnvenue">
		<p id="eventlist"></p>			
		<div id="venueAll" style="display:none;">
		
		<!-- Venue Info -->
			<div style="width:100%; float:left;">
				<div id="titleid1" style="color: #bfbfbf; ">click to show venue info</div>
				<img src="http://csci571.com/hw/hw6/images/arrow_down.png" id="imgid1" style="width: 30px; height:20px;" 
				onclick = "img_text_toggle('imgid1','titleid1','dataid1')"/>
				<div id="dataid1" style="display: none"></div>
			</div>

			<!-- Venue Images -->
			<div style="width:100%;float:left;margin:auto;">
				<div id="titleid2" style="color: #bfbfbf; ">click to show venue info</div>
				<img src="http://csci571.com/hw/hw6/images/arrow_down.png" id="imgid2" style="width: 30px; height:20px;" onclick = "img_text_toggle('imgid2','titleid2','dataid2')"/>
				<div id="dataid2" style="display: none"></div>
			</div>
		</div>
	</div>

</body>

<script type="text/javascript">
		//Retaining Select drop down, radio button and location text enabled
		<?php if( isset($_POST['category'])){ ?>	
			document.getElementById("category").value = "<?php echo $_POST['category'] ?>";
			document.getElementById("category2").value = "<?php echo $_POST['category'] ?>";
		<?php } ?>

		<?php if(isset($_POST['startpoint'])){ ?>
			<?php	if($_POST['startpoint'] == "location"){ ?>	
				document.getElementById('locationId').checked = true;
				document.getElementById('hereId').checked = false;
				document.getElementById("locationText").disabled = false;		 
	
				document.getElementById('locationId2').checked = true;
				document.getElementById('hereId2').checked = false;
				document.getElementById("locationText2").disabled = false;	
			<?php } ?>
		<?php } ?>

		<?php if(isset($_POST['startpoint2'])){ ?>
			<?php	if($_POST['startpoint2'] == "location"){ ?>	
				document.getElementById('locationId').checked = true;
				document.getElementById('hereId').checked = false;
				document.getElementById("locationText").disabled = false;		 
	
				document.getElementById('locationId2').checked = true;
				document.getElementById('hereId2').checked = false;
				document.getElementById("locationText2").disabled = false;	
			<?php } ?>
		<?php } ?>

		function showeventdetails(eventid){		
			document.getElementById('eventId').value = eventid;
			document.getElementById("hiddenformId").submit();
		}

		//Creating List of Events Table	
		<?php if( isset($_POST['keyword']) &&  !isset($_POST['eventIdname'])){ ?>
		var jsonObject = <?php echo $jsonObject3 ?>;
		var htmlDoc = "<div id='parent_eventlist' style='width:100%'>";
		try{
			var id=10;
			if(jsonObject._embedded.events){
				htmlDoc += "<table id='child_eventlist' border=1 style='border-collapse: collapse; width:80%; margin:auto; text-align:center;'><col width='10%'><col width='10%'><col width='40%'><col width='10%'><col width='30%'><b><tr id='tableheading'><td>Date</td><td>Icon</td><td>Event</td><td>Genre</td><td>Venue</td></tr></b>";
				destlat2 = [];
				destlon2 = [];
				mapsid = [];
				mapndir = [];
			 	for (var i = 0; i< jsonObject._embedded.events.length ; i++) {
			 		htmlDoc += "<tr><td>";
					if(jsonObject._embedded.events[i].dates.start.localDate){
						htmlDoc += jsonObject._embedded.events[i].dates.start.localDate + "";
					}	
					if(jsonObject._embedded.events[i].dates.start.localTime){
						htmlDoc += "<br>" + jsonObject._embedded.events[i].dates.start.localTime + "";			
					} 		
					htmlDoc += "</td><td>";
					if(jsonObject._embedded.events[i].images){
						htmlDoc += "<img src='" + jsonObject._embedded.events[i].images[0].url + "' style='width:60px; height: 60px'>";
					}
					htmlDoc += "</td><td>";
					if(jsonObject._embedded.events[i].name){
						id = jsonObject._embedded.events[i].id;

						htmlDoc += '<div class="link" onclick="showeventdetails(\'' +jsonObject._embedded.events[i].id + '\')" >'+jsonObject._embedded.events[i].name+'</div>';
					}
					htmlDoc += "</td><td>";					
					if(jsonObject._embedded.events[i].classifications[0].segment.name){
						htmlDoc += jsonObject._embedded.events[i].classifications[0].segment.name + "";
					}
					htmlDoc += "</td><td>";
					if(jsonObject._embedded.events[i]._embedded.venues[0].location){
						destlat2[i] = jsonObject._embedded.events[i]._embedded.venues[0].location.latitude;
						destlon2[i] = jsonObject._embedded.events[i]._embedded.venues[0].location.longitude;
						mapsid[i] = "map"+i;
						mapndir[i] = "mapndir"+i;
						htmlDoc += '<div class="venuename" onclick="maptoggle('+i+')">' + jsonObject._embedded.events[i]._embedded.venues[0].name;

						htmlDoc +=  '</div> <div style="margin-top: 10px;position: absolute;z-index: 20; display: none;border-style:solid" id="'+mapndir[i]+'">   <div class="mode" style="position: absolute;float:left;z-index:50;width:110px;">	<div class="link" onclick="mycalculateAndDisplayRoute(\'WALKING\','+jsonObject._embedded.events[i]._embedded.venues[0].location.latitude +','+ jsonObject._embedded.events[i]._embedded.venues[0].location.longitude +','+i+')">Walk There</div>	<div class="link"  onclick="mycalculateAndDisplayRoute(\'DRIVING\','+jsonObject._embedded.events[i]._embedded.venues[0].location.latitude +','+ jsonObject._embedded.events[i]._embedded.venues[0].location.longitude +','+i+')">Drive There</div>    	    <div class="link" onclick="mycalculateAndDisplayRoute(\'BICYCLING\','+jsonObject._embedded.events[i]._embedded.venues[0].location.latitude +','+ jsonObject._embedded.events[i]._embedded.venues[0].location.longitude +','+i+')">Bike There</div>  	</div> <div style="width:350px;height:350px;background-color:#000;position: absolute; z-index:30" id="'+ mapsid[i] +'">Hi there</div> </div>';
					}
					htmlDoc += "</td></tr>";
			 	} // end of for loop of making the table
			 	htmlDoc += "</table>";
			} // end of if event exists
			else{
				htmlDoc += "<div class='norecordid' id='child_eventlist'>No Records has been found</div>";

			}
		}catch(e){
			htmlDoc = "<div id='parent_eventlist' style='width:100%'><div class='norecordid' id='child_eventlist'>No Records has been found</div>";
		}
		htmlDoc += "</div>" //end of parent_eventlist
	 	document.getElementById("eventlist").innerHTML = htmlDoc;
	
	<?php } ?>

	onlyinfo = 0;
	//Get Event Detail and Venue Detail
	<?php if( isset($_POST['eventIdname'])){ ?>
		var eventJsonObject = <?php echo $jsonObjectED2 ?>;

		//Outer div of 100 width
		var eventdetail = "<div style='width:100%'>";
		
		//Main div having both left and right div
		eventdetail += "<div class='maindiv' style='width:100%; margin:auto;'><h4 style='margin:auto; width:100%;text-align:center'>"+ eventJsonObject.name +"</h4><br>";
		
		//Left div with all text details
		eventdetail += "<div class='leftdiv' id='leftdiv' style='float:left;margin-left:300px;'>"
		
		//Date and Time
		if(eventJsonObject.dates.start.localDate){
			eventdetail += "<div id='bfont'>Date</div>"+eventJsonObject.dates.start.localDate + " ";
		}
		if(eventJsonObject.dates.start.localTime){
			eventdetail += eventJsonObject.dates.start.localTime + "";	
		}
		//Artist or Team
		if(eventJsonObject._embedded.attractions){
			eventdetail += "<div id='bfont'>Artist/Team</div>";
			for (var i = 0; i<eventJsonObject._embedded.attractions.length; i++) {
				eventdetail += "<a href='" +eventJsonObject._embedded.attractions[i].url + "' class='link' target='_blank' class='link'>" + eventJsonObject._embedded.attractions[i].name +"</a>";
				if(i<eventJsonObject._embedded.attractions.length-1){
					eventdetail += " | "; 
				}
			}
		}
		//if venue present
		if(eventJsonObject._embedded.venues[0]){
			eventdetail += "<div id='bfont'>Venue</div>" + eventJsonObject._embedded.venues[0].name + "";
		}
		// Genre type
		if(eventJsonObject.classifications){
			if(eventJsonObject.classifications[0].subGenre || eventJsonObject.classifications[0].genre || eventJsonObject.classifications[0].segment || eventJsonObject.classifications[0].subType || eventJsonObject.classifications[0].type){
			
				eventdetail += "<div id='bfont'>Genre</div>";
				var sgenre=false, genre=false, segment = false, stype=false;
				if(eventJsonObject.classifications[0].subGenre){
					if(eventJsonObject.classifications[0].subGenre.name != "Undefined"){
						eventdetail += eventJsonObject.classifications[0].subGenre.name + "";
						sgenre=true;
					}
				}
				if(eventJsonObject.classifications[0].genre){
					if(eventJsonObject.classifications[0].genre.name != "Undefined"){
						if(sgenre == true){
							eventdetail += " | ";
						}
					eventdetail += eventJsonObject.classifications[0].genre.name + "";
					genre = true;
					}
				}
				if(eventJsonObject.classifications[0].segment){
					if(eventJsonObject.classifications[0].segment.name != "Undefined"){	
						if(sgenre == true || genre == true){
							eventdetail += " | ";
						}
						eventdetail += eventJsonObject.classifications[0].segment.name + "";
						segment = true;
					}
				}
				if(eventJsonObject.classifications[0].subType){
					if(eventJsonObject.classifications[0].subType.name != "Undefined"){
						if(sgenre == true || genre == true || segment == true){
							eventdetail += " | ";			
						}
						eventdetail += eventJsonObject.classifications[0].subType.name + "";
						stype= true;
					}
				}
				if(eventJsonObject.classifications[0].type){
					if(eventJsonObject.classifications[0].type.name != "Undefined"){
						if(sgenre == true || genre == true || segment==true || stype == true){
							eventdetail += " | ";			
						}					
						eventdetail += eventJsonObject.classifications[0].type.name + "";
					}
				}
			}			
		}
		//Price ranges
		if(eventJsonObject.priceRanges){
			if(eventJsonObject.priceRanges[0].max && eventJsonObject.priceRanges[0].min && eventJsonObject.priceRanges[0].min != eventJsonObject.priceRanges[0].max){
				eventdetail += "<div id='bfont'>Price Ranges</div>" + eventJsonObject.priceRanges[0].min + "-" +eventJsonObject.priceRanges[0].max + " USD";				
			}else if(eventJsonObject.priceRanges[0].max){
				eventdetail += "<div id='bfont'>Price Ranges</div>" + eventJsonObject.priceRanges[0].max + " USD";
			}else if(eventJsonObject.priceRanges[0].min){
				eventdetail += "<div id='bfont'>Price Ranges</div>" + eventJsonObject.priceRanges[0].min + " USD";
			}
		}
		//ticket status
		if(eventJsonObject.dates.status){
			var statusstring = eventJsonObject.dates.status.code;
			eventdetail += "<div id='bfont'>Ticket Status</div>" + statusstring.charAt(0).toUpperCase() + statusstring.slice(1);	
		}
		//Buy ticket 
		eventdetail += "<div id='bfont'>Buy Ticket At</div>";
		if(eventJsonObject.url){
			eventdetail += "<a href='"+ eventJsonObject.url +"' class='link' target='_blank' style='text-decoration:none;'>Ticketmaster</a>";
		}
		eventdetail += "</div>"; //end of left div
		//If Image present
		if(eventJsonObject.seatmap){
			onlyinfo = 0;
			eventdetail += "<div class='rightdiv' id='rightdiv' style='float:left;'><img src='"+ eventJsonObject.seatmap.staticUrl +"' style='width=300px;height:300px;'></div>";
 		}
		else{
			//align other event details to center
			onlyinfo = 1;	
		}

		eventdetail += "</div></div>"; //end of main div and outer div
 		document.getElementById("eventlist").innerHTML = eventdetail;

 		if(onlyinfo==1){
			document.getElementById("leftdiv").style.float = "none";
			document.getElementById("leftdiv").style.width = "500px";
			document.getElementById("leftdiv").style.margin = "auto";

 		}
 		//Venue Details
		var venueJsonObject = <?php echo $jsonObjectVD2 ?>;

		//If venue has any info records
		document.getElementById("venueAll").style.display = "block";
		if(venueJsonObject._embedded.venues[0].name){
			venueinfo = "<table border=1 style='border-collapse:collapse;margin:auto'><col width='200px;'><col width='500px'><tr><td id='td1'>Name</td>";
			
			if(venueJsonObject._embedded.venues[0].name){
				venueinfo += "<td id='td2'>"+ venueJsonObject._embedded.venues[0].name +"</td></tr>";
			}else{
				venueinfo += "<td id='td2'> N/A </td></tr>";
			}
			
			if(venueJsonObject._embedded.venues[0].location){
				destlat = venueJsonObject._embedded.venues[0].location.latitude;
				destlon = venueJsonObject._embedded.venues[0].location.longitude;
				venueinfo += "<tr><td id='td1'>Map</td>";	
			 	venueinfo += '<td style="height:300px; padding:10px;"> <div class="mode" style="float:left;">  		    <div class="link" onclick="calculateAndDisplayRoute(\'WALKING\','+venueJsonObject._embedded.venues[0].location.latitude +','+ venueJsonObject._embedded.venues[0].location.longitude +')">Walk There</div>    	 <div class="link"  onclick="calculateAndDisplayRoute(\'DRIVING\','+venueJsonObject._embedded.venues[0].location.latitude +','+ venueJsonObject._embedded.venues[0].location.longitude +')">Drive There</div>		    <div class="link" onclick="calculateAndDisplayRoute(\'BICYCLING\','+venueJsonObject._embedded.venues[0].location.latitude +','+ venueJsonObject._embedded.venues[0].location.longitude +')">Bike There</div>			</div> <div style="float:left; width:350px;height:350px" id="map">Venue map comes here</div> </td></tr>';
				}else{
				venueinfo += "<td id='td2'> N/A </td></tr>";
			}

			venueinfo += "<tr><td id='td1'>Address</td>";			
			if(venueJsonObject._embedded.venues[0].address.line1){
				venueinfo += "<td id='td2'>" + venueJsonObject._embedded.venues[0].address.line1 + "</td></tr>";
			}else{
				venueinfo += "<td id='td2'> N/A </td></tr>";
			}
			
			venueinfo += "<tr><td id='td1'>city</td>";
			if(venueJsonObject._embedded.venues[0].city.name){
				venueinfo += "<td id='td2'>" + venueJsonObject._embedded.venues[0].city.name + "</td></tr>";
			}else{
				venueinfo += "<td id='td2'> N/A </td></tr>";
			}
			
			venueinfo += "<tr><td id='td1'>Postal Code</td>";
			if(venueJsonObject._embedded.venues[0].postalCode){
				venueinfo += "<td id='td2'>" + venueJsonObject._embedded.venues[0].postalCode + "</td></tr>";
			}else{
				venueinfo += "<td id='td2'> N/A </td></tr>";
			}

			venueinfo += "<tr><td id='td1'>Upcoming Events</td>";
			if(venueJsonObject._embedded.venues[0].url){
				venueinfo += "<td id='td2'><a href='" + venueJsonObject._embedded.venues[0].url + "' class='link' target='_blank'>"+ venueJsonObject._embedded.venues[0].name +" Tickets</a></td></tr>";
			}else{
				venueinfo += "<td id='td2'> N/A </td></tr>";
			}
			venueinfo += "</table>";			
		}else{
			venueinfo = "<div class='norecordid'>No Venue Info Found</div>";
		}
		document.getElementById('dataid1').innerHTML = venueinfo;
	
		if(venueJsonObject._embedded.venues[0].images){
			venueimages = "<table style='margin:auto;padding-left:100px;padding-right:100px;border-style:solid'>" ;
			for (var i = 0; i < venueJsonObject._embedded.venues[0].images.length; i++) {
				venueimages += "<tr><td><img src='" + venueJsonObject._embedded.venues[0].images[i].url + "' style='width:700px;height:400px'></td></tr>";
			}
			venueimages += "</table>";
		}else{
			venueimages = "<div class='norecordid'>No Venue Photos Found</div><br>";
		}
		document.getElementById('dataid2').innerHTML = venueimages;
	<?php } ?>
</script>
</html>
