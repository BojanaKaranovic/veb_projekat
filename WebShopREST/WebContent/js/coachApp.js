const Profile = { template: '<viewProfile></viewProfile>' }

const router = new VueRouter({
	mode: 'hash',
	  routes: [
		
		{ path: '/profile', component: Profile},
		
		
		]
});

var app = new Vue({
	router,
	el: '#Coach'
});