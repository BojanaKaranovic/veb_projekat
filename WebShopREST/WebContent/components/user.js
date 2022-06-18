Vue.component("user", { 
	data: function () {
	    return {
	      user: null,
	      username: "",
	      password : ""
	    }
	},template: ` 
    	<form id="forma">
		<table>
			<tr><td>Username</td><td><input v-model="username" type="text" name="username"></td></tr>
			<tr><td>Password</td><td><input v-model="password" type="password" name="password"></td></tr>
			<tr><td><button v-on:click = "loginUser">Login</button></td></tr>
		</table>
		<p id="error" hidden="true"></p>
		<p id="success" hidden="true"></p>
	</form>
    	`,mounted () {
        /*axios
          .get('rest/login/'+this.username+'''password')
          .then(response => (this.user = response.data))*/
    },
    methods: {
	loginUser : function(event){
		event.preventDefault()
		axios.get('rest/userLogin/login/'+this.username+'/'+this.password)
		.then(response => {
		location.href=response.data
		})
	}
}
});