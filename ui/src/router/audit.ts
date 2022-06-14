const audit = [
  {
    path: '/audit/code',
    name: 'code',
    meta: {
      authority: ['audit_code_read']
    },
    component: () => import('@/views/audit/CodeView.vue')
  },
  {
    path: '/audit/access-token',
    name: 'accessToken',
    meta: {
      authority: ['audit_accessToken_read']
    },
    component: () => import('@/views/audit/AccessTokenView.vue')
  },
  {
    path: '/audit/refresh-token',
    name: 'refreshToken',
    meta: {
      authority: ['audit_refreshToken_read']
    },
    component: () => import('@/views/audit/RefreshTokenView.vue')
  }
]

export default audit
