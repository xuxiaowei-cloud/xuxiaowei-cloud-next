const manage = [
  {
    path: '/manage/user',
    name: 'user',
    meta: {
      authority: ['manage_user_read']
    },
    component: () => import('@/views/manage/UserView.vue')
  },
  {
    path: '/manage/client',
    name: 'client',
    meta: {
      authority: ['manage_client_read']
    },
    component: () => import('@/views/manage/ClientView.vue')
  }
]

export default manage
