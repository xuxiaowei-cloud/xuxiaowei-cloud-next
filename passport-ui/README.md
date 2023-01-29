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
npm create vite@latest passport-ui -- --template vue-ts
```

1. [eslint](https://www.npmjs.com/package/eslint)
    ```shell
    npm i eslint -D
    npx eslint --init
    ```

2. [vue-router](https://router.vuejs.org/installation.html)
    ```shell
    npm install vue-router
    ```

3. [element-plus](https://element-plus.gitee.io/zh-CN/guide/quickstart.html#%E6%8C%89%E9%9C%80%E5%AF%BC%E5%85%A5)
    ```shell
    npm i element-plus
    ```

4. [axios](https://www.npmjs.com/package/axios)

5. [terser](https://www.npmjs.com/package/terser)
    1. [General Changes](https://vitejs.dev/guide/migration.html#general-changes)

```
npm i vue
npm i @element-plus/icons-vue axios crypto-js element-plus jsencrypt pinia vue-router
```

```
npm i -D @types/crypto-js @typescript-eslint/eslint-plugin @typescript-eslint/parser @vitejs/plugin-vue eslint eslint-config-standard eslint-plugin-import eslint-plugin-n eslint-plugin-promise eslint-plugin-vue lint-staged terser typescript unocss unplugin-auto-import unplugin-vue-components vite vue-tsc
```

## 项目说明

- 由于本项目使用了 Session 共享，故：需要访问`example.xuxiaowei.cloud`的子域
    - 否则在使用密码登录失败时，无法获取到登录失败的原因

## 依赖说明

1. 创建项目时自动生成
    - 依赖
        - vue
    - 开发依赖
        - @vitejs/plugin-vue
        - vite

2. lint
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

3. 路由
    - 依赖
        - vue-router

4. http 请求
    - 依赖
        - axios

5. element-plus 前端 UI 框架
    - 依赖
        - element-plus
    - 开发依赖
        - unplugin-auto-import
        - unplugin-vue-components

6. 加密
    - jsencrypt
        - RSA

7. 缓存
    - pinia
        - 已手动添加缓存持久化，缓存刷新时保留

## 依赖地址

- [@element-plus/icons-vue](https://www.npmjs.com/package/@element-plus/icons-vue)
- [axios](https://www.npmjs.com/package/axios)
- [element-plus](https://www.npmjs.com/package/element-plus)
- [jsencrypt](https://www.npmjs.com/package/jsencrypt)
- [pinia](https://www.npmjs.com/package/pinia)
- [vue](https://www.npmjs.com/package/vue)
- [vue-router](https://www.npmjs.com/package/vue-router)

- [@typescript-eslint/eslint-plugin](https://www.npmjs.com/package/@typescript-eslint/eslint-plugin)
- [@typescript-eslint/parser](https://www.npmjs.com/package/@typescript-eslint/parser)
- [@vitejs/plugin-vue](https://www.npmjs.com/package/@vitejs/plugin-vue)
- [eslint](https://www.npmjs.com/package/eslint)
- [eslint-config-standard](https://www.npmjs.com/package/eslint-config-standard)
- [eslint-plugin-import](https://www.npmjs.com/package/eslint-plugin-import)
- [eslint-plugin-n](https://www.npmjs.com/package/eslint-plugin-n)
- [eslint-plugin-promise](https://www.npmjs.com/package/eslint-plugin-promise)
- [eslint-plugin-vue](https://www.npmjs.com/package/eslint-plugin-vue)
- [lint-staged](https://www.npmjs.com/package/lint-staged)
- [typescript](https://www.npmjs.com/package/typescript)
- [unplugin-auto-import](https://www.npmjs.com/package/unplugin-auto-import)
- [unplugin-vue-components](https://www.npmjs.com/package/unplugin-vue-components)
- [vite](https://www.npmjs.com/package/vite)
- [vue-tsc](https://www.npmjs.com/package/vue-tsc)

## 其他说明

- 使用 Maven 进行打包

```shell
mvn clean package
```

# Vue 3 + TypeScript + Vite

This template should help get you started developing with Vue 3 and TypeScript in Vite. The template uses Vue
3 `<script setup>` SFCs, check out
the [script setup docs](https://v3.vuejs.org/api/sfc-script-setup.html#sfc-script-setup) to learn more.

## Recommended IDE Setup

- [VS Code](https://code.visualstudio.com/) + [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar)

## Type Support For `.vue` Imports in TS

Since TypeScript cannot handle type information for `.vue` imports, they are shimmed to be a generic Vue component type
by default. In most cases this is fine if you don't really care about component prop types outside of templates.
However, if you wish to get actual prop types in `.vue` imports (for example to get props validation when using
manual `h(...)` calls), you can enable Volar's Take Over mode by following these steps:

1. Run `Extensions: Show Built-in Extensions` from VS Code's command palette, look
   for `TypeScript and JavaScript Language Features`, then right click and select `Disable (Workspace)`. By default,
   Take Over mode will enable itself if the default TypeScript extension is disabled.
2. Reload the VS Code window by running `Developer: Reload Window` from the command palette.

You can learn more about Take Over mode [here](https://github.com/johnsoncodehk/volar/discussions/471).
