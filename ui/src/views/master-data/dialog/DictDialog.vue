<template>
  <el-container>
    <el-form :model="param" ref="cloudFormRef" label-position="left" label-width="210px" id="cloud-el-form">

      <el-form-item label="dictCode" v-if="props.edit">
        <el-input v-model="param.dictCode" disabled/>
      </el-form-item>
      <el-form-item label="dictCode" prop="dictCode" v-else :rules="[{ required: true, message: 'dictCode is required' }]">
        <el-input v-model="param.dictCode"/>
      </el-form-item>

      <el-form-item label="dictExplain" prop="dictExplain"
                    :rules="[{ required: true, message: 'dictExplain is required' }]">
        <el-input v-model="param.dictExplain"/>
      </el-form-item>

      <el-form-item label="redisExpire" prop="redisExpire"
                    :rules="[{ required: true, message: 'redisExpire is required' }]">
        <el-input type="number" v-model="param.redisExpire"/>
      </el-form-item>

      <el-form-item label="gb" prop="gb">
        <el-input v-model="param.gb"/>
      </el-form-item>

      <el-form-item label="gbUrl" prop="gbUrl">
        <el-input v-model="param.gbUrl"/>
      </el-form-item>

      <el-form-item label="remark" prop="remark">
        <el-input v-model="param.remark"/>
      </el-form-item>

      <el-form-item>
        <el-button class="cloud-el-button" v-if="props.edit" @click="cloudUpdate">更新</el-button>
        <el-button class="cloud-el-button" v-else @click="cloudSave">保存</el-button>
      </el-form-item>

    </el-form>
  </el-container>
</template>

<script setup lang="ts">
import { ElMessage, ElMessageBox } from 'element-plus'
import { getById, save, updateById } from '../../../api/master-data/dict'
import settings from '../../../settings'
import { reactive, ref } from 'vue'

const props = defineProps({
  dialogVisible: {
    type: Boolean
  },
  edit: { // 是否编辑
    type: Boolean
  },
  dictCode: { // 编辑字典主键
    type: String
  }
})

// 参数
const param = reactive({
  dictCode: null,
  dictExplain: null,
  redisExpire: null,
  gb: null,
  gbUrl: null,
  remark: null
})

// 初始化数据
const initData = () => {
  if (props.edit && props.dictCode) {
    getById(props.dictCode).then(response => {
      if (response.code === settings.okCode) {
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

initData()

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
        save(param).then(response => {
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
  // @ts-ignore
  cloudFormRef.value.validate((valid: boolean) => {
    if (valid) {
      ElMessageBox.confirm('确认更新？', '警告', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        updateById(param).then(response => {
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

</style>
