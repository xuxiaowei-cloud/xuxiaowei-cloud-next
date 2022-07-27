<template>
  <h1>homepage1</h1>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useStore } from '../../store'

const router = useRouter()

console.error('HomePage1.vue')

router.isReady().then(() => {
  // @ts-ignore
  const matched = router.currentRoute._value.matched
  for (const i in matched) {
    if (matched[i].path === router.currentRoute.value.path) {
      // 重新进入页面，移除 keep-alive 排除
      useStore.removeKeepAliveExclude(matched[i].components.default.__name)
    }
  }
})
</script>

<style scoped>

</style>
