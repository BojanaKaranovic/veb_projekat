const Create = {template: '<create></create>'}
const CreateSportsFacility = {template: '<createSportsFacility></createSportsFacility>'}
const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/create/:id', component: Create},
		{ path: '/createSportsFacility', component: CreateSportsFacility},
		]
});

var app = new Vue({
	router,
	el: '#Administrator'
});