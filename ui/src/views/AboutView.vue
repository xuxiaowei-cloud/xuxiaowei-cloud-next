<template>
  <div>
    About
  </div>
</template>

<script setup lang="ts">
import { actuator, baidu } from '../api/actuator'
import { getCurrentInstance } from 'vue'

const currentInstance = getCurrentInstance()
if (currentInstance != null) {
  const globalProperties = currentInstance.appContext.config.globalProperties

  globalProperties.$ipv4().then((response: any) => {
    console.log(response)
  })

  actuator().then((response: any) => {
    console.log(response)
  })

  baidu().then((response: any) => {
    console.log(response)
  })
}
</script>

<style scoped>

</style>
