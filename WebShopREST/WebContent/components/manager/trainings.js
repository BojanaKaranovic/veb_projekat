Vue.component("trainings", { 
	data: function () {
	    return {
	      trainings: {},
	      searched: {},
	      manager:{},
	      dates: {},
	    }
	}
	,
	    template: ` 
    	<div style="margin-top:5%;">
    	
			<div class="row row-cols-2 row-cols-md-4" v-bind:trainings = "this.searched" >
				<div class="card border-light" style="margin-left:10px" v-for="training in trainings" >
					<img style="width:50%"  v-bind:src=" 'images/'+training.image " class="card-img-top" />
					<div class="card-body">
						<h5 class="card-title">{{training.name}}</h5>
					</div>
					<ul class="list-group list-group-flush">
					    <li class="list-group-item">{{training.type}}</li>
					    <li class="list-group-item">  {{training.sportsFacility}}</li>
					  	<li class="list-group-item">{{training.durationInMinutes}}</li>
					  	<li class="list-group-item">{{training.coach}}</li>
					  	<li class="list-group-item">{{training.description}}</li>
					  </ul>
				</div>
			
			</div> 
			
    		</div>		  
    	`,
    mounted () {
	axios.get('rest/userLogin/loggedInManager')
		.then(response => {
			this.manager = response.data
			axios.get('rest/sportsFacilities/getTrainingsForSportsFacility/' + this.manager.sportsFacility)
			.then(response => {this.trainings = response.data, this.searched=response.data,
			axios.get('rest/sportsFacilities/getDates')
			.then(response =>{
				this.dates = response.data;
				})})})
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