import CryptoJS from 'crypto-js'

/**
 * ASE 加密
 * @param key 秘钥
 * @param iv 偏移量/加盐
 * @param text 加密内容
 */
export const encrypt = function (key: string, iv: string, text: string) {
  const SECRET_KEY = CryptoJS.enc.Utf8.parse(key)
  const SECRET_IV = CryptoJS.enc.Latin1.parse(iv)
  const pwdEncode = CryptoJS.enc.Utf8.parse(text)
  const encryptedPassword = CryptoJS.AES.encrypt(pwdEncode, SECRET_KEY, {
    iv: SECRET_IV,
    mode: CryptoJS.mode.CBC,
    padding: CryptoJS.pad.Pkcs7
  })
  return CryptoJS.enc.Base64.stringify(encryptedPassword.ciphertext)
}

/**
 * ASE 解密
 * @param key 秘钥
 * @param iv 偏移量/加盐
 * @param text 解密内容
 */
export const decrypt = function (key: string, iv: string, text: string) {
  const SECRET_KEY = CryptoJS.enc.Utf8.parse(key)
  const SECRET_IV = CryptoJS.enc.Latin1.parse(iv)
  const decrypt = CryptoJS.AES.decrypt(text, SECRET_KEY, {
    iv: SECRET_IV,
    mode: CryptoJS.mode.CBC,
    padding: CryptoJS.pad.Pkcs7
  })
  // 解密完成，将解密后的数据放入响应数据的位置
  return decrypt.toString(CryptoJS.enc.Utf8)
}
