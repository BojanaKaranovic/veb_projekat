Vue.component("updateTraining", { 
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
	      	price: null,
	      	previous: null,
	      	training: null,
	      	previousName: ''
	      	
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
			            	 		<input v-if="training" v-model="training.name" type="text" class="form-control" id="inputName" placeholder="Naziv*">
			            	 	</div>
			            	 	<div class=" mb-4">
			            	 			<select class="form-select" v-if="training" v-model="training.type">
							  				<option value="GRUPNI">Grupni</option>
							  				<option value="PERSONALNI">Personalni</option>
							  				<option value="TERETANA">Teretana</option>
										</select>
			            	 		</div>
			            	 	<div class="form-outline mb-4">
			            	 		<input v-if="training" v-model="training.durationInMinutes" type="number" class="form-control" id="inputdurationInMinutes" placeholder="Trajanje">
			            	 	</div>
			            	 	<div class="form-outline mb-4">
			            	 		<input v-if="training" v-model="training.price" type="number" class="form-control" id="inputdurationInMinutes" placeholder="Cena">
			            	 	</div>
			            	 	<div class="form-outline mb-4">
			            	 		<input v-if="training" v-model="training.description" type="text" class="form-control" id="inputdescription" placeholder="Opis">
			            	 	</div>
			            	 	<div class="form-outline mb-4">
			            	 		<input v-model="image" type="file" v-if="training" name="image">
			            	 	</div>
			            	 	<div class=" mb-4">
			            	 			<select class="form-select" v-if="training" v-model="training.coach">
							  				<option v-for="c in coaches" >{{c.firstName}} {{c.lastName}}</option>	
										</select>
			            	 		</div>
			            	 	
			            	 	
			            	 	<button class="btn btn-success mb-1"  v-on:click = "update">Izmeni</button>
			            	 </form>
			            </div>
					</div>
				</div>
			</div>
		</div>
	</section>
    	
	
    	`,
    		mounted () {
		this.name = this.$route.params.id;
		if(this.name != ''){
	        axios.get("rest/userLogin/coaches")
	        .then((response)=> {this.coaches = response.data,
	    		    axios.get('rest/sportsFacilities/training/' + this.name)
	    		    .then((response)=>{this.training = response.data, this.previous = response.data, this.previousName = response.data.name})
	    	})
    	}else{
	alert("Nesto ne radi")}
    },
    	
    	methods: {
		update : function(){
			event.preventDefault()
            let array = this.image.split("\\")
            let logoDestination = array[array.length - 1]
            this.training.image = logoDestination;
            if(this.training.price==null){
				this.price=0;
			}
			if(this.training.durationInMinutes==null){
				this.durationInMinutes=0;
			}
            if(!this.training.name || !this.training.type || !logoDestination || !this.training.coach){
				alert("Nepopunjeno obavezno polje")
				return
			}axios.put('rest/userLogin/updateTraining/' + this.training.coach +'/' + this.previousName, this.training)
			.then(response => {
	                if(response.data){
						alert("Updated successfully")
					}
	            })
	            .catch((e) => { alert("Exception")})
		}
	
}
});