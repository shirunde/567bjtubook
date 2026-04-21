<template>
  <div class="admin">
    <el-container>
      <Navbar />
      <el-main>
        <div class="admin-content">
          <div class="header">
            <h2>管理中心</h2>
            <el-button type="primary" @click="refreshData">
              <el-icon><Refresh /></el-icon>
              刷新数据
            </el-button>
          </div>
          
          <el-tabs v-model="activeTab" @tab-change="handleTabChange">
            <el-tab-pane label="用户管理" name="users">
              <el-table :data="users" style="width: 100%">
                <el-table-column prop="id" label="ID" width="80" />
                <el-table-column prop="username" label="用户名" />
                <el-table-column prop="nickname" label="昵称" />
                <el-table-column prop="phone" label="手机号" />
                <el-table-column prop="role" label="角色">
                  <template #default="scope">
                    {{ scope.row.role === 1 ? '管理员' : '普通用户' }}
                  </template>
                </el-table-column>
                <el-table-column prop="banStatus" label="状态">
                  <template #default="scope">
                    <el-tag :type="scope.row.banStatus === 1 ? 'danger' : 'success'">
                      {{ scope.row.banStatus === 1 ? '已封禁' : '正常' }}
                    </el-tag>
                  </template>
                </el-table-column>
                <el-table-column prop="createdAt" label="创建时间" />
                <el-table-column label="操作" width="150">
                  <template #default="scope">
                    <el-button 
                      :type="scope.row.banStatus === 1 ? 'success' : 'danger'" 
                      @click="toggleBan(scope.row.id, scope.row.banStatus)" 
                      :disabled="scope.row.role === 1"
                    >
                      {{ scope.row.banStatus === 1 ? '解封' : '封禁' }}
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-tab-pane>
            
            <el-tab-pane label="书籍管理" name="books">
              <el-table :data="books" style="width: 100%">
                <el-table-column prop="id" label="ID" width="80" />
                <el-table-column prop="title" label="书名" />
                <el-table-column prop="author" label="作者" />
                <el-table-column prop="price" label="价格" />
                <el-table-column prop="status" label="状态">
                  <template #default="scope">
                    {{ scope.row.status === 0 ? '在售' : '已售出' }}
                  </template>
                </el-table-column>
                <el-table-column prop="createdAt" label="创建时间" />
                <el-table-column label="操作" width="150">
                  <template #default="scope">
                    <el-button type="danger" @click="removeBook(scope.row.id)">
                      下架
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue'
import { Refresh } from '@element-plus/icons-vue'
import api from '../api/api'

export default {
  name: 'Admin',
  components: {
    Navbar,
    Refresh
  },
  data() {
    return {
      activeTab: 'users',
      users: [],
      books: []
    }
  },
  mounted() {
    this.loadUsers()
    this.loadBooks()
  },
  methods: {
    loadUsers() {
      api.get('/user/all')
        .then(response => {
          this.users = response || []
        })
        .catch(error => {
          console.error('加载用户列表失败:', error)
          this.$message.error('加载用户列表失败，请稍后重试')
        })
    },
    loadBooks() {
      api.get('/book/all')
        .then(response => {
          this.books = response || []
        })
        .catch(error => {
          console.error('加载书籍列表失败:', error)
          this.$message.error('加载书籍列表失败，请稍后重试')
        })
    },
    refreshData() {
      this.loadUsers()
      this.loadBooks()
      this.$message.success('数据刷新成功')
    },
    handleTabChange() {
      // 切换标签时刷新对应数据
      if (this.activeTab === 'users') {
        this.loadUsers()
      } else if (this.activeTab === 'books') {
        this.loadBooks()
      }
    },
    toggleBan(userId, currentBanStatus) {
      const newBanStatus = currentBanStatus === 1 ? 0 : 1
      const action = newBanStatus === 1 ? '封禁' : '解封'
      
      this.$confirm(`确定要${action}该用户吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
      .then(() => {
        api.put(`/user/ban/${userId}?banStatus=${newBanStatus}`)
          .then(() => {
            this.$message.success(`用户已${action}`)
            this.loadUsers() // 刷新用户列表
          })
          .catch(error => {
            console.error(`${action}用户失败:`, error)
            this.$message.error(`${action}用户失败，请稍后重试`)
          })
      })
      .catch(() => {
        this.$message.info('已取消操作')
      })
    },
    removeBook(bookId) {
      this.$confirm('确定要下架该书籍吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
      .then(() => {
        api.delete(`/book/${bookId}`)
          .then(() => {
            this.$message.success('书籍已下架')
            this.loadBooks() // 刷新书籍列表
          })
          .catch(error => {
            console.error('下架书籍失败:', error)
            this.$message.error('下架书籍失败，请稍后重试')
          })
      })
      .catch(() => {
        this.$message.info('已取消操作')
      })
    }
  }
}
</script>

<style scoped>
.admin {
  min-height: 100vh;
  background-color: #f5f7fa;
}

.admin-content {
  max-width: 1200px;
  margin: 20px auto;
  padding: 20px;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.admin-content h2 {
  text-align: left;
  margin: 0;
  color: #303133;
}
</style>
