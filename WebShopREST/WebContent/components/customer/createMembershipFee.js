Vue.component("createMembershipFee", { 
	data: function () {
	    return {
			type: '',
			package: '',
			validityPeriod: '',
			cost: 0.0,
			entranceCountPerDay: '',
			
	    }
	},template: ` 
	<div style="margin-top:10%">
    	<form id="createMembership">
		<table>
			<tr>
				<td>Tip paketa clanarine</td>
				<td><select @change="change()" v-model="package" name="package">
						<option value="Osnovni">Osnovni</option>
						<option value="Srednji">Srednji</option>
						<option value="Premium">Premium</option>
						</select>
				</td>
			</tr>
			<tr>
				<td>Tip clanarine</td><td>{{type}}</td>
			</tr>
			<tr>
				<td>Vazenje</td><td>{{validityPeriod}}</td>
			</tr>
			<tr>
				<td>Cena</td><td>{{cost}}</td>
			</tr>
			<tr>
				<td>Moguc broj ulazaka u objekte na dnevnom nivou</td><td>{{entranceCountPerDay}}</td>
			</tr>
			<tr><td><button v-on:click = "create">Pregled</button></td></tr>
		</table>
		<p id="error" hidden="true"></p>
		<p id="success" hidden="true"></p>
	</form>
	</div>
    	`,
    		
    	methods: {
	
		create: function(event){
            event.preventDefault()
            var today = new Date();
			var paymentDate = new Date();
			var expirationDateAndTime;
			expirationDateAndTime =today;
			if(this.package == 'Osnovni'){
				expirationDateAndTime.setMonth(expirationDateAndTime.getMonth()+1);
			}
			else if(this.package == 'Srednji'){
				expirationDateAndTime.setMonth(expirationDateAndTime.getMonth()+6);
			}
			else{
				expirationDateAndTime.setFullYear(expirationDateAndTime.getFullYear()+1);
			}
            axios.post('rest/userLogin/createMembershipFee', 
	            {"uniqueId": '', "membershipType": this.type, "paymentDate": new Date(), "validityOfMembership" : expirationDateAndTime ,
	            "cost" : this.cost, "customer" : null, "status": false, "entranceCountPerDay": this.entranceCountPerDay})
	            .then(response => {alert("Created successfully")})
				.catch((e) => { alert("Exception")})
        },
        change: function(){
            if(this.package == 'Osnovni'){
				this.type = 'MESECNA'
				this.validityPeriod = '1 mesec'
				this.entranceCountPerDay = '1'
				this.cost = '2000'
			}
			else if(this.package == 'Srednji'){
				this.type = 'MESECNA'
				this.validityPeriod = '6 meseci'
				this.entranceCountPerDay = '2'
				this.cost = '6000'
			}
			else{
				this.type = 'GODISNJA'
				this.validityPeriod = '12 meseci'
				this.entranceCountPerDay = 'Neograniceno'
				this.cost = '10000'
			}
        }
}
});