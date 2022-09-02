Vue.component("sportsFacilities", { 
	data: function () {
	    return {
	      sportsFacilities: null,
	      searched:null,
	      name:'',
	      city:'',
	      rating:'',
	      type:'',
	      logo:null
	    }
	}
	,
	    template: ` 
    	<div>
    	
    		<form class="d-flex" style="margin-top:5%;">
        			<input class="form-control me-2" type="search" aria-label="Search" v-model="name" placeholder="Ime">
        			<input class="form-control me-2" type="search" aria-label="Search" v-model="type" placeholder="Tip">
        			<input class="form-control me-2" type="search" aria-label="Search" v-model="city" placeholder="Grad">
        			<input class="form-control me-2" type="search" aria-label="Search" v-model="rating" placeholder="Ocena">
        			<button class="btn btn-success"  v-on:click="search()" style="margin-right:10px;">Pretrazi</button>
        			<button class="btn btn-danger" v-on:click="restore()">Ponisti</button>
        		</form>
			
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
					  	<li class="list-group-item" v-if="sportFacility.averageRating !== 0">{{sportFacility.averageRating}}</li>
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
		},
		search: function(){
			this.searched = this.sportsFacilities;
			if(this.name !=''){
				this.searched = this.sportsFacilities.filter(o => o.name.toLowerCase().includes(this.name.toLowerCase()));
			
			}
			if(this.city !=''){
				this.searched = this.sportsFacilities.filter(o => o.location.address.city.toLowerCase().includes(this.city.toLowerCase()));
			
			}
			if(this.type !=''){
				this.searched = this.sportsFacilities.filter(o => o.type.toString().toLowerCase().includes(this.type.toLowerCase()));
			}
			if(this.rating !=''){
				this.searched = this.sportsFacilities.filter(o => o.averageRating.toString().includes(this.rating.toString()));
			}
			
		},
		restore: function(){
			this.searched = this.sportsFacilities;
			this.name='';
			this.type='';
			this.city='';
			this.rating='';
		}
    	
    }
});