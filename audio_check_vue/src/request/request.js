//参考：https://developer.aliyun.com/article/1192031

import axios from 'axios'
import Vue from 'vue'

const request = axios.create({
  baseURL: 'http://localhost:8080/',
  timeout: 5000,
})

//请求拦截器
request.interceptors.request.use(
  function (config) {
    const token = sessionStorage.getItem("tokenValue") ? sessionStorage.getItem("tokenValue") : null
    if (token) {
      config.headers['token'] = token
    }
    return config
  },
  function (error) {
    Vue.prototype.$message.error('出错了')
    return Promise.reject(error)
  }
)

//返回拦截器
request.interceptors.response.use(
  function (response) {
    if(response.data.code != 200){
      Vue.prototype.$message.error(response.data.message)
      throw new Error()
    }
    return response.data
  },
  function (error) {
    Vue.prototype.$message.error(error.response.data.message)
    return Promise.reject(error)
  }
)
export default request
