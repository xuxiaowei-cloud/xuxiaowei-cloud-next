<template>
  <div id="cloud-el-search">
    <el-input class="cloud-el-input" clearable v-model="param.codeId" placeholder="Please input codeId"/>
    <el-input class="cloud-el-input" clearable v-model="param.code" placeholder="Please input code"/>
    <el-input class="cloud-el-input" clearable v-model="param.username" placeholder="Please input username"/>
    <el-input class="cloud-el-input" clearable v-model="param.clientId" placeholder="Please input clientId"/>
    <el-input class="cloud-el-input" clearable v-model="param.remoteAddress" placeholder="Please input remoteAddress"/>
    <el-button class="cloud-el-search" @click="cloudSearch">搜索</el-button>
    <el-button class="cloud-el-reset" @click="cloudClearable">重置</el-button>
    <el-button class="cloud-el-remove" @click="cloudRemove" v-if="hasAuthority('audit_code_delete')">删除</el-button>
  </div>
  <el-container>
    <el-table stripe :data="tableData" v-loading="loading" height="460" @selection-change="handleSelectionChange">
      <el-table-column type="expand">
        <template #default="props">
          <el-form label-width="120px">
            <el-form-item label="redirectUri">
              <el-input v-model="props.row.redirectUri" class="cloud-el-expand-input" disabled/>
            </el-form-item>
            <el-form-item label="scope">
              <el-input v-model="props.row.scope" class="cloud-el-expand-input" disabled/>
            </el-form-item>

            <el-form-item label="authorities">
              <el-input v-model="props.row.authoritiesJson" class="cloud-el-expand-textarea" type="textarea" disabled
                        rows="5"/>
            </el-form-item>
            <el-form-item label="authentication">
              <el-input v-model="props.row.authenticationJson" class="cloud-el-expand-textarea" type="textarea" disabled
                        rows="10"/>
            </el-form-item>

            <el-form-item label="sessionId">
              <el-input v-model="props.row.sessionId" class="cloud-el-expand-input" disabled/>
            </el-form-item>
            <el-form-item label="state">
              <el-input v-model="props.row.state" class="cloud-el-expand-input" disabled/>
            </el-form-item>
            <el-form-item label="responseType">
              <el-input v-model="props.row.responseType" class="cloud-el-expand-input" disabled/>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column type="selection" width="55"/>
      <el-table-column prop="codeId" label="codeId" width="70"/>
      <el-table-column prop="code" label="code" width="320"/>
      <el-table-column prop="username" label="username" width="130"/>
      <el-table-column prop="clientId" label="clientId" width="160"/>
      <el-table-column prop="remoteAddress" label="remoteAddress" width="130"/>
      <el-table-column prop="createDate" label="createDate" width="160"/>
      <el-table-column prop="updateDate" label="updateDate" width="160"/>
      <el-table-column prop="deleted" label="deleted" width="100"/>
      <el-table-column fixed="right" label="Operations" width="100" v-if="hasAuthority('audit_code_delete')">
        <template #default="scope">
          <el-button size="small" v-if="scope.row.deleted" disabled>Delete</el-button>
          <el-button size="small" v-else @click="deleteCodeId(scope.row.codeId)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-container>
  <el-container>
    <el-pagination layout="prev, pager, next, total" @current-change="currentChange" :total="param.total"/>
  </el-container>
</template>

<script setup lang="ts">
import { page, removeById, removeByIds } from '../../api/audit/code'
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
const codeIds = ref<number[]>([])

// 搜索参数
const param = reactive({
  current: 1,
  size: 10,
  total: 0,
  codeId: null,
  code: null,
  username: null,
  clientId: null,
  remoteAddress: null
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
  param.codeId = null
  param.code = null
  param.username = null
  param.clientId = null
  param.remoteAddress = null
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
    removeByIds(codeIds.value).then(response => {
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

// 删除授权Code
const deleteCodeId = (e: number) => {
  removeById(e).then(response => {
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
  codeIds.value = []
  for (const i in val) {
    codeIds.value[i] = multipleSelection.value[i].codeId
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
