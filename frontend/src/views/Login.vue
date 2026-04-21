<template>
  <div class="login">
    <el-container>
      <el-main>
        <div class="login-form">
          <h2>用户登录</h2>
          <el-form :model="loginForm" :rules="rules" ref="loginFormRef" label-width="80px">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="loginForm.username" placeholder="请输入用户名" />
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input
                v-model="loginForm.password"
                type="password"
                placeholder="请输入密码"
                show-password
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="login" style="width: 100%">登录</el-button>
            </el-form-item>
            <el-form-item>
              <el-link type="primary" @click="goToRegister">没有账号？立即注册</el-link>
            </el-form-item>
          </el-form>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import { authApi } from '../api/auth'

export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    login() {
      this.$refs.loginFormRef.validate((valid) => {
        if (valid) {
          authApi.login(this.loginForm.username, this.loginForm.password)
            .then(response => {
              if (response) {
                localStorage.setItem('user', JSON.stringify(response))
                this.$message.success('登录成功')
                this.$router.push('/')
              } else {
                this.$message.error('用户名或密码错误')
              }
            })
            .catch(error => {
              console.error('登录失败:', error)
              this.$message.error('登录失败，请稍后重试')
            })
        }
      })
    },
    goToRegister() {
      this.$router.push('/register')
    }
  }
}
</script>

<style scoped>
.login {
  min-height: 100vh;
  background-color: #f5f7fa;
}

.login-form {
  max-width: 400px;
  margin: 100px auto;
  padding: 30px;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.login-form h2 {
  text-align: center;
  margin-bottom: 30px;
  color: #303133;
}
</style>
