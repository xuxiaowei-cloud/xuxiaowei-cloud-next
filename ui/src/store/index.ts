import { ref } from 'vue'
import { LocationQuery, Router } from 'vue-router'
import { defineStore, createPinia } from 'pinia'
import settings from '../settings'
import { checkToken } from '../api/passport/oauth2'
import { info } from '../api/user'

export const useDefineStore = defineStore('store', {
  state: () => ({ // 单一状态树
    usersId: ref<string>(), // 用户主键
    username: ref<string>(), // 用户名
    nickname: ref<string>(), // 昵称
    authorities: ref<string[]>([]), // 权限
    accessToken: ref<string>(), // Token
    checkTokenTime: ref<number>(), // 检查Token时间
    refreshToken: ref<string>(), // 刷新Token
    isCollapse: ref<boolean>(false), // 是否折叠菜单
    keepAliveExclude: ref<string[]>([]) // keep-alive 排除页面（组件）名
  }),
  getters: {
    /**
     * 获取 用户主键
     * @param state 单一状态树
     */
    getUsersId (state) {
      return state.usersId
    },
    /**
     * 获取 用户名
     * @param state 单一状态树
     */
    getUsername (state) {
      return state.username
    },
    /**
     * 获取 昵称
     * @param state 单一状态树
     */
    getNickname (state) {
      return state.nickname
    },
    /**
     * 获取 权限
     * @param state 单一状态树
     */
    getAuthorities (state) {
      return state.authorities
    },
    /**
     * 获取 Token
     * @param state 单一状态树
     */
    getAccessToken (state) {
      return state.accessToken
    },
    /**
     * 获取 检查Token时间
     * @param state 单一状态树
     */
    getCheckTokenTime (state) {
      return state.checkTokenTime === undefined ? 0 : state.checkTokenTime
    },
    /**
     * 获取 刷新Token
     * @param state 单一状态树
     */
    getRefreshToken (state) {
      return state.refreshToken
    },
    /**
     * 是否折叠菜单
     * @param state
     */
    getIsCollapse (state) {
      return state.isCollapse
    },
    /**
     * keep-alive 排除页面（组件）名
     * @param state
     */
    getKeepAliveExclude (state) {
      return state.keepAliveExclude
    }
  },
  actions: {
    /**
     * 设置 用户名
     * @param usersId Token
     */
    setUsersId (usersId:string) {
      this.usersId = usersId
    },
    /**
     * 设置 用户名
     * @param username 用户名
     */
    setUsername (username:string) {
      this.username = username
    },
    /**
     * 设置 昵称
     * @param nickname 用户昵称
     */
    setNickname (nickname:string) {
      this.nickname = nickname
    },
    /**
     * 设置 权限
     * @param authorities 权限
     */
    setAuthorities (authorities:string[]) {
      this.authorities = authorities
    },
    /**
     * 设置 Token
     * @param accessToken 授权Token
     */
    setAccessToken (accessToken:any) {
      this.accessToken = accessToken
    },
    /**
     * 设置 检查Token时间
     * @param checkTokenTime
     */
    setCheckTokenTime (checkTokenTime:number) {
      this.checkTokenTime = checkTokenTime
    },
    /**
     * 设置 刷新Token
     * @param refreshToken 刷新Token
     */
    setRefreshToken (refreshToken:any) {
      this.refreshToken = refreshToken
    },
    /**
     * 设置是否折叠菜单
     * @param isCollapse 是否折叠菜单
     */
    setIsCollapse (isCollapse:boolean) {
      this.isCollapse = isCollapse
    },
    /**
     * 添加 keep-alive 排除页面（组件）名
     * @param keepAliveExclude keep-alive 排除页面（组件）名
     */
    addKeepAliveExclude (keepAliveExclude:string) {
      // @ts-ignore
      this.keepAliveExclude.push(keepAliveExclude)
    },
    /**
     * 移除 keep-alive 排除
     * @param keepAliveExclude
     */
    removeKeepAliveExclude (keepAliveExclude:string) {
      this.keepAliveExclude.splice(this.keepAliveExclude.indexOf(keepAliveExclude), 1)
    }
  }
})

const useStore = useDefineStore(createPinia())

// 订阅缓存的修改
useStore.$subscribe((mutation, state) => {
  // 将缓存的修改放入本地缓存中
  localStorage.setItem(useStore.$id, JSON.stringify({ ...state }))
})

// 获取历史缓存
const useStoreOld = localStorage.getItem(useStore.$id)
if (useStoreOld) {
  // 返回已存在的缓存
  useStore.$state = JSON.parse(useStoreOld)
}

// 注意，在使用时，不用构造方法，直接调用即可
export {
  useStore
}

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

    delete query.store

    delete query.accessToken
    delete query.refreshToken

    console.log('获取到URL中的accessToken', accessToken)
    console.log('获取到URL中的refreshToken', refreshToken)

    useStore.setAccessToken(accessToken)
    useStore.setRefreshToken(refreshToken)

    console.log('已完成store中的accessToken缓存：', useStore.getAccessToken)
    console.log('已完成store中的refreshToken缓存：', useStore.getRefreshToken)

    // 此次检查Token，不受 settings.checkTokenInterval 控制
    checkToken().then(response => {
      console.log('完成store中的Token缓存后检查Token', response)
      useStore.setCheckTokenTime(new Date().getTime())
      info().then(() => {})
    })

    router.push({ path, query }).then(() => {

    })
  } else {
    const checkTokenInterval = settings.checkTokenInterval
    console.log(new Date().getTime() - useStore.getCheckTokenTime)

    if (checkTokenInterval === -1) {
      console.log('路由不检查Token')
    } else if (checkTokenInterval === 0) {
      checkToken().then(response => {
        console.log('检查Token', response)
        useStore.setCheckTokenTime(new Date().getTime())
      })
    } else if (checkTokenInterval > 0 && new Date().getTime() - useStore.getCheckTokenTime > checkTokenInterval) {
      checkToken().then(response => {
        console.log('超过检查Token时间间隔后，检查Token结果', response)
        useStore.setCheckTokenTime(new Date().getTime())
      })
    }
  }
}
