Vue.component("payMembershipFee", { 
	data: function () {
	    return {
	      membershipFee:null,
	      discount: 0
	    }
	},template: ` 
	<div  style="margin-top:10%; margin-left:30%;">
		<form class="form">
			<div class="form-group row">
    			<label for="type" class="col-sm-4 col-form-label">Tip clanarine</label>
    			 <div class="col-sm-3">
    				<label  v-if ="membershipFee" id="type" type ="text" class="form-control">{{membershipFee.membershipType}}</label >
    			</div>
    		</div>
			<div class="form-group row">
    			<label for="cost" class="col-sm-4 col-form-label">Cena</label>
    			<div class="col-sm-3">
    				<label  type ="text" v-if ="membershipFee"  id="cost" class="form-control ">{{membershipFee.cost}}</label >
    			</div>
    		</div>
    		<div class="form-group row">
    			<label for="entranceCount" class="col-sm-4 col-form-label">Moguc broj ulazaka</label>
    			<div class="col-sm-3">
    				<label  v-if ="membershipFee" id="entranceCount" class="form-control">{{membershipFee.entranceCountPerDay}}</label>
    			</div>
			</div>    			
    		<div class="form-group row">
    			<label for="discount" class="col-sm-4 col-form-label">Popust</label>
    			<div class="col-sm-3">
	    			<label  id="discount" class="form-control">{{discount}}%</label>
	    		</div>
	    	</div>
	    	<br/>
				<button class="btn btn-primary" style="margin-left:15%; width:200px;" v-on:click = "pay">Plati</button>
		</form>
	</div>
	
    	`,
    	mounted () {
        axios.get('rest/userLogin/getMembership')
        .then(response => this.membershipFee = response.data, 
        axios.get('rest/userLogin/loggedInCustomer')
		.then(response => { this.discount=response.data.customerType.discount}))
    },
    	methods: {
	pay : function(event){
		event.preventDefault()
		
		axios.post('rest/userLogin/payMembershipFee')
		.then(response => {
			location.href=response.data
		})
		.catch(function(){
			alert("Problem")
		})
		
		
	}
	,
	
    
}
});