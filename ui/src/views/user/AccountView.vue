<template>
  <el-tabs v-model="activeName" class="demo-tabs">
    <el-tab-pane label="手机号" name="phone">
      手机号
    </el-tab-pane>
    <el-tab-pane label="邮箱" name="mail">
      邮箱
    </el-tab-pane>
  </el-tabs>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const activeName = ref('phone')

</script>

<style scoped>

</style>
