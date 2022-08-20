const Profile = { template: '<viewProfile></viewProfile>' }
const SportsFacilities = {template: '<sportsFacilities></sportsFacilities>'}
const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/', name: 'home', component: SportsFacilities},
		{ path: '/profile', component: Profile}
		
		
		]
});

var app = new Vue({
	router,
	el: '#Manager'
});