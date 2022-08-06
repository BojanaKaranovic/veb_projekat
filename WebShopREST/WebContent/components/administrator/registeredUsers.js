Vue.component("users", { 
	data: function () {
	    return {
			registeredUsers: {},
			registeredFiltered:[],
			registered: [],
			firstName: "",
			lastName: "",
			username: "",
			sortedFirstName: false,
			sortedLastName: false,
			sortedUsername: false,
			sortedPoints: false,
			userTypes: [],
			customerTypes: [],
			type: '',
			userType: ''
	    }
	},template: ` 
    	<div>
    		<title>Registrovani korisnici</title>
    		<h4>Pretrazi korisnike:</h4>
    		<table>
				<tr><td>Ime</td><td><input v-model="firstName" type="text" name="firstName"></td><td><button  v-on:click="searchFirstName(firstName)">Pretrazi</button></td></tr>
				<tr><td>Prezime</td><td><input v-model="lastName" type="text" name="lastName"></td><td><button  v-on:click="searchLastName(lastName)">Pretrazi</button></td></tr>
				<tr><td>Username</td><td><input v-model="username" type="text" name="username"></td><td><button  v-on:click="searchUsername(username)">Pretrazi</button></td></tr>
				<tr><td colspan="3"><button  v-on:click="cancelSearch()">Ponisti</button></td></tr>
			</table>
			
			<br/>
			<select name="userTypes" id="userTypes"  v-on:change = "userTypeFilter">
	  			<option  disabled value="">Izaberi tip kupca</option>
	    		<option v-for = "t in userTypes">{{t}}</option>
	    		
	  		</select>
	  		<select name="customerTypes" id="customerTypes"  v-on:change = "customerTypeFilter">
  			<option disabled value="">Izaberi ulogu</option>
    		<option v-for = "u in customerTypes">{{u}}</option>
    		
  		</select>
  		<br>
			<table border="1" id="tabelaKorisnika">
				<thead>
					<tr >
						<th v-on:click = "sortFirstName()"><b>Ime</b></th>
						<th v-on:click = "sortLastName()"><b>Prezime</b></th>
						<th v-on:click = "sortUsername()"><b>Username</b></th>
						<th><b>Datum rodjenja</b></th>
						<th><b>Uloga</b></th>
						<th><b>Tip</b></th>
						<th  v-on:click = "sortPoints()"><b>Poeni</b></th>
						<th><b>Obrisi</b></th>
					</tr>
				</thead>
				
				<tbody>
					<tr v-for="k in registeredFiltered">
						<td>{{k.user.firstName}}</td>
						<td>{{k.user.lastName}}</td>
						<td>{{k.user.username}}</td>
						<td>{{k.user.dateOfBirth}}</td>
						<td>{{k.user.userType}}</td>
						<td>{{k.type}}</td>
						<td>{{k.points}}</td>
						<td v-bind:hidden="k.user.userType == 'CUSTOMER' || k.user.userType == 'ADMINISTRATOR'"><button  v-on:click="obrisiKorisnika(k)">Obrisi</button></td>
						<td v-bind:hidden="k.user.userType !== 'CUSTOMER' && k.user.userType !== 'ADMINISTRATOR'"></td>
						</tr>
				</tbody>
			</table>
			<br />
    	</div>
	
    	`,
    		mounted () {
	        	axios
			.get("rest/userLogin/registeredUsers")
			.then((response) => {
				this.registeredUsers = response.data;
		    	for (let i = 0, len = this.registeredUsers.length; i < len; i++) {
					var	type1 = "NONE"
				      	this.registered.push({
					        user: this.registeredUsers[i],
							type: type1,
							points: 0
				      });
			}
			this.registeredFiltered = this.registered;
			axios.get("rest/userLogin/registeredCustomers")
			.then((response1) => {
				for (let i = 0, len = response1.data.length; i < len; i++) {
					var type1 = response1.data[i].customerType.typeName
			      	this.registered.push({
			        user: response1.data[i],
					type: type1,
					points: response1.data[i].points
			      }); 
				}
			})
			
			this.registeredFiltered = this.registered;
		});
		axios.get("rest/enum/customerTypeNames").then((response) => {this.customerTypes = response.data})
		axios.get("rest/enum/userTypes").then((response) => {this.userTypes = response.data})
    },
    	methods: {
		searchFirstName: function(firstName){
			this.registeredFiltered = this.registered.filter(u => u.user.firstName.toLowerCase().includes(firstName.toLowerCase()));
			this.username = '';
			this.lastName = '';
		},
		searchLastName: function(lastName){
			this.registeredFiltered = this.registered.filter(u => u.user.lastName.toLowerCase().includes(lastName.toLowerCase()));
			this.username = '';
			this.firstName = '';
		},
		searchUsername: function(username){
			this.registeredFiltered = this.registered.filter(u => u.user.username.toLowerCase().includes(username.toLowerCase()));
			this.firstName = '';
			this.lastName = '';
		},
		cancelSearch: function(){
			this.registeredFiltered = this.registered;
			this.lastName = '';
			this.firstName = '';
			this.username = '';
		},
		sortFirstName: function(){
			if(this.sortedFirtsName === false)
			{
				this.registeredFiltered = this.registeredFiltered.sort((a,b) => (a.user.firstName > b.user.firstName) ? 1 : ((a.user.firstName < b.user.firstName) ? -1 : 0));
				this.sortedFirstName = true
				this.sortedLastName = false
				this.sortedUsername = false
				this.sortedPoints = false
			}
			else
			{
				this.registeredFiltered.reverse()
				this.sortedFirstName = false
			}
				
		},
		sortLastName: function(){
			if(this.sortedLastName === false)
			{
				this.registeredFiltered = this.registeredFiltered.sort((a,b) => (a.user.lastName > b.user.lastName) ? 1 : ((a.user.lastName < b.user.lastName) ? -1 : 0));
				this.sortedFirstName = false
				this.sortedLastName = true
				this.sortedUsername = false
				this.sortedPoints = false
			}
			else
			{
				this.registeredFiltered.reverse()
				this.sortedLastName = false
			}
				
		},
		sortUsername: function(){
			if(this.sortedUserame === false)
			{
				this.registeredFiltered = this.registeredFiltered.sort((a,b) => (a.user.username > b.user.username) ? 1 : ((a.user.username < b.user.username) ? -1 : 0));
				this.sortedFirstName = false
				this.sortedLastName = false
				this.sortedUsername = true
				this.sortedPoints = false
			}
			else
			{
				this.registeredFiltered.reverse()
				this.sortedUsername = false
			}
				
		},
		sortPoints: function(){
			if(this.sortedPoints === false)
			{
				this.registeredFiltered = this.registeredFiltered.sort((a,b) => (a.points > b.points) ? 1 : ((a.points < b.points) ? -1 : 0));
				this.sortedFirstName = false
				this.sortedLastName = false
				this.sortedUsername = false
				this.sortedPoints = true
			}
			else
			{
				this.registeredFiltered.reverse()
				this.sortedPoints = false
			}
				
		},
		userTypeFilter: function(evt){
			
			var t = evt.target.value;
				this.userType = t;	
			if(this.userType != ''  )
				this.registeredFiltered = this.registered.filter(o => o.user.userType == this.userType);
			else
				this.registeredFiltered = this.registered;
				
			if(this.customerType != '')	
				this.registeredFiltered = this.registeredFiltered.filter(o => o.customerType == this.customerType);	
			
		},
		customerTypeFilter:function(evt){
			
			var t = evt.target.value;
				this.customerType = t;	
			if(this.customerType != ''  )
				this.registeredFiltered = this.registered.filter(o => o.user.customerType == this.customerType);
			else
				this.registeredFiltered = this.registered;
				
			if(this.userType != '')	
				this.registeredFiltered = this.registeredFiltered.filter(o => o.userType == this.userType);	
			
		},
}
});