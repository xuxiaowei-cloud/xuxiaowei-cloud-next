import axios from 'axios'

/**
 * ipv4
 */
export const ipv4 = function () {
  return axios.get('https://api.ipify.org', {
    withCredentials: false // Cookie
  }).then(ipv4 => {
    return ipv4.data
  })
}

/**
 * ipv6
 */
export const ipv6 = function () {
  return axios.get('https://api64.ipify.org', {
    withCredentials: false // Cookie
  }).then(ipv6 => {
    return ipv6.data
  })
}
