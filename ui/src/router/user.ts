const user = [
  {
    path: '/user/account',
    name: 'account',
    meta: {
      authority: ['user_info']
    },
    component: () => import('@/views/user/AccountView.vue')
  },
  {
    path: '/user/personal',
    name: 'personal',
    meta: {
      authority: ['user_info']
    },
    component: () => import('@/views/user/PersonalView.vue')
  },
  {
    path: '/user/security',
    name: 'security',
    meta: {
      authority: ['user_info']
    },
    component: () => import('@/views/user/SecurityView.vue')
  },
  {
    path: '/user/social',
    name: 'social',
    meta: {
      authority: ['user_info']
    },
    component: () => import('@/views/user/SocialView.vue')
  }
]

export default user
