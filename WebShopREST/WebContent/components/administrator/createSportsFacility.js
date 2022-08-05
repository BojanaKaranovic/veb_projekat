Vue.component("createSportsFacility", { 
	data: function () {
	    return {
			sportsFacility:{"name":null, "type":null, "trainingType": null, "status": "false", "location":{"longitude": null,  "latitude": null, "address": {"street": null, "number": null, "city": null, "zipCode": null}}, "logo": null, "averageRating": 0, "workTime": "08.00-21.00"},
	      manager: {"firstName":null, "lastName":null, "email":null, "username":null, "password":null, "gender":null, "dateOfBirth":null, "userType":null, "sportsFacility":null},
	      managers: {},
	      selectedManager: null,
	      haveAvailabeManagers: false
	    }
	},template: ` 
    	<form id="formaSportskiObjekat">
		<table>
			<tr><td>Naziv</td><td><input v-model="sportsFacility.name" type="text" name="firstname"></td></tr>
			<tr>
				<td>Tip</td>
				<td><select v-model="sportsFacility.type" name="type">
						<option value="TERETANA">Teretana</option>
						<option value="BAZEN">Bazen</option>
						<option value="SPORTSKI_CENTAR">Sportski centar</option>
						<option value="PLESNI_STUDIO">Plesni studio</option>
						</select>
				</td>
			</tr>  
			<tr><td>Ulica</td><td><input v-model="sportsFacility.location.address.street" type="text" name="street"></td></tr>
			<tr><td>Broj</td><td><input v-model="sportsFacility.location.address.number" type="number" name="number"></td></tr>
			<tr><td>Grad</td><td><input v-model="sportsFacility.location.address.city" type="text" name="city"></td></tr>
			<tr><td>Postanski broj</td><td><input v-model="sportsFacility.location.address.zipCode" type="text" name="zipCode"></td></tr>
		  	<tr>
				<td>Logo</td><td><input v-model="sportsFacility.logo" type="text" name="logo"></td>
			</tr>
			
			
			
			
			<tr v-show="managers.length !== 0">
				<td >Menadzeri</td>
				<td >
					<select  v-model = "selectedManager">
						<option v-for="m in managers" >{{m.firstName}} {{m.lastName}}</option>
					</select>
				</td>
			</tr>
			<tr><td><button v-on:click = "createSportsFacility">Kreiraj sportski objekat</button></td></tr>
			</table>
			<form v-show="managers.length === 0">
			<table>
			<tr><td colspan="2">Kreirajte novog menadzera</td></tr>
			<tr><td>Ime</td><td><input v-model="manager.firstName" type="text" name="firstname"></td></tr>
			<tr><td>Prezime</td><td><input v-model="manager.lastName" type="text" name="lastname"></td></tr>
			<tr><td>Username</td><td><input v-model="manager.username" type="text" name="username"></td></tr>
			<tr><td>Password</td><td><input v-model="manager.password" type="password" name="password"></td></tr>
			<tr><td>Email</td><td><input v-model="manager.email" type="text" name="email"></td></tr>
			<tr><td>Datum rodjenja</td><td><input v-model="manager.dateOfBirth" type="text" name="dateOfBirth"></td></tr>
			<tr><td>Pol</td><td>
			<select v-model="manager.gender">
  				<option disabled value="">Izaberite</option>
  				<option value="MALE">Male</option>
  				<option value="FEMALE">Female</option>
			</select></td></tr>
			<tr>
			<tr><td><button v-on:click = "registerManager">Registruj menadzera</button></td></tr>
			</tr>
			</table>
			</form>
		
		
		<p id="error" hidden="true"></p>
		<p id="success" hidden="true"></p>
	</form>
	
    	`,
    		mounted () {
        axios.get('rest/managers/availableManagers')
		.then(response => {this.managers = response.data})
		if(this.managers.length===0){
			this.haveAvailableManagers = false;
		}
		else{
			this.haveAvailableManagers = true;
		}
    },
    	methods: {
		registerManager : function(event){
			event.preventDefault()
			if((this.manager.firstName === null) || (this.manager.lastName === null) || (this.manager.email === null) || (this.manager.username === null) || (this.manager.password === null) || this.manager.dateOfBirth === null){
				alert("Postoji nepopunjeno polje!")
				return
			}
		axios
			.post('rest/managers/',this.manager)
				.then(response => {alert("Created successfully"), this.selectedManager = this.manager.firstName + " " + this.manager.lastName})
				.catch((e) => { alert("Exception")})
				},
		createSportsFacility : function(event){
			event.preventDefault()
			//let array = this.sportsFacility.logo.split("\\")
	        //let logoDestination = array[array.length - 1]
	        //this.sportsFacility.logo = logoDestination;
			if((this.sportsFacility.name === null) || (this.sportsFacility.type === null) || (this.sportsFacility.location.address.street === null) || this.sportsFacility.location.address.city === null || this.sportsFacility.location.address.zipCode===null || this.sportsFacility.logo ===null){
				alert("Postoji nepopunjeno polje!")
				return
			}
		
			axios.post('rest/sportsFacilities/createSportsFacility/'+this.selectedManager, this.sportsFacility)
				.then(response => {alert("Created successfully")})
				.catch((e) => { alert("Exception")})
	}}
});