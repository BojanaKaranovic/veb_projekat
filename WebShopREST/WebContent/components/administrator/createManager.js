Vue.component("manager", { 
	data: function () {
	    return {
	      manager: {"firstName":null, "lastName":null, "email":null, "username":null, "password":null, "gender":null, "dateOfBirth":null, "userType":null, "sportsFacility":null},
	      isShow :false
	    	
	    }
	},template: ` 
	<div>
			<button v-on:click="isShow = !isShow">Registrujte menadzera</button>
	    	<form  id="forma" v-show="isShow">
			<table >
				<tr><td>Ime</td><td><input v-model="manager.firstName" type="text" name="firstname"></td></tr>
				<tr><td>Prezime</td><td><input v-model="manager.lastName" type="text" name="lastname"></td></tr>
				<tr><td>Username</td><td><input v-model="manager.username" type="text" name="username"></td></tr>
				<tr><td>Password</td><td><input v-model="manager.password" type="password" name="password"></td></tr>
				<tr><td>Email</td><td><input v-model="manager.email" type="text" name="email"></td></tr>
				<tr><td>Datum rodjenja</td><td><input v-model="manager.dateOfBirth" type="text" name="dateOfBirth"></td></tr>
				
				
				<tr><td><button v-on:click = "registerUser">Registruj menadzera</button></td></tr>
			</table>
			<p id="error" hidden="true"></p>
			<p id="success" hidden="true"></p>
		</form>
		</div>
    	`,
    	methods: {
	registerUser : function(event){
		event.preventDefault()
		if((this.manager.firstName === null) || (this.manager.lastName === null) || (this.manager.email === null) || (this.manager.username === null) || (this.manager.password === null) || this.manager.dateOfBirth === null){
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