<template>
  <div id="cloud-el-search">
    <el-input class="cloud-el-input" clearable v-model="param.dictCode" placeholder="Please input dictCode"/>
    <el-input class="cloud-el-input" clearable v-model="param.dictDataCode" placeholder="Please input dictDataCode"/>
    <el-input class="cloud-el-input" clearable v-model="param.dictDataLabel" placeholder="Please input dictDataLabel"/>
    <el-input class="cloud-el-input" clearable v-model="param.dictDataExplain"
              placeholder="Please input dictDataExplain"/>
    <el-input class="cloud-el-input" clearable v-model="param.externalCodeOne"
              placeholder="Please input externalCodeOne"/>
    <el-input class="cloud-el-input" clearable v-model="param.externalLabelOne"
              placeholder="Please input externalLabelOne"/>
    <el-input class="cloud-el-input" clearable v-model="param.remark" placeholder="Please input remark"/>
    <el-button class="cloud-el-search" @click="cloudSearch">搜索</el-button>
    <el-button class="cloud-el-reset" @click="cloudClearable">重置</el-button>
    <el-button class="cloud-el-remove" @click="cloudRemove" v-if="hasAuthority('dict_delete')">删除</el-button>
    <el-button class="cloud-el-add" @click="cloudAdd" v-if="hasAuthority('dict_add')">添加</el-button>
  </div>

  <!-- 字典数据 -->
  <el-dialog v-if="dictDialogVisible" v-model="dictDialogVisible" :title="dictDialogVisibleTitle" width="40%"
             :before-close="dictDialogHandleClose">
    <DictDataDialog :dialogVisible="dictDialogVisible" :edit="edit" @dialogVisibleClose="dictDialogVisibleClose"
                    :dictCode="dialogVisibleDictCode" :dictDataCode="dialogVisibleDictDataCode"/>
  </el-dialog>

  <el-container>
    <el-table stripe :data="tableData" v-loading="loading" height="460" @selection-change="handleSelectionChange"
              @cell-dblclick="rowDblClick">
      <el-table-column type="selection" width="55"/>
      <el-table-column prop="dictCode" label="dictCode" width="130" :show-overflow-tooltip="true"/>
      <el-table-column prop="dictDataCode" label="dictDataCode" width="160" :show-overflow-tooltip="true"/>
      <el-table-column prop="dictDataLabel" label="dictDataLabel" width="120" :show-overflow-tooltip="true"/>
      <el-table-column prop="dictDataSort" label="dictDataSort" width="110" :show-overflow-tooltip="true"/>
      <el-table-column prop="dictDataExplain" label="dictDataExplain" width="140" :show-overflow-tooltip="true"/>
      <el-table-column prop="externalCodeOne" label="externalCodeOne" width="140" :show-overflow-tooltip="true"/>
      <el-table-column prop="externalLabelOne" label="externalLabelOne" width="150" :show-overflow-tooltip="true"/>
      <el-table-column prop="remark" label="remark" width="80" :show-overflow-tooltip="true"/>
      <el-table-column prop="createUsersId" label="createUsersId" width="120" :show-overflow-tooltip="true"/>
      <el-table-column prop="createDate" label="createDate" width="160" :show-overflow-tooltip="true"/>
      <el-table-column prop="createIp" label="createIp" width="130" :show-overflow-tooltip="true"/>
      <el-table-column prop="updateUsersId" label="updateUsersId" width="130" :show-overflow-tooltip="true"/>
      <el-table-column prop="updateDate" label="updateDate" width="160" :show-overflow-tooltip="true"/>
      <el-table-column prop="updateIp" label="updateIp" width="130" :show-overflow-tooltip="true"/>
      <el-table-column fixed="right" label="Operations" width="140"
                       v-if="hasAnyAuthority(['dict_delete', 'dict_edit'])">
        <template #default="scope">
          <el-button size="small" @click="deleteId(scope.row)" v-if="hasAuthority('dict_delete')">Delete</el-button>
          <el-button size="small" @click="editId(scope.row)" v-if="hasAuthority('dict_edit')">Edit</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-container>
  <el-container>
    <el-pagination layout="prev, pager, next, total" @current-change="currentChange" :total="param.total"/>
  </el-container>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus/es'
import useClipboard from 'vue-clipboard3'
import { PrimaryKey, page, removeById, removeByIds } from '../../api/master-data/dict-data'
import { hasAnyAuthority, hasAuthority } from '../../utils/authority'
import settings from '../../settings'
// 字典数据添加、编辑弹窗内容
import DictDataDialog from './dialog/DictDataDialog.vue'

// 复制
const { toClipboard } = useClipboard()

const tableData = ref([])

// 加载
const loading = ref(true)

const param = reactive({
  type: true,
  current: 1,
  size: 10,
  total: 0,
  dictCode: null,
  dictDataCode: null,
  dictDataLabel: null,
  dictDataExplain: null,
  externalCodeOne: null,
  externalLabelOne: null,
  remark: null
})

// 搜索
const cloudSearch = () => {
  loading.value = true
  page(param).then(response => {
    console.log(response)
    if (response.code === settings.okCode) {
      const data = response.data
      tableData.value = data.records
      param.total = data.total
      loading.value = false
    } else {
      ElMessage.error(response.msg)
    }
  })
}

cloudSearch()

// 改变时触发
const currentChange = (e: number) => {
  param.current = e
  cloudSearch()
}

// 重置（清空搜索条件）
// Q：为何不使用 reset？
// A：因为使用 reset 后，页面不显示了，但是值还在，影响搜索
const cloudClearable = () => {
  param.dictCode = null
  param.dictDataCode = null
  param.dictDataLabel = null
  param.dictDataExplain = null
  param.externalCodeOne = null
  param.externalLabelOne = null
  param.remark = null
}

// 多选
const multipleSelection = ref<any[]>([])
// 多选主键
const ids = ref<PrimaryKey[]>([])

// 选择事件
const handleSelectionChange = (val: any[]) => {
  multipleSelection.value = val

  // 清空
  ids.value = []
  for (const i in val) {
    ids.value.push({
      dictCode: multipleSelection.value[i].dictCode,
      dictDataCode: multipleSelection.value[i].dictDataCode
    })
  }
}

// 删除字典
const deleteId = (e: any) => {
  ElMessageBox.confirm(`确认删除【${e.dictDataLabel}】？`, '警告', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    removeById({ dictCode: e.dictCode, dictDataCode: e.dictDataCode }).then(response => {
      if (response.code === settings.okCode) {
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
  }).catch(() => {

  })
}

// 修改字典数据
const editId = (e: any) => {
  // 字典数据类型：编辑
  edit.value = true
  dictDialogVisibleTitle.value = '编辑字典数据'
  // 编辑字典代码
  dialogVisibleDictCode.value = e.dictCode
  dialogVisibleDictDataCode.value = e.dictDataCode
  // 字典数据：打开
  dictDialogVisible.value = true
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
    ElMessageBox.confirm('确认批量删除？', '警告', {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      removeByIds(ids.value).then(response => {
        if (response.code === settings.okCode) {
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
    }).catch(() => {

    })
  }
}

// 字典数据中的字典代码（修改时使用）
const dialogVisibleDictCode = ref<string>()
const dialogVisibleDictDataCode = ref<string>()

// 字典数据：是否打开
const dictDialogVisible = ref(false)

// 字典数据标题
const dictDialogVisibleTitle = ref<String>()
// 字典数据是否编辑
const edit = ref(false)

// 添加字典数据
const cloudAdd = () => {
  // 字典数据类型：添加
  edit.value = false
  dictDialogVisibleTitle.value = '添加字典数据'
  dialogVisibleDictCode.value = undefined
  dialogVisibleDictDataCode.value = undefined
  // 字典数据：打开
  dictDialogVisible.value = true
}

// 字典数据关闭：弹窗右上角的 x
const dictDialogHandleClose = (done: () => void) => {
  console.log('关闭字典数据')
  done()
}

// 字典数据关闭：子窗口使用
const dictDialogVisibleClose = () => {
  // 字典数据：打开
  dictDialogVisible.value = false
  // 关闭窗口后，重新搜索
  cloudSearch()
}

// 当某个单元格被双击击时会触发该事件
const rowDblClick = async (row: any, column: any, cell: any, event: any) => {
  const columnValue = row[column.property]
  console.log(columnValue)
  try {
    await toClipboard(columnValue + '')
    ElMessage({
      message: '已复制到剪贴板。',
      type: 'success'
    })
  } catch (e) {
    console.error(e)
  }
}

</script>

<style scoped>

.cloud-el-input {
  width: 300px;
}

</style>
