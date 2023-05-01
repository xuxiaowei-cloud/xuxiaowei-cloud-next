<template>
  <el-header class="text-center">
    <el-switch v-model="type" inactive-text="模糊查询" active-text="精确查询"/>
  </el-header>

  <div v-if="type">
    <el-select clearable filterable v-model="province" placeholder="请选择省份">
      <el-option v-for="item in provinceOptions" :key="item.provinceCode" :value="item.provinceCode"
                 :label="item.provinceName"/>
    </el-select>

    <el-select clearable filterable v-model="city" placeholder="请选择城市"
               :disabled="province === undefined || province === ''">
      <el-option v-for="item in cityOptions" :key="item.cityCode" :value="item.cityCode" :label="item.cityName"/>
    </el-select>

    <el-select clearable filterable v-model="county" placeholder="请选择区/县"
               :disabled="city === undefined || city === ''">
      <el-option v-for="item in countyOptions" :key="item.countyCode" :value="item.countyCode"
                 :label="item.countyName"/>
    </el-select>

    <el-select clearable filterable v-model="town" placeholder="请选择镇"
               :disabled="county === undefined || county === ''">
      <el-option v-for="item in townOptions" :key="item.townCode" :value="item.townCode" :label="item.townName"/>
    </el-select>

    <el-select clearable filterable v-model="village" placeholder="请选择居委会"
               :disabled="town === undefined || town === ''">
      <el-option v-for="item in villageOptions" :key="item.villageCode" :value="item.villageCode"
                 :label="item.villageName"/>
    </el-select>

    <el-button @click="cloudSearch">搜索</el-button>
  </div>
  <div v-else>
    <el-input class="cloud-el-input" clearable v-model="param.provinceCode" placeholder="Please input provinceCode"/>
    <el-input class="cloud-el-input" clearable v-model="param.provinceName" placeholder="Please input provinceName"/>
    <el-input class="cloud-el-input" clearable v-model="param.cityCode" placeholder="Please input cityCode"/>
    <el-input class="cloud-el-input" clearable v-model="param.cityName" placeholder="Please input cityName"/>
    <el-input class="cloud-el-input" clearable v-model="param.countyCode" placeholder="Please input countyCode"/>
    <el-input class="cloud-el-input" clearable v-model="param.countyName" placeholder="Please input countyName"/>
    <el-input class="cloud-el-input" clearable v-model="param.townCode" placeholder="Please input townCode"/>
    <el-input class="cloud-el-input" clearable v-model="param.townName" placeholder="Please input townName"/>
    <el-input class="cloud-el-input" clearable v-model="param.villageCode" placeholder="Please input villageCode"/>
    <el-input class="cloud-el-input" clearable v-model="param.villageName" placeholder="Please input villageName"/>
    <el-button @click="cloudSearch">搜索</el-button>
  </div>

  <el-container>
    <el-table stripe :data="tableData" v-loading="loading" height="460" @cell-dblclick="rowDblClick">
      <el-table-column prop="year" label="year" width="80"/>

      <el-table-column prop="provinceCode" label="provinceCode" width="120"/>
      <el-table-column prop="provinceName" label="provinceName" width="160"/>

      <el-table-column prop="cityCode" label="cityCode" width="90"/>
      <el-table-column prop="cityName" label="cityName" width="160"/>

      <el-table-column prop="countyCode" label="countyCode" width="110"/>
      <el-table-column prop="countyName" label="countyName" width="160"/>

      <el-table-column prop="townCode" label="townCode" width="110"/>
      <el-table-column prop="townName" label="townName" width="160"/>

      <el-table-column prop="villageCode" label="villageCode" width="120"/>
      <el-table-column prop="villageName" label="villageName" width="180"/>
      <el-table-column prop="villageTypeCode" label="villageTypeCode" width="140"/>
    </el-table>
  </el-container>
  <el-container>
    <el-pagination layout="prev, pager, next, total" @current-change="currentChange" :total="param.total"/>
  </el-container>
</template>

<script setup lang="ts">
import { reactive, ref, watch } from 'vue'
import { ElMessage } from 'element-plus/es'
import useClipboard from 'vue-clipboard3'

import { page as provincePage } from '../../api/master-data/province-handle'
import { page as cityPage } from '../../api/master-data/city-handle'
import { page as countyPage } from '../../api/master-data/county-handle'
import { page as townPage } from '../../api/master-data/town-handle'
import { page as villagePage } from '../../api/master-data/village-handle'
import { page as regionPage } from '../../api/master-data/region'
import settings from '../../settings'
import { regionStore } from '../../store/region'

// 复制
const { toClipboard } = useClipboard()

// 查询类型
const type = ref<boolean>(regionStore.type)
watch(() => type.value, (newValue, oldValue) => {
  regionStore.setType(newValue)
})

// 省
const province = ref()

interface ProvinceOption {
  year: number;
  provinceCode: number;
  provinceName: string;
}

const provinceOptions = ref<ProvinceOption[]>([])

// 一共31个省
provincePage({ size: 40 }).then(response => {
  provinceOptions.value = response.data.records
})

// 市
const city = ref()

interface CityOption {
  provinceCode: number;
  cityCode: number;
  cityName: string;
}

const cityOptions = ref<CityOption[]>([])

watch(() => province.value, (newValue, oldValue) => {
  city.value = undefined
  if (newValue == null || newValue === '') {
    cityOptions.value = []
  } else {
    // 省份管辖下，最多有21个城市
    cityPage({ provinceCode: newValue, size: 30 }).then(response => {
      cityOptions.value = response.data.records
    })
  }
})

// 区/县
const county = ref()

interface CountyOption {
  cityCode: number;
  countyCode: number;
  countyName: string;
}

const countyOptions = ref<CountyOption[]>([])

watch(() => city.value, (newValue, oldValue) => {
  county.value = undefined
  if (newValue == null || newValue === '') {
    countyOptions.value = []
  } else {
    // 城市管辖下，最多有26个县
    countyPage({ cityCode: city.value, size: 30 }).then(response => {
      countyOptions.value = response.data.records
    })
  }
})

// 镇
const town = ref()

interface TownOption {
  countyCode: number;
  townCode: number;
  townName: string;
}

const townOptions = ref<TownOption[]>([])

watch(() => county.value, (newValue, oldValue) => {
  town.value = undefined
  if (newValue == null || newValue === '') {
    townOptions.value = []
  } else {
    // 县管辖下，最多有52个镇
    townPage({ countyCode: county.value, size: 60 }).then(response => {
      townOptions.value = response.data.records
    })
  }
})

// 居委会
const village = ref()

interface VillageOption {
  townCode: number;
  villageCode: number;
  villageName: string;
  villageTypeCode: string;
}

const villageOptions = ref<VillageOption[]>([])

watch(() => town.value, (newValue, oldValue) => {
  village.value = undefined
  if (newValue == null || newValue === '') {
    villageOptions.value = []
  } else {
    // 镇管辖下，最多有168居委会
    villagePage({ townCode: town.value, size: 170 }).then(response => {
      villageOptions.value = response.data.records
      console.log(villageOptions.value)
    })
  }
})

const param = reactive({
  type: true,
  current: 1,
  size: 10,
  total: 0,
  provinceCode: null,
  provinceName: null,
  cityCode: null,
  cityName: null,
  countyCode: null,
  countyName: null,
  townCode: null,
  townName: null,
  villageCode: null,
  villageName: null
})

const tableData = ref([])

// 加载
const loading = ref(true)

// 搜索
const cloudSearch = () => {
  loading.value = true
  param.type = type.value
  let tmp
  if (type.value) {
    tmp = {
      current: param.current,
      size: param.size,
      provinceCode: province.value,
      cityCode: city.value,
      countyCode: county.value,
      townCode: town.value,
      villageCode: village.value
    }
  } else {
    tmp = param
  }
  regionPage(tmp).then(response => {
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

// 改变时触发
const currentChange = (e: number) => {
  param.current = e
  cloudSearch()
}

// 默认搜索
cloudSearch()

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
