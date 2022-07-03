<template>
  <div style="text-align: center" id="cloud-transfer">
    <!-- :left-default-checked="[2, 3]" :right-default-checked="[1]" -->
    <el-transfer v-model="rightValue" style="text-align: left; display: inline-block" filterable
                 :titles="['系统权限', '用户权限']"
                 :button-texts="['删除', '添加']" :data="data" @change="handleChange"
                 :format="{ noChecked: '${total}', hasChecked: '${checked}/${total}',}">
      <template #default="{ option }">
        <span>{{ option.key }} - {{ option.label }}</span>
      </template>
      <template #left-footer>
        <el-button class="transfer-footer" size="small">Operation</el-button>
      </template>
      <template #right-footer>
        <el-button class="transfer-footer" size="small">Operation</el-button>
      </template>
    </el-transfer>
  </div>

  <el-button class="cloud-el-button-UserAuthorityDialog" @click="cloudSave">保存</el-button>
</template>

<script setup lang="ts">
import { authorityList, getById, saveAuthorities } from '../../../api/user'

import { defineEmits, defineProps, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useStore } from 'vuex'

// 缓存
const store = useStore()

const props = defineProps({
  dialogVisible: {
    type: Boolean
  },
  usersId: { // 编辑用户权限主键
    type: Number
  }
})

// 权限数据
interface Option {
  key: string
  label: string
  disabled: boolean
}
const authorityData: Option[] = []
const grantTypes = reactive(authorityData)
authorityList().then(response => {
  if (response.code === store.state.settings.okCode) {
    const data = response.data
    if (data) {
      for (const i in data) {
        const team = data[i]
        grantTypes.push({
          key: team.authority,
          label: team.explain,
          disabled: false
        })
      }
    }
  } else {
    ElMessage.error(response.msg)
  }
})

// 左侧显示数据
const data = ref(authorityData)

// 用户数据
const user : string[] = []
const userData = reactive(user)

const username = ref(null)

// 初始化用户数据
if (props.usersId) {
  getById(props.usersId).then(response => {
    if (response.code === store.state.settings.okCode) {
      const data = response.data
      if (data) {
        username.value = data.username
        const authorityList = data.authorityList
        for (const i in authorityList) {
          const team = authorityList[i]
          userData.push(team.authority)
        }
      }
    } else {
      ElMessage.error(response.msg)
    }
  })
}

// 右侧用户数据
const rightValue = ref(userData)

const emit = defineEmits(['dialogVisibleClose'])

// 保存
const cloudSave = () => {
  saveAuthorities({
    username: username.value,
    authorityList: rightValue.value
  }).then(response => {
    if (response.code === store.state.settings.okCode) {
      const data = response.data
      if (data) {
        ElMessage({
          message: response.msg,
          // 显示时间，单位为毫秒。设为 0 则不会自动关闭，类型：number，默认值：3000
          duration: 1500,
          type: 'success',
          onClose: () => {
            emit('dialogVisibleClose')
          }
        })
      } else {
        ElMessage.error(response.msg)
      }
    } else {
      ElMessage.error(response.msg)
    }
  })
}

const handleChange = (value: number | string, direction: 'left' | 'right', movedKeys: string[] | number[]) => {
  console.log(value, direction, movedKeys)
}

</script>

<style scoped>

</style>

<style>

/* 左右穿梭框宽度 */
/* 该穿梭框宽度不能在 scoped 中设置 */
#cloud-transfer .el-transfer-panel {
  width: 420px !important;
}

.cloud-el-button-UserAuthorityDialog {
  margin-left: calc(50% - 30px);
}

</style>
