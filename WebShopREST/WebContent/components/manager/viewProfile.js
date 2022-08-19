Vue.component("viewProfile", {
	data: function () {
		return {
			username: '',
			manager: null,
			previous: null
		}
	}, template:
	`<div style="margin-top:5%">
	<form id="managerProfil">
	<table>
		<tr><td>Ime</td><td><input  v-if="manager" v-model="manager.firstName" type="text" name="firstname"></td></tr>
		<tr><td>Prezime</td><td><input  v-if="manager" v-model="manager.lastName" type="text" name="lastname"></td></tr>
		<tr><td>Username</td><td><input  v-if="manager" v-model="manager.username" type="text" name="username"></td></tr>
		<tr><td>Password</td><td><input  v-if="manager" v-model="manager.password" type="password" name="password"></td></tr>
		<tr><td>Email</td><td><input  v-if="manager" v-model="manager.email" type="text" name="email"></td></tr>
		<tr><td>Datum rodjenja</td><td><input  v-if="manager" v-model="manager.dateOfBirth" type="text" name="dateOfBirth"></td></tr>
		<tr><td>Pol</td><td>
		<select  v-if="manager" v-model="manager.gender">
			<option disabled value="">Izaberite</option>
			<option value="MALE">Male</option>
			<option value="FEMALE">Female</option>
		</select></td></tr>
		
		<tr><td colspan="2"><button v-on:click = "update">Sacuvaj izmene</button><button v-on:click="restore" >Ponisti izmene</button></td></tr>
	</table>
	</form>
	</div>
	`,
		mounted (){
			axios.get('rest/userLogin/loggedInManager').then(response=>{this.manager = response.data;this.previous = this.manager;this.username = response.data.username})
		},
		methods: {
			
		update: function(event){
			event.preventDefault()
			if(!this.manager.firstName || !this.manager.lastName || !this.manager.username || !this.manager.password || !this.manager.email
			 || !this.manager.gender || !this.manager.dateOfBirth ){
				alert("Postoji nepopunjeno polje!")
				return
			}
			axios.put('rest/userLogin/updateManager' + this.username, this.manager)
			.then(response=>{
				location.href = response.data
			})
			.catch(function(){
				alert("Korisnicko ime nije jedinstveno!")
			})
		},
		restore: function(){
			this.admin = this.previous
		}
		}
});