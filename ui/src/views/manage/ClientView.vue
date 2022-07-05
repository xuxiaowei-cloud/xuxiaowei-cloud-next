<template>
  <div id="cloud-el-search">
    <el-input class="cloud-el-input" clearable v-model="param.id" placeholder="Please input id"/>
    <el-input class="cloud-el-input" clearable v-model="param.clientId" placeholder="Please input clientId"/>
    <el-input class="cloud-el-input" clearable v-model="param.clientName" placeholder="Please input clientName"/>
    <el-button class="cloud-el-search" @click="cloudSearch">搜索</el-button>
    <el-button class="cloud-el-reset" @click="cloudClearable">重置</el-button>
    <el-button class="cloud-el-remove" @click="cloudRemove" v-if="hasAuthority('manage_client_delete')">删除</el-button>
    <el-button class="cloud-el-add" @click="cloudAdd" v-if="hasAuthority('manage_client_add')">添加
    </el-button>
    <el-button class="cloud-el-username_token_delete" @click="cloudTokenDelete"
               v-if="hasAuthority('clientId_token_delete')">
      删除Token
    </el-button>
  </div>

  <!-- 客户弹窗 -->
  <el-dialog v-if="clientDialogVisible" v-model="clientDialogVisible" :title="clientDialogVisibleTitle" width="40%"
             :before-close="clientDialogHandleClose">
    <ClientDialog :dialogVisible="clientDialogVisible" :edit="edit" @dialogVisibleClose="clientDialogVisibleClose"
                  :id="dialogVisibleId"/>
  </el-dialog>

  <el-container>
    <el-table stripe :data="tableData" v-loading="loading" height="460" @selection-change="handleSelectionChange">
      <el-table-column type="expand">
        <template #default="props">
          <el-form label-width="200px">
            <el-form-item label="id">
              <el-input v-model="props.row.id" class="cloud-el-expand-input" disabled/>
            </el-form-item>
            <el-form-item label="clientId">
              <el-input v-model="props.row.clientId" class="cloud-el-expand-input" disabled/>
            </el-form-item>
            <el-form-item label="clientName">
              <el-input v-model="props.row.clientName" class="cloud-el-expand-input" disabled/>
            </el-form-item>
            <el-form-item label="clientAuthenticationMethods">
              <el-input v-model="props.row.clientAuthenticationMethods" class="cloud-el-expand-input" disabled/>
            </el-form-item>
            <el-form-item label="authorizationGrantTypes">
              <el-input v-model="props.row.authorizationGrantTypes" class="cloud-el-expand-input" disabled/>
            </el-form-item>
            <el-form-item label="redirectUris">
              <el-input v-model="props.row.redirectUris" class="cloud-el-expand-input" disabled/>
            </el-form-item>
            <el-form-item label="scopes">
              <el-input v-model="props.row.scopes" class="cloud-el-expand-input" disabled/>
            </el-form-item>
            <el-form-item label="tokenSigningAlgorithm">
              <el-input v-model="props.row.tokenSigningAlgorithm" class="cloud-el-expand-input" disabled/>
            </el-form-item>
            <el-form-item label="clientSettings">
              <el-input v-model="props.row.clientSettings" class="cloud-el-expand-input" type="textarea" disabled rows="3"/>
            </el-form-item>
            <el-form-item label="requireProofKey">
              <el-input v-model="props.row.requireProofKey" class="cloud-el-expand-input" disabled/>
            </el-form-item>
            <el-form-item label="requireAuthorizationConsent">
              <el-input v-model="props.row.requireAuthorizationConsent" class="cloud-el-expand-input" disabled/>
            </el-form-item>
            <el-form-item label="tokenSignatureAlgorithm">
              <el-input v-model="props.row.tokenSignatureAlgorithm" class="cloud-el-expand-input" disabled/>
            </el-form-item>
            <el-form-item label="tokenSettings">
              <el-input v-model="props.row.tokenSettings" class="cloud-el-expand-input" type="textarea" disabled rows="5"/>
            </el-form-item>
            <el-form-item label="accessTokenTimeToLive">
              <el-input v-model="props.row.accessTokenTimeToLive" class="cloud-el-expand-input" disabled/>
            </el-form-item>
            <el-form-item label="refreshTokenTimeToLive">
              <el-input v-model="props.row.refreshTokenTimeToLive" class="cloud-el-expand-input" disabled/>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column type="selection" width="55"/>
      <el-table-column prop="id" label="id" width="100" :show-overflow-tooltip="true"/>
      <el-table-column prop="clientId" label="clientId" width="160" :show-overflow-tooltip="true"/>
      <el-table-column prop="clientName" label="clientName" width="200" :show-overflow-tooltip="true"/>
      <el-table-column prop="clientIdIssuedAt" label="clientIdIssuedAt" width="160"/>
      <el-table-column prop="clientSecretExpiresAt" label="clientSecretExpiresAt" width="170"/>
      <el-table-column prop="clientAuthenticationMethods" label="clientAuthenticationMethods" width="220" :show-overflow-tooltip="true"/>
      <el-table-column prop="authorizationGrantTypes" label="authorizationGrantTypes" width="190" :show-overflow-tooltip="true"/>
      <el-table-column prop="scopes" label="scopes" width="160" :show-overflow-tooltip="true"/>

      <el-table-column fixed="right" label="Operations" width="140"
                       v-if="hasAnyAuthority(['manage_client_delete', 'manage_client_edit', 'manage_client_authority'])">
        <template #default="scope">
          <el-button size="small" @click="deleteId(scope.row.id)" v-if="hasAuthority('manage_client_delete')">Delete</el-button>
          <el-button size="small" @click="editId(scope.row.id)" v-if="hasAuthority('manage_client_edit')">Edit</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-container>
  <el-container>
    <el-pagination layout="prev, pager, next, total" @current-change="currentChange" :total="param.total"/>
  </el-container>
</template>

<script setup lang="ts">
import { page, removeByIds, removeById } from '../../api/passport/oauth2-registered-client'
import { hasAnyAuthority, hasAuthority } from '../../utils/authority'
import { reactive, ref } from 'vue'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'
// 客户添加、编辑弹窗内容
import ClientDialog from './dialog/ClientDialog.vue'

// 缓存
const store = useStore()

// 客户弹窗：是否打开
const clientDialogVisible = ref(false)

// 客户弹窗中的客户ID（修改时使用）
const dialogVisibleId = ref<string>()

// 客户弹窗标题
const clientDialogVisibleTitle = ref<String>()
// 客户弹窗是否编辑
const edit = ref(false)

// 添加客户
const cloudAdd = () => {
  // 客户弹窗类型：添加
  edit.value = false
  clientDialogVisibleTitle.value = '添加客户'
  dialogVisibleId.value = undefined
  // 客户弹窗：打开
  clientDialogVisible.value = true
}

// 修改客户
const editId = (id: string) => {
  // 客户弹窗类型：编辑
  edit.value = true
  clientDialogVisibleTitle.value = '编辑客户'
  // 编辑客户的ID
  dialogVisibleId.value = id
  // 客户弹窗：打开
  clientDialogVisible.value = true
}

// 客户弹窗关闭：弹窗右上角的 x
const clientDialogHandleClose = (done: () => void) => {
  console.log('关闭客户弹窗')
  done()
}

// 客户弹窗关闭：子窗口使用
const clientDialogVisibleClose = () => {
  // 客户弹窗：打开
  clientDialogVisible.value = false
  // 关闭窗口后，重新搜索
  cloudSearch()
}

// 表格数据
const tableData = ref([])

// 多选
const multipleSelection = ref<any[]>([])
// 多选主键
const ids = ref<number[]>([])

// 搜索参数
const param = reactive({
  current: 1,
  size: 10,
  total: 0,
  id: null,
  clientId: null,
  clientName: null
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
  param.id = null
  param.clientId = null
  param.clientName = null
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
    removeByIds(ids.value).then(response => {
      if (response.code === store.state.settings.okCode) {
        ElMessage({
          message: response.msg,
          // 显示时间，单位为毫秒。设为 0 则不会自动关闭，类型：number，默认值：3000
          duration: 1500,
          type: 'success',
          onClose: () => {
            // 重新搜索
            cloudSearch()
          }
        })
      } else {
        ElMessage({
          message: response.msg,
          // 显示时间，单位为毫秒。设为 0 则不会自动关闭，类型：number，默认值：3000
          duration: 1500,
          type: 'error',
          onClose: () => {
            // 重新搜索
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

// 删除客户
const deleteId = (e: number) => {
  removeById(e).then(response => {
    if (response.code === store.state.settings.okCode) {
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

// 删除Token
const cloudTokenDelete = () => {
  if (multipleSelection.value.length === 0) {
    ElMessage({
      message: '请先选择数据',
      // 显示时间，单位为毫秒。设为 0 则不会自动关闭，类型：number，默认值：3000
      duration: 1500,
      type: 'error'
    })
  } else {
    removeByIds(ids.value).then(response => {
      if (response.code === store.state.settings.okCode) {
        ElMessage({
          message: response.msg,
          // 显示时间，单位为毫秒。设为 0 则不会自动关闭，类型：number，默认值：3000
          duration: 1500,
          type: 'success',
          onClose: () => {
            // 重新搜索
            cloudSearch()
          }
        })
      } else {
        ElMessage({
          message: response.msg,
          // 显示时间，单位为毫秒。设为 0 则不会自动关闭，类型：number，默认值：3000
          duration: 1500,
          type: 'error',
          onClose: () => {

          }
        })
      }
    })
  }
}

// 选择事件
const handleSelectionChange = (val: any[]) => {
  multipleSelection.value = val

  // 清空
  ids.value = []
  for (const i in val) {
    ids.value[i] = multipleSelection.value[i].id
  }
}

</script>

<style scoped>

.cloud-el-input {
  width: 300px;
}

.cloud-el-input,
.cloud-el-search,
.cloud-el-reset,
.cloud-el-remove,
.cloud-el-add,
.cloud-el-username_token_delete {
  margin-left: 5px;
  margin-right: 5px;
}

.cloud-el-expand-input,
.cloud-el-expand-textarea {
  /*max-width: 1100px;*/
}

</style>
