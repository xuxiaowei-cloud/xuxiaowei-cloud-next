/**
 * 自动生成
 *
 * @author 徐晓伟 <xuxiaowei@xuxiaowei.com.cn>
 */

/**
 * 数字
 */
export const NUMBER = '0123456789'

/**
 * 小写字母
 */
export const LOWER_CASE = 'abcdefghijklmnopqrstuvwxyz'

/**
 * 大写字母
 */
export const UPPER_CASE = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'

/**
 * 符号
 */
export const SYMBOL = '!@#$%^&*()-_=+[{]};:,<.>/?'

/**
 * 生成密码选项
 */
interface PasswordOption {
  /**
   * 数字个数
   */
  number: Number

  /**
   * 小写字母个数
   */
  lowerCase: Number

  /**
   * 大写字母个数
   */
  upperCase: Number

  /**
   * 符号个数
   */
  symbol: Number

  /**
   * 补充长度
   */
  suppl: Number
}

/**
 * 字符串生成
 * @param chars
 * @param length
 */
const generate = (chars: string, length: Number) => {
  let password = ''
  for (let i = 0; i < length; i++) {
    password += chars.charAt(Math.floor(Math.random() * chars.length))
  }
  return password
}

/**
 * 生成密码
 * @param option
 */
export const randomPassword = (option: PasswordOption): any => {
  let password = ''

  password += generate(NUMBER, option.number)
  password += generate(LOWER_CASE, option.lowerCase)
  password += generate(UPPER_CASE, option.upperCase)
  password += generate(SYMBOL, option.symbol)
  password += generate(LOWER_CASE, option.suppl)

  // 随机排列
  return password.split('').sort(function () {
    return Math.random() - 0.5
  }).join('')
}
