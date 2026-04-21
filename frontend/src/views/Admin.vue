<template>
  <div class="admin">
    <el-container>
      <Navbar />
      <el-main>
        <div class="admin-content">
          <h2>管理中心</h2>
          
          <el-tabs v-model="activeTab">
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
                <el-table-column prop="createdAt" label="创建时间" />
                <el-table-column label="操作" width="150">
                  <template #default="scope">
                    <el-button type="danger" @click="banUser(scope.row.id)" :disabled="scope.row.role === 1">
                      封禁
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

export default {
  name: 'Admin',
  components: {
    Navbar
  },
  data() {
    return {
      activeTab: 'users',
      users: [
        { id: 1, username: 'admin', nickname: '管理员', phone: '13800138000', role: 1, createdAt: '2024-01-01 00:00:00' },
        { id: 2, username: 'user1', nickname: '用户1', phone: '13800138001', role: 0, createdAt: '2024-01-02 00:00:00' }
      ],
      books: [
        { id: 1, title: 'Java核心技术', author: 'Cay S. Horstmann', price: 50, status: 0, createdAt: '2024-01-01 00:00:00' },
        { id: 2, title: '数据结构与算法分析', author: 'Mark Allen Weiss', price: 60, status: 1, createdAt: '2024-01-02 00:00:00' }
      ]
    }
  },
  methods: {
    banUser(userId) {
      this.$confirm('确定要封禁该用户吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
      .then(() => {
        // 这里应该调用后端API封禁用户
        this.$message.success('用户已封禁')
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
        // 这里应该调用后端API下架书籍
        this.$message.success('书籍已下架')
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

.admin-content h2 {
  text-align: center;
  margin-bottom: 30px;
  color: #303133;
}
</style>