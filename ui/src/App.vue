<script setup lang="ts">
// This starter template is using Vue 3 <script setup> SFCs
// Check out https://v3.vuejs.org/api/sfc-script-setup.html#sfc-script-setup
import Layout from '@/components/Layout.vue'
import { onMounted } from 'vue'
import { checkToken } from './api/passport/oauth2'
import { info } from './api/user'
import store from './store'

onMounted(() => {
  setTimeout(function () {
    // 首次进入系统/刷新页面 时，进行检查 Token
    checkToken().then(response => {
      console.log('完成store中的Token缓存后检查Token', response)
      store.commit('setCheckTokenTime', new Date().getTime())
      info().then(() => {})
    })
  }, 1000)
})

</script>

<template>
  <Layout />
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
</style>
