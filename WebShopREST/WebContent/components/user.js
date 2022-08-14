Vue.component("user", { 
	data: function () {
	    return {
	      user: null,
	      username: "",
	      password : ""
	    }
	},template: ` 
    	
    	<div class="container" align="center">
    		</br>
    		<div>
    			<img class="mb-4" src="images/logo.png" alt="" width="35%">
    		</div>
	    	<div class="col-md-3" align="left">
		        <label class="form-label" style="color:#FFFFFF;" for="inputUsername">Korisnicko ime</label>
		        <input v-model="username" type="text" class="form-control" id="inputUsername" placeholder="Username">
		    </div>
		    <div class="col-md-3 " align="left">
		        <label class="form-label" style="color:#FFFFFF;" for="inputPassword">Lozinka</label>
		        <input  max-width="330px"  padding="15px" v-model="password" type="password" class="form-control" id="inputPassword" placeholder="Password">
		    </div>
		    </br>
		    <div class="col-md-3" align="center" >
		    	<button v-on:click = "loginUser" type="submit"  class="btn btn-primary">Uloguj se</button>
		    </div>
		    <p id="error" hidden="true"></p>
		<p id="success" hidden="true"></p>
		</div>
		
	
    	`,mounted () {
        /*axios
          .get('rest/login/'+this.username+'''password')
          .then(response => (this.user = response.data))*/
    },
    methods: {
	loginUser : function(event){
		event.preventDefault()
		if(!this.username || !this.password){
			alert("Postoji nepopunjeno polje!")
			return
		}
		axios.get('rest/userLogin/login/'+this.username+'/'+this.password)
		.then(response => {
		location.href=response.data
		})
		.catch(function(){
			alert("Niste uneli dobar username ili password!")
		})

	}
}
});