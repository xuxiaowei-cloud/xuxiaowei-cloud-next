<template>
  <div id="cloud-el-search">
    <el-input class="cloud-el-input" clearable v-model="param.registeredClientId"
              placeholder="Please input registeredClientId"/>
    <el-input class="cloud-el-input" clearable v-model="param.principalName" placeholder="Please input principalName"/>
    <el-button class="cloud-el-search" @click="cloudSearch">搜索</el-button>
    <el-button class="cloud-el-reset" @click="cloudClearable">重置</el-button>
    <el-button class="cloud-el-remove" @click="cloudRemove" v-if="hasAuthority('audit_authorization_consent_delete')">删除
    </el-button>
  </div>
  <el-container>
    <el-table stripe :data="tableData" v-loading="loading" height="460" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"/>
      <el-table-column prop="registeredClientId" label="registeredClientId" width="180" :show-overflow-tooltip="true"/>
      <el-table-column prop="principalName" label="principalName" width="180" :show-overflow-tooltip="true"/>
      <el-table-column prop="authorities" label="authorities" width="180" :show-overflow-tooltip="true"/>
      <el-table-column fixed="right" label="Operations" width="100" v-if="hasAuthority('audit_authorization_consent_delete')">
        <template #default="scope">
          <el-button size="small" v-if="scope.row.deleted" disabled>Delete</el-button>
          <el-button size="small" v-else @click="deletePrimaryKey(scope.row.registeredClientId, scope.row.principalName)">Delete
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-container>
  <el-container>
    <el-pagination layout="prev, pager, next, total" @current-change="currentChange" :total="param.total"/>
  </el-container>
</template>

<script setup lang="ts">
import { page, removeById, removeByIds, Oauth2AuthorizationConsentPrimaryKey } from '../../api/passport/oauth2-authorization-consent'
import { hasAuthority } from '../../utils/authority'
import { reactive, ref } from 'vue'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'

// 缓存
const store = useStore()

// 表格数据
const tableData = ref([])

// 多选
const multipleSelection = ref<any[]>([])
// 多选主键
let ids : Oauth2AuthorizationConsentPrimaryKey[]

const param = reactive({
  current: 1,
  size: 10,
  total: 0,
  registeredClientId: null,
  principalName: null
})

// 加载
const loading = ref(true)

// 搜索
const cloudSearch = () => {
  loading.value = true
  page(param).then(response => {
    console.log(response)
    if (response.code === store.state.settings.okCode) {
      const data = response.data
      tableData.value = data.records
      param.total = data.total
      loading.value = false
    } else {
      ElMessage.error(response.msg)
    }
  })
}

// 重置（清空搜索条件）
// Q：为何不使用 reset？
// A：因为使用 reset 后，页面不显示了，但是值还在，影响搜索
const cloudClearable = () => {
  param.registeredClientId = null
  param.principalName = null
}

// 批量删除
const cloudRemove = () => {
  if (multipleSelection.value.length === 0) {
    ElMessage({
      message: '请先选择数据',
      // 显示时间，单位为毫秒。设为 0 则不会自动关闭，类型：number，默认值：3000
      duration: 1500,
      type: 'error'
    })
  } else {
    removeByIds(ids).then(response => {
      if (response.code === store.state.settings.okCode) {
        if (response.data) {
          ElMessage({
            message: response.msg,
            // 显示时间，单位为毫秒。设为 0 则不会自动关闭，类型：number，默认值：3000
            duration: 1500,
            type: 'success',
            onClose: () => {
              cloudSearch()
            }
          })
        } else {
          ElMessage({
            message: '删除失败',
            // 显示时间，单位为毫秒。设为 0 则不会自动关闭，类型：number，默认值：3000
            duration: 1500,
            type: 'error',
            onClose: () => {
              cloudSearch()
            }
          })
        }
      } else {
        ElMessage({
          message: response.msg,
          // 显示时间，单位为毫秒。设为 0 则不会自动关闭，类型：number，默认值：3000
          duration: 1500,
          type: 'error',
          onClose: () => {
            cloudSearch()
          }
        })
      }
    })
  }
}

// 初始搜索
cloudSearch()

// 改变时触发
const currentChange = (e: number) => {
  param.current = e
  cloudSearch()
}

// 删除授权Token
const deletePrimaryKey = (registeredClientId:string, principalName:string) => {
  removeById({ registeredClientId, principalName }).then(response => {
    if (response.code === store.state.settings.okCode) {
      if (response.data) {
        ElMessage({
          message: response.msg,
          // 显示时间，单位为毫秒。设为 0 则不会自动关闭，类型：number，默认值：3000
          duration: 1500,
          type: 'success',
          onClose: () => {
            cloudSearch()
          }
        })
      } else {
        ElMessage({
          message: '删除失败',
          // 显示时间，单位为毫秒。设为 0 则不会自动关闭，类型：number，默认值：3000
          duration: 1500,
          type: 'error',
          onClose: () => {
            cloudSearch()
          }
        })
      }
    } else {
      ElMessage({
        message: response.msg,
        // 显示时间，单位为毫秒。设为 0 则不会自动关闭，类型：number，默认值：3000
        duration: 1500,
        type: 'error',
        onClose: () => {
          cloudSearch()
        }
      })
    }
  })
}

// 选择事件
const handleSelectionChange = (val: any[]) => {
  multipleSelection.value = val

  // 清空
  ids = []
  for (const i in val) {
    ids.push({
      registeredClientId: multipleSelection.value[i].registeredClientId,
      principalName: multipleSelection.value[i].principalName
    })
  }
}

</script>

<style scoped>

.cloud-el-expand-input,
.cloud-el-expand-textarea {
  /*max-width: 1100px;*/
}

.cloud-el-input {
  width: 300px;
}

.cloud-el-input,
.cloud-el-search,
.cloud-el-reset,
.cloud-el-remove {
  margin-left: 5px;
  margin-right: 5px;
}

</style>
