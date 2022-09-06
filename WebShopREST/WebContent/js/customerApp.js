const Profile = { template: '<viewProfile></viewProfile>' }
const MembershipFee = {template: '<createMembershipFee></createMembershipFee>'}
const PayMembershipFee = {template: '<payMembershipFee></payMembershipFee>'}
const SportsFacilities = {template: '<sportsFacilities></sportsFacilities>'}
const SportsFacilityInfo = { template: '<sportsFacilityInfo></sportsFacilityInfo>' }
const AddTraining = {template: '<addTraining></addTraining>'}
const CreateComment = { template: '<createComment></createComment>' }
const Trainings = {template: '<trainings></trainings>'}
const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/', name: 'home', component: SportsFacilities},
		{ path: '/profile', component: Profile},
		{ path: '/createMembershipFee', component: MembershipFee},
		{ path: '/payMembershipFee', component: PayMembershipFee},
		{ path: '/sportsFacilityInfo/:id', component: SportsFacilityInfo},
		{ path: '/addTraining', component: AddTraining},
		{ path: '/createComment', component: CreateComment},
		{ path: '/trainings', component: Trainings}
		]
});

var app = new Vue({
	router,
	el: '#Customer'
});