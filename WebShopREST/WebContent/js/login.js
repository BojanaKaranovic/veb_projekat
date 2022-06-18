const User = { template: '<user></user>' }

const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/', name: 'home', component: User}
	  ]
});

var login = new Vue({
	router,
	el: '#user'
});