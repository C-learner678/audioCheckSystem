import request from '@/request/request'

export function hello() {
  return request({
    url: 'hello',
    method: 'GET'
  })
}

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

export function modifyPassword(name, oldPassword, newPassword) {
  return request({
    url: 'login',
    method: 'POST',
    data: {
      name,
      oldPassword,
      newPassword
    }
  })
}