# passport-ui

## 项目设置

```
npm install
```

### 编译、热加载

```
npm run dev
```

### 生产编译和压缩

```
npm run build
```

### lints和修复文件

```
npm run lint
```

### 自定义配置

- [配置参考](https://cli.vuejs.org/zh/config/index.html).

## 项目创建

- [nodejs v16.14.0 下载](https://nodejs.org/dist/v16.14.0/)
    - node v16.14.0
    - npm 8.3.1
- [Vite 官方中文文档](https://cn.vitejs.dev/guide/)

1. init

```shell
npm create vite@latest passport-ui -- --template vue
```

1. [eslint](https://eslint.vuejs.org/user-guide/#installation)
    ```shell
    npm install -g @vue/cli
    # 选择 Standard
    # 勾选 Lint on save、Lint and fix on commit
    vue add @vue/cli-plugin-eslint
    # vue-cli-service lint 无法运行
    npm i -D @vue/cli-service
    ```

1. [vue-router](https://router.vuejs.org/installation.html)
    ```shell
    npm install vue-router
    ```

1. [element-plus](https://element-plus.gitee.io/zh-CN/guide/quickstart.html#%E6%8C%89%E9%9C%80%E5%AF%BC%E5%85%A5)
    ```shell
    npm i element-plus
    ```

1. [axios](https://www.npmjs.com/package/axios)

## 项目说明

- 由于本项目使用了 Session 共享，故：需要访问`example.next.xuxiaowei.cloud`的子域
    - 否则在使用密码登录失败时，无法获取到登录失败的原因

## 依赖说明

1. 创建项目时自动生成
    - 依赖
        - vue
    - 开发依赖
        - @vitejs/plugin-vue
        - vite

1. lint
    - 开发依赖
        - @vue/cli-plugin-eslint
        - @vue/cli-service
        - @vue/eslint-config-standard
        - eslint
        - eslint-plugin-import
        - eslint-plugin-node
        - eslint-plugin-promise
        - eslint-plugin-vue
        - lint-staged

1. 路由
    - 依赖
        - vue-router

1. http 请求
    - 依赖
        - axios

1. element-plus 前端 UI 框架
    - 依赖
        - element-plus
    - 开发依赖
        - unplugin-auto-import
        - unplugin-vue-components

1. 加密
    - jsencrypt
        - RSA

1. 缓存
    - vuex
    - vuex-persistedstate
        - 缓存刷新时保留

## 依赖地址

- [@element-plus/icons-vue](https://www.npmjs.com/package/@element-plus/icons-vue)
- [axios](https://www.npmjs.com/package/axios)
- [element-plus](https://www.npmjs.com/package/element-plus)
- [jsencrypt](https://www.npmjs.com/package/jsencrypt)
- [vue](https://www.npmjs.com/package/vue)
- [vue-router](https://www.npmjs.com/package/vue-router)
- [vuex](https://www.npmjs.com/package/vuex)
- [vuex-persistedstate](https://www.npmjs.com/package/vuex-persistedstate)

- [@vitejs/plugin-vue](https://www.npmjs.com/package/@vitejs/plugin-vue)
- [@vue/cli-plugin-eslint](https://www.npmjs.com/package/@vue/cli-plugin-eslint)
- [@vue/cli-service](https://www.npmjs.com/package/@vue/cli-service)
- [@vue/eslint-config-standard](https://www.npmjs.com/package/@vue/eslint-config-standard)
- [eslint](https://www.npmjs.com/package/eslint)
- [eslint-plugin-import](https://www.npmjs.com/package/eslint-plugin-import)
- [eslint-plugin-node](https://www.npmjs.com/package/eslint-plugin-node)
- [eslint-plugin-promise](https://www.npmjs.com/package/eslint-plugin-promise)
- [eslint-plugin-vue](https://www.npmjs.com/package/eslint-plugin-vue)
- [lint-staged](https://www.npmjs.com/package/lint-staged)
- [unplugin-auto-import](https://www.npmjs.com/package/unplugin-auto-import)
- [unplugin-vue-components](https://www.npmjs.com/package/unplugin-vue-components)
- [vite](https://www.npmjs.com/package/vite)

## 其他说明

- 使用 Maven 进行打包

```shell
mvn clean package
```

# Vue 3 + Vite

[setup 文档](https://v3.vuejs.org/api/sfc-script-setup.html#sfc-script-setup)
