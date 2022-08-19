Vue.component("addTraining", { 
	data: function () {
	    return {
			sportsFacilities: {},
	        trainings: {},
			trainingsFiltered: {},
			date: '',
			time: ''
	    }
	},template: ` 
	<div>
		<h3>Izaberite objekat za koji zelite da vidite treninge</h3>
		<br/>
		<select name="sportsFacilities" id="sportsFacilities"  v-on:change = "spFilter">
	  			<option>Izaberite objekat</option>
	    		<option v-for = "s in sportsFacilities">{{s.name}}</option>
	    		
  		</select>
  		<h3>Izaberite datum i vreme treninga:</h3>
		<br/>
		<table>
				<tr>
					<td>Datum treninga:</td><td><input style='width:100%' v-model="date" type="date" name="datum" min='2022-07-12'></td>
				</tr>
				<tr>
					<td>Vreme treninga:</td><td><input style='width:100%' v-model="time" type="time" name="vreme"></td>
				</tr>
		</table>
		<br/><br/><br/>
		<table border="1">
		<thead>
			<tr bgcolor="lightgrey">
				<th><b>Slika</b></th>
				<th><b>Naziv treninga</b></th>
				<th><b>Opis</b></th>
				<th><b>Trener</b></th>
				<th><b>Cena</b></th>
				<th><b>Sportski objekat</b></th>
				<th><b>Izaberi</b></th>
			</tr>	
		</thead>
		<tbody>
			<tr v-for="t in trainingsFiltered">
				<td><img v-bind:src=" 'images/' + t.image"></td>
				<td>{{t.name}}</td>
				<td>{{t.description }}</td>
				<td>{{t.coach}} </td>
				<td>{{t.durationInMinutes}}</td>
				<td>{{t.sportFacility}}</td>
				<td><button  v-on:click="addTraining(t)">Izaberi</button></td>
			</tr>
		</tbody>
	</table>
	</div>
    	`,
    		mounted() {
		axios.get('rest/sportsFacilities/')
		.then(response => {this.sportsFacilities = response.data})
		axios.get('rest/sportsFacilities/getAllTrainings')
		.then(response => {
			this.trainings = response.data
			this.trainingsFiltered = response.data
		})
		
	},
    	methods: {
		spFilter: function(evt){
			var t = evt.target.value;
				this.trainingsFiltered = this.trainings.filter(tr => tr.sportFacility == t)
		},
		addTraining: function(t){
			if(!this.date || !this.time){
				alert("Postoji nepopunjeno polje forme za datum ili vreme treninga!")
				return
			}
			var array = this.time.split(":")
			if(Number(array[0])<8 || Number(array[0])>21){
				alert("Neispravan vremenski interval!")
				return
			}
			
			axios.post('rest/userLogin/addTraining/' + this.date +'/' + this.time, {"name": t.name, "type": t.trainingType, "sportFacility": t.sportFacility,
			"durationInMinutes": t.durationInMinutes, "coach": t.coach, "description": t.description, "image": t.image, "deleted" : false})
			.then(response => {
				if(response.data == 1 || response.data == 2)
				alert("Created successfully")})
            .catch(function(){
                alert("Članarina nije validna!")
            })
		}}
});