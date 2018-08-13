(function () {
	var $usernameFld, $passwordFld, $verifyPasswordFld;
	var $registerBtn;
	var userService = new UserServiceClient();
	$(main);

	function main() {
		$usernameFld = $("#username");
		$passwordFld = $("#inputPassword");
		$verifyPasswordFld = $("#inputverifyPassword");
		$registerBtn = $("#registartionBtn")
		.click(register);
	}

	$(document).ready(function () {
		$("#inputverifyPassword").keyup(checkPassword);
	});


	function register() {
		if($passwordFld.val() != $verifyPasswordFld.val())
		{
			$.toast({
				heading: 'Error',
				text: 'Passwords entered doesnot match. Please enter again',
				position: 'top-right',
				hideAfter: 4000,
				icon: 'error'
			})
		}
		else{
			var user = {
					username : $usernameFld.val(),
					password : $passwordFld.val()
			};
			userService.register(user)
			.then(success);
		}
	}

	function checkPassword(){
		if($passwordFld.val() != $verifyPasswordFld.val())
		{
			$('#inputverifyPassword').removeClass('is-valid');
			$('#inputverifyPassword').addClass('is-invalid');
		}
		else{
			$('#inputverifyPassword').removeClass('is-invalid');
			$('#inputverifyPassword').addClass('is-valid');
		}
	}

	function success(str)
	{
		if(str === true)
		{
			$.toast({
				heading: 'Success',
				text: 'User has been registered.',
				position: 'top-right',
				hideAfter: 4000,
				icon: 'success'
			})

			window.setTimeout(function(){
				window.location.pathname = "/jquery/components/Profile/profile.template.client.html";
			}, 4500);
		}
		else{
			$.toast({
				heading: 'Error',
				text: 'A user exists with the same username. Try some different username',
				position: 'top-right',
				hideAfter: 5000,
				icon: 'error'
			})
		}
	}

})();
