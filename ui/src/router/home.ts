const home = [
  {
    path: '/home/homepage1',
    name: 'homepage1',
    meta: {
      authority: ['user_info']
    },
    component: () => import('@/views/home/homepage1.vue')
  },
  {
    path: '/home/homepage2',
    name: 'homepage2',
    meta: {
      authority: ['user_info']
    },
    component: () => import('@/views/home/homepage2.vue')
  }
]

export default home
