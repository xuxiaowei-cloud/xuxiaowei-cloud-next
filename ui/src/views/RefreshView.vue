<template>
  <el-container>

  </el-container>
</template>

<script setup lang="ts">

import { useRouter } from 'vue-router'

const router = useRouter()

// 回到上一个页面
router.go(-1)

</script>

<style scoped>

</style>
