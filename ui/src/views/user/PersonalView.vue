<template>
  <el-header class="cloud-el-header"></el-header>
  <el-container v-loading="loading">
    <el-form :model="cloudForm" v-if="!loading" label-width="120px">
      <el-form-item label="用户名">{{ cloudForm.username }}</el-form-item>
      <el-form-item label="用户昵称">{{ cloudForm.nickname }}</el-form-item>
      <el-form-item label="性别">{{ cloudForm.sexLabel }}</el-form-item>
      <el-form-item label="出生日期">{{ cloudForm.birthday }}</el-form-item>
      <el-form-item label="地址">{{ getAddress() }}</el-form-item>
      <el-button class="cloud-el-button" @click="update">更新</el-button>
    </el-form>
  </el-container>

  <!-- 个人中心弹窗 -->
  <el-dialog v-if="personalDialogVisible" v-model="personalDialogVisible" title="修改资料" width="40%"
             :before-close="personalDialogHandleClose">
    <PersonalDialog :dialogVisible="personalDialogVisible" @dialogVisibleClose="personalDialogVisibleClose"
                    :personalCode="dialogVisiblePersonalCode"/>
  </el-dialog>

</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { info, UsersVo } from '../../api/user'
import { AjaxResponse } from '../../utils/request'
// 个人中心编辑弹窗内容
import PersonalDialog from './dialog/PersonalDialog.vue'

const cloudForm = reactive({
  username: null,
  nickname: null,
  sex: null,
  sexLabel: null,
  birthday: null,

  provinceCode: null,
  provinceName: null,
  cityCode: null,
  cityName: null,
  countyCode: null,
  countyName: null,
  townCode: null,
  townName: null,
  villageCode: null,
  villageName: null,

  detailAddress: null
})

// 加载
const loading = ref(true)

const initData = () => {
  info().then((response: AjaxResponse<UsersVo>) => {
    loading.value = false
    const usersVo = response?.data
    for (const name in cloudForm) {
      // @ts-ignore
      cloudForm[name] = usersVo[name]
    }
  })
}

initData()

const getAddress = () => {
  let address = ''
  if (cloudForm.provinceName) {
    address += cloudForm.provinceName + ' '
  }
  if (cloudForm.cityName) {
    address += cloudForm.cityName + ' '
  }
  if (cloudForm.countyName) {
    address += cloudForm.countyName + ' '
  }
  if (cloudForm.townName) {
    address += cloudForm.townName + ' '
  }
  if (cloudForm.villageName) {
    address += cloudForm.villageName + ' '
  }
  if (cloudForm.detailAddress) {
    address += cloudForm.detailAddress
  }
  return address
}

// 个人中心弹窗中的个人中心代码（修改时使用）
const dialogVisiblePersonalCode = ref<string>()

// 个人中心弹窗：是否打开
const personalDialogVisible = ref(false)

// 更新个人中心
const update = () => {
  // 个人中心弹窗类型：添加
  // 个人中心弹窗：打开
  personalDialogVisible.value = true
}

// 个人中心弹窗关闭：弹窗右上角的 x
const personalDialogHandleClose = (done: () => void) => {
  console.log('关闭个人中心弹窗')
  done()
  initData()
}

// 个人中心弹窗关闭：子窗口使用
const personalDialogVisibleClose = () => {
  // 个人中心弹窗：打开
  personalDialogVisible.value = false
  // 关闭窗口后，重新搜索
  initData()
}

</script>

<style scoped>

.cloud-el-header {
  --el-header-height: 30px;
}

.cloud-el-button {
  width: 100%;
}

</style>
