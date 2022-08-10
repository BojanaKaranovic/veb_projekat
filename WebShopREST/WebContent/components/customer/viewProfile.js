Vue.component("viewProfile", { 
	data: function () {
	    return {
			username: '',
			
	      customer: null,
	      previous:null
	    }
	},template: ` 
    	<form id="customerProfile">
		<table>
			<tr><td>Ime</td><td><input  v-if="customer" v-model="customer.firstName" type="text" name="firstname"></td></tr>
			<tr><td>Prezime</td><td><input  v-if="customer" v-model="customer.lastName" type="text" name="lastname"></td></tr>
			<tr><td>Username</td><td><input  v-if="customer" v-model="customer.username" type="text" name="username"></td></tr>
			<tr><td>Password</td><td><input  v-if="customer" v-model="customer.password" type="password" name="password"></td></tr>
			<tr><td>Email</td><td><input  v-if="customer" v-model="customer.email" type="text" name="email"></td></tr>
			<tr><td>Datum rodjenja</td><td><input  v-if="customer" v-model="customer.dateOfBirth" type="text" name="dateOfBirth"></td></tr>
			<tr><td>Pol</td><td>
			<select  v-if="customer" v-model="customer.gender">
  				<option disabled value="">Izaberite</option>
  				<option value="MALE">Male</option>
  				<option value="FEMALE">Female</option>
			</select></td></tr>
			
			<tr><td colspan="2"><button v-on:click = "update">Sacuvaj izmene</button><button v-on:click="restore" >Ponisti izmene</button></td></tr>
		</table>
		<p id="error" hidden="true"></p>
		<p id="success" hidden="true"></p>
	</form>
	
    	`,
    		mounted () {
	        	axios.get('rest/userLogin/loggedInCustomer').then(response=>{this.customer = response.data;this.previous = this.customer;this.username=response.data.username})
	        	
    },
    	methods: {
	
		update: function(event){
			event.preventDefault()
			if(!this.customer.firstName || !this.customer.lastName || !this.customer.username || !this.customer.password || !this.customer.email
			 || !this.customer.gender || !this.customer.dateOfBirth){
				alert("Postoji nepopunjeno polje!")
				return
			}
			axios.put('rest/userLogin/updateCustomer/' + this.username, this.customer)
			.then(response => {
                location.href = response.data
            })
            .catch(function(){
                alert("Korisnicko ime nije jedinstveno!")
            })
		
		
		
	},
	restore: function(){
			this.customer = this.previous
	}
}
});