import { createRouter, createWebHashHistory } from 'vue-router'
import { queryToken } from '../store'
import { hasAnyAuthority } from '../utils/authority'
import home from './home'
import audit from './audit'
import editor from './editor'
import user from './user'
import manage from './manage'

import ConsoleView from '../views/home/ConsoleView.vue'

let routes = [
  {
    path: '/',
    name: 'console',
    meta: {
      authority: ['user_info']
    },
    // 首页不能使用 import
    component: ConsoleView
  },
  {
    path: '/refresh',
    name: 'refresh',
    meta: {
      authority: ['user_info']
    },
    component: () => import('@/views/RefreshView.vue')
  },
  {
    path: '/non-authority',
    name: 'non-authority',
    meta: {
      authority: ['user_info']
    },
    component: () => import('@/views/NonAuthorityView.vue')
  },
  {
    path: '/about',
    name: 'about',
    meta: {
      authority: ['user_info']
    },
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '@/views/AboutView.vue')
  }
]

routes = routes.concat(home)
routes = routes.concat(audit)
routes = routes.concat(editor)
routes = routes.concat(user)
routes = routes.concat(manage)

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  console.log(to)
  queryToken(to.path, to.query, router)
  const meta = to.meta
  const authority = meta.authority
  if (to.path === '/non-authority') {
    next()
  } else if (authority instanceof Array && hasAnyAuthority(authority)) { // 判断是否允许访问
    next()
  } else {
    // 不允许访问，跳转到无权限页面
    next('/non-authority')
  }
})

export default router
