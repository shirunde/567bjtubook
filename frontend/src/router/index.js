import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/Home.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '/book/:id',
    name: 'BookDetail',
    component: () => import('../views/BookDetail.vue')
  },
  {
    path: '/publish',
    name: 'PublishBook',
    component: () => import('../views/PublishBook.vue')
  },
  {
    path: '/my-books',
    name: 'MyBooks',
    component: () => import('../views/MyBooks.vue')
  },
  {
    path: '/buyer-orders',
    name: 'BuyerOrders',
    component: () => import('../views/BuyerOrders.vue')
  },
  {
    path: '/seller-orders',
    name: 'SellerOrders',
    component: () => import('../views/SellerOrders.vue')
  },
  {
    path: '/admin',
    name: 'Admin',
    component: () => import('../views/Admin.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
