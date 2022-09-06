Vue.component("sportsFacilityInfo", { 
	data: function () {
	    return {
	      sportsFacility: null,
	      name:'',
	      comments:{},
	      trainings: {},
	    }
	}
	,
	    template: ` 
    	<div>
    		<h3>Informacije o sportskom objektu</h3>
    		
			<table  class="table table-borderless table-hover sortable" >
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
		    			<td v-if="sportsFacility && sportsFacility.status">Radi</td>
		    			<td v-if="sportsFacility && !sportsFacility.status">Ne radi</td>
		    			<td v-if="sportsFacility">{{sportsFacility.location.address.street}} {{sportsFacility.location.address.number}}, {{sportsFacility.location.address.city}}, {{sportsFacility.location.address.zipCode}}</td>
		    			<td v-if="sportsFacility"><img v-bind:src=" 'images/'+sportsFacility.logo " /> </td>
		    			<td v-if="sportsFacility && sportsFacility.averageRating">{{sportsFacility.averageRating}}</td>
		    			<td v-if="sportsFacility && !sportsFacility.averageRating">Nema ocena</td>
		    			<td v-if="sportsFacility">{{sportsFacility.workTime}}</td>
		    			
		    			
		    		</tr>
		    	</tbody>
	    	</table>
	    	<h3>Komentari o sportskom objektu</h3>
	    	<table  class="table table-borderless table-hover " >
			<thead style="background-color:#426166; color:#FFFFFF;">
	    		<tr >
	    			<th>Kupac</th>
				    <th>Sportski objekat</th>
				    <th>Sadrzaj</th>
				    <th>Ocena</th>
				    <th>Status</th>
	    		</tr>
	    		</thead>	
	    		<tbody>
		    		<tr v-for="c in comments"">
						<td>{{c.customer}}</td>
						<td>{{c.sportsFacility}}</td>
						<td>{{c.text}}</td>
						<td>{{c.grade}}</td>
						<td>{{c.status}}</td>
					</tr>
		    	</tbody>
	    	</table>
	    	<br>
	    	<h3>Treninzi u sportskom objektu</h3>
		    	<div class="row row-cols-2 row-cols-md-4"  >
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
						  	<li class="list-group-item">{{training.price}}</li>
						  </ul>
					</div>
				
				</div> 
    		</div>		
    	`,
    mounted () {
	this.name = this.$route.params.id;
	if(this.name != ''){
        axios
          .get('rest/sportsFacilities/' + this.name)
          .then(response => {this.sportsFacility = response.data
          						axios.get('rest/comments/allowedCommentsForFacility/' + this.sportsFacility.name)
							  .then(response => {this.comments = response.data
							   axios.get('rest/sportsFacilities/trainingsForSportsFacilityAll/' + this.sportsFacility.name)
							  .then(response => {this.trainings = response.data})})})
          }
          else{
	alert("Nesto ne radi")
}
    }
    	
    
});