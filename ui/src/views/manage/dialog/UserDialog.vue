<template>
  <el-container>
    <el-form :model="param" ref="cloudFormRef" label-position="left" label-width="160px" id="cloud-el-form">
      <el-form-item label="usersId" v-if="props.edit">
        <el-input v-model="param.usersId" disabled/>
      </el-form-item>
      <el-form-item label="username" prop="username"
                    :rules="[{ required: true, message: 'username is required' }]">
        <el-input v-if="props.edit" v-model="param.username" disabled/>
        <el-input v-else v-model="param.username"/>
      </el-form-item>
      <el-form-item label="email" prop="email" :rules="[{ required: true, message: 'email is required' }]">
        <el-input v-model="param.email"/>
      </el-form-item>
      <el-form-item label="emailValid">
        <el-switch v-model="param.emailValid"/>
      </el-form-item>
      <el-form-item label="nickname" prop="nickname" :rules="[{ required: true, message: 'nickname is required' }]">
        <el-input v-model="param.nickname"/>
      </el-form-item>
      <el-form-item label="password">
        <el-input class="cloud-el-password" v-model="param.password"/>
        <el-button class="cloud-el-password-generate" @click="passwordGenerate">生成随机密码</el-button>
      </el-form-item>
      <el-form-item label="enabled">
        <el-switch v-model="param.enabled"/>
      </el-form-item>
      <el-form-item label="accountNonExpired">
        <el-switch v-model="param.accountNonExpired"/>
      </el-form-item>
      <el-form-item label="credentialsNonExpired">
        <el-switch v-model="param.credentialsNonExpired"/>
      </el-form-item>
      <el-form-item label="accountNonLocked">
        <el-switch v-model="param.accountNonLocked"/>
      </el-form-item>

      <el-form-item>
        <el-button class="cloud-el-button" v-if="props.edit" @click="cloudUpdate">更新</el-button>
        <el-button class="cloud-el-button" v-else @click="cloudSave">保存</el-button>
      </el-form-item>

    </el-form>
  </el-container>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { getById, save, updateById, codeRsa } from '../../../api/user'
import { randomPassword } from '../../../utils/generate'
import settings from '../../../settings'
import { ElMessage, ElMessageBox } from 'element-plus'
// TS 未能识别，其实不存在问题
// @ts-ignore
import JsEncrypt from 'jsencrypt/bin/jsencrypt.min'

const props = defineProps({
  dialogVisible: {
    type: Boolean
  },
  edit: { // 是否编辑
    type: Boolean
  },
  usersId: { // 编辑用户主键
    type: Number
  }
})

// 参数
const param = reactive({
  usersId: null,
  username: null,
  email: null,
  emailValid: false,
  nickname: null,
  password: null,
  enabled: true,
  accountNonExpired: true,
  credentialsNonExpired: true,
  accountNonLocked: true,
  // 识别码
  code: null
})

// 公钥
const publicKey = ref(null)

// 获取识别码与公钥
codeRsa().then(response => {
  if (response.code === settings.okCode) {
    if (response.code === settings.okCode) {
      const data = response.data
      if (data) {
        param.code = data.code
        publicKey.value = data.publicKey
      }
    } else {
      ElMessage.error(response.msg)
    }
  }
})

// 初始化数据
const initData = () => {
  if (props.edit && props.usersId) {
    getById(props.usersId).then(response => {
      if (response.code === settings.okCode) {
        const data = response.data
        if (data) {
          param.usersId = data.usersId
          param.username = data.username
          param.email = data.email
          param.emailValid = data.emailValid
          param.nickname = data.nickname
          param.enabled = data.enabled
          param.accountNonExpired = data.accountNonExpired
          param.credentialsNonExpired = data.credentialsNonExpired
          param.accountNonLocked = data.accountNonLocked
        }
      } else {
        ElMessage.error(response.msg)
      }
    })
  }
}

const emit = defineEmits(['dialogVisibleClose'])

// 初始化数据
initData()

// 生成随机密码
const passwordGenerate = () => {
  param.password = randomPassword({
    number: 3,
    lowerCase: 1,
    upperCase: 1,
    symbol: 1,
    suppl: 0
  })
}

// 表单验证
const cloudFormRef = ref(null)

// 保存
const cloudSave = () => {
  // @ts-ignore
  cloudFormRef.value.validate((valid: boolean) => {
    if (valid) {
      ElMessageBox.confirm('确认添加？', '警告', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const paramEncryption = JSON.parse(JSON.stringify(param))
        JsEncrypt.prototype.setPublicKey(publicKey.value)
        paramEncryption.password = JsEncrypt.prototype.encrypt(param.password)
        save(paramEncryption).then(response => {
          console.log(response)
          if (response.code === settings.okCode) {
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
        })
      }).catch(() => {

      })
    }
  })
}

// 更新
const cloudUpdate = () => {
  const paramEncryption = JSON.parse(JSON.stringify(param))
  JsEncrypt.prototype.setPublicKey(publicKey.value)
  paramEncryption.password = JsEncrypt.prototype.encrypt(param.password)
  // @ts-ignore
  cloudFormRef.value.validate((valid: boolean) => {
    if (valid) {
      ElMessageBox.confirm('确认更新？', '警告', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        updateById(paramEncryption).then(response => {
          console.log(response)
          if (response.code === settings.okCode) {
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
        })
      }).catch(() => {

      })
    }
  })
}

</script>

<style scoped>

#cloud-el-form,
.cloud-el-button {
  width: 100%;
}

.cloud-el-password {
  width: calc(100% - 116px - 4px);
}

.cloud-el-password-generate {
  margin-left: 4px;
}

</style>
