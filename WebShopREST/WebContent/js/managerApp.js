const Profile = { template: '<viewProfile></viewProfile>' }
const SportsFacilities = {template: '<sportsFacilities></sportsFacilities>'}
const SportsFacilityInfo = { template: '<sportsFacilityInfo></sportsFacilityInfo>' }
const ManagerSportsFacility = {template: '<managerSportsFacility></managerSportsFacility>'}
const CreateTraining = {template: '<createTraining></createTraining>'}
const Trainings = {template: '<trainings></trainings>'}
const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/', name: 'home', component: SportsFacilities},
		{ path: '/profile', component: Profile},
		{ path: '/sportsFacilityInfo/:id', component: SportsFacilityInfo},
		{ path: '/managerSportsFacility', component: ManagerSportsFacility},
		{ path: '/createTraining', component: CreateTraining},
		{ path: '/trainings', component: Trainings}
		]
});

var app = new Vue({
	router,
	el: '#Manager'
});