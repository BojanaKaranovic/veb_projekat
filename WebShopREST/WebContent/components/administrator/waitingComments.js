Vue.component("waitingComments", { 
	data: function () {
	    return {
			comments:{}
	    }
	},template: ` 
    	<div style="margin-top:5%;">
    		<title>Komentari</title>
    		<h4>Komentari na cekanju</h4>
    		<br>
    		<table class="table table-borderless table-hover " >
			    <thead  style="background-color:#426166; color:#FFFFFF;">
				    <tr>
					    <th scope="col">Kupac</th>
					    <th scope="col">Sportski objekat</th>
					    <th scope="col">Sadrzaj</th>
					    <th scope="col">Ocena</th>
					    <th scope="col">Status</th>
					    <th scope="col">Odobri</th>
					    <th scope="col">Odbij</th>
				    </tr>
			    </thead>
			    <tbody>
			    	<tr v-for="c in comments"">
						<td>{{c.customer}}</td>
						<td>{{c.sportsFacility}}</td>
						<td>{{c.text}}</td>
						<td>{{c.grade}}</td>
						<td>{{c.status}}</td>
						<td><button class="btn btn-success" v-on:click="allow(c)">Odobri</button></td>
						<td><button class="btn btn-danger" v-on:click="decline(c)">Odbij</button></td>
					</tr>
				</tbody>
			</table>
			<br>
			
    	</div>
	
    	`,
    		mounted () {
	        	axios.get("rest/comments/waitingForApproval")
	        	.then((response) =>
	        	{this.comments= response.data})
    },
    	methods: {
		allow: function(comment){
			axios.post("rest/comments/allowComment",comment)
			.then(response => {alert("Komentar odobren")},  location.reload() )
				.catch((e) => { alert("Exception")})
		},
		decline: function(comment){
			axios.post("rest/comments/declineComment",comment)
			.then(response => {alert("Komentar odbijen")}, location.reload() )
				.catch((e) => { alert("Exception")})
		}
}
});