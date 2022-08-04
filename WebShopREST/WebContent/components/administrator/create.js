Vue.component("create", { 
	data: function () {
	    return {
			username: null,
			password: null,
			firstName: null,
			lastName: null,
			email: null,
			gender: null,
			mode: "IDLE",
	      	mode2 : "IDLE",
	      	mode3 : "IDLE",
	      	temp: null,
	      	createView:false,
	      	createType: null,
	      	pomocna: null,
	      	dateOfBirth : null,
	      coach: {"firstName":null, "lastName":null, "email":null, "username":null, "password":null, "gender":null, "dateOfBirth":null, "userType":null, "trainingHistory":null}
	    	
	    }
	},template: ` 
    	<form id="formaTrener">
		<table>
			<tr><td>Ime</td><td><input v-model="firstName" type="text" name="firstname"></td></tr>
			<tr><td>Prezime</td><td><input v-model="lastName" type="text" name="lastname"></td></tr>
			<tr><td>Username</td><td><input v-model="username" type="text" name="username"></td></tr>
			<tr><td>Password</td><td><input v-model="password" type="password" name="password"></td></tr>
			<tr><td>Email</td><td><input v-model="email" type="text" name="email"></td></tr>
			<tr><td>Datum rodjenja</td><td><input v-model="dateOfBirth" type="text" name="dateOfBirth"></td></tr>
			<select v-model="gender">
  				<option disabled value="">Izaberite</option>
  				<option>Male</option>
  				<option>Female</option>
  				<option>Other</option>
			</select></td></tr>
			
			<tr><td><button v-on:click = "registerUser">Registruj</button></td></tr>
		</table>
		<p id="error" hidden="true"></p>
		<p id="success" hidden="true"></p>
	</form>
	
    	`,
    		mounted () {
        this.createType = this.$route.params.id;
    },
    	updated(){this.createType = this.$route.params.id;},
    	methods: {
	registerUser : function(event){
		event.preventDefault()
		if((this.firstName === null) || (this.lastName === null) || (this.email === null) || (this.username === null) || (this.password === null) || this.dateOfBirth === null){
			alert("Postoji nepopunjeno polje!")
			return
		}
		if(this.createType == "manager")
			{
				axios
			.post('rest/managers/', {"firstName":''+this.firstName,"lastName":''+this.lastName,"email":''+this.email,"username":''+this.username,"password":''+this.password, "gender":null,"dateOfBirth":''+this.dateOfBirth,"userType":'MANAGER',sportsFacility:null})
				.then(response => {alert("Created successfully")})
				.catch((e) => { alert("Exception")})
			}
		else{
			axios
			.post('rest/coaches/', {"firstName":''+this.firstName,"lastName":''+this.lastName,"email":''+this.email,"username":''+this.username,"password":''+this.password, "gender":null,"dateOfBirth":''+this.dateOfBirth,"userType":'COACH',trainingHistory:null})
				.then(response => {alert("Created successfully")})
				.catch((e) => { alert("Exception")})
		}
		
		
		
		
	}
	,
	
}
});