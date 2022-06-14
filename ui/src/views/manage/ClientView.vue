<template>
  <div id="cloud-el-search">
    <el-input class="cloud-el-input" clearable v-model="param.oauthClientDetailsId"
              placeholder="Please input oauthClientDetailsId"/>
    <el-input class="cloud-el-input" clearable v-model="param.clientId" placeholder="Please input clientId"/>
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
                  :oauthClientDetailsId="dialogVisibleOauthClientDetailsId"/>
  </el-dialog>

  <el-container>
    <el-table stripe :data="tableData" v-loading="loading" height="460" @selection-change="handleSelectionChange">
      <el-table-column type="expand">
        <template #default="props">
          <el-form label-width="160px">
            <el-form-item label="clientId">
              <el-input v-model="props.row.clientId" class="cloud-el-expand-input" disabled/>
            </el-form-item>
            <el-form-item label="grantTypes">
              <el-input v-model="props.row.authorizedGrantTypes" class="cloud-el-expand-input" disabled/>
            </el-form-item>
            <el-form-item label="scope">
              <el-input v-model="props.row.scope" class="cloud-el-expand-input" disabled/>
            </el-form-item>
            <el-form-item label="redirectUri">
              <el-input v-model="props.row.webServerRedirectUri" class="cloud-el-expand-input" disabled/>
            </el-form-item>
            <el-form-item label="resourceIds">
              <el-input v-model="props.row.resourceIds" class="cloud-el-expand-input" disabled/>
            </el-form-item>
            <el-form-item label="authorities">
              <el-input v-model="props.row.authorities" class="cloud-el-expand-input" disabled/>
            </el-form-item>
            <el-form-item label="additionalInformation">
              <el-input v-model="props.row.additionalInformation" class="cloud-el-expand-input" disabled/>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column type="selection" width="55"/>
      <el-table-column prop="oauthClientDetailsId" label="oauthClientDetailsId" width="160"/>
      <el-table-column prop="clientId" label="clientId" width="160" :show-overflow-tooltip="true"/>
      <el-table-column prop="authorizedGrantTypes" label="grantTypes" width="240" :show-overflow-tooltip="true"/>
      <el-table-column prop="accessTokenValidity" label="accessTokenValidity" width="160"/>
      <el-table-column prop="refreshTokenValidity" label="refreshTokenValidity" width="170"/>
      <el-table-column prop="scope" label="scope" width="110" :show-overflow-tooltip="true"/>
      <el-table-column prop="autoapprove" label="autoapprove" width="110"/>
      <el-table-column prop="createDate" label="createDate" width="160"/>
      <el-table-column prop="updateDate" label="updateDate" width="160"/>

      <el-table-column fixed="right" label="Operations" width="140"
                       v-if="hasAnyAuthority(['manage_client_delete', 'manage_client_edit', 'manage_client_authority'])">
        <template #default="scope">
          <el-button size="small" @click="deleteOauthClientDetailsId(scope.row.oauthClientDetailsId)"
                     v-if="hasAuthority('manage_client_delete')">Delete
          </el-button>
          <el-button size="small" @click="editOauthClientDetailsId(scope.row.oauthClientDetailsId)"
                     v-if="hasAuthority('manage_client_edit')">Edit
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
import { page, removeByIds, removeById, removeByClientIds } from '../../api/authorization-server'
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
const dialogVisibleOauthClientDetailsId = ref<number>()

// 客户弹窗标题
const clientDialogVisibleTitle = ref<String>()
// 客户弹窗是否编辑
const edit = ref(false)

// 添加客户
const cloudAdd = () => {
  // 客户弹窗类型：添加
  edit.value = false
  clientDialogVisibleTitle.value = '添加客户'
  dialogVisibleOauthClientDetailsId.value = undefined
  // 客户弹窗：打开
  clientDialogVisible.value = true
}

// 修改客户
const editOauthClientDetailsId = (oauthClientDetailsId: number) => {
  // 客户弹窗类型：编辑
  edit.value = true
  clientDialogVisibleTitle.value = '编辑客户'
  // 编辑客户的ID
  dialogVisibleOauthClientDetailsId.value = oauthClientDetailsId
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
const oauthClientDetailsIds = ref<number[]>([])
// 多选客户ID
const clientIds = ref<string[]>([])

// 搜索参数
const param = reactive({
  current: 1,
  size: 10,
  total: 0,
  oauthClientDetailsId: null,
  clientId: null
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
  param.oauthClientDetailsId = null
  param.clientId = null
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
    removeByIds(oauthClientDetailsIds.value).then(response => {
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
const deleteOauthClientDetailsId = (e: number) => {
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
    removeByClientIds(clientIds.value).then(response => {
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
  oauthClientDetailsIds.value = []
  clientIds.value = []
  for (const i in val) {
    oauthClientDetailsIds.value[i] = multipleSelection.value[i].oauthClientDetailsId
    clientIds.value[i] = multipleSelection.value[i].clientId
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
