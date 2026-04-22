<template>
  <div class="publish-book">
    <el-container>
      <Navbar />
      <el-main>
        <div class="publish-form">
          <h2>发布二手书</h2>
          <el-form :model="bookForm" :rules="rules" ref="bookFormRef" label-width="100px">
            <el-form-item label="书名" prop="title">
              <el-input v-model="bookForm.title" placeholder="请输入书名" />
            </el-form-item>
            <el-form-item label="作者" prop="author">
              <el-input v-model="bookForm.author" placeholder="请输入作者" />
            </el-form-item>
            <el-form-item label="ISBN" prop="isbn">
              <el-input v-model="bookForm.isbn" placeholder="请输入ISBN" />
            </el-form-item>
            <el-form-item label="价格" prop="price">
              <el-input-number v-model="bookForm.price" :min="0" :step="0.01" placeholder="请输入价格" />
            </el-form-item>
            <el-form-item label="版本" prop="version">
              <el-input v-model="bookForm.version" placeholder="请输入版本" />
            </el-form-item>
            <el-form-item label="类型" prop="type">
              <el-select v-model="bookForm.type" placeholder="请选择类型">
                <el-option label="教材" value="教材" />
                <el-option label="教辅" value="教辅" />
                <el-option label="课外书" value="课外书" />
                <el-option label="其他" value="其他" />
              </el-select>
            </el-form-item>
            <el-form-item label="学院" prop="college">
              <el-select v-model="bookForm.college" placeholder="请选择学院">
                <el-option label="电子信息工程学院" value="电子信息工程学院" />
                <el-option label="计算机与信息技术学院" value="计算机与信息技术学院" />
                <el-option label="经济管理学院" value="经济管理学院" />
                <el-option label="交通运输学院" value="交通运输学院" />
                <el-option label="机械与电子控制工程学院" value="机械与电子控制工程学院" />
                <el-option label="土木建筑工程学院" value="土木建筑工程学院" />
                <el-option label="自动化与智能学院" value="自动化与智能学院" />
                <el-option label="物理科学与工程学院" value="物理科学与工程学院" />
                <el-option label="数学与统计学院" value="数学与统计学院" />
                <el-option label="外国语学院" value="外国语学院" />
                <el-option label="法学院" value="法学院" />
                <el-option label="马克思主义学院" value="马克思主义学院" />
                <el-option label="人文学院" value="人文学院" />
                <el-option label="软件学院" value="软件学院" />
                <el-option label="网络空间安全学院" value="网络空间安全学院" />
                <el-option label="环境学院" value="环境学院" />
                <el-option label="生命科学与生物工程学院" value="生命科学与生物工程学院" />
                <el-option label="设计学院" value="设计学院" />
                <el-option label="詹天佑学院" value="詹天佑学院" />
                <el-option label="国际教育学院" value="国际教育学院" />
                <el-option label="继续教育学院" value="继续教育学院" />
              </el-select>
            </el-form-item>
            <el-form-item label="专业" prop="major">
              <el-input v-model="bookForm.major" placeholder="请输入专业" />
            </el-form-item>
            <el-form-item label="年级" prop="grade">
              <el-select v-model="bookForm.grade" placeholder="请选择年级">
                <el-option label="大一" value="大一" />
                <el-option label="大二" value="大二" />
                <el-option label="大三" value="大三" />
                <el-option label="大四" value="大四" />
                <el-option label="研究生" value="研究生" />
              </el-select>
            </el-form-item>
            <el-form-item label="联系方式" prop="contact">
              <el-input v-model="bookForm.contact" placeholder="请输入联系方式（微信/电话）" />
            </el-form-item>
            <el-form-item label="描述" prop="description">
              <el-input
                v-model="bookForm.description"
                type="textarea"
                :rows="4"
                placeholder="请输入书籍描述"
              />
            </el-form-item>
            <el-form-item label="书籍图片">
              <el-upload
                class="upload-demo"
                action="/api/book/upload"
                :on-success="handleImageUpload"
                :on-error="handleImageUploadError"
                :show-file-list="true"
                :auto-upload="true"
                :limit="1"
                :file-list="imageList"
              >
                <el-button size="small" type="primary">点击上传</el-button>
                <template #tip>
                  <div class="el-upload__tip">
                    请上传书籍封面图片，支持 JPG、PNG 格式
                  </div>
                </template>
              </el-upload>
              <div v-if="bookForm.image" class="image-preview">
                <img :src="bookForm.image" alt="书籍图片" style="width: 100px; height: 100px; object-fit: cover;" />
              </div>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="publish">发布书籍</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue'
import { bookApi } from '../api/book'

export default {
  name: 'PublishBook',
  components: {
    Navbar
  },
  data() {
    return {
      bookForm: {
        title: '',
        author: '',
        isbn: '',
        price: 0,
        version: '',
        type: '',
        college: '',
        major: '',
        grade: '',
        contact: '',
        description: '',
        image: ''
      },
      imageList: [],
      rules: {
        title: [
          { required: true, message: '请输入书名', trigger: 'blur' }
        ],
        author: [
          { required: true, message: '请输入作者', trigger: 'blur' }
        ],
        price: [
          { required: true, message: '请输入价格', trigger: 'blur' }
        ],
        version: [
          { required: true, message: '请输入版本', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请选择类型', trigger: 'change' }
        ],
        college: [
          { required: true, message: '请选择学院', trigger: 'change' }
        ],
        major: [
          { required: true, message: '请输入专业', trigger: 'blur' }
        ],
        grade: [
          { required: true, message: '请选择年级', trigger: 'change' }
        ],
        contact: [
          { required: true, message: '请输入联系方式', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入描述', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    handleImageUpload(response) {
      this.bookForm.image = response;
      this.imageList = [{ url: response }];
      this.$message.success('图片上传成功');
    },
    handleImageUploadError(error) {
      this.$message.error('图片上传失败，请稍后重试');
      console.error('图片上传失败:', error);
    },
    publish() {
      this.$refs.bookFormRef.validate((valid) => {
        if (valid) {
          const user = localStorage.getItem('user')
          if (user) {
            const userInfo = JSON.parse(user)
            const bookData = {
              ...this.bookForm,
              userId: userInfo.id
            }
            
            bookApi.publishBook(bookData)
              .then(response => {
                this.$message.success('书籍发布成功')
                this.$router.push('/my-books')
              })
              .catch(error => {
                console.error('发布书籍失败:', error)
                this.$message.error('发布书籍失败，请稍后重试')
              })
          } else {
            this.$message.error('请先登录')
            this.$router.push('/login')
          }
        }
      })
    }
  }
}
</script>

<style scoped>
.publish-book {
  min-height: 100vh;
  background-color: #f5f7fa;
}

.publish-form {
  max-width: 600px;
  margin: 30px auto;
  padding: 30px;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.publish-form h2 {
  text-align: center;
  margin-bottom: 30px;
  color: #303133;
}
</style>