Vue.component("sportsFacilities", { 
	data: function () {
	    return {
	      sportsFacilities: null
	    }
	},
	    template: ` 
    	<div>
    		<h3>Prikaz sportskih objekata</h3>
    		<table border="1">
	    		<tr bgcolor="lightgrey">
	    			<th>Naziv</th>
	    			<th>Ulica</th>
	    		</tr>
	    			
	    		<tr v-for="p in sportsFacilities">
	    			<td>{{p.name}}</td>
	    			<td>{{p.address}}</td>
	    			
	    		</tr>
	    	</table>
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