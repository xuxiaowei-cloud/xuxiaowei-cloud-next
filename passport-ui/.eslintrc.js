module.exports = {
  root: true,
  env: {
    node: true
  },
  globals: {
    // 'defineProps' is not defined  no-undef
    defineProps: 'readonly'
  },
  extends: [
    'plugin:vue/vue3-essential',
    '@vue/standard'
  ],
  parserOptions: {
    ecmaVersion: 2020
  },
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    // 强制在关键字前后使用空格
    'keyword-spacing': 0
  }
}
