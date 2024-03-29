const SportsFacilities = { template: '<sportsFacilities></sportsFacilities>' }
const SportsFacilityInfo = { template: '<sportsFacilityInfo></sportsFacilityInfo>' }
const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/', name: 'home', component: SportsFacilities},
		{ path: '/sportsFacilityInfo/:id', component: SportsFacilityInfo}
	  ]
});

var app = new Vue({
	router,
	el: '#sportsFacilities'
});