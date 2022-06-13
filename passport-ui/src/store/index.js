import { createStore } from 'vuex'
import createPersistedState from 'vuex-persistedstate'
import settings from '@/settings'

export default createStore({
  state: { // 单一状态树
  },
  getters: {
  },
  mutations: { // 更改 Vuex 的 store 中的状态的唯一方法是提交 mutation
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
