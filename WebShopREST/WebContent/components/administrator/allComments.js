Vue.component("allComments", { 
	data: function () {
	    return {
			comments:{}
	    }
	},template: ` 
    	<div style="margin-top:5%;">
    		<title>Komentari</title>
    		<h4>Komentari</h4>
    		<table >
			    <thead>
				    <tr>
				    <th>Kupac</th>
				    <th>Sportski objekat</th>
				    <th>Sadrzaj</th>
				    <th>Ocena</th>
				    <th>Status</th>
			    </tr>
			    </thead>
			    <tbody>
			    	<tr v-for="c in comments"">
						<td>{{c.customer}}</td>
						<td>{{c.sportsFacility}}</td>
						<td>{{c.text}}</td>
						<td>{{c.grade}}</td>
						<td>{{c.status}}</td>
					</tr>
				</tbody>
			</table>
			<br/>
			
    	</div>
	
    	`,
    		mounted () {
	        	axios.get("rest/comments/all")
	        	.then((response) =>
	        	{this.comments= response.data})
    },
    	

});