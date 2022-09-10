Vue.component("createSportsFacility", { 
	data: function () {
	    return {
			sportsFacility:{"name":null, "type":null, "trainings": [], "status": "false", "location":{"longitude": null,  "latitude": null, "address": {"street": null, "number": null, "city": null, "zipCode": null}}, "logo": null, "averageRating": 0, "workTime": "08:00-21:00"},
	      manager: {"firstName":null, "lastName":null, "email":null, "username":null, "password":null, "gender":null, "dateOfBirth":null, "userType":null, "sportsFacility":null},
	      managers: {},
	      selectedManager: null,
	      haveAvailabeManagers: false
	    }
	},template: ` 
	 <form id="formaSportskiObjekat" style="margin-top:5%;">
		<div class="container-fluid">
		
	    <div class="row">
	        <div class="col-lg-4">
	        <h5>Kreirajte novi sportski objekat</h5>
	        	<div class="form-outline mb-4">
        	 		<input v-model="sportsFacility.name" type="text" class="form-control" id="inputName" placeholder="Naziv">
        	  	</div>
        	  	<div class="form-outline mb-4">
	        	 	<div class="row">
	    	  			<div class="col-3">
	        	  			<label>Tip</label>
	        	  		</div>
	        	  		<div class="col">
	        	 			<select class="form-select" v-model="sportsFacility.type">
				  				<option value="TERETANA">Teretana</option>
								<option value="BAZEN">Bazen</option>
								<option value="SPORTSKI_CENTAR">Sportski centar</option>
								<option value="PLESNI_STUDIO">Plesni studio</option>
							</select>
						</div>
					</div>
	 			</div>
    	 		
    	 		<div class="form-outline mb-4">
        	 		<input v-model="sportsFacility.location.address.street" class="form-control" type="text" name="street" placeholder="Ulica">
        	  	</div>
        	  	<div class="form-outline mb-4">
        	 		<input v-model="sportsFacility.location.address.city" class="form-control" type="text" name="city" placeholder="Grad">
        	  	</div>
        	  	<div class="form-outline mb-4">
        	 		<input v-model="sportsFacility.location.address.number" class="form-control" type="number" name="number" placeholder="Broj">
        	  	</div>
        	  	<div class="form-outline mb-4">
        	 		<input v-model="sportsFacility.location.address.zipCode" class="form-control" type="text" name="city" placeholder="Postanski broj">
        	  	</div>
        	  	<div class="form-outline mb-4">
        	  		<div class="row">
        	  			<div class="col-3">
	        	  			<label>Logo</label>
	        	  		</div>
	        	  		<div class="col">
	        	 			<input v-model="sportsFacility.logo" type="file" class="form-control" name="logo">
        	 			</div>
        	 		</div>
        	 	</div>
				<div v-show="managers.length !== 0" class="form-outline mb-4">
        	 		<div class="row">
        	  			<div class="col-3">
	        	  			<label>Menadzer</label>
	        	  		</div>
	        	  		<div class="col">
	        	 			<select class="form-select" v-model = "selectedManager">
								<option v-for="m in managers" >{{m.firstName}} {{m.lastName}}</option>
							</select>
						</div>
					</div>
				</div>
				<br>
				<button v-on:click = "createSportsFacility" class = "btn btn-success">Kreiraj sportski objekat</button>	

	        </div>
	        <div class="col-lg-2">
	        </div>
	        <div class="col-lg-4">
	           <form v-show="managers.length === 0">
						
						<h5>Kreirajte novog menadzera</h5>
						<div class="form-outline mb-4">
		        	 		<input v-model="manager.firstName" type="text" class="form-control"  placeholder="Ime">
		        	  	</div>
		        	  	<div class="form-outline mb-4">
		        	 		<input v-model="manager.lastName" type="text" class="form-control"  placeholder="Prezime">
		        	  	</div>
		        	  	<div class="form-outline mb-4">
		        	 		<input v-model="manager.username" type="text" class="form-control"  placeholder="Korisnicko ime">
		        	  	</div>
		        	  	<div class="form-outline mb-4">
		        	 		<input v-model="manager.password" type="password" class="form-control"  placeholder="Lozinka">
		        	  	</div>
		        	  	<div class="form-outline mb-4">
		        	 		<input v-model="manager.email" type="text" class="form-control"  placeholder="Email">
		        	  	</div>
		        	  	<div class="form-outline mb-4">
		        	 		<input v-model="manager.dateOfBirth" type="text" class="form-control"  placeholder="Datum rodjenja">
		        	  	</div>
		        	  	<div class="form-outline mb-4">
			        	 	<div class="row">
			    	  			<div class="col-3">
			        	  			<label>Tip</label>
			        	  		</div>
			        	  		<div class="col">
			        	 			<select class="form-select" v-model="sportsFacility.type">
					  					<option value="MALE">Male</option>
			  							<option value="FEMALE">Female</option>
									</select>
								</div>
							</div>
			 			</div>
			 			<br>
			 			<button v-on:click = "registerManager" class="btn btn-success">Registruj menadzera</button>
					
						
					
					
					<p id="error" hidden="true"></p>
					<p id="success" hidden="true"></p>
				</form>
	        </div>
	    </div>
	</div>
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
			let array = this.sportsFacility.logo.split("\\")
	        let logoDestination = array[array.length - 1]
	        this.sportsFacility.logo = logoDestination;
			if((this.sportsFacility.name === null) || (this.sportsFacility.type === null) || (this.sportsFacility.location.address.street === null) || this.sportsFacility.location.address.city === null || this.sportsFacility.location.address.zipCode===null || this.sportsFacility.logo ===null){
				alert("Postoji nepopunjeno polje!")
				return
			}
		
			axios.post('rest/sportsFacilities/createSportsFacility/'+this.selectedManager, this.sportsFacility)
				.then(response => {alert("Created successfully")})
				.catch((e) => { alert("Exception")})
	}}
});