import request from '@/request/request'

export function login(name, password) {
  return request({
    url: 'login',
    method: 'POST',
    data: {
      name,
      password
    }
  })
}

export function register(name, password) {
  return request({
    url: 'register',
    method: 'POST',
    data: {
      name,
      password
    }
  })
}

export function modifyPassword(oldPassword, newPassword) {
  return request({
    url: 'modifyPassword',
    method: 'POST',
    data: {
      oldPassword,
      newPassword
    }
  })
}

export function checkLogin() {
  return request({
    url: 'checkLogin',
    method: 'POST',
  })
}

export function logout() {
  return request({
    url: 'logout',
    method: 'POST',    
  })
}

export function getRuleList() {
  return request({
    url: 'getRuleList',
    method: 'POST',
  })  
}

export function createRule(name, context, description) {
  return request({
    url: 'createRule',
    method: 'POST',
    data: {
      name,
      context,
      description
    }
  })  
}