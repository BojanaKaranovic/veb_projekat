const Create = {template: '<create></create>'}

const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/create/:id', component: Create}
		]
});

var app = new Vue({
	router,
	el: '#Administrator'
});