Vue.component("viewProfile", { 
	data: function () {
	    return {
			username: '',
			
	      admin: null,
	      previous:null
	    }
	},template: ` 
		<div style="margin-top:5%">
    	<form id="adminProfil">
		<table>
			<tr><td>Ime</td><td><input  v-if="admin" v-model="admin.firstName" type="text" name="firstname"></td></tr>
			<tr><td>Prezime</td><td><input  v-if="admin" v-model="admin.lastName" type="text" name="lastname"></td></tr>
			<tr><td>Username</td><td><input  v-if="admin" v-model="admin.username" type="text" name="username"></td></tr>
			<tr><td>Password</td><td><input  v-if="admin" v-model="admin.password" type="password" name="password"></td></tr>
			<tr><td>Email</td><td><input  v-if="admin" v-model="admin.email" type="text" name="email"></td></tr>
			<tr><td>Datum rodjenja</td><td><input  v-if="admin" v-model="admin.dateOfBirth" type="text" name="dateOfBirth"></td></tr>
			<tr><td>Pol</td><td>
			<select  v-if="admin" v-model="admin.gender">
  				<option disabled value="">Izaberite</option>
  				<option value="MALE">Male</option>
  				<option value="FEMALE">Female</option>
			</select></td></tr>
			
			<tr><td colspan="2"><button v-on:click = "update">Sacuvaj izmene</button><button v-on:click="restore" >Ponisti izmene</button></td></tr>
		</table>
		<p id="error" hidden="true"></p>
		<p id="success" hidden="true"></p>
	</form>
	</div>
    	`,
    		mounted () {
	        	axios.get('rest/userLogin/loggedInAdmin').then(response=>{this.admin = response.data;this.previous = this.admin;this.username=response.data.username})
	        	
    },
    	methods: {
	
		update: function(event){
			event.preventDefault()
			if(!this.admin.firstName || !this.admin.lastName || !this.admin.username || !this.admin.password || !this.admin.email
			 || !this.admin.gender || !this.admin.dateOfBirth){
				alert("Postoji nepopunjeno polje!")
				return
			}
			axios.put('rest/userLogin/updateAdmin/' + this.username, this.admin)
			.then(response => {
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