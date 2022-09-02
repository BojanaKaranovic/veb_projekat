Vue.component("createTraining", { 
	data: function () {
	    return {
			name: '',
			type: '',
			sportsFacility: '',
			durationInMinutes: null,
			coach: '',
			description: '',
	      	image: '',
	      	coaches: {},
	      	coach: '',
	      	price: null
	      	
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
			            	 		<input v-model="name" type="text" class="form-control" id="inputName" placeholder="Naziv*">
			            	 	</div>
			            	 	<div class=" mb-4">
			            	 			<select class="form-select" v-model="type">
							  				<option value="GRUPNI">Grupni</option>
							  				<option value="PERSONALNI">Personalni</option>
							  				<option value="TERETANA">Teretana</option>
										</select>
			            	 		</div>
			            	 	<div class="form-outline mb-4">
			            	 		<input v-model="durationInMinutes" type="number" class="form-control" id="inputdurationInMinutes" placeholder="Trajanje">
			            	 	</div>
			            	 	<div class="form-outline mb-4">
			            	 		<input v-model="price" type="number" class="form-control" id="inputdurationInMinutes" placeholder="Cena">
			            	 	</div>
			            	 	<div class="form-outline mb-4">
			            	 		<input v-model="description" type="text" class="form-control" id="inputdescription" placeholder="Opis">
			            	 	</div>
			            	 	<div class="form-outline mb-4">
			            	 		<input v-model="image" type="file" name="image">
			            	 	</div>
			            	 	<div class=" mb-4">
			            	 			<select class="form-select" v-model="coach">
							  				<option v-for="c in coaches" >{{c.firstName}} {{c.lastName}}</option>	
										</select>
			            	 		</div>
			            	 	
			            	 	
			            	 	<button class="btn btn-success mb-1" v-on:click = "create">Kreiraj</button>
			            	 </form>
			            </div>
					</div>
				</div>
			</div>
		</div>
	</section>
    	
	
    	`,
    		mounted () {
        axios.get("rest/userLogin/coaches")
        .then((response)=> this.coaches = response.data)
    },
    	
    	methods: {
		create : function(){
			event.preventDefault()
            let array = this.image.split("\\")
            let logoDestination = array[array.length - 1]
            if(this.price==null){
				this.price=0;
			}
			if(this.durationInMinutes==null){
				this.durationInMinutes=0;
			}
            if(!this.name || !this.type || !logoDestination || !this.coach){
				alert("Nepopunjeno obavezno polje")
				return
			}axios.post('rest/userLogin/createTraining/' + this.coach, 
	            {"name": this.name, "type": this.type, "sportFacility": '', "durationInMinutes" : this.durationInMinutes, "coach" : '', "description": this.description, "image" : logoDestination, "deleted": false, "price": this.price})
	            .then(response => {
	                if(response.data){
						alert("Created successfully")
					}
	            })
	            .catch((e) => { alert("Exception")})
		}
	
}
});