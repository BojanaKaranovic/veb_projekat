Vue.component("managerSportsFacility", { 
	data: function () {
	    return {
	      sportsFacility: null,
	      name:'',
	      customers:{},
	      coaches:{}
	      
	    }
	}
	,
	    template: ` 
    	<div style="margin-top:5%;">
    		<h3>Informacije o sportskom objektu</h3>
    		
			<table  class="table table-borderless table-hover " >
			<thead style="background-color:#426166; color:#FFFFFF;">
	    		<tr >
	    			<th>Naziv</th>
	    			<th>Tip objekta</th>
	    			<th>Status</th>
	    			<th>Lokacija</th>
	    			<th>Logo</th>
	    			<th>Prosecna ocena</th>
	    			<th>Radno vreme</th>
	    		</tr>
	    		</thead>	
	    		<tbody>
		    		<tr>
		    			<td v-if="sportsFacility">{{sportsFacility.name}}</td>
		    			<td v-if="sportsFacility">{{sportsFacility.type}}</td>
		    			<td v-if="sportsFacility.status">Radi</td>
		    			<td v-if="!sportsFacility.status">Ne radi</td>
		    			<td v-if="sportsFacility">{{sportsFacility.location.address.street}} {{sportsFacility.location.address.number}}, {{sportsFacility.location.address.city}}, {{sportsFacility.location.address.zipCode}}</td>
		    			<td v-if="sportsFacility"><img v-bind:src=" 'images/'+sportsFacility.logo " /> </td>
		    			<td v-if="sportsFacility.averageRating">{{sportsFacility.averageRating}}</td>
		    			<td v-if="!sportsFacility.averageRating">Nema ocena</td>
		    			<td v-if="sportsFacility">{{sportsFacility.workTime}}</td>
		    			
		    			
		    		</tr>
		    	</tbody>
	    	</table>
	    	<br/>
	    	<h3>Posetioci sportskog objekta</h3>
    		
		<table class="table table-borderless table-hover " >
				<thead style="background-color:#426166; color:#FFFFFF;">
					<tr>
						<th scope="col">Ime</th>
						<th scope="col">Prezime</th>
						<th scope="col">Username</th>
						<th scope="col">Datum rodjenja</th>
					</tr>
				</thead>
				
				<tbody>
					<tr v-for="c in customers">
						<td>{{c.firstName}}</td>
						<td>{{c.lastName}}</td>
						<td>{{c.username}}</td>
						<td>{{c.dateOfBirth}}</td>
						
						</tr>
				</tbody>
			</table>
	    	<br/>
	    	<h3>Treneri sportskog objekta</h3>
	    	<table class="table table-borderless table-hover " >
				<thead style="background-color:#426166; color:#FFFFFF;">
					<tr>
						<th scope="col">Ime</th>
						<th scope="col">Prezime</th>
						<th scope="col">Username</th>
						<th scope="col">Datum rodjenja</th>
						<th scope="col">Tip</th>
						<th scope="col">Poeni</th>
					</tr>
				</thead>
				
				<tbody>
					<tr v-for="c in coaches">
						<td>{{c.firstName}}</td>
						<td>{{c.lastName}}</td>
						<td>{{c.username}}</td>
						<td>{{c.dateOfBirth}}</td>
						<td>{{c.customerType}}</td>
						<td>{{c.colectedPoints}}</td>
						
						</tr>
				</tbody>
			</table>
	    	<br/>
    		</div>		  
    	`,
    mounted () {
	
        axios
          .get('rest/userLogin/managerSportsFacility')
          .then(response => {this.sportsFacility = response.data,
          						axios.get('rest/userLogin/customersSportsFacility')
							  .then(response => {this.customers = response.data,
							  axios.get('rest/userLogin/coachesSportsFacility')
							  .then(response => {this.coaches = response.data
							  })})})
          

    }
    	
    
});