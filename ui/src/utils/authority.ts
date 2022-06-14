import store from '../store'

/**
 * 判断是否拥有某项权限
 * @param authority 权限
 */
export const hasAuthority = function (authority: string) {
  if (store.getters.authorities === undefined) {
    return false
  }
  return store.getters.authorities.indexOf(authority) !== -1
}

/**
 * 判断是否拥有某项权限
 * @param authoritys 权限
 */
export const hasAnyAuthority = function (authoritys: string[]) {
  if (store.getters.authorities === undefined) {
    return false
  }
  for (const i in authoritys) {
    if (store.getters.authorities.indexOf(authoritys[i]) !== -1) {
      return true
    }
  }
  return false
}
