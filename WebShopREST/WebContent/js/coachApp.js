const Profile = { template: '<viewProfile></viewProfile>' }
const SportsFacilities = {template: '<sportsFacilities></sportsFacilities>'}
const SportsFacilityInfo = { template: '<sportsFacilityInfo></sportsFacilityInfo>' }
const Trainings = {template: '<trainings></trainings>'}
const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/', name: 'home', component: SportsFacilities},
		{ path: '/profile', component: Profile},
		{ path: '/sportsFacilityInfo/:id', component: SportsFacilityInfo},
		{ path: '/trainings', component: Trainings}
		
		]
});

var app = new Vue({
	router,
	el: '#Coach'
});