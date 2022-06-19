Vue.component("customer", { 
	data: function () {
	    return {
	      customer: {firstname:null, lastname:null, username:null, password:null, dateOfBirth:null, gender:null, email:null}
	    }
	},template: ` 
    	<form id="forma">
		<table>
			<tr><td>Ime</td><td><input v-model="customer.firstname" type="text" name="firstname"></td></tr>
			<tr><td>Prezime</td><td><input v-model="customer.lastname" type="text" name="lastname"></td></tr>
			<tr><td>Username</td><td><input v-model="customer.username" type="text" name="username"></td></tr>
			<tr><td>Password</td><td><input v-model="customer.password" type="password" name="password"></td></tr>
			<tr><td>Email</td><td><input v-model="customer.email" type="text" name="email"></td></tr>
			<tr><td>Pol</td><td><input v-model="customer.gender" type="text" name="gender"></td></tr>
			<tr><td>Datum rodjenja</td><td><input v-model="customer.dateOfBirth" type="datetime-local" name="dateOfBirth"></td></tr>
			
			<tr><td><button v-on:click = "registerUser">Registruj se</button></td></tr>
		</table>
		<p id="error" hidden="true"></p>
		<p id="success" hidden="true"></p>
	</form>
	
    	`,
    	methods: {
	registerUser : function(event){
		event.preventDefault()
		axios.get('rest/userRegistration/registration/'+this.customer)
		.then(response => (router.push(`/`))
		
		)
	},
	mounted () {
        axios
          .get('rest/userRegistration/registration/'+this.customer)
          .then(response => (this.customer = response.data))
    },
    
}
});