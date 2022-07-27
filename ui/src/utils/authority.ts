import { useStore } from '../store'

/**
 * 判断是否拥有某项权限
 * @param authority 权限
 */
export const hasAuthority = function (authority: string) {
  if (useStore.getAuthorities === undefined) {
    return false
  }
  return useStore.getAuthorities.indexOf(authority) !== -1
}

/**
 * 判断是否拥有某项权限
 * @param authoritys 权限
 */
export const hasAnyAuthority = function (authoritys: string[]) {
  if (useStore.getAuthorities === undefined) {
    return false
  }
  for (const i in authoritys) {
    if (useStore.getAuthorities.indexOf(authoritys[i]) !== -1) {
      return true
    }
  }
  return false
}
