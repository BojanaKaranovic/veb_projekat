const Profile = { template: '<viewProfile></viewProfile>' }
const MembershipFee = {template: '<createMembershipFee></createMembershipFee>'}
const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/profile', component: Profile},
		{ path: '/createMembershipFee', component: MembershipFee}
		]
});

var app = new Vue({
	router,
	el: '#Customer'
});