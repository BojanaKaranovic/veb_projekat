Vue.component("customer", { 
	data: function () {
	    return {
	      customer: {"firstName":null, "lastName":null, "email":null, "username":null, "password":null, "gender":null, "dateOfBirth":null, "userType":"CUSTOMER", "visitedFacility":null, "collectedPoints":0, "customerType":{"typeName":"OBICNI", "discount": 0.0, "requiredPoints":200}}
	    	
	    }
	},template: ` 
    	<form id="forma">
		<table>
			<tr><td>Ime</td><td><input v-model="customer.firstName" type="text" name="firstname"></td></tr>
			<tr><td>Prezime</td><td><input v-model="customer.lastName" type="text" name="lastname"></td></tr>
			<tr><td>Username</td><td><input v-model="customer.username" type="text" name="username"></td></tr>
			<tr><td>Password</td><td><input v-model="customer.password" type="password" name="password"></td></tr>
			<tr><td>Email</td><td><input v-model="customer.email" type="text" name="email"></td></tr>
			<tr><td>Datum rodjenja</td><td><input v-model="customer.dateOfBirth" type="text" name="dateOfBirth"></td></tr>
			<tr><td>Pol</td><td>
			<select v-model="customer.gender">
  				<option disabled value="">Izaberite</option>
  				<option value="MALE">Male</option>
  				<option value="FEMALE">Female</option>
			</select></td></tr>
			
			
			<tr><td><button v-on:click = "registerUser">Registruj se</button></td></tr>
		</table>
		<p id="error" hidden="true"></p>
		<p id="success" hidden="true"></p>
	</form>
	
    	`,
    	methods: {
	registerUser : function(event){
		event.preventDefault()
		if((this.customer.firstName === null) || (this.customer.lastName === null) || (this.customer.email === null) || (this.customer.username === null) || (this.customer.password === null) || this.customer.dateOfBirth === null){
			alert("Postoji nepopunjeno polje!")
			return
		}
		axios.post('rest/userLogin/registration', this.customer)
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