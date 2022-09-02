Vue.component("trainings", { 
	data: function () {
	    return {
	      trainings: {},
	      searched: {},
	      coach:{},
	      dates: {},
	      trainings1: [],
	      searched1: [],
	      minPrice: 0,
	      maxPrice: 100000,
	      start: '',
	      end: '',
	      sortedPrice: false,
	      sortedDate: false,
	      facilityType: '',
	      facilityTypes: {},
	      
	      trainingType: '',
	      trainingTypes: {}
	    }
	}
	,
	    template: ` 
    	<div style="margin-top:3.5%;">
    		<div class="btn-group" role="group" style="width:100%;">
			  <button type="button" class="btn btn-primary" style="width:30%;" v-on:click="all()">Svi treninzi</button>
			  <button type="button" class="btn btn-primary" style="width:30%;" v-on:click="personal()">Personalni treninzi</button>
			  <button type="button" class="btn btn-primary" style="width:30%;" v-on:click="group()">Grupni treninzi</button>
			</div>
			
    	<form class="d-flex" style="margin-top:1%;">
        			<input class="form-control me-1" type="number" aria-label="Search" v-model="minPrice" placeholder="Minimalna cena">
        			<input class="form-control me-1" type="number" aria-label="Search" v-model="maxPrice" placeholder="Maksimalna cena">
        			<button class="btn btn-success btn-sm"  v-on:click="searchPrice()" >Pretrazi po ceni</button>
        			<input class="form-control me-1" type="date" aria-label="Search" v-model="start" placeholder="Pocetak">
        			<input class="form-control me-1" type="date" aria-label="Search" v-model="end" placeholder="Kraj">
        			<button class="btn btn-success btn-sm"  v-on:click="search()" >Pretrazi po datumu</button>
        			<button class="btn btn-success btn-sm"  v-on:click="sortPrice()" >Sortiraj po ceni</button>
					<button class="btn btn-success btn-sm"  v-on:click="sortDate()" >Sortiraj po datumu</button>
					
        		</form>
			<div class="row row-cols-2 row-cols-md-4" v-bind:trainings1 = "this.searched1" >
				<div class="card border-light" style="margin-left:10px" v-for="training in searched1" >
					<img style="width:50%"  v-bind:src=" 'images/'+training.trainings.image " class="card-img-top" />
					<div class="card-body">
						<h5 class="card-title">{{training.trainings.name}}</h5>
					</div>
					<ul class="list-group list-group-flush">
					    <li class="list-group-item">{{training.trainings.type}}</li>
					    <li class="list-group-item">  {{training.trainings.sportsFacility}}</li>
					  	<li class="list-group-item">{{training.trainings.durationInMinutes}}</li>
					  	<li class="list-group-item">{{training.trainings.coach}}</li>
					  	<li class="list-group-item">{{training.trainings.description}}</li>
					  	<li class="list-group-item">{{training.trainings.price}}</li>
					  	<li class="list-group-item">{{training.dates}}</li>
					  </ul>
				</div>
			
			</div> 
			
    		</div>		  
    	`,
    mounted () {
	axios.get('rest/userLogin/loggedInCoach')
		.then(response => {
			this.coach = response.data
			axios.get('rest/sportsFacilities/getTrainingsCoach/' + this.coach.username)
			.then(response => {this.trainings = response.data, this.searched = response.data,
			axios.get('rest/sportsFacilities/getDatesCoach/'+ this.coach.username)
			.then(response =>{
				this.dates = response.data;
				for (let i = 0 ; i < this.dates.length; i++) {
				      this.trainings1.push({
				       trainings: this.trainings[i],
				       dates: this.dates[i]
	      				})
	    		}
	    		})
	    		this.searched1 = this.trainings1;
				axios.get("rest/enum/facilityTypes").then((response) => {this.facilityTypes = response.data})
				axios.get("rest/enum/trainingTypes").then((response) => {this.trainingTypes = response.data})
    })})},
    methods: {
		all: function(){
			this.trainings={};
			this.searched={};
			this.searched1=[];
			this.trainings1=[];
			axios.get('rest/userLogin/loggedInCoach')
		.then(response => {
			this.coach = response.data
			axios.get('rest/sportsFacilities/getTrainingsCoach/' + this.coach.username)
			.then(response => {this.trainings = response.data, this.searched = response.data,
			axios.get('rest/sportsFacilities/getDatesCoach/'+ this.coach.username)
			.then(response =>{
				this.dates = response.data;
				for (let i = 0 ; i < this.dates.length; i++) {
				      this.trainings1.push({
				       trainings: this.trainings[i],
				       dates: this.dates[i]
	      				})
	    		}
	    		})
	    		this.searched1 = this.trainings1; })})
		},
		group: function(){
			this.trainings={};
			this.searched={};
			this.searched1=[];
			this.trainings1=[];
			axios.get('rest/userLogin/loggedInCoach')
		.then(response => {
			this.coach = response.data
			axios.get('rest/sportsFacilities/getGroupCoach/' + this.coach.username)
			.then(response => {this.trainings = response.data, this.searched = response.data,
			axios.get('rest/sportsFacilities/getGroupDatesCoach/'+ this.coach.username)
			.then(response =>{
				this.dates = response.data;
				for (let i = 0 ; i < this.dates.length; i++) {
				      this.trainings1.push({
				       trainings: this.trainings[i],
				       dates: this.dates[i]
	      				})
	    		}
	    		})
	    		this.searched1 = this.trainings1; })})
		},
		personal: function(){
			this.trainings={};
			this.searched={};
			this.searched1=[];
			this.trainings1=[];
			axios.get('rest/userLogin/loggedInCoach')
		.then(response => {
			this.coach = response.data
			axios.get('rest/sportsFacilities/getPersonalCoach/' + this.coach.username)
			.then(response => {this.trainings = response.data, this.searched = response.data,
			axios.get('rest/sportsFacilities/getPersonalDatesCoach/'+ this.coach.username)
			.then(response =>{
				this.dates = response.data;
				for (let i = 0 ; i < this.dates.length; i++) {
				      this.trainings1.push({
				       trainings: this.trainings[i],
				       dates: this.dates[i]
	      				})
	    		}
	    		})
	    		this.searched1 = this.trainings1; })})
		},
		searchPrice: function(){
			this.searched1 = this.trainings1;
			if(this.minPrice > this.maxPrice){
				alert("Ne valja cena")
				this.minPrice = 0;
				this.maxPrice = 100000;
			}
			else {
				
				this.searched1 = this.trainings1.filter(o => o.trainings.price >= this.minPrice && o.trainings.price <= this.maxPrice);
				
			}
			
			
			
		},
		
    	search: function(){
			this.searched1 = this.trainings1;
			if(this.start > this.end){
				alert("Ne valja datum")
			
			}else if(this.start !='' && this.end !=''){
				
				this.searched1 = this.trainings1.filter(o => o.dates.split(' ')[1] >= this.start && o.dates.split(' ')[1] <= this.end);
			}
		},
		
		sortPrice: function(){
			if(this.sortedPrice === false)
			{
				this.searched1 = this.searched1.sort((a,b) => (a.trainings.price > b.trainings.price) ? 1 : ((a.trainings.price < b.trainings.price) ? -1 : 0));
				this.sortedPrice = true
				this.sortedDate = false
			}
			else
			{
				this.searched1.reverse()
				this.sortedPrice = false
			}
		},
		
		sortDate: function(){
			if(this.sortedDate === false)
			{
				this.searched1 = this.searched1.sort((a,b) => (a.dates > b.dates) ? 1 : ((a.dates < b.dates) ? -1 : 0));
				this.sortedPrice = false
				this.sortedDate = true
			}
			else
			{
				this.searched1.reverse()
				this.sortedDate = false
			}
		},
		trainingTypeFilter: function(evt){
			
			var t = evt.target.value;
			if(t == "Izaberi tip")
			{
				
				this.trainingType = '';
				this.searched1 = this.trainings1;
			}
			else{
				this.trainingType = t;	
				this.searched1 = this.trainings1.filter(o => o.trainings.type == this.trainingType);
			}
			
			
		},
    }
});