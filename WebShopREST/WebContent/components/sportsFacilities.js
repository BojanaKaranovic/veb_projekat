Vue.component("sportsFacilities", { 
	data: function () {
	    return {
	      sportsFacilities: null,
	      searched:null,
	      name:null,
	      city:null,
	      rating:null,
	      type:null,
	      logo:null
	    }
	}
	,
	    template: ` 
    	<div>
    		<h3>Prikaz sportskih objekata</h3>
    		<div>
    		<input type="text" v-model="this.name">
			<button v-on:click="searchName(this.name)">Pretrazi po imenu</button>
			<input type="text" v-model="this.city">
			<button v-on:click="searchLocation(this.city)">Pretrazi po gradu</button>
			<input type="text" v-model="this.rating">
			<button v-on:click="searchRating(this.rating)">Pretrazi po oceni</button>
			<input type="text" v-model="this.type">
			<button v-on:click="searchType(this.type)">Pretrazi po tipu</button>
			</div>
			</br>
			<div class="d-grid gap-2 d-md-flex  justify-content-md-end">
				<button type="button" class="btn btn-primary"><a class="link-light" href ="loginPage.html">Uloguj se</a></button>
				<button type="button" class="btn btn-primary"><a class="link-light" href ="registrationPage.html">Registruj se</a></button>
			</div>
			</br>
			<div class="row row-cols-2 row-cols-md-4" v-bind:sportsFacilities = "this.searched" >
				<div class="card border-light" style="margin-left:10px" v-for="sportFacility in searched" v-on:click="sportsFacilityInfo(sportFacility.name)">
					<img style="width:50%"  v-bind:src=" 'images/'+sportFacility.logo " class="card-img-top" />
					<div class="card-body">
						<h5 class="card-title">{{sportFacility.name}}</h5>
					</div>
					<ul class="list-group list-group-flush">
					    <li class="list-group-item">{{sportFacility.type}}</li>
					    <li class="list-group-item" v-if="sportFacility.status">Radi</li>
					    <li class="list-group-item" v-if="!sportFacility.status">Ne radi</li>
					    <li class="list-group-item">{{sportFacility.location.address.street}} {{sportFacility.location.address.number}}, {{sportFacility.location.address.city}}, {{sportFacility.location.address.zipCode}}</li>
					  	<li class="list-group-item" v-if="sportFacility.averageRating !== 0"></li>
					  	<li class="list-group-item" v-if="sportFacility.averageRating === 0">Nema ocenu</li>
					  	<li class="list-group-item">{{sportFacility.workTime}}</li>
					  </ul>
				</div>
			
			</div>
			
    		</div>		  
    	`,
    mounted () {
        axios
          .get('rest/sportsFacilities/')
          .then(response => (this.sportsFacilities = response.data, this.searched = response.data))
    },
    methods: {
		searchName: function(name){
			this.searched = this.sportsFacilities.filter(o => o.name.toLowerCase().includes(name.toLowerCase()));
			
		},
		searchLocation: function(city){
			this.searched = this.sportsFacilities.filter(o => o.location.address.city.toLowerCase().includes(city.toLowerCase()));
			
		},
		searchRating: function(rating){
			this.searched = this.sportsFacilities.filter(o => o.averageRating.toString().includes(rating.toString()));
			
		},
		searchType: function(type){
			this.searched = this.sportsFacilities.filter(o => o.type.toString().toLowerCase().includes(type.toString().toLowerCase()));
			
		},
		sportsFacilityInfo: function(id){
			router.push('/sportsFacilityInfo/'+id);
		}
    	
    }
});