<template>
  <div class="my-books">
    <el-container>
      <Navbar />
      <el-main>
        <div class="page-title">
          <h2>我的书籍</h2>
        </div>
        
        <el-card>
          <el-table :data="books" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="title" label="书名" />
            <el-table-column prop="author" label="作者" width="120" />
            <el-table-column prop="price" label="价格" width="100">
              <template #default="scope">
                ¥{{ scope.row.price }}
              </template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="scope.row.status === 0 ? 'success' : 'info'">
                  {{ scope.row.status === 0 ? '在售' : '已售出' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="createdAt" label="发布时间" width="180" />
            <el-table-column label="操作" width="200">
              <template #default="scope">
                <el-button type="primary" size="small" @click="editBook(scope.row.id)">编辑</el-button>
                <el-button type="danger" size="small" @click="deleteBook(scope.row.id)">删除</el-button>
                <el-button v-if="scope.row.status === 0" type="success" size="small" @click="markSold(scope.row.id)">标记已售</el-button>
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
import { bookApi } from '../api/book'

export default {
  name: 'MyBooks',
  components: {
    Navbar
  },
  data() {
    return {
      books: [],
      currentPage: 1,
      pageSize: 10,
      total: 0
    }
  },
  mounted() {
    this.loadMyBooks()
  },
  methods: {
    loadMyBooks() {
      const user = localStorage.getItem('user')
      if (user) {
        const userInfo = JSON.parse(user)
        bookApi.getUserBooks(userInfo.id)
          .then(response => {
            this.books = response || []
            this.total = response.length || 0
          })
          .catch(error => {
            console.error('加载我的书籍失败:', error)
            this.$message.error('加载我的书籍失败，请稍后重试')
          })
      } else {
        this.$message.error('请先登录')
        this.$router.push('/login')
      }
    },
    editBook(id) {
      this.$message.info('编辑功能开发中')
    },
    deleteBook(id) {
      this.$confirm('确定要删除这本书吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          bookApi.deleteBook(id)
            .then(() => {
              this.$message.success('删除成功')
              this.loadMyBooks()
            })
            .catch(error => {
              console.error('删除书籍失败:', error)
              this.$message.error('删除书籍失败，请稍后重试')
            })
        })
        .catch(() => {
          // 取消删除
        })
    },
    markSold(id) {
      this.$confirm('确定要标记这本书为已售出吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          bookApi.updateBook({ id, status: 1 })
            .then(() => {
              this.$message.success('标记成功')
              this.loadMyBooks()
            })
            .catch(error => {
              console.error('标记已售失败:', error)
              this.$message.error('标记已售失败，请稍后重试')
            })
        })
        .catch(() => {
          // 取消标记
        })
    },
    handleSizeChange(size) {
      this.pageSize = size
      this.loadMyBooks()
    },
    handleCurrentChange(current) {
      this.currentPage = current
      this.loadMyBooks()
    }
  }
}
</script>

<style scoped>
.my-books {
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
