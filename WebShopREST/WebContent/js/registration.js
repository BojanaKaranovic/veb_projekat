const Customer = { template: '<customer></customer>' }

const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/', name: 'home', component: Customer}
	  ]
});

var app = new Vue({
	router,
	el: '#customer'
});