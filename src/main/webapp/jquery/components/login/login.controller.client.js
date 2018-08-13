(function () {
	var $usernameFld, $passwordFld;
	var $loginBtn;
	var $forgotPwdBtn;
	var userService = new UserServiceClient();
	$(main);

	function main() {
		$usernameFld = $("#username");
		$passwordFld = $("#inputPassword");
		$loginBtn = $("#loginBtn")
		.click(login);
		$forgotPwdBtn = $("#forgotPwdBtn")
		.click(forgotPassword);
		$resetPwdBtn = $("#resetPwdBtn")
		.click(resetPassword);
		$resetPwd = $("#resetPasswordFld");

		$resetPasswordFld = $("#resetPasswordFld");
		$confirmPasswordFld = $("#confirmPasswordFld");
	}

	$(document).ready(function () {
		$("#confirmPasswordFld").keyup(checkPassword);
	});


	function checkPassword(){
		if($resetPasswordFld.val() != $confirmPasswordFld.val())
		{
			$('#confirmPasswordFld').removeClass('is-valid');
			$('#confirmPasswordFld').addClass('is-invalid');
		}
		else{
			$('#confirmPasswordFld').removeClass('is-invalid');
			$('#confirmPasswordFld').addClass('is-valid');
		}
	}

	function forgotPassword()
	{
		var emailID = $('#resetEmailID').val();
		var userEmail = {
				email : emailID
		};
		userService.forgotPassword(userEmail)
		.then(postforgotPassword);
	}
	
	function getParams(url)
	{
		var preurl  =  document.createElement('a');
		preurl.href = url;
		var str  = preurl.search.substring(1);
		var arr = str.split('&');
		var result = {};
		for (var m = 0; m < arr.length; m++) {
			var part = arr[m].split('=');
			result[part[0]] = decodeURIComponent(part[1]);
		}
		return result;
	}

	function resetPassword()
	{

		if($resetPasswordFld.val() != $confirmPasswordFld.val())
		{
			$.toast({
				heading: 'Reset Error',
				text: 'Both passwords doesnot match. Enter again.',
				position: 'top-right',
				hideAfter: 6000,
				icon: 'error'
			})
		}
		else{
			var password = $('#resetPasswordFld').val();
			var url = window.location.href;
			var token = getParams(url).token;
			var userPwd = {
					password : password,
					token : token
			};
			userService.resetPassword(userPwd)
			.then(resetforgotPassword);
		}

	}

	function postforgotPassword(val)
	{
		if(val == "success")
		{
			$.toast({
				heading: 'Information',
				text: 'A mail has been sent to given mail address. Click the link to reset password.',
				position: 'top-right',
				hideAfter: 6000,
				icon: 'info'
			})

			window.setTimeout(function(){
				window.location.pathname = "/jquery/components/login/login.template.client.html";
			}, 6500);
		}
		else{
			$.toast({
				heading: 'Error',
				text: val,
				position: 'top-right',
				hideAfter: 6000,
				icon: 'error'
			})
		}

	}

	function resetforgotPassword(val)
	{
		if(val[0] === "success")
		{
			$.toast({
				heading: 'Information',
				text: 'Password has been changed successfully. Please log in to access the system.',
				position: 'top-right',
				hideAfter: 6000,
				icon: 'info'
			})
			window.setTimeout(function(){
				window.location.pathname = "/jquery/components/login/login.template.client.html";
			}, 6500);
		}
		else{
			$.toast({
				heading: 'Error',
				text: 'Error in resetting password.Try again.',
				position: 'top-right',
				hideAfter: 6000,
				icon: 'error'
			})
		}

	}


	function login() {
		var user = {
				username : $usernameFld.val(),
				password : $passwordFld.val()
		};
		userService.login(user)
		.then(success);
	}

	function success(str)
	{
		if(str[1] === "true")
		{
			//alert("Logged In");

			$.toast({
				heading: 'Success',
				text: 'Logged In.',
				position: 'top-right',
				hideAfter: 3000,
				icon: 'success'
			})
			window.setTimeout(function(){
				window.location.pathname = "/jquery/components/Profile/profile.template.client.html";
			}, 3500);
		}
		else{
			$.toast({
				heading: 'Error',
				text: 'Invalid User Credentials',
				position: 'top-right',
				hideAfter: 4000,
				icon: 'error'
			})

		}
	}

})();
