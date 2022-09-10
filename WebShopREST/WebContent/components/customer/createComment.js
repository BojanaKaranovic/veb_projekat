Vue.component("createComment", { 
	data: function () {
	    return {
			text: '',
			grade: 1
			
	    }
	},template: ` 
	<div  style="margin-top:10%; margin-left:35%;">
		<div class="card" style="width:40%">
			<h5 class="card-header text-center" style="background-color:#426166; color:#FFFFFF;">
			    Da li zelite da ostavite komentar?
			 </h5>
		
			<div class="card-body text-center">
				<p>Komentar</p>
				<input class = "form-control" v-model="text" type="text" name="text">
				<br>
				<p>Ocena</p>
				<input type="range" v-model="grade" name="grade" min="1" max="10" step="1"> {{grade}}
				<br><br>
				<button class="btn btn-success" v-on:click="create()">Sacuvaj</button>
			</div>
		</div>
		<a href="customerMainPage.html">Preskoči</a>
	</div>
    	`,
    		
    	methods: {
	
		create: function(){
            axios.post("rest/userLogin/addComment", {"id": "", "customer": "", "sportsFacility": "", "text": this.text, "grade": this.grade, "status": "WAITING"})
			.then(response => {
                location.href=response.data 
            })
        }
}
});