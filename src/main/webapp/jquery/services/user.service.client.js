function UserServiceClient() {
	this.register = register;
	this.login = login;
	this.forgotPassword = forgotPassword;
	this.resetPassword = resetPassword;
	this.logout = logout;
	this.findProfileById = findProfileById;
	this.updateProfile = updateProfile;
	this.checkIfAdmin = checkIfAdmin;
	this.createUser = createUser;
	this.findAllUsers = findAllUsers;
	this.findUserById = findUserById;
	this.searchUsers = searchUsers;
	this.deleteUser = deleteUser;
	this.updateUser = updateUser;
	this.url = '/api/user';
	this.registerURL = '/api/register';
	this.loginURL = '/api/login';
	this.profileURL = '/api/profile';
	var self = this;

	function register(user) {

		return fetch(this.registerURL,{
			method :'post',
			body : JSON.stringify(user),
			credentials: 'same-origin',
			headers : {
				'content-type':'application/json'
			}
		})
		.then(function(response)
				{
			return response.json();
				});
	}

	function login(user) {
		return fetch(this.loginURL,{
			method: 'post',
			credentials: 'same-origin',
			body: JSON.stringify({username:user.username, password: user.password}),
			headers: {
				'content-type': 'application/json'
			}
		})
		.then(function(response)
				{
			return response.json();
				});
	}
	
	function forgotPassword(userEmail)
	{
		return fetch(this.loginURL+'/forgot',{
			method :'post',
			credentials: 'same-origin',
			body : JSON.stringify(userEmail),
			headers : {
				'content-type':'application/json',
			}
		})
		.then(function(response)
				{
			return response.json();
				});
	}

	function resetPassword(userPwd)
	{
		return fetch(this.loginURL+'/reset',{
			method :'post',
			credentials: 'same-origin',
			body : JSON.stringify(userPwd),
			headers : {
				'content-type':'application/json',
			}
		})
		.then(function(response)
				{
			return response.json();
				});
	}
	
	function logout(){
		return fetch(this.url+'/logout',{
			method :'post',
			credentials: 'same-origin',
			headers : {
				'Content-Type': 'application/json',
			}
		})
		.then(function(response)
				{
			return response;
				});
	}
	
	function findProfileById() {
		return fetch(this.profileURL,{
			method :'get',
			credentials: 'same-origin',
		})
		.then(function(response)
				{
			return response.json();
				});
	}
	
	function updateProfile(userId,user) {
		return fetch(this.profileURL,{
			method :'put',
			credentials: 'same-origin',
			body : JSON.stringify(user),
			headers : {
				'content-type':'application/json'
			}
		})
		.then(function(response)
				{
			return response.json();
				});
	}
	
	function checkIfAdmin(){
		return fetch(this.url+'/checkAdmin',{
			method :'post',
			credentials: 'same-origin',
			headers : {
				'content-type':'application/json',
			}
		})
		.then(function(response)
				{
			return response.json();
				});
	}
	
	function searchUsers(user) {

		return fetch(this.url+'/search',{
			method :'post',
			body : JSON.stringify(user),
			headers : {
				'content-type':'application/json'
			}
		})
		.then(function(response)
				{
			return response.json();
				});
	}
	
	function createUser(user, callback) {
		return fetch(this.url,{
			method :'post',
			credentials: 'same-origin',
			body : JSON.stringify(user),
			headers : {
				'content-type':'application/json'
			}
		})
		.then(function(response)
				{
			return response.json();
				});
	}
	function findAllUsers(callback) {
		return fetch(this.url)
		.then(function(response)
				{
			return response.json();
				});
	}
	function findUserById(userId) {
		return fetch(this.url+'/'+userId,{
			method :'get',
			credentials: 'same-origin',
		})
		.then(function(response)
				{
			return response.json();
				});
	}
	
	function updateUser(user,userId) {
		return fetch(this.url+'/'+userId,{
			method :'put',
			credentials: 'same-origin',
			body : JSON.stringify(user),
			headers : {
				'content-type':'application/json'
			}
		})
		.then(function(response)
				{
			return response.json();
				});
	}
	
	function deleteUser(userId) {
		return fetch(this.url+'/'+userId,{
			credentials: 'same-origin',
			method :'delete'
		})
		.then(function(response)
				{
			return response;
				});
	}



}
