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
	<div  style="margin-top:10%; margin-left:35%;">
		
    	<form id="createMembership" class="form ">
    		<div class="form-group row">
    			<label for="select" class="col-md-4 col-form-label">Paket</label>
			    <div class="col-sm-2 mb-4">
			    	<select class="form-select"  @change="change()" v-model="package" name="package" id="select">
						<option value="Osnovni">Osnovni</option>
						<option value="Srednji">Srednji</option>
						<option value="Premium">Premium</option>
					</select>
			    </div>
    		</div>
    		<div class="form-group row">
    			<label for="type" class="col-sm-4 col-form-label">Tip clanarine</label>
    			<label id="type" class="col-sm-2 col-form-label">{{type}}</label>
    		</div>
    		<div class="form-group row">
    			<label for="valid" class="col-sm-4 col-form-label">Vazenje</label>
    			<label id="valid" class="col-sm-2 col-form-label">{{validityPeriod}}</label>
    		</div>
    		<div class="form-group row">
    			<label for="cost" class="col-sm-4 col-form-label">Cena</label>
    			<label id="cost" class="col-sm-2 col-form-label">{{cost}}</label>
    		</div>
    		<div class="form-group row">
    			<label for="entranceCountPerDay" class="col-sm-4 col-form-label">Moguc broj ulazaka na dnevnom nivou</label>
    			<label id="entranceCountPerDay" class="col-sm-2 col-form-label">{{entranceCountPerDay}}</label>
    		</div>
    		</br>
    		<button class="btn btn-primary" style="margin-left:20%;" v-on:click = "create">Pregled</button>
		
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