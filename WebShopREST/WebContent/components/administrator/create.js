Vue.component("create", { 
	data: function () {
	    return {
			username: null,
			password: null,
			firstName: null,
			lastName: null,
			email: null,
			gender: null,
	      	temp: null,
	      	createView:false,
	      	createType: null,
	      	pomocna: null,
	      	dateOfBirth : null,
	      	
	    }
	},template: ` 
	<section class="h-100 h-custom" style=" margin-top:1.1%;">
		<div class="container py-5">
			<div class="row d-flex justify-content-center align-items-center ">
				<div class="col-lg-8 col-xl-6">
					<div class="card rounded-3 ">
						<img src="images/logo.png" 
			             style=" width:25%; align:right"
			            alt="Logo">
			            <div class="card-body p-4 p-md-5">
			            	<form class="px-md-2">
			            	 	<div class="form-outline mb-4">
			            	 		<input v-model="firstName" type="text" class="form-control" id="inputName" placeholder="Ime">
			            	 	</div>
			            	 	<div class="form-outline mb-4">
			            	 		<input v-model="lastName" type="text" class="form-control" id="inputLastName" placeholder="Prezime">
			            	 	</div>
			            	 	<div class="form-outline mb-4">
			            	 		<input v-model="username" type="text" class="form-control" id="inputUsername" placeholder="Username">
			            	 	</div>
			            	 	<div class="form-outline mb-4">
			            	 		<input v-model="password" type="text" class="form-control" id="inputPassword" placeholder="Password">
			            	 	</div>
			            	 	<div class="form-outline mb-4">
			            	 		<input v-model="email" type="text" class="form-control" id="inputEmail" placeholder="Email">
			            	 	</div>
			            	 	<div class="row">
			            	 		<div class="col-md-6 mb-4">
			            	 			 <div class="form-outline datepicker">
					            	 		<input v-model="dateOfBirth" type="text" class="form-control" id="inputDate" placeholder="Datum rodjenja">
			            	 			 </div>
			            	 		</div>
			            	 		<div class="col-md-6 mb-4">
			            	 			<select class="form-select" v-model="gender">
							  				<option value="MALE">Male</option>
							  				<option value="FEMALE">Female</option>
										</select>
			            	 		</div>
			            	 	</div>
			            	 	<button class="btn btn-success mb-1" v-on:click = "registerUser">Registruj se</button>
			            	 </form>
			            </div>
					</div>
				</div>
			</div>
		</div>
	</section>
    	
	
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
			.post('rest/managers/', {"firstName":''+this.firstName,"lastName":''+this.lastName,"email":''+this.email,"username":''+this.username,"password":''+this.password, "gender":this.gender,"dateOfBirth":''+this.dateOfBirth,"userType":'MANAGER',"sportsFacility":''})
				.then(response => {alert("Created successfully")}, location.reload())
				.catch((e) => { alert("Exception")})
			}
		else{
			axios
			.post('rest/coaches/', {"firstName":''+this.firstName,"lastName":''+this.lastName,"email":''+this.email,"username":''+this.username,"password":''+this.password, "gender":this.gender,"dateOfBirth":''+this.dateOfBirth,"userType":'COACH',"trainingHistory":null})
				.then(response => {alert("Created successfully")}, location.reload())
				.catch((e) => { alert("Exception")})
		}
		
		
		
		
	}
	,
	
}
});