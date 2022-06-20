<template>
  <el-container>
    <el-form :model="param" label-position="left" label-width="190px" id="cloud-el-form">
      <el-form-item label="id" v-if="props.edit">
        <el-input v-model="param.id" disabled/>
      </el-form-item>
      <el-form-item label="clientId">
        <el-input v-if="props.edit" v-model="param.clientId" disabled/>
        <el-input v-else v-model="param.clientId"/>
      </el-form-item>
      <el-form-item label="clientName">
        <el-input v-model="param.clientName"/>
      </el-form-item>
      <el-form-item label="clientSecret">
        <el-input class="cloud-el-password" v-model="param.clientSecret"/>
        <el-button class="cloud-el-password-generate" @click="passwordGenerate">生成随机凭证</el-button>
      </el-form-item>
      <el-form-item label="clientIdIssuedAt">
        <el-date-picker v-model="param.clientIdIssuedAt" type="datetime" placeholder="Pick a day" value-format="YYYY-MM-DD HH:mm:ss" format="YYYY-MM-DD HH:mm:ss"/>
      </el-form-item>
      <el-form-item label="clientSecretExpiresAt">
        <el-date-picker v-model="param.clientSecretExpiresAt" type="datetime" placeholder="Pick a day" value-format="YYYY-MM-DD HH:mm:ss" format="YYYY-MM-DD HH:mm:ss"/>
      </el-form-item>
      <el-form-item label="clientAuthenticationMethods">
        <el-input v-model="param.clientAuthenticationMethods"/>
      </el-form-item>
      <el-form-item label="authorizationGrantTypes">
        <el-select v-model="authorizationGrantTypes" multiple placeholder="Select authorizationGrantTypes" style="width: 100%">
          <el-option v-for="item in grantTypeOptions" :key="item.value" :label="item.label" :value="item.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="redirectUris">
        <el-input v-model="param.redirectUris"/>
      </el-form-item>
      <el-form-item label="scopes">
        <el-input v-model="param.scopes"/>
      </el-form-item>
      <el-form-item label="clientSettings">
        <el-input v-model="param.clientSettings"/>
      </el-form-item>
      <el-form-item label="tokenSettings">
        <el-input v-model="param.tokenSettings"/>
      </el-form-item>
      <el-form-item>
        <el-button class="cloud-el-button" v-if="props.edit" @click="cloudUpdate">更新</el-button>
        <el-button class="cloud-el-button" v-else @click="cloudSave">保存</el-button>
      </el-form-item>
    </el-form>
  </el-container>
</template>

<script setup lang="ts">
import { defineEmits, defineProps, reactive, ref } from 'vue'
import { getById, save, updateById } from '../../../api/passport/oauth2-registered-client'
import { codeRsa } from '../../../api/user'
import { randomPassword } from '../../../utils/generate'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'
// TS 未能识别，其实不存在问题
// @ts-ignore
import JsEncrypt from 'jsencrypt/bin/jsencrypt.min'

// 缓存
const store = useStore()

const props = defineProps({
  dialogVisible: {
    type: Boolean
  },
  edit: { // 是否编辑
    type: Boolean
  },
  id: { // 编辑客户主键
    type: String
  }
})

// 授权类型
const authorizationGrantTypes = ref([])

// 授权类型：可选内容
const grantTypeOptions = [
  {
    value: 'authorization_code',
    label: 'authorization_code'
  },
  {
    value: 'refresh_token',
    label: 'refresh_token'
  },
  {
    value: 'client_credentials',
    label: 'client_credentials'
  },
  {
    value: 'password',
    label: 'password'
  },
  {
    value: 'implicit',
    label: 'implicit'
  }
]

// 参数
const param = reactive({
  id: null,
  clientId: null,
  clientName: null,
  clientSecret: null,
  clientIdIssuedAt: null,
  clientSecretExpiresAt: null,
  clientAuthenticationMethods: null,
  authorizationGrantTypes: null,
  redirectUris: null,
  scopes: null,
  clientSettings: null,
  tokenSettings: null,
  // 识别码
  code: null
})

// 公钥
const publicKey = ref(null)

// 获取识别码与公钥
codeRsa().then(response => {
  if (response.code === store.state.settings.okCode) {
    if (response.code === store.state.settings.okCode) {
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
  if (props.edit && props.id) {
    getById(props.id).then(response => {
      if (response.code === store.state.settings.okCode) {
        const data = response.data
        if (data) {
          param.id = data.id
          param.clientId = data.clientId
          param.clientName = data.clientName
          param.clientIdIssuedAt = data.clientIdIssuedAt
          param.clientSecretExpiresAt = data.clientSecretExpiresAt
          param.clientAuthenticationMethods = data.clientAuthenticationMethods
          if (data.authorizationGrantTypes) {
            data.authorizationGrantTypes.split(',').forEach(function (e: String) {
              // @ts-ignore
              authorizationGrantTypes.value.push(e)
            })
          }
          param.redirectUris = data.redirectUris
          param.scopes = data.scopes
          param.clientSettings = data.clientSettings
          param.tokenSettings = data.tokenSettings
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
  param.clientSecret = randomPassword()
}

// 数据处理
const encryption = () => {
  const paramEncryption = JSON.parse(JSON.stringify(param))
  JsEncrypt.prototype.setPublicKey(publicKey.value)
  paramEncryption.clientSecret = JsEncrypt.prototype.encrypt(param.clientSecret)
  paramEncryption.authorizationGrantTypes = authorizationGrantTypes.value.toString()
  return paramEncryption
}

// 保存
const cloudSave = () => {
  save(encryption()).then(response => {
    console.log(response)
    if (response.code === store.state.settings.okCode) {
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
}

// 更新
const cloudUpdate = () => {
  updateById(encryption()).then(response => {
    console.log(response)
    if (response.code === store.state.settings.okCode) {
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
