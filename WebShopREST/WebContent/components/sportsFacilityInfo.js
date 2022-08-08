Vue.component("sportsFacilityInfo", { 
	data: function () {
	    return {
	      sportsFacility: null,
	      name:'',
	    }
	}
	,
	    template: ` 
    	<div>
    		<h3>Informacije o sportskom objektu</h3>
    		
			<table border="1">
	    		<tr bgcolor="lightgrey">
	    			<th>Naziv</th>
	    			<th>Tip objekta</th>
	    			<th>Sadrzaj koji nudimo</th>
	    			<th>Status</th>
	    			<th>Lokacija</th>
	    			<th>Logo</th>
	    			<th>Prosecna ocena</th>
	    			<th>Radno vreme</th>
	    		</tr>
	    			
	    		<tr>
	    			<td v-if="sportsFacility">{{sportsFacility.name}}</td>
	    			<td v-if="sportsFacility">{{sportsFacility.type}}</td>
	    			<td v-if="sportsFacility">{{sportsFacility.trainingType}}</td>
	    			
	    			<td v-if="sportsFacility.status">Radi</td>
	    			<td v-if="!sportsFacility.status">Ne radi</td>
	    			<td v-if="sportsFacility">{{sportsFacility.location.address.street}} {{sportsFacility.location.address.number}}, {{sportsFacility.location.address.city}}, {{sportsFacility.location.address.zipCode}}</td>
	    			<td v-if="sportsFacility"><img v-bind:src=" 'images/'+sportsFacility.logo " /> </td>
	    			<td v-if="sportsFacility.averageRating">{{sportsFacility.averageRating}}</td>
	    			<td v-if="!sportsFacility.averageRating">Nema ocena</td>
	    			<td v-if="sportsFacility">{{sportsFacility.workTime}}</td>
	    			
	    			
	    		</tr>
	    	</table>
    		</div>		  
    	`,
    mounted () {
	this.name = this.$route.params.id;
	if(this.name != ''){
        axios
          .get('rest/sportsFacilities/' + this.name)
          .then(response => (this.sportsFacility = response.data))
          }
          else{
	alert("Nesto ne radi")
}
    }
    	
    
});