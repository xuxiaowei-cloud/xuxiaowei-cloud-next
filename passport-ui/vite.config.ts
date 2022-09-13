import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
// @ts-ignore
import { resolve } from 'path'

// https://github.com/unocss/unocss/#vite
import Unocss from 'unocss/vite'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    AutoImport({
      resolvers: [ElementPlusResolver()]
    }),
    Components({
      resolvers: [ElementPlusResolver()]
    }),
    Unocss({ /* options */ })
  ],
  resolve: {
    alias: {
      '@': resolve('./src')
    }
  },
  build: {
    minify: 'terser',
    terserOptions: {
      compress: {
        // 生产环境移除console日志
        drop_console: true,
        // 生产环境移除debugger测试
        drop_debugger: true
      }
    },
    // 构建后是否生成 source map 文件。默认： false
    sourcemap: false,
    // 指定生成静态资源的存放路径（相对于 build.outDir）。默认： assets
    assetsDir: 'passport-assets',
    rollupOptions: {
      output: {
        // entryFileNames: `assets/[name].${timestamp}.js`,
        // chunkFileNames: `assets/[name].${timestamp}.js`,
        // assetFileNames: `assets/[name].${timestamp}.[ext]`
        entryFileNames: 'passport-assets/[name].js',
        chunkFileNames: 'passport-assets/[name].js',
        assetFileNames: 'passport-assets/[name].[ext]'
      }
    }
  },
  server: {
    // 代理
    proxy: {
      '/baidu': {
        target: 'https://www.baidu.com',
        // 将主机标头的来源更改为目标 URL。
        changeOrigin: true,
        // 重写地址
        rewrite: (path) => path.replace(/^\/baidu/, '')
      }
    }
  }
})
