<template>
  <el-container>
    <el-form :model="param" ref="cloudFormRef" label-position="left" label-width="100px" id="cloud-el-form">

      <el-form-item label="用户名">
        <el-input v-model="param.username" disabled/>
      </el-form-item>

      <el-form-item label="用户昵称" prop="nickname" :rules="[{ required: true, message: 'nickname is required' }]">
        <el-input v-model="param.nickname"/>
      </el-form-item>

      <el-form-item label="性别" prop="sex" :rules="[{ required: true, message: 'sex is required' }]">
        <el-select filterable v-model="param.sex" placeholder="Select sex">
          <el-option v-for="item in dictCodeOptions" :key="item.value" :label="item.label" :value="item.value"/>
        </el-select>
      </el-form-item>

      <el-form-item label="出生日期" prop="birthday" :rules="[{ required: true, message: 'birthday is required' }]">
        <el-date-picker v-model="param.birthday" type="date" placeholder="Pick a day" value-format="YYYY-MM-DD"
                        format="YYYY-MM-DD"/>
      </el-form-item>

      <el-form-item label="省" prop="provinceCode">
        <el-select clearable filterable v-model="param.provinceCode" placeholder="请选择省份"
                   @change="provinceCodeChange">
          <el-option v-for="item in provinceOptions" :key="item.provinceCode" :value="item.provinceCode"
                     :label="item.provinceName"/>
        </el-select>
      </el-form-item>

      <el-form-item label="市" prop="cityCode" v-if="param.provinceCode !== null && param.provinceCode !== ''">
        <el-select clearable filterable v-model="param.cityCode" placeholder="请选择城市" @change="cityCodeChange">
          <el-option v-for="item in cityOptions" :key="item.cityCode" :value="item.cityCode" :label="item.cityName"/>
        </el-select>
      </el-form-item>

      <el-form-item label="区/县" prop="countyCode" v-if="param.cityCode !== null && param.cityCode !== ''">
        <el-select clearable filterable v-model="param.countyCode" placeholder="请选择区/县" @change="countyCodeChange">
          <el-option v-for="item in countyOptions" :key="item.countyCode" :value="item.countyCode"
                     :label="item.countyName"/>
        </el-select>
      </el-form-item>

      <el-form-item clearable label="镇" prop="townCode" v-if="param.countyCode !== null && param.countyCode !== ''">
        <el-select filterable v-model="param.townCode" placeholder="请选择镇" @change="townCodeChange">
          <el-option v-for="item in townOptions" :key="item.townCode" :value="item.townCode" :label="item.townName"/>
        </el-select>
      </el-form-item>

      <el-form-item label="居委会" prop="villageCode" v-if="param.townCode !== null && param.townCode !== ''">
        <el-select clearable filterable v-model="param.villageCode" placeholder="请选择居委会">
          <el-option v-for="item in villageOptions" :key="item.villageCode" :value="item.villageCode"
                     :label="item.villageName"/>
        </el-select>
      </el-form-item>

      <el-form-item label="详细地址" prop="detailAddress">
        <el-input v-model="param.detailAddress"/>
      </el-form-item>

      <el-form-item>
        <el-button class="cloud-el-button" @click="cloudUpdate">更新</el-button>
      </el-form-item>

    </el-form>
  </el-container>
</template>

<script setup lang="ts">
import { reactive, ref, watch } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { info, update, UsersVo } from '../../../api/user'
import settings from '../../../settings'
import { AjaxResponse } from '../../../utils/request'
import { listByDictCode } from '../../../api/master-data/dict-data'
import { page as provincePage } from '../../../api/master-data/province-handle'
import { page as cityPage } from '../../../api/master-data/city-handle'
import { page as countyPage } from '../../../api/master-data/county-handle'
import { page as townPage } from '../../../api/master-data/town-handle'
import { page as villagePage } from '../../../api/master-data/village-handle'

// 参数
const param = reactive({
  username: null,
  nickname: null,
  sex: null,
  sexLabel: null,
  birthday: null,

  provinceCode: null,
  provinceName: null,
  cityCode: null,
  cityName: null,
  countyCode: null,
  countyName: null,
  townCode: null,
  townName: null,
  villageCode: null,
  villageName: null,

  detailAddress: null
})

watch(() => param.provinceCode, (newValue, oldValue) => {
  if (newValue === '') {
    param.cityCode = null
  }
})

watch(() => param.cityCode, (newValue, oldValue) => {
  if (newValue === '') {
    param.countyCode = null
  }
})

watch(() => param.countyCode, (newValue, oldValue) => {
  if (newValue === '') {
    param.townCode = null
  }
})

watch(() => param.townCode, (newValue, oldValue) => {
  if (newValue === '') {
    param.villageCode = null
  }
})

// 可选内容
interface Option {
  label: string
  value: string
}

const dictCodeData: Option[] = []
const dictCodeOptions = reactive(dictCodeData)

listByDictCode('sex').then(response => {
  const data = response?.data
  for (const i in data) {
    dictCodeOptions.push({
      value: data[i].dictDataCode,
      label: data[i].dictDataLabel
    })
  }
})

interface ProvinceOption {
  year: number;
  provinceCode: number;
  provinceName: string;
}

interface CityOption {
  provinceCode: number | string;
  cityCode: number | string;
  cityName: string;
}

interface CountyOption {
  cityCode: number | string;
  countyCode: number | string;
  countyName: string;
}

interface TownOption {
  countyCode: number | string;
  townCode: number | string;
  townName: string;
}

interface VillageOption {
  townCode: number | string;
  villageCode: number | string;
  villageName: string;
  villageTypeCode: string;
}

const provinceOptions = ref<ProvinceOption[]>([])
const cityOptions = ref<CityOption[]>([])
const countyOptions = ref<CountyOption[]>([])
const townOptions = ref<TownOption[]>([])
const villageOptions = ref<VillageOption[]>([])

// 一共31个省
provincePage({ size: 40 }).then(response => {
  provinceOptions.value = response.data.records
})

const city = (provinceCode: any) => {
  // 省份管辖下，最多有21个城市
  cityPage({ provinceCode, size: 30 }).then(response => {
    cityOptions.value = response.data.records
  })
}

const county = (cityCode: any) => {
  // 城市管辖下，最多有26个县
  countyPage({ cityCode, size: 30 }).then(response => {
    countyOptions.value = response.data.records
  })
}

const town = (countyCode: any) => {
  // 县管辖下，最多有52个镇
  townPage({ countyCode, size: 60 }).then(response => {
    townOptions.value = response.data.records
  })
}

const village = (townCode: any) => {
  // 镇管辖下，最多有168居委会
  villagePage({ townCode, size: 170 }).then(response => {
    villageOptions.value = response.data.records
  })
}

const provinceCodeChange = (provinceCode: any) => {
  param.cityCode = null
  param.countyCode = null
  param.townCode = null
  param.villageCode = null
  city(provinceCode)
  countyOptions.value = []
  townOptions.value = []
  villageOptions.value = []
}

const cityCodeChange = (cityCode: any) => {
  param.countyCode = null
  param.townCode = null
  param.villageCode = null
  county(cityCode)
  townOptions.value = []
  villageOptions.value = []
}

const countyCodeChange = (countyCode: any) => {
  param.townCode = null
  param.villageCode = null
  town(countyCode)
  villageOptions.value = []
}

const townCodeChange = (townCode: any) => {
  param.villageCode = null
  village(townCode)
}

// 初始化数据
info().then((response: AjaxResponse<UsersVo>) => {
  const usersVo = response?.data

  // 此方法仅为了防止闪烁
  cityOptions.value.push({
    provinceCode: usersVo?.provinceCode,
    cityCode: usersVo?.cityCode,
    cityName: usersVo?.cityName
  })
  // 创建城市选项
  city(usersVo?.provinceCode)

  // 此方法仅为了防止闪烁
  countyOptions.value.push({
    cityCode: usersVo?.cityCode,
    countyCode: usersVo?.countyCode,
    countyName: usersVo?.countyName
  })
  // 创建区/县选项
  county(usersVo?.cityCode)

  // 此方法仅为了防止闪烁
  townOptions.value.push({
    countyCode: usersVo?.countyCode,
    townCode: usersVo?.townCode,
    townName: usersVo?.townName
  })
  // 创建镇选项
  town(usersVo?.countyCode)

  // 此方法仅为了防止闪烁
  villageOptions.value.push({
    townCode: usersVo?.townCode,
    villageCode: usersVo?.villageCode,
    villageName: usersVo?.villageName,
    villageTypeCode: ''
  })
  // 创建居委会选项
  village(usersVo?.townCode)

  for (const name in param) {
    // @ts-ignore
    param[name] = usersVo[name]
  }
})

const emit = defineEmits(['dialogVisibleClose'])

// 表单验证
const cloudFormRef = ref(null)

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
        update(param).then(response => {
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
