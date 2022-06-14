// TS 未能识别，其实不存在问题
// @ts-ignore
import CryptoJS from 'crypto-js'

/**
 * ASE 加密
 * @param key 秘钥
 * @param iv 偏移量/加盐
 * @param text 加密内容
 */
export const aes = function (key: string, iv: string, text: string) {
  // 十六位十六进制数作为密钥
  const SECRET_KEY = CryptoJS.enc.Utf8.parse(key)
  // 十六位十六进制数作为密钥偏移量/加盐
  const SECRET_IV = CryptoJS.enc.Utf8.parse(iv)

  const pwdEncode = CryptoJS.enc.Utf8.parse(text)
  const encryptedPassword = CryptoJS.AES.encrypt(pwdEncode, SECRET_KEY, {
    iv: SECRET_IV,
    mode: CryptoJS.mode.CBC,
    padding: CryptoJS.pad.Pkcs7
  })
  return CryptoJS.enc.Base64.stringify(encryptedPassword.ciphertext)
}
