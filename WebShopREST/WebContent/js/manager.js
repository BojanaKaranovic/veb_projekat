const Manager = { template: '<manager></manager>' }

const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/', name: 'home', component: Manager}
	  ]
});

var manager = new Vue({
	router,
	el: '#manager'
});

