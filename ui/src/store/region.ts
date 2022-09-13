import { ref } from 'vue'
import { createPinia, defineStore } from 'pinia'

export const regionDefineStore = defineStore('region', {
  state: () => ({ // 单一状态树
    type: ref<boolean>(true) // 搜索类型
  }),
  getters: {

  },
  actions: {
    /**
     * 设置 搜索类型
     * @param type 搜索类型
     */
    setType (type: boolean) {
      this.type = type
    }
  }
})

const regionStore = regionDefineStore(createPinia())

// 订阅缓存的修改
regionStore.$subscribe((mutation, state) => {
  // 将缓存的修改放入本地缓存中
  localStorage.setItem(regionStore.$id, JSON.stringify({ ...state }))
})

// 获取历史缓存
const useStoreOld = localStorage.getItem(regionStore.$id)
if (useStoreOld) {
  // 返回已存在的缓存
  regionStore.$state = JSON.parse(useStoreOld)
}

// 注意，在使用时，不用构造方法，直接调用即可
export {
  regionStore
}
