Vue.component("sportsFacilities", { 
	data: function () {
	    return {
	      sportsFacilities: null,
	      searched:null,
	      name:null,
	      city:null,
	      rating:null,
	      type:null
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
	    			<th>Sadrzaj koji nudimo</th>
	    			<th>Status</th>
	    			<th colspan=4>Lokacija</th>
	    			<th>Logo</th>
	    			<th>Prosecna ocena</th>
	    			<th>Radno vreme</th>
	    		</tr>
	    			
	    		<tr v-for="sportFacility in searched">
	    			<td id = "name">{{sportFacility.name}}</td>
	    			<td>{{sportFacility.type}}</td>
	    			<td>{{sportFacility.trainingType}}</td>
	    			<td v-if="sportFacility.status">Radi</td>
	    			<td v-if="!sportFacility.status">Ne radi</td>
	    			<td>{{sportFacility.location.address.street}}</td>
	    			<td>{{sportFacility.location.address.number}}</td>
	    			<td>{{sportFacility.location.address.city}}</td>
	    			<td>{{sportFacility.location.address.zipCode}}</td>
	    			<td>{{sportFacility.logo}}</td>
	    			<td>{{sportFacility.averageRating}}</td>
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
			
		}
    	/*addProduct : function() {
    		router.push(`/products/-1`);
    	},
    	editProduct : function(id) {
    		router.push(`/products/${id}`);
    	},
    	deleteProduct : function(id, index) {
    		r = confirm("Are you sure?")
    		if (r){
	    		axios
	            .delete('rest/products/' + id)
	            .then(response => (this.products.splice(index, 1)))
    		}
    	}*/
    }
});