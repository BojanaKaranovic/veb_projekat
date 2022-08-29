const Profile = { template: '<viewProfile></viewProfile>' }
const SportsFacilities = {template: '<sportsFacilities></sportsFacilities>'}
const SportsFacilityInfo = { template: '<sportsFacilityInfo></sportsFacilityInfo>' }
const ManagerSportsFacility = {template: '<managerSportsFacility></managerSportsFacility>'}
const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/', name: 'home', component: SportsFacilities},
		{ path: '/profile', component: Profile},
		{ path: '/sportsFacilityInfo/:id', component: SportsFacilityInfo},
		{ path: '/managerSportsFacility', component: ManagerSportsFacility}
		
		]
});

var app = new Vue({
	router,
	el: '#Manager'
});