Vue.component("user", { 
	data: function () {
	    return {
	      user: null
	    }
	},template: ` 
    	<form id="forma">
		<table>
			<tr><td>Username</td><td><input type="text" name="username"></td></tr>
			<tr><td>Password</td><td><input type="password" name="password"></td></tr>
			<tr><td><button v-on:click = "loginUser">Login</button></td></tr>
		</table>
		<p id="error" hidden="true"></p>
		<p id="success" hidden="true"></p>
	</form>
    	`,mounted () {
        axios
          .get('rest/login/')
          .then(response => (this.user = response.data))
    },
    methods: {
	loginUser : function(){
		router.push('login/${username}')
	}
}
});