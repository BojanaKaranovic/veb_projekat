Vue.component("sportsFacilities", { 
	data: function () {
	    return {
	      sportsFacilities: null
	    }
	},
	    template: ` 
    	<div>
    		<h3>Prikaz sportskih objekata</h3>
    		<p><input type="text" id="nameToCheck"></p>

			<button onclick="myFunction()">Pretrazi</button>
    		<table border="1" id="tableSportsFacilities">
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
	    			
	    		<tr v-for="sportFacility in sportsFacilities">
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
	    	<script>
	    		function checkName(name) {
 				 return name === document.getElementById("sportFacility.name").value;
				}
				function myFunction() {
				  document.getElementById("tableSportsFacilities").innerHTML = sportsFacilities.filter(checkName);
				}
	    	</script>
    		</div>		  
    	`,
    mounted () {
        axios
          .get('rest/sportsFacilities/')
          .then(response => (this.sportsFacilities = response.data))
    },
    methods: {
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