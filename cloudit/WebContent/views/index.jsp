<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="google-signin-client_id" content="619431868263-si1ppssnmd1ev4gq7hm1444gqotlq74f.apps.googleusercontent.com">
<title>Home</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://apis.google.com/js/platform.js" async defer></script>
</head>
<body>
	<div class="g-signin2" data-onsuccess="onSignIn"></div>
	
	<script>
		//google callback. The function will redirect in to our login servlet
		function onSignIn(googleUser)
		{
			var profile = googleUser.getBasicProfile();
			
			var id = profile.getId();
			var name = profile.getName();
			var imageUrl = profile.getImageUrl();
			var email = profile.getEmail();
			var idTokenVal = googleUser.getAuthResponse().id_token;
			
			var redirectUrl = 'login';
			
			//using jQuery to post data to the server dynamically
			var form = $('<form action="' + redirectUrl + '" method="post">' +
					'<input type="text" name="id" value="' +id +'" />' +
					'<input type="text" name="name" value="' +name +'" />' +
					'<input type="text" name="imageUrl" value="' +imageUrl +'" />' +
					'<input type="text" name="email" value="' +email +'" />' +
					'<input type="text" name="id_token" value="' +idTokenVal +'" />' + 
					'</form>');
			
			$('body').append(form);
			form.submit();
		}
	</script>
</body>
</html>