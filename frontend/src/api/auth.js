import api from './api'

export const authApi = {
  // 注册
  register(data) {
    return api.post('/user/register', data)
  },
  // 登录
  login(username, password) {
    return api.post('/user/login', { username, password })
  },
  // 获取用户信息
  getUserInfo(id) {
    return api.get(`/user/${id}`)
  }
}
