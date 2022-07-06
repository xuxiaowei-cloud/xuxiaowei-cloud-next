<template>
  <el-container>
    <el-form :model="param" ref="cloudFormRef" label-position="left" label-width="200px" id="cloud-el-form">
      <el-form-item label="id" v-if="props.edit">
        <el-input v-model="param.id" disabled/>
      </el-form-item>
      <el-form-item label="clientId" prop="clientId" :rules="[{ required: true, message: 'clientId is required' }]">
        <el-input v-if="props.edit" v-model="param.clientId" disabled/>
        <el-input v-else v-model="param.clientId"/>
      </el-form-item>
      <el-form-item label="clientName" prop="clientName" :rules="[{ required: true, message: 'clientName is required' }]">
        <el-input v-model="param.clientName"/>
      </el-form-item>
      <el-form-item label="clientSecret">
        <el-input class="cloud-el-password" v-model="param.clientSecret"/>
        <el-button class="cloud-el-password-generate" @click="passwordGenerate">生成随机凭证</el-button>
      </el-form-item>
      <el-form-item label="clientIdIssuedAt">
        <el-date-picker v-model="param.clientIdIssuedAt" type="datetime" placeholder="Pick a day"
                        value-format="YYYY-MM-DD HH:mm:ss" format="YYYY-MM-DD HH:mm:ss"/>
      </el-form-item>
      <el-form-item label="clientSecretExpiresAt">
        <el-date-picker v-model="param.clientSecretExpiresAt" type="datetime" placeholder="Pick a day"
                        value-format="YYYY-MM-DD HH:mm:ss" format="YYYY-MM-DD HH:mm:ss"/>
      </el-form-item>
      <el-form-item label="clientAuthenticationMethods" prop="clientAuthenticationMethods"
                    :rules="[{ required: true, message: 'clientAuthenticationMethods is required' }]">
        <el-select v-model="param.authenticationMethods" multiple placeholder="Select authenticationMethods" style="width: 100%">
          <el-option v-for="item in authenticationMethodList" :key="item.value" :label="item.label" :value="item.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="authorizationGrantTypes" prop="authorizationGrantTypes"
                    :rules="[{ required: true, message: 'authorizationGrantTypes is required' }]">
        <el-select v-model="param.grantTypes" multiple placeholder="Select authorizationGrantTypes" style="width: 100%">
          <el-option v-for="item in grantTypeList" :key="item.value" :label="item.label" :value="item.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="redirectUris">
        <el-input v-model="param.redirectUris"/>
      </el-form-item>
      <el-form-item label="scopes" prop="scopes" :rules="[{ required: true, message: 'scopes is required' }]">
        <el-select v-model="param.scopeList" multiple placeholder="Select scopes" style="width: 100%">
          <el-option v-for="item in scopeList" :key="item.value" :label="item.label" :value="item.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="requireProofKey">
        <el-switch v-model="param.requireProofKey"/>
      </el-form-item>
      <el-form-item label="requireAuthorizationConsent">
        <el-switch v-model="param.requireAuthorizationConsent"/>
      </el-form-item>
      <el-form-item label="jwkSetUrl" prop="jwkSetUrl" :rules="[{ required: true, message: 'jwkSetUrl is required' }]">
        <el-input v-model="param.jwkSetUrl"/>
      </el-form-item>
      <el-form-item label="tokenSigningAlgorithm" prop="tokenSigningAlgorithm"
                    :rules="[{ required: true, message: 'tokenSigningAlgorithm is required' }]">
        <el-select v-model="param.tokenSigningAlgorithm" placeholder="Select tokenSigningAlgorithm" style="width: 100%">
          <el-option v-for="item in tokenSigningAlgorithmList" :key="item.value" :label="item.label" :value="item.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="reuseRefreshTokens">
        <el-switch v-model="param.reuseRefreshTokens"/>
      </el-form-item>
      <el-form-item label="tokenSignatureAlgorithm" prop="tokenSignatureAlgorithm"
                    :rules="[{ required: true, message: 'tokenSignatureAlgorithm is required' }]">
        <el-select v-model="param.tokenSignatureAlgorithm" placeholder="Select tokenSignatureAlgorithm" style="width: 100%">
          <el-option v-for="item in tokenSignatureAlgorithmList" :key="item.value" :label="item.label" :value="item.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="accessTokenTimeToLive" prop="accessTokenTimeToLive"
                    :rules="[{ required: true, message: 'accessTokenTimeToLive is required' }]">
        <el-input v-model="param.accessTokenTimeToLive" type="number"/>
      </el-form-item>
      <el-form-item label="refreshTokenTimeToLive" prop="accessTokenTimeToLive"
                    :rules="[{ required: true, message: 'refreshTokenTimeToLive is required' }]">
        <el-input v-model="param.refreshTokenTimeToLive" type="number"/>
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
import {
  getById,
  save,
  updateById,
  grantTypeOptions,
  authenticationMethodOptions,
  scopeOptions,
  tokenSigningAlgorithmOptions,
  tokenSignatureAlgorithmOptions
} from '../../../api/passport/oauth2-registered-client'
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

// 可选内容
interface Option {
  label: string
  value: string
}

// 授权类型
const grantTypeData: Option[] = []
const grantTypeList = reactive(grantTypeData)
grantTypeOptions().then(response => {
  if (response.code === store.state.settings.okCode) {
    const data = response.data
    for (const i in data) {
      grantTypeList.push({
        label: data[i].label,
        value: data[i].value
      })
    }
  }
})

// 客户端身份验证方法
const authenticationMethodData: Option[] = []
const authenticationMethodList = reactive(authenticationMethodData)
authenticationMethodOptions().then(response => {
  if (response.code === store.state.settings.okCode) {
    const data = response.data
    for (const i in data) {
      authenticationMethodList.push({
        label: data[i].label,
        value: data[i].value
      })
    }
  }
})

// 授权范围
const scopeData: Option[] = []
const scopeList = reactive(scopeData)
scopeOptions().then(response => {
  if (response.code === store.state.settings.okCode) {
    const data = response.data
    for (const i in data) {
      scopeList.push({
        label: data[i].label,
        value: data[i].value
      })
    }
  }
})

// 令牌端点认证签名算法选项
const tokenSigningAlgorithmData: Option[] = []
const tokenSigningAlgorithmList = reactive(tokenSigningAlgorithmData)
tokenSigningAlgorithmOptions().then(response => {
  if (response.code === store.state.settings.okCode) {
    const data = response.data
    for (const i in data) {
      tokenSigningAlgorithmList.push({
        label: data[i].label,
        value: data[i].value
      })
    }
  }
})

// id 令牌签名算法选项
const tokenSignatureAlgorithmData: Option[] = []
const tokenSignatureAlgorithmList = reactive(tokenSignatureAlgorithmData)
tokenSignatureAlgorithmOptions().then(response => {
  if (response.code === store.state.settings.okCode) {
    const data = response.data
    for (const i in data) {
      tokenSignatureAlgorithmList.push({
        label: data[i].label,
        value: data[i].value
      })
    }
  }
})

// 参数
const param = reactive({
  id: null,
  clientId: null,
  clientName: null,
  clientSecret: null,
  clientIdIssuedAt: null,
  clientSecretExpiresAt: null,
  clientAuthenticationMethods: null,
  authenticationMethods: [],
  authorizationGrantTypes: null,
  grantTypes: [],
  redirectUris: null,
  scopes: null,
  scopeList: [],
  clientSettings: null,
  tokenSettings: null,
  requireProofKey: null,
  requireAuthorizationConsent: null,
  jwkSetUrl: null,
  tokenSigningAlgorithm: null,
  reuseRefreshTokens: null,
  tokenSignatureAlgorithm: null,
  accessTokenTimeToLive: null,
  refreshTokenTimeToLive: null,
  // 识别码
  code: null
})

// 公钥
const publicKey = ref(null)

// 获取识别码与公钥
codeRsa().then(response => {
  if (response.code === store.state.settings.okCode) {
    const data = response.data
    if (data) {
      param.code = data.code
      publicKey.value = data.publicKey
    }
  } else {
    ElMessage.error(response.msg)
  }
})

// 初始化数据
const initData = () => {
  if (props.edit && props.id) {
    getById(props.id).then(response => {
      if (response.code === store.state.settings.okCode) {
        const data = response.data
        if (data) {
          for (const name in data) {
            // @ts-ignore
            param[name] = data[name]
          }
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
  paramEncryption.authorizationGrantTypes = param.grantTypes.toString()
  paramEncryption.clientAuthenticationMethods = param.authenticationMethods.toString()
  paramEncryption.scopes = param.scopeList.toString()
  return paramEncryption
}

// 表单验证
const cloudFormRef = ref(null)

// 保存
const cloudSave = () => {
  // @ts-ignore
  cloudFormRef.value.validate((valid: boolean) => {
    if (valid) {
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
  })
}

// 更新
const cloudUpdate = () => {
  // @ts-ignore
  cloudFormRef.value.validate((valid: boolean) => {
    if (valid) {
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
