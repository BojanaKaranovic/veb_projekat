Vue.component("createComment", { 
	data: function () {
	    return {
			text: '',
			grade: 1
			
	    }
	},template: ` 
	<div style="margin-top:10%">
    	<h3>Da li želite da ostavite komentar?</h3>
		<table>
			<tr><td>Komentar:</td><td><input v-model="text" type="text" name="text"></td>
			<tr><td>Ocena:</td><td><input type="range" v-model="grade" name="grade" min="1" max="10" step="1">{{grade}}</td>
			<tr><td align="right" colspan="2"><button  v-on:click="create()">Sacuvaj</button></td></tr>
		</table>
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