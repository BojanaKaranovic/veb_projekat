const Profile = { template: '<viewProfile></viewProfile>' }
const MembershipFee = {template: '<createMembershipFee></createMembershipFee>'}
const SportsFacilities = {template: '<sportsFacilities></sportsFacilities>'}
const SportsFacilityInfo = { template: '<sportsFacilityInfo></sportsFacilityInfo>' }
const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/', name: 'home', component: SportsFacilities},
		{ path: '/profile', component: Profile},
		{ path: '/createMembershipFee', component: MembershipFee},
		{ path: '/sportsFacilityInfo/:id', component: SportsFacilityInfo}
		
		]
});

var app = new Vue({
	router,
	el: '#Customer'
});