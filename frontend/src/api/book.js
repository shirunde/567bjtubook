import api from './api'

export const bookApi = {
  // 获取书籍列表（分页）
  getBooks(page, size) {
    return api.get(`/book/page?page=${page}&size=${size}`)
  },
  // 搜索书籍
  searchBooks(keyword, page, size) {
    return api.get(`/book/page?page=${page}&size=${size}&keyword=${keyword}`)
  },
  // 获取书籍详情
  getBookDetail(id) {
    return api.get(`/book/${id}`)
  },
  // 发布书籍
  publishBook(data) {
    return api.post('/book', data)
  },
  // 更新书籍
  updateBook(data) {
    return api.put('/book', data)
  },
  // 删除书籍
  deleteBook(id) {
    return api.delete(`/book/${id}`)
  },
  // 获取用户发布的书籍
  getUserBooks(userId) {
    return api.get(`/book/user/${userId}`)
  }
}
