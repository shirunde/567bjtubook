import api from './api'

export const orderApi = {
  // 创建订单
  createOrder(data) {
    return api.post('/order', data)
  },
  // 获取订单详情
  getOrderDetail(id) {
    return api.get(`/order/${id}`)
  },
  // 获取买家订单列表
  getBuyerOrders(buyerId) {
    return api.get(`/order/buyer/${buyerId}`)
  },
  // 获取卖家订单列表
  getSellerOrders(sellerId) {
    return api.get(`/order/seller/${sellerId}`)
  },
  // 更新订单状态
  updateOrderStatus(id, status) {
    return api.put(`/order/status?id=${id}&status=${status}`)
  },
  // 获取订单列表（分页）
  getOrders(page, size) {
    return api.get(`/order/page?page=${page}&size=${size}`)
  }
}
