<template>
  <el-container>

    <el-aside id="cloud-el-aside">

      <el-menu :default-active="route.path" class="el-menu-vertical" :collapse="isCollapse" :router="true"
               :unique-opened="true" @open="handleOpen" @close="handleClose" id="cloud-aside-el-menu">

        <!-- 使用 template 用于遍历 -->
        <template v-for="(item, i) in routes">

          <!-- 有二级菜单，且二级菜单的个数大于 1 -->
          <!-- 有多个（大于 1）二级菜单时，index 无意义，只要唯一就行 -->
          <el-sub-menu :index="i + ''" :key="i"
                       v-if="childrenLength(item.children) > 1 && show(item.children) && subMenuAuthority(item)">
            <template #title>
              <el-icon v-if="item.meta?.icon">
                <component :is="item.meta?.icon"/>
              </el-icon>
              <span>{{ item.name }}</span>
            </template>

            <template v-for="(children, j) in item.children">
              <el-menu-item :index="children.path" :key="j" @click="menuItem"
                            v-if="show(item.children) && menuItemAuthority(children)">
                {{ children.name }}
              </el-menu-item>
            </template>
          </el-sub-menu>

          <!-- 无二级菜单，或二级菜单的个数小于等于 1 -->
          <el-menu-item :index="menuItemPath(item)" :key="menuItemPath(item)" @click="menuItem"
                        v-if="childrenLength(item.children) <= 1 && show(item.children) && menuItemsAuthority(item.children)">
            <el-tooltip class="box-item" effect="dark" :disabled="!isCollapse" :content="item.name" placement="right">
              <el-icon v-if="item.meta?.icon">
                <component :is="item.meta?.icon"/>
              </el-icon>
            </el-tooltip>
            <span>{{ item.name }}</span>
          </el-menu-item>

        </template>

      </el-menu>

    </el-aside>
    <el-container>
      <el-header id="cloud-el-header">

        <!-- 左侧菜单打开/关闭按钮 -->
        <el-button v-if="isCollapse" @click="isCollapseClick">
          <el-icon>
            <expand/>
          </el-icon>
        </el-button>
        <el-button v-else @click="isCollapseClick">
          <el-icon>
            <fold/>
          </el-icon>
        </el-button>

        <!-- 刷新 -->
        <el-button @click="refreshClick">
          <el-icon>
            <refresh/>
          </el-icon>
        </el-button>

        <!-- 用户菜单 -->
        <el-dropdown id="cloud-el-dropdown" @command="handleCommand">
          <span class="el-dropdown-link">
            {{ useStore.getNickname }}
            <el-icon class="el-icon--right">
              <arrow-down/>
            </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="personal">个人中心</el-dropdown-item>
              <el-dropdown-item command="security">安全设置</el-dropdown-item>
              <el-dropdown-item command="social">社交绑定</el-dropdown-item>
              <el-dropdown-item command="password">密码设置</el-dropdown-item>
              <el-dropdown-item command="signout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>

        <span id="cloud-now">
          {{nowShow}}
        </span>

      </el-header>
      <el-main>

        <el-tabs v-model="editableTabsValue" type="card" class="demo-tabs" @tab-remove="removeTab"
                 @tab-change="changeTab">
          <el-tab-pane v-for="item in editableTabs" :key="item.name" :label="item.title" :name="item.name"
                       :closable="item.closable"></el-tab-pane>
        </el-tabs>

        <router-view v-slot="{ Component }">
          <keep-alive :exclude="keepAliveExclude">
            <component :is="Component" :key="route.name" v-if="route.meta.keepAlive"/>
          </keep-alive>
          <component :is="Component" :key="route.name" v-if="!route.meta.keepAlive"/>
        </router-view>

      </el-main>
      <el-footer>Footer</el-footer>
    </el-container>

  </el-container>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { RouteRecordRaw, useRoute, useRouter } from 'vue-router'
import { useStore } from '../store'
import { TabPaneName } from 'element-plus'
import { hasAnyAuthority } from '../utils/authority'
import { signout } from '../api/passport'
import { routes } from '../router'
import { currentTimeMillis } from '../api/gateway'

const route = useRoute()
const router = useRouter()

// 当前时间
const now = ref<number>(0)
// 当前时间：展示
const nowShow = ref<string>()

// 当前时间戳
currentTimeMillis().then((response: number) => {
  now.value = response || new Date().getTime()
})

// 每五分钟一次，从系统中获取当前时间，无限循环
setInterval(function () {
  currentTimeMillis().then((response: number) => {
    now.value = response || new Date().getTime()
  })
}, 1000 * 60 * 5)

watch(() => now.value, (newValue, oldValue) => {
  // 储存当前时间戳
  useStore.setCurrentTimeMillis(newValue)

  const nowDate = new Date(newValue)
  const year = nowDate.getFullYear()
  const month = nowDate.getMonth() + 1
  const date = nowDate.getDate()

  let hours = nowDate.getHours().toString()
  hours = hours.length === 1 ? '0' + hours : hours

  let minutes = nowDate.getMinutes().toString()
  minutes = minutes.length === 1 ? '0' + minutes : minutes

  let seconds = nowDate.getSeconds().toString()
  seconds = seconds.length === 1 ? '0' + seconds : seconds

  nowShow.value = `${year}年${month}月${date}日 ${hours}:${minutes}:${seconds}`
})

// 每秒一次，无限循环
setInterval(function () {
  now.value += 1000
}, 1000)

// 数据转换
const keepAliveExclude = ref(useStore.getKeepAliveExclude)

// 子菜单个数
const childrenLength = (children: RouteRecordRaw[] | undefined) => {
  if (children === undefined) {
    return 0
  } else {
    return children.length
  }
}

// 菜单路径
const menuItemPath = (item: RouteRecordRaw) => {
  if (item.children === undefined) {
    return item.path
  } else {
    return item.children[0].path
  }
}

// 是否显示菜单
const show = (children: RouteRecordRaw[] | undefined) => {
  if (children === undefined) {
    return false
  }
  for (const i in children) {
    if (children[i].meta?.show === false) {
      return false
    }
  }
  return true
}

// 一级菜单权限
const subMenuAuthority = (item: RouteRecordRaw) => {
  for (const i in item.children) {
    // @ts-ignore
    if (hasAnyAuthority(item.children[i].meta?.authority)) {
      return true
    }
  }
  return false
}

// 二级菜单权限
const menuItemAuthority = (item: RouteRecordRaw) => {
  // @ts-ignore
  return hasAnyAuthority(item.meta?.authority)
}
// 二级菜单权限
const menuItemsAuthority = (items: RouteRecordRaw[] | undefined) => {
  if (items === undefined) {
    return false
  }
  for (const i in items) {
    const item = items[i]
    // @ts-ignore
    const has = hasAnyAuthority(item.meta?.authority)
    if(has) {
      return has
    }
  }
  return false
}

// 标签页：默认选择
const editableTabsValue = ref('/console')

// activeName 改变时触发
const changeTab = (name: TabPaneName) => {
  // 切换标签页时，改变URL
  location.hash = name.toString()
}

// 标签页：默认展示
const editableTabs = ref([
  {
    title: '控制台',
    name: '/console',
    // 不可关闭
    closable: false
  }
])

// 移除标签页
const removeTab = (targetName: string) => {
  const tabs = editableTabs.value
  let activeName = editableTabsValue.value
  if (activeName === targetName) {
    tabs.forEach((tab, index) => {
      if (tab.name === targetName) {
        const nextTab = tabs[index + 1] || tabs[index - 1]
        if (nextTab) {
          activeName = nextTab.name
        }
      }
    })
  }
  editableTabsValue.value = activeName
  editableTabs.value = tabs.filter((tab) => tab.name !== targetName)

  // 移除标签页时，改变URL
  location.hash = activeName

  const routeRecords = router.getRoutes()
  for (const routeRecord of routeRecords) {
    if (routeRecord.path === targetName) {
      const components = routeRecord.components
      if (components) {
        if (components.default) {
          // 使用 el-tabs 的 @tab-remove 删除 el-tab-pane，需要销毁
          // @ts-ignore
          useStore.addKeepAliveExclude(components.default.__name)
        }
      }
    }
  }
}

// 是否折叠菜单
const isCollapse = ref(useStore.getIsCollapse)
// 昵称
// const nickname = ref(useStore.getNickname)

const handleOpen = (key: number, keyPath: string) => {
  // console.log('handleOpen：', key, keyPath)
}

const handleClose = (key: number, keyPath: string) => {
  // console.log('handleClose：', key, keyPath)
}

// 激活菜单
const menuItem = (key: any) => {
  // 点击左侧菜单时，标签页跟随变动
  editableTabsValue.value = key.index

  if (key.index === '/refresh') {
    // 如果访问的是刷新页面时，不添加tabs标签页
    return
  }

  if (key.index === '/non-authority') {
    // 如果访问的是无权限页面时，不添加tabs标签页
    return
  }

  // 标签页已存在时，跳过
  for (const i in editableTabs.value) {
    const value = editableTabs.value[i]
    if (value.name === key.index) {
      return
    }
  }

  // 标签页不存在时，添加标签页
  const routeRecords = router.getRoutes()
  for (const i in routeRecords) {
    const routeRecord = routeRecords[i]
    const name = routeRecord.name
    const path = routeRecord.path
    if (path === key.index) {
      editableTabs.value.push({
        title: name === undefined ? '未知标签页名称' : name.toString(),
        name: key.index,
        closable: true // 可关闭标签页
      })
    }
  }
}

watch(() => route.path, (newValue, oldValue) => {
  // 方法一：刷新页面时（路由真正加载完成时），选中标签页
  menuItem({ index: route.path })
})

router.isReady().then(() => {
  // 方法二：刷新页面时（路由真正加载完成时），选中标签页
  menuItem({ index: route.path })
})

// 是否折叠菜单
const isCollapseClick = () => {
  isCollapse.value = !isCollapse.value
  useStore.setIsCollapse(isCollapse.value)
}

// 刷新当前页面（局部刷新）
const refreshClick = () => {
  // 无论如何都会销毁组件
  const routeRecords = router.getRoutes()
  for (const i in routeRecords) {
    const routeRecord = routeRecords[i]
    if (routeRecord.path === location.hash.substring(1, location.hash.length)) {
      const components = routeRecord.components
      if (components) {
        if (components.default) {
          // 使用 el-tabs 的 @tab-remove 删除 el-tab-pane，需要销毁
          // @ts-ignore
          useStore.addKeepAliveExclude(components.default.__name)
        }
      }
    }
  }

  location.hash = '/refresh'
}

// 用户菜单
const handleCommand = (command: any, e: any) => {
  console.log(command, e)
  switch (command) {
    case 'signout':
      signout()
      break
    case 'personal':
      location.hash = '/user/personal'
      break
    case 'security':
      location.hash = '/user/security'
      break
    case 'social':
      location.hash = '/user/social'
      break
    case 'password':
      location.hash = '/user/password'
      break
  }
}

router.isReady().then(() => {
  // 获取昵称并显示
  // nickname.value = useStore.getNickname
})

</script>

<style scoped>

#cloud-el-aside {
  /* 最侧边框无宽度（内部填充） */
  --el-aside-width: none;
}

/* 左侧菜单 */
.el-menu-vertical:not(.el-menu--collapse) {
  /* 宽度 */
  width: 200px;
}

/* 左侧菜单 */
.el-menu-vertical {
  /* 最小高度 */
  min-height: 800px;
}

/* 左侧菜单 */
#cloud-aside-el-menu {
  --el-menu-item-height: 50px;
}

/* 左侧菜单 */
#cloud-aside-el-menu .el-menu-item {
  height: 40px;
}

/* 顶部导航 */
#cloud-el-header {
  --el-header-height: 50px;
  line-height: var(--el-header-height);
}

/* 用户菜单 */
#cloud-el-header .el-dropdown {
  display: inline;
}

/* 当前时间 */
#cloud-now {
  float: right;
  margin-right: 20px;
  color: var(--el-text-color-regular);
}

/* 用户菜单 */
#cloud-el-dropdown {
  height: 34px;
  line-height: 34px;
  padding: 8px;
  float: right;
}

</style>
