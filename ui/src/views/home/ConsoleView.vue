<template>
  <h1>Console</h1>

  <el-button @click="connect">连接</el-button>
  <el-button @click="disconnect">断开连接</el-button>
  <el-button @click="sendMessage">发送</el-button>

</template>

<script setup lang="ts">
// TS 打包不处理
// @ts-ignore
import SockJS from 'sockjs-client/dist/sockjs.min'
// TS 打包不处理
// @ts-ignore
import Stomp from 'stompjs'

import store from '../../store'

let stompClient: any = null

const connect = () => {
  const socket = new SockJS(import.meta.env.VITE_APP_BASE_API + '/websocket/broadcast?access_token=' + store.getters.accessToken)
  stompClient = Stomp.over(socket)

  stompClient.debug = false

  stompClient.connect({
    authorization: 'Bearer ' + store.getters.accessToken
  }, function (frame: any) {
    console.log('Stomp SockJS 已连接')

    stompClient.subscribe('/topic/broadcast', function (message: { body: string }) {
      const body = JSON.parse(message.body)
      if (body.online === true) {
        console.log('上线通知', body)
      } else if (body.online === false) {
        console.log('下线通知', body)
      } else {
        console.log('消息', body)
      }
    }, function (err: any) { // 连接异常
      console.log('连接异常：', err)
    })
  })
}

const disconnect = () => {
  if (stompClient === null) {
    console.log('Stomp SockJS 未连接')
  } else {
    stompClient.disconnect()
    stompClient = null
    console.log('Stomp SockJS 已断开连接')
  }
}

const sendMessage = () => {
  if (stompClient === null) {
    console.log('stompClient 为空')
    return
  }

  stompClient.send('/app/welcome', {
    authorization: 'Bearer ' + store.getters.accessToken
  }, JSON.stringify({
    msg: '你好' + new Date().getTime()
  }))

  console.log('Stomp SockJS 已发送 - ' + new Date().toLocaleTimeString())
}

</script>

<style scoped>

</style>
