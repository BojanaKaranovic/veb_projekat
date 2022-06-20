const Customer = { template: '<customer></customer>' }

const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/', name: 'registration', component: Customer}
	  ]
});

var registration = new Vue({
	router,
	el: '#customer'
});