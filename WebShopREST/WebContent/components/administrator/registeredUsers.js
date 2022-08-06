Vue.component("users", { 
	data: function () {
	    return {
			registeredUsers: {},
			registeredFiltered:[],
			registered: [],
			firstName: "",
			lastName: "",
			username: "",
			sortiranoIme: 0,
			sortiranoPrezime: 0,
			sortiranoUsername: 0,
			sortiranoPoeni: 0,
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
			<table border="1" id="tabelaKorisnika">
				<thead>
					<tr >
						<th v-on:click = "sortFirstName()"><b>Ime</b></th>
						<th v-on:click = "sortLastName()"><b>Prezime</b></th>
						<th v-on:click = "sortUsername()"><b>Username</b></th>
						<th><b>Datum rodjenja</b></th>
						<th><b>Uloga</b></th>
						<th><b>Tip</b></th>
						<th><b>Poeni</b></th>
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
}
});