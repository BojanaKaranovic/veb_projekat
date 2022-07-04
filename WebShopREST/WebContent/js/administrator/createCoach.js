const Coach = { template: '<coach></coach>' }

const routerA = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/', name: 'home', component: Coach}
	  ]
});

var coach = new Vue({
	routerA,
	el: '#coach'
});

