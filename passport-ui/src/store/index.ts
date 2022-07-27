import { defineStore, createPinia } from 'pinia'

export const useDefineStore = defineStore('store', {
  state: () => ({ // 单一状态树
  }),
  getters: {
  },
  actions: {
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
