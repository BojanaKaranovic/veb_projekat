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
    		<input type="text" v-model="this.name">
			<button v-on:click="searchName(this.name)">Pretrazi po imenu</button>
			<input type="text" v-model="this.city">
			<button v-on:click="searchLocation(this.city)">Pretrazi po gradu</button>
			<input type="text" v-model="this.rating">
			<button v-on:click="searchRating(this.rating)">Pretrazi po oceni</button>
			<input type="text" v-model="this.type">
			<button v-on:click="searchType(this.type)">Pretrazi po tipu</button>
			<table border="1" id="tableSportsFacilities" v-bind:sportsFacilities = "this.searched">
	    		<tr bgcolor="lightgrey">
	    			<th>Naziv</th>
	    			<th>Tip objekta</th>
	    			<th>Status</th>
	    			<th>Lokacija</th>
	    			<th>Logo</th>
	    			<th>Prosecna ocena</th>
	    			<th>Radno vreme</th>
	    		</tr>
	    			
	    		<tr v-for="sportFacility in searched" v-on:click="sportsFacilityInfo(sportFacility.name)">
	    			<td id = "name">{{sportFacility.name}}</td>
	    			<td>{{sportFacility.type}}</td>
	    			<td v-if="sportFacility.status">Radi</td>
	    			<td v-if="!sportFacility.status">Ne radi</td>
	    			<td>{{sportFacility.location.address.street}} {{sportFacility.location.address.number}}, {{sportFacility.location.address.city}}, {{sportFacility.location.address.zipCode}}</td>
	    			<td><img v-bind:src=" 'images/'+sportFacility.logo " /> </td>
	    			<td v-if="sportFacility.averageRating !== 0">{{sportFacility.averageRating}}</td>
	    			<td v-if="sportFacility.averageRating === 0">Nema ocenu</td>
	    			<td>{{sportFacility.workTime}}</td>
	    			
	    			
	    		</tr>
	    	</table>
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