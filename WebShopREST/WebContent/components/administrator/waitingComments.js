Vue.component("waitingComments", { 
	data: function () {
	    return {
			comments:{}
	    }
	},template: ` 
    	<div style="margin-top:5%;">
    		<title>Komentari</title>
    		<h4>Komentari na cekanju</h4>
    		<table >
			    <thead>
				    <tr>
				    <th>Kupac</th>
				    <th>Sportski objekat</th>
				    <th>Sadrzaj</th>
				    <th>Ocena</th>
				    <th>Status</th>
				    <th>Odobri</th>
				    <th>Odbij</th>
			    </tr>
			    </thead>
			    <tbody>
			    	<tr v-for="c in comments"">
						<td>{{c.customer}}</td>
						<td>{{c.sportsFacility}}</td>
						<td>{{c.text}}</td>
						<td>{{c.grade}}</td>
						<td>{{c.status}}</td>
						<td><button v-on:click="allow(c)">Odobri</button></td>
						<td><button v-on:click="decline(c)">Odbij</button></td>
					</tr>
				</tbody>
			</table>
			<br/>
			
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