Vue.component("customer", { 
	data: function () {
	    return {
	      customer: {"firstName":null, "lastName":null, "email":null, "username":null, "password":null, "gender":null, "dateOfBirth":null, "userType":"CUSTOMER", "visitedFacility":null, "collectedPoints":0, "customerType":{"typeName":"OBICNI", "discount": 0.0, "requiredPoints":200}}
	    	
	    }
	},template: ` 
	<section class="h-100 h-custom" style="background-color: #426166;">
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
			            	 		<input v-model="customer.firstName" type="text" class="form-control" id="inputName" placeholder="Ime">
			            	 	</div>
			            	 	<div class="form-outline mb-4">
			            	 		<input v-model="customer.lastName" type="text" class="form-control" id="inputLastName" placeholder="Prezime">
			            	 	</div>
			            	 	<div class="form-outline mb-4">
			            	 		<input v-model="customer.username" type="text" class="form-control" id="inputUsername" placeholder="Username">
			            	 	</div>
			            	 	<div class="form-outline mb-4">
			            	 		<input v-model="customer.password" type="text" class="form-control" id="inputPassword" placeholder="Password">
			            	 	</div>
			            	 	<div class="form-outline mb-4">
			            	 		<input v-model="customer.email" type="text" class="form-control" id="inputEmail" placeholder="Email">
			            	 	</div>
			            	 	<div class="row">
			            	 		<div class="col-md-6 mb-4">
			            	 			 <div class="form-outline datepicker">
					            	 		<input v-model="customer.dateOfBirth" type="text" class="form-control" id="inputDate" placeholder="Datum rodjenja">
			            	 			 </div>
			            	 		</div>
			            	 		<div class="col-md-6 mb-4">
			            	 			<select class="form-select" v-model="customer.gender">
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