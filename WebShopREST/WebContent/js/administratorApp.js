const Create = {template: '<create></create>'}
const CreateSportsFacility = {template: '<createSportsFacility></createSportsFacility>'}
const Profile = { template: '<viewProfile></viewProfile>' }
const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/create/:id', component: Create},
		{ path: '/createSportsFacility', component: CreateSportsFacility},
		{ path: '/profile', component: Profile}
		]
});

var app = new Vue({
	router,
	el: '#Administrator'
});