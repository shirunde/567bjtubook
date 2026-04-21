<template>
  <div class="register">
    <el-container>
      <el-main>
        <div class="register-form">
          <h2>用户注册</h2>
          <el-form :model="registerForm" :rules="rules" ref="registerFormRef" label-width="80px">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="registerForm.username" placeholder="请输入用户名" />
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input
                v-model="registerForm.password"
                type="password"
                placeholder="请输入密码"
                show-password
              />
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
              <el-input
                v-model="registerForm.confirmPassword"
                type="password"
                placeholder="请确认密码"
                show-password
              />
            </el-form-item>
            <el-form-item label="昵称" prop="nickname">
              <el-input v-model="registerForm.nickname" placeholder="请输入昵称" />
            </el-form-item>
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="registerForm.phone" placeholder="请输入手机号" />
            </el-form-item>
            <el-form-item label="地址" prop="address">
              <el-input v-model="registerForm.address" placeholder="请输入地址" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="register" style="width: 100%">注册</el-button>
            </el-form-item>
            <el-form-item>
              <el-link type="primary" @click="goToLogin">已有账号？立即登录</el-link>
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
  name: 'Register',
  data() {
    return {
      registerForm: {
        username: '',
        password: '',
        confirmPassword: '',
        nickname: '',
        phone: '',
        address: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请确认密码', trigger: 'blur' },
          {
            validator: (rule, value, callback) => {
              if (value !== this.registerForm.password) {
                callback(new Error('两次输入的密码不一致'))
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ],
        nickname: [
          { required: true, message: '请输入昵称', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    register() {
      this.$refs.registerFormRef.validate((valid) => {
        if (valid) {
          const userData = {
            username: this.registerForm.username,
            password: this.registerForm.password,
            nickname: this.registerForm.nickname,
            phone: this.registerForm.phone,
            address: this.registerForm.address
          }
          
          authApi.register(userData)
            .then(response => {
              this.$message.success('注册成功')
              this.$router.push('/login')
            })
            .catch(error => {
              console.error('注册失败:', error)
              this.$message.error('注册失败，请稍后重试')
            })
        }
      })
    },
    goToLogin() {
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>
.register {
  min-height: 100vh;
  background-color: #f5f7fa;
}

.register-form {
  max-width: 400px;
  margin: 80px auto;
  padding: 30px;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.register-form h2 {
  text-align: center;
  margin-bottom: 30px;
  color: #303133;
}
</style>
