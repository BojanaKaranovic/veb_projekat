const SportsFacilities = { template: '<sportsFacilities></sportsFacilities>' }

const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/', name: 'home', component: SportsFacilities}
	  ]
});

var app = new Vue({
	router,
	el: '#sportsFacilities'
});