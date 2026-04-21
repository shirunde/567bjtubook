<template>
  <div class="seller-orders">
    <el-container>
      <Navbar />
      <el-main>
        <div class="page-title">
          <h2>卖家订单</h2>
        </div>
        
        <el-card>
          <el-table :data="orders" style="width: 100%">
            <el-table-column prop="id" label="订单ID" width="100" />
            <el-table-column label="书籍" width="200">
              <template #default="scope">
                {{ scope.row.bookTitle }}
              </template>
            </el-table-column>
            <el-table-column prop="price" label="价格" width="100">
              <template #default="scope">
                ¥{{ scope.row.price }}
              </template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="120">
              <template #default="scope">
                <el-tag :type="getStatusType(scope.row.status)">
                  {{ getStatusText(scope.row.status) }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="createdAt" label="创建时间" width="180" />
            <el-table-column label="操作" width="200">
              <template #default="scope">
                <el-button type="primary" size="small" @click="viewOrder(scope.row.id)">查看</el-button>
                <el-button v-if="scope.row.status === 1" type="success" size="small" @click="markShipped(scope.row.id)">标记发货</el-button>
              </template>
            </el-table-column>
          </el-table>
          
          <div class="pagination">
            <el-pagination
              v-model:current-page="currentPage"
              :page-size="pageSize"
              :page-sizes="[10, 20, 50]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
            />
          </div>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue'
import { orderApi } from '../api/order'

export default {
  name: 'SellerOrders',
  components: {
    Navbar
  },
  data() {
    return {
      orders: [],
      currentPage: 1,
      pageSize: 10,
      total: 0
    }
  },
  mounted() {
    this.loadSellerOrders()
  },
  methods: {
    loadSellerOrders() {
      const user = localStorage.getItem('user')
      if (user) {
        const userInfo = JSON.parse(user)
        orderApi.getSellerOrders(userInfo.id)
          .then(response => {
            this.orders = response || []
            this.total = response.length || 0
          })
          .catch(error => {
            console.error('加载订单失败:', error)
            this.$message.error('加载订单失败，请稍后重试')
          })
      } else {
        this.$message.error('请先登录')
        this.$router.push('/login')
      }
    },
    viewOrder(id) {
      this.$router.push(`/order/${id}`)
    },
    markShipped(id) {
      this.$confirm('确定要标记发货吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          orderApi.updateOrderStatus(id, 2) // 2表示已发货
            .then(() => {
              this.$message.success('标记发货成功')
              this.loadSellerOrders()
            })
            .catch(error => {
              console.error('标记发货失败:', error)
              this.$message.error('标记发货失败，请稍后重试')
            })
        })
        .catch(() => {
          // 取消标记
        })
    },
    getStatusType(status) {
      const typeMap = {
        0: 'info',
        1: 'primary',
        2: 'warning',
        3: 'success',
        4: 'success'
      }
      return typeMap[status] || 'info'
    },
    getStatusText(status) {
      const textMap = {
        0: '待支付',
        1: '已支付',
        2: '已发货',
        3: '已收货',
        4: '已完成'
      }
      return textMap[status] || '未知状态'
    },
    handleSizeChange(size) {
      this.pageSize = size
      this.loadSellerOrders()
    },
    handleCurrentChange(current) {
      this.currentPage = current
      this.loadSellerOrders()
    }
  }
}
</script>

<style scoped>
.seller-orders {
  min-height: 100vh;
  background-color: #f5f7fa;
}

.page-title {
  margin: 20px 0;
}

.page-title h2 {
  color: #303133;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>
