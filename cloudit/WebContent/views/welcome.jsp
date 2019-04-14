<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="google-signin-client_id" content="619431868263-si1ppssnmd1ev4gq7hm1444gqotlq74f.apps.googleusercontent.com">
<title>Welcome Member</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="js/instascan.min.js"></script>
<script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
<link rel="stylesheet" href="css/styles.css"/>
</head>
<body>
	Welcome <label id="userName">${userName}</label> with ID <label id="userId">${id}</label> and Email <label id="userEmail">${email}</label>: Details obtained from the database <button onclick="createQR();">Create QR</button>
	<br>
	Address : <input type="text" id="userAddress" size="70">
	<br>
	<br>
	<button onclick="viewProfile();">View Profile</button>
	<br>
	<br>
	<img alt="QR Code" src="viewqr">
	<br>
	<br>
	${message}
	<br>
	<br>
	<br>
	<button id="start">Camera ON</button>&nbsp;<button id="scan" disabled="disabled">Scan QR</button>&nbsp;<button id="stop" disabled="disabled">Camera OFF</button>
	<br>
	<br>
	<div id="webcamera">
		<video autoplay="false" id="videoElement">
		
		</video>
	</div>
	<br>
	<br>
	<button onclick="signOut();">Sign Out</button>
	
	<script>
	
		function signOut()
		{
			var auth2 = gapi.auth2.getAuthInstance();
		    auth2.signOut().then(function () {
		      console.log('User signed out.');
		      window.location.href="http://localhost:8080/cloudit/signout";
		    });
		}
		
		function onLoad() 
		{
		      gapi.load('auth2', function() {
		        gapi.auth2.init();
		      });
		}
		
		function createQR()
		{
			var redirectUrl = "createqr";
			
			var idval = document.getElementById("userId").innerHTML;
			var nameval = document.getElementById("userName").innerHTML;
			var emailval = document.getElementById("userEmail").innerHTML;
			var addval = document.getElementById("userAddress").value;
			
			//using jQuery to post data to the server dynamically
			var form = $('<form action="' + redirectUrl + '" method="post">' +
					'<input type="text" name="userid" value="' +idval +'" />' + 
					'<input type="text" name="username" value="' +nameval +'" />' +
					'<input type="text" name="useremail" value="' +emailval +'" />' +
					'<input type="text" name="useradd" value="' +addval +'" />' +
					'</form>');
			
			$('body').append(form);
			form.submit();
		}
		
		function viewProfile()
		{
			var redirectProfile = "profile";
			
			//using jQuery to post data to the server dynamically
			var form = $('<form action="' + redirectProfile + '" method="post">' +
			'</form>');
			
			$('body').append(form);
			form.submit();
		}
		
		var video = document.querySelector("#videoElement");
		var openVideo = document.querySelector("#start");
		var closeVideo = document.querySelector("#stop");
		var scanQrCode = document.querySelector("#scan");

		openVideo.addEventListener("click", openWebcam, false);

		function openWebcam(e){
			if (navigator.mediaDevices.getUserMedia) {
			  navigator.mediaDevices.getUserMedia({ video: true })
			    .then(function (stream) {
			      video.srcObject = stream;
			    })['catch'](function (err0r) {
			      console.log("Something went wrong!");
			    });
			}
			
			scanQrCode.disabled = false;
			closeVideo.disabled = false;
			openVideo.disabled = true;
		}
		
		closeVideo.addEventListener("click", closeWebcam, false);

		function closeWebcam(e) {
		  var stream = video.srcObject;
		  var tracks = stream.getTracks();

		  for (var i = 0; i < tracks.length; i++) {
		    var track = tracks[i];
		    track.stop();
		  }

		  video.srcObject = null;
		  scanQrCode.disabled = true;
		  openVideo.disabled = false;
		  closeVideo.disabled = true;
		}

		scanQrCode.addEventListener("click", scanQR, false);
		
		function scanQR()
		{
			let scanner = new Instascan.Scanner({ video: document.getElementById('videoElement'), mirror:false });
		      scanner.addListener('scan', function (content) {
		        decodeQR(content);
		      });
		      Instascan.Camera.getCameras().then(function (cameras) {
		        if (cameras.length > 0) {
		          scanner.start(cameras[0]);
		        } else {
		          console.error('No cameras found.');
		        }
		      })['catch'](function (e) {
		        console.error(e);
		      });
		}
		
		function decodeQR(content)
		{
			var redirectUrl = "scanqr";
			var idvalue = document.getElementById("userId").innerHTML;
			
			//using jQuery to post data to the server dynamically
			var form = $('<form action="' + redirectUrl + '" method="post">' +
					'<input type="text" name="userid" value="' +idvalue +'" />' + 
					'<input type="text" name="decodeQr" value="' +content +'" />' + 
			'</form>');
			
			$('body').append(form);
			form.submit();
		}
	</script>
</body>
</html>