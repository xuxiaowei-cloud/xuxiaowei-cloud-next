<template>
  <el-container>
    <h1>无权限访问</h1>
  </el-container>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { checkToken } from '../api/passport/oauth2'

const router = useRouter()

router.isReady().then(() => {
  // 进入 无权限访问页面 时，进行检查 Token
  checkToken().then(response => {
    console.log('进入 无权限访问页面 时，进行检查 Token', response)
    if (response && response.active === true) {
      // 如果 Token 有效，跳转到首页
      location.hash = '/'
    }
  })
})

</script>

<style scoped>

</style>
