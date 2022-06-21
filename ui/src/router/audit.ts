const audit = [
  {
    path: '/audit/authorization',
    name: 'authorization',
    meta: {
      authority: ['audit_authorization_read']
    },
    component: () => import('@/views/audit/Authorization.vue')
  },
  {
    path: '/audit/authorization-consent',
    name: 'authorizationConsent',
    meta: {
      authority: ['audit_authorization_consent_read']
    },
    component: () => import('@/views/audit/AuthorizationConsent.vue')
  }
]

export default audit
