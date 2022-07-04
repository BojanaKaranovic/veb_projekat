Vue.component("coach", { 
	data: function () {
	    return {
	      coach: {"firstName":null, "lastName":null, "email":null, "username":null, "password":null, "gender":null, "dateOfBirth":null, "userType":null, "trainingHistory":null}
	    	
	    }
	},template: ` 
    	<form id="formaTrener">
		<table>
			<tr><td>Ime</td><td><input v-model="coach.firstName" type="text" name="firstname"></td></tr>
			<tr><td>Prezime</td><td><input v-model="coach.lastName" type="text" name="lastname"></td></tr>
			<tr><td>Username</td><td><input v-model="coach.username" type="text" name="username"></td></tr>
			<tr><td>Password</td><td><input v-model="coach.password" type="password" name="password"></td></tr>
			<tr><td>Email</td><td><input v-model="coach.email" type="text" name="email"></td></tr>
			<tr><td>Datum rodjenja</td><td><input v-model="coach.dateOfBirth" type="text" name="dateOfBirth"></td></tr>
			
			
			<tr><td><button v-on:click = "registerUser">Registruj trenera</button></td></tr>
		</table>
		<p id="error" hidden="true"></p>
		<p id="success" hidden="true"></p>
	</form>
	
    	`,
    	methods: {
	registerUser : function(event){
		event.preventDefault()
		if((this.coach.firstName === null) || (this.coach.lastName === null) || (this.coach.email === null) || (this.coach.username === null) || (this.coach.password === null) || this.coach.dateOfBirth === null){
			alert("Postoji nepopunjeno polje!")
			return
		}
		axios.post('rest/userLogin/manager', this.manager)
		.then(response => {
			location.href=response.data
		})
		.catch(function(){
			alert("Korisnicko ime je vec zauzeto!")
		})
		
		
	}
	,
	mounted () {
        
    },
    
}
});