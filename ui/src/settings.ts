/**
 * 设置
 */
const settings = {
  state: {
    /**
     * 正常代码
     */
    okCode: '000000',
    /**
     * 登录页面
     */
    loginPage: import.meta.env.VITE_APP_LOGIN_PAGE,
    /**
     * 登录请求代码（未授权代码）
     */
    loginRequiredCode: [
      // 未进行身份认证
      'T00000',
      // 不允许访问
      'T00002',

      // 令牌异常
      'T10000',
      // 令牌未识别
      'T10001',
      // 令牌已过期
      'T10002',
      // 令牌缺失
      'T10003',
      // 令牌无效
      'T10004'
    ],
    /**
     * 路由切换时 检查Token 时间间隔（单位：毫秒）
     *
     * -1：不检查
     * 0：每次都检查
     * > 0：按照时间间隔查询
     */
    checkTokenInterval: -1
  },
  mutations: {},
  actions: {}
}

export default settings
