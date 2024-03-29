<template>
  <el-container>

    <!-- 高度自动，原 el-header、el-footer 均为固定高度 -->
    <!-- 上下左右外边距 -->
    <el-header class="cloud-header m-20px">
      <h1>Login</h1>
      <div>登录服务-徐晓伟微服务</div>
      <br v-if="!cross">
      <div v-if="!cross" class="color-red">
        由于跨域、Session共享、登录成功的授权URL配置，请使用{{ crossDomain }}的子域，如：{{ passportDomain }}，否则将无法登录
      </div>
    </el-header>

    <el-main class="cloud-main">

      <el-form class="cloud-form" ref="cloudFormRef" :model="cloudForm">

        <el-form-item label="" prop="username" :rules="[{ required: true, message: '用户名必填' }]">
          <el-input v-model.trim="cloudForm.username" :prefix-icon="User" placeholder="用户名"/>
        </el-form-item>

        <el-form-item label="" prop="password" :rules="[{ required: true, message: '密码必填' }]">
          <el-input v-model.trim="cloudForm.password" :prefix-icon="Key" placeholder="密码" :type="passwordType">
            <template #suffix>
              <el-icon class="el-input__icon">
                <Unlock v-if="passwordType==='password'" @click="passwordTypeClick"/>
                <Lock v-else @click="passwordTypeClick"/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item label="" prop="rememberMe" class="remember-me">
          <el-checkbox-group v-model="cloudForm.rememberMe">
            <el-checkbox name="rememberMe">记住我</el-checkbox>
          </el-checkbox-group>
        </el-form-item>

        <el-form-item>
          <el-button class="submit-cloud-form" @click="submitCloudForm()">登录</el-button>
        </el-form-item>

      </el-form>

    </el-main>

    <!-- 高度自动，原 el-header、el-footer 均为固定高度 -->
    <!-- 上下左右外边距 -->
    <el-footer class="cloud-footer h-auto m-20px">
      <span>&copy;&nbsp;2022</span>&nbsp;
      <a target="_blank" href="http://xuxiaowei.com.cn">徐晓伟工作室</a>&nbsp;
      <a target="_blank" href="http://beian.miit.gov.cn">鲁ICP备19009036号</a>
    </el-footer>

  </el-container>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { User, Key, Lock, Unlock } from '@element-plus/icons-vue'
import { JSEncrypt } from 'jsencrypt'
import { ElMessage } from 'element-plus'
import { useRoute } from 'vue-router'
import { login } from '../api/user'
import settings from '../settings'

// 跨域
const crossDomain = ref<string>('example.next.xuxiaowei.cloud')
// 登录域名
const passportDomain = ref<string>('passport.example.next.xuxiaowei.cloud')
// 跨域配置
const cross = ref<boolean>(location.host.includes(crossDomain.value))

const route = useRoute()

// 表单中的值
const cloudForm = reactive({
  username: '',
  password: '',
  rememberMe: []
})

// 密码输入框类型
const passwordType = ref('password')

// 密码输入框类型切换
const passwordTypeClick = () => {
  passwordType.value = passwordType.value === 'password' ? 'text' : 'password'
}

// 表单验证
const cloudFormRef = ref(null)

// 提交表单
const submitCloudForm = () => {
  // @ts-ignore
  cloudFormRef.value.validate(valid => {
    if (valid) {
      let header = 'header'
      let token = 'token'
      let password = cloudForm.password
      let rememberMeParameter = 'remember-me'
      // @ts-ignore
      if (process.env.NODE_ENV === 'production') {
        // @ts-ignore
        header = document.head.querySelector('[name=_csrf_header][content]').content
        // @ts-ignore
        token = document.head.querySelector('[name=_csrf][content]').content
        // @ts-ignore
        const rsaPublicKeyBase64 = document.head.querySelector('[name=rsa_public_key_base64][content]').content
        // @ts-ignore
        rememberMeParameter = document.head.querySelector('[name=rememberMeParameter][content]').content

        const jsEncrypt = new JSEncrypt()
        jsEncrypt.setPublicKey(rsaPublicKeyBase64)
        const encrypt = jsEncrypt.encrypt(password)
        if (encrypt === false) {
          ElMessage.error('密码加密失败')
          return
        }

        password = encrypt
      }
      const redirectUri = route.query.redirectUri

      // encodeURIComponent()
      const homePage = route.query.homePage
      // @ts-ignore
      login(cloudForm.username, password, cloudForm.rememberMe[0], header, token, rememberMeParameter, redirectUri, homePage).then(response => {
        console.log(response)
        const msg = response.msg

        if (response.code === settings.okCode) {
          ElMessage({
            message: msg,
            // 显示时间，单位为毫秒。设为 0 则不会自动关闭，类型：number，默认值：3000
            duration: 1500,
            type: 'success',
            onClose: () => {
              location.href = response.data.authorizeUri
            }
          })
        } else {
          ElMessage.error(msg)
        }
      }).catch(error => {
        console.error(error)
      })
    }
  })
}

</script>

<style scoped>

.cloud-header,
.cloud-footer {
  /* 高度自动，原 el-header、el-footer 均为固定高度 */
  height: auto;
}

.cloud-form {
  /* form 居中 */
  margin-left: auto;
  margin-right: auto;
}

.cloud-footer {
  /* 版权信息：绝对位置 */
  position: absolute;
  bottom: 30px;
  width: calc(100% - 40px);
  padding: 0;
}

/* xs<768px 响应式栅格数或者栅格属性对象 */
@media only screen and (max-width: 768px) {

  /* xs 主内容无内边距 */
  .cloud-main {
    --el-main-padding: 0;
    margin-left: 0;
    margin-right: 0;
  }

  /* 表达宽度 */
  .cloud-form {
    width: 300px;
  }

  .cloud-form .el-input,
  .remember-me,
  .submit-cloud-form {
    /* xs ：输入框、按钮宽度 */
    width: 280px;
  }

}

/* sm≥768px 响应式栅格数或者栅格属性对象 */
@media only screen and (min-width: 768px) {

  .cloud-form .el-input,
  .remember-me,
  .submit-cloud-form {
    /* 非 xs ：输入框、按钮宽度 */
    width: 335px;
  }

  .cloud-form {
    /* 非 xs ：表单宽度 */
    width: 375px;
  }
}

/* md≥992px 响应式栅格数或者栅格属性对象 */
@media only screen and (min-width: 992px) {

}

/* lg≥1200px 响应式栅格数或者栅格属性对象 */
@media only screen and (min-width: 1200px) {

}

/* xl≥1920px 响应式栅格数或者栅格属性对象 */
@media only screen and (min-width: 1920px) {

}

</style>
