import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'
import { queryToken } from '../store'
import { hasAnyAuthority } from '../utils/authority'
import Layout from '../components/Layout.vue'

import ConsoleView from '../views/home/ConsoleView.vue'

export const routes: Array<RouteRecordRaw> = [
  {
    name: '主页',
    path: '',
    redirect: '/console',
    component: Layout,
    meta: {
      icon: 'House'
    },
    children: [
      {
        path: '/console',
        name: '控制台',
        meta: {
          authority: ['user_info']
        },
        // 首页不能使用 import
        component: ConsoleView
      },
      {
        path: '/home/homepage1',
        name: '主页一',
        meta: {
          keepAlive: true,
          authority: ['user_info']
        },
        component: () => import('@/views/home/HomePage1.vue')
      },
      {
        path: '/home/homepage2',
        name: '主页二',
        meta: {
          authority: ['user_info']
        },
        component: () => import('@/views/home/HomePage2.vue')
      }
    ]
  },
  {
    name: '富文本',
    path: '',
    component: Layout,
    meta: {
      icon: 'Notebook'
    },
    children: [
      {
        path: '/editor/tui-ui-editor',
        name: 'Tui UI Editor',
        meta: {
          authority: ['user_info']
        },
        component: () => import('../views/editor/ToastUiEditorView.vue')
      },
      {
        path: '/editor/wangeditor',
        name: 'WangEditor',
        meta: {
          authority: ['user_info']
        },
        component: () => import('../views/editor/WangEditorView.vue')
      }
    ]
  },
  {
    name: '个人中心',
    path: '',
    component: Layout,
    meta: {
      icon: 'User'
    },
    children: [
      {
        path: '/user/personal',
        name: '个人中心',
        meta: {
          authority: ['user_info']
        },
        component: () => import('@/views/user/PersonalView.vue')
      },
      {
        path: '/user/account',
        name: '账户绑定',
        meta: {
          authority: ['user_info']
        },
        component: () => import('@/views/user/AccountView.vue')
      },
      {
        path: '/user/security',
        name: '安全设置',
        meta: {
          authority: ['user_info']
        },
        component: () => import('@/views/user/SecurityView.vue')
      },
      {
        path: '/user/social',
        name: '社交绑定',
        meta: {
          authority: ['user_info']
        },
        component: () => import('@/views/user/SocialView.vue')
      }
    ]
  },
  {
    name: '系统管理',
    path: '',
    component: Layout,
    meta: {
      icon: 'Setting'
    },
    children: [
      {
        path: '/manage/user',
        name: '用户管理',
        meta: {
          authority: ['manage_user_read']
        },
        component: () => import('@/views/manage/UserView.vue')
      },
      {
        path: '/manage/client',
        name: '客户管理',
        meta: {
          authority: ['manage_client_read']
        },
        component: () => import('@/views/manage/ClientView.vue')
      }
    ]
  },
  {
    name: '审计',
    path: '',
    component: Layout,
    meta: {
      icon: 'Aim'
    },
    children: [
      {
        path: '/audit/authorization',
        name: '授权记录',
        meta: {
          authority: ['audit_authorization_read']
        },
        component: () => import('@/views/audit/Authorization.vue')
      },
      {
        path: '/audit/authorization-consent',
        name: '授权同意书',
        meta: {
          authority: ['audit_authorization_consent_read']
        },
        component: () => import('@/views/audit/AuthorizationConsent.vue')
      }
    ]
  },
  {
    name: '其他',
    path: '',
    component: Layout,
    children: [
      {
        path: '/refresh',
        name: '刷新',
        meta: {
          authority: ['user_info'],
          show: false
        },
        component: () => import('@/views/RefreshView.vue')
      },
      {
        path: '/non-authority',
        name: '无权限',
        meta: {
          authority: ['user_info'],
          show: false
        },
        component: () => import('@/views/NonAuthorityView.vue')
      }
    ]
  },
  {
    name: '关于',
    path: '',
    component: Layout,
    meta: {
      icon: 'Document'
    },
    children: [
      {
        path: '/about',
        name: '关于',
        meta: {
          authority: ['user_info']
        },
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '@/views/AboutView.vue')
      }
    ]
  }
]

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
