Vue.component("viewProfile", { 
	data: function () {
	    return {
			username: '',
			
	      admin: null,
	      previous:null
	    }
	},template: ` 
		<div>
		<div class="container py-5" style="margin-top:5%; align:center;" >
			<div class="row d-flex justify-content-center align-items-center ">
				<div class="col-lg-8 col-xl-6" style="align:center">
					<div class="card rounded-3 " style="align:center">
						<div class="card-body p-3 p-md-5" style="align:center">
							<form >
								<div class="form-group row">
								    <label for="staticFirstName" class="col-sm-4 col-form-label">Ime</label>
								    <div class="col-sm-6">
								      <input type="text" class="form-control" id="staticFirstName" v-if="admin" v-model="admin.firstName">
								    </div>
								 </div>
								 <div class="form-group row">
								    <label for="staticLastName" class="col-sm-4 col-form-label">Prezime</label>
								    <div class="col-sm-6">
								      <input type="text" class="form-control" id="staticLastName" v-if="admin" v-model="admin.lastName">
								    </div>
								 </div>
								 <div class="form-group row">
								    <label for="staticUsername" class="col-sm-4 col-form-label">Korisnicko ime</label>
								    <div class="col-sm-6">
								      <input type="text" class="form-control" id="staticUsername" v-if="admin" v-model="admin.username" readonly>
								    </div>
								 </div>
								 <div class="form-group row">
								    <label for="staticPassword" class="col-sm-4 col-form-label">Lozinka</label>
								    <div class="col-sm-6">
								      <input type="text" class="form-control" id="staticPassword" v-if="admin" v-model="admin.password">
								    </div>
								 </div>
								 <div class="form-group row">
								    <label for="staticDateOfBirth" class="col-sm-4 col-form-label">Datum rodjenja</label>
								    <div class="col-sm-6">
								      <input type="text" class="form-control" id="staticDateOfBirth" v-if="admin" v-model="admin.dateOfBirth">
								    </div>
								 </div>
								 <div class="form-group row">
								    <label  class="col-sm-4 col-form-label">Pol</label>
								    <div class="col-sm-6">
								      <select class="form-select" v-if="admin" v-model="admin.gender">
							  				<option value="MALE">Male</option>
							  				<option value="FEMALE">Female</option>
									  </select> 
									 </div>
								 </div>
								 
							</br>
								 <div class="text-center">
								<button v-on:click="restore" class="btn btn-danger" >Ponisti izmene</button>
								<button v-on:click = "update" class="btn btn-success">Sacuvaj izmene</button>
							</div>
							</form>
							
						</div>
						
					</div>
				</div>
			</div>
		</div>
    	
	</div>
    	`,
    		mounted () {
	        	axios.get('rest/userLogin/loggedInAdmin').then(response=>{this.admin = response.data;this.previous = this.admin;this.username=response.data.username})
	        	
    },
    	methods: {
	
		update: function(event){
			event.preventDefault()
			if(!this.admin.firstName || !this.admin.lastName || !this.admin.username || !this.admin.password || !this.admin.email
			 || !this.admin.gender || !this.admin.dateOfBirth){
				alert("Postoji nepopunjeno polje!")
				return
			}
			axios.put('rest/userLogin/updateAdmin/' + this.username, this.admin)
			.then(response => {
                location.href = response.data
            })
            .catch(function(){
                alert("Korisnicko ime nije jedinstveno!")
            })
		
		
		
	},
	restore: function(){
			this.admin = this.previous
	}
}
});