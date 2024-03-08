//参考：https://developer.aliyun.com/article/1192031

import axios from 'axios'
import Vue from 'vue'

const request = axios.create({
  baseURL: 'http://localhost:8080/',
  timeout: 5000
})

//请求拦截器
request.interceptors.request.use(
  function (config) {
    return config
  },
  function (error) {
    Vue.prototype.$message.error('服务器请求错误')
    return Promise.reject(error)
  }
)

//返回拦截器
request.interceptors.response.use(
  function (response) {
    return response.data
  },
  function (error) {
    Vue.prototype.$message.error('服务器请求错误')
    return Promise.reject(error)
  }
)
export default request
