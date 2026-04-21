<template>
  <el-header height="60px" class="header">
    <div class="logo">二手书交易平台</div>
    <div class="nav">
      <el-menu :router="true" mode="horizontal" default-active="/">
        <el-menu-item index="/">首页</el-menu-item>
        <el-menu-item index="/publish">发布书籍</el-menu-item>
        <el-menu-item index="/my-books">我的书籍</el-menu-item>
        <el-menu-item v-if="isAdmin" index="/admin">管理中心</el-menu-item>
        <el-menu-item v-if="!isLogin" index="/login">登录</el-menu-item>
        <el-menu-item v-if="!isLogin" index="/register">注册</el-menu-item>
        <el-menu-item v-else>
          <span>{{ username }}</span>
          <el-dropdown>
            <el-button type="text">
              <el-icon><arrow-down /></el-icon>
            </el-button>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </el-menu-item>
      </el-menu>
    </div>
  </el-header>
</template>

<script>
import { ArrowDown } from '@element-plus/icons-vue'

export default {
  name: 'Navbar',
  components: {
    ArrowDown
  },
  data() {
    return {
      isLogin: false,
      isAdmin: false,
      username: ''
    }
  },
  mounted() {
    this.checkLoginStatus()
  },
  methods: {
    checkLoginStatus() {
      const user = localStorage.getItem('user')
      if (user) {
        const userInfo = JSON.parse(user)
        this.isLogin = true
        this.username = userInfo.username
        this.isAdmin = userInfo.role === 1
      }
    },
    logout() {
      localStorage.removeItem('user')
      this.isLogin = false
      this.username = ''
      this.isAdmin = false
      this.$router.push('/')
      this.$message.success('退出登录成功')
    }
  }
}
</script>

<style scoped>
.header {
  background-color: #409EFF;
  color: white;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.logo {
  font-size: 24px;
  font-weight: bold;
}

.nav {
  flex: 1;
  margin-left: 50px;
}

.el-menu {
  background-color: transparent;
  border-bottom: none;
}

.el-menu-item {
  color: white;
}

.el-menu-item:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.el-menu-item.is-active {
  color: white;
  background-color: rgba(255, 255, 255, 0.2);
}
</style>
