Vue.component("allComments", { 
	data: function () {
	    return {
			comments:{}
	    }
	},template: ` 
    	<div style="margin-top:5%;">
    		
    		<h4>Komentari</h4>
    		<table  class="table table-borderless table-hover ">
			    <thead style="background-color:#426166; color:#FFFFFF;">
				    <tr>
					    <th scope="col">Kupac</th>
					    <th scope="col">Sportski objekat</th>
					    <th scope="col">Sadrzaj</th>
					    <th scope="col">Ocena</th>
					    <th scope="col">Status</th>
			    	</tr>
			    </thead>
			    <tbody>
			    	<tr v-for="c in comments"">
						<td>{{c.customer}}</td>
						<td>{{c.sportsFacility}}</td>
						<td>{{c.text}}</td>
						<td>{{c.grade}}</td>
						<td v-if="c.status == 'ALLOWED'" style="color:Green;">{{c.status}}</td>
						<td v-if="c.status == 'WAITING'" style="color:Orange;">{{c.status}}</td>
						<td v-if="c.status == 'DECLINED'" style="color:Red;">{{c.status}}</td>
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