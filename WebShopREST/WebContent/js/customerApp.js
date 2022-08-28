const Profile = { template: '<viewProfile></viewProfile>' }
const MembershipFee = {template: '<createMembershipFee></createMembershipFee>'}
const SportsFacilities = {template: '<sportsFacilities></sportsFacilities>'}
const SportsFacilityInfo = { template: '<sportsFacilityInfo></sportsFacilityInfo>' }
const AddTraining = {template: '<addTraining></addTraining>'}
const CreateComment = { template: '<createComment></createComment>' }
const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/', name: 'home', component: SportsFacilities},
		{ path: '/profile', component: Profile},
		{ path: '/createMembershipFee', component: MembershipFee},
		{ path: '/sportsFacilityInfo/:id', component: SportsFacilityInfo},
		{ path: '/addTraining', component: AddTraining},
		{ path: '/createComment', component: CreateComment}
		]
});

var app = new Vue({
	router,
	el: '#Customer'
});