import { createStore } from 'vuex'
import createPersistedState from 'vuex-persistedstate'
import { LocationQuery, Router } from 'vue-router'

import settings from '../settings'

import { checkToken } from '../api/authorization-server'
import { info } from '../api/user'

const store = createStore({
  state: { // 单一状态树
    usersId: null, // 用户主键
    username: null, // 用户名
    nickname: null, // 昵称
    authorities: [], // 权限
    accessToken: null, // Token
    checkTokenTime: null, // 检查Token时间
    refreshToken: null, // 刷新Token
    jti: null, // 票据
    defaultActive: '/', // 获取默认激活菜单
    isCollapse: false // 是否折叠菜单
  },
  getters: {
    /**
     * 获取 用户主键
     * @param state 单一状态树
     */
    usersId (state) {
      return state.usersId
    },
    /**
     * 获取 用户名
     * @param state 单一状态树
     */
    username (state) {
      return state.username
    },
    /**
     * 获取 昵称
     * @param state 单一状态树
     */
    nickname (state) {
      return state.nickname
    },
    /**
     * 获取 权限
     * @param state 单一状态树
     */
    authorities (state) {
      return state.authorities
    },
    /**
     * 获取 Token
     * @param state 单一状态树
     */
    accessToken (state) {
      return state.accessToken
    },
    /**
     * 获取 检查Token时间
     * @param state 单一状态树
     */
    checkTokenTime (state) {
      return state.checkTokenTime
    },
    /**
     * 获取 刷新Token
     * @param state 单一状态树
     */
    refreshToken (state) {
      return state.refreshToken
    },
    /**
     * 获取 票据
     * @param state 单一状态树
     */
    jti (state) {
      return state.jti
    },
    /**
     * 获取默认激活菜单
     * @param state 单一状态树
     */
    defaultActive (state) {
      return state.defaultActive
    },
    /**
     * 是否折叠菜单
     * @param state
     */
    isCollapse (state) {
      return state.isCollapse
    }
  },
  mutations: { // 更改 Vuex 的 store 中的状态的唯一方法是提交 mutation
    /**
     * 设置 用户名
     * @param state 单一状态树
     * @param usersId Token
     */
    setUsersId (state, usersId) {
      state.usersId = usersId
    },
    /**
     * 设置 用户名
     * @param state 单一状态树
     * @param username 用户名
     */
    setUsername (state, username) {
      state.username = username
    },
    /**
     * 设置 昵称
     * @param state 单一状态树
     * @param nickname 用户昵称
     */
    setNickname (state, nickname) {
      state.nickname = nickname
    },
    /**
     * 设置 权限
     * @param state 单一状态树
     * @param authorities 权限
     */
    setAuthorities (state, authorities) {
      state.authorities = authorities
    },
    /**
     * 设置 Token
     * @param state 单一状态树
     * @param accessToken 授权Token
     */
    setAccessToken (state, accessToken) {
      state.accessToken = accessToken
    },
    /**
     * 设置 检查Token时间
     * @param state
     * @param checkTokenTime
     */
    setCheckTokenTime (state, checkTokenTime) {
      state.checkTokenTime = checkTokenTime
    },
    /**
     * 设置 刷新Token
     * @param state 单一状态树
     * @param refreshToken 刷新Token
     */
    setRefreshToken (state, refreshToken) {
      state.refreshToken = refreshToken
    },
    /**
     * 设置 票据
     * @param state 单一状态树
     * @param jti 票据
     */
    setJti (state, jti) {
      state.jti = jti
    },
    /**
     * 设置默认激活菜单
     * @param state 单一状态树
     * @param defaultActive 默认激活
     */
    setDefaultActive (state, defaultActive) {
      state.defaultActive = defaultActive
    },
    /**
     * 设置是否折叠菜单
     * @param state 单一状态树
     * @param isCollapse 是否折叠菜单
     */
    setIsCollapse (state, isCollapse) {
      state.isCollapse = isCollapse
    }
  },
  actions: {
  },
  modules: {
    settings
  },
  plugins: [
    createPersistedState({
      storage: localStorage
    })
  ]
})

export default store

/**
 * 参数中的Token缓存
 * @param path 路径
 * @param query 参数
 * @param router 路由
 */
export const queryToken = function (path: string, query: LocationQuery, router: Router) {
  if (query.store === 'true') {
    const accessToken = query.accessToken
    const refreshToken = query.refreshToken
    const jti = query.jti

    delete query.store

    delete query.accessToken
    delete query.refreshToken
    delete query.jti

    console.log('获取到URL中的accessToken', accessToken)
    console.log('获取到URL中的refreshToken', refreshToken)
    console.log('获取到URL中的jti', jti)

    store.commit('setAccessToken', accessToken)
    store.commit('setRefreshToken', refreshToken)
    store.commit('setJti', jti)

    console.log('已完成store中的accessToken缓存：', store.getters.accessToken)
    console.log('已完成store中的refreshToken缓存：', store.getters.refreshToken)
    console.log('已完成store中的jti缓存：', store.getters.jti)

    // 此次检查Token，不受 settings.state.checkTokenInterval 控制
    checkToken(store.getters.accessToken).then(response => {
      console.log('完成store中的Token缓存后检查Token', response)
      store.commit('setCheckTokenTime', new Date().getTime())
      info().then(() => {})
    })

    router.push({ path: path, query: query }).then(() => {

    })
  } else {
    const checkTokenInterval = settings.state.checkTokenInterval
    console.log(new Date().getTime() - store.getters.checkTokenTime)

    if (checkTokenInterval === -1) {
      console.log('路由不检查Token')
    } else if (checkTokenInterval === 0) {
      checkToken(store.getters.accessToken).then(response => {
        console.log('检查Token', response)
        store.commit('setCheckTokenTime', new Date().getTime())
      })
    } else if (checkTokenInterval > 0 && new Date().getTime() - store.getters.checkTokenTime > checkTokenInterval) {
      checkToken(store.getters.accessToken).then(response => {
        console.log('超过检查Token时间间隔后，检查Token结果', response)
        store.commit('setCheckTokenTime', new Date().getTime())
      })
    }
  }
}
