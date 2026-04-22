<template>
  <div class="book-detail">
    <el-container>
      <Navbar />
      <el-main>
        <div class="detail-content">
          <el-card>
            <div class="book-detail-content">
              <div class="book-image">
                <img :src="book.image || '/images/default-book.jpg'" alt="书籍图片" />
              </div>
              <div class="book-info">
                <div class="book-title">{{ book.title }}</div>
                <div class="info-item">
                  <span class="label">作者：</span>
                  <span>{{ book.author }}</span>
                </div>
                <div class="info-item">
                  <span class="label">ISBN：</span>
                  <span>{{ book.isbn }}</span>
                </div>
                <div class="info-item">
                  <span class="label">价格：</span>
                  <span class="price">¥{{ book.price }}</span>
                </div>
                <div class="info-item">
                  <span class="label">版本：</span>
                  <span>{{ book.version }}</span>
                </div>
                <div class="info-item">
                  <span class="label">类型：</span>
                  <span>{{ book.type }}</span>
                </div>
                <div class="info-item">
                  <span class="label">学院：</span>
                  <span>{{ book.college }}</span>
                </div>
                <div class="info-item">
                  <span class="label">专业：</span>
                  <span>{{ book.major }}</span>
                </div>
                <div class="info-item">
                  <span class="label">年级：</span>
                  <span>{{ book.grade }}</span>
                </div>
                <div class="info-item">
                  <span class="label">状态：</span>
                  <span>{{ book.status === 0 ? '在售' : '已售出' }}</span>
                </div>
                <div class="info-item">
                  <span class="label">发布时间：</span>
                  <span>{{ book.createdAt }}</span>
                </div>
                <div class="info-item">
                  <span class="label">联系方式：</span>
                  <span class="contact">{{ book.contact }}</span>
                </div>
                <div class="info-item">
                  <span class="label">描述：</span>
                  <span class="description">{{ book.description }}</span>
                </div>
              </div>
            </div>
            <div class="action-buttons">
              <el-button type="primary" @click="contactSeller">联系卖家</el-button>
            </div>
          </el-card>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue'
import { bookApi } from '../api/book'

export default {
  name: 'BookDetail',
  components: {
    Navbar
  },
  data() {
    return {
      book: {
        id: 0,
        title: '',
        author: '',
        isbn: '',
        price: 0,
        description: '',
        status: 0,
        createdAt: ''
      }
    }
  },
  mounted() {
    this.loadBookDetail()
  },
  methods: {
    loadBookDetail() {
      const id = this.$route.params.id
      bookApi.getBookDetail(id)
        .then(response => {
          this.book = response
        })
        .catch(error => {
          console.error('加载书籍详情失败:', error)
          this.$message.error('加载书籍详情失败，请稍后重试')
        })
    },
    contactSeller() {
      this.$message.info(`卖家联系方式：${this.book.contact}`)
    }
  }
}
</script>

<style scoped>
.book-detail {
  min-height: 100vh;
  background-color: #f5f7fa;
}

.detail-content {
  max-width: 800px;
  margin: 20px auto;
}

.book-detail-content {
  display: flex;
  gap: 30px;
  margin-bottom: 30px;
}

.book-image {
  width: 300px;
  height: 400px;
  overflow: hidden;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.book-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.book-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}

.book-info {
  flex: 1;
}

.info-item {
  margin: 10px 0;
  line-height: 1.5;
}

.label {
  font-weight: bold;
  margin-right: 10px;
  color: #606266;
}

.price {
  color: #F56C6C;
  font-size: 18px;
  font-weight: bold;
}

.description {
  display: block;
  margin-top: 5px;
  line-height: 1.6;
  color: #606266;
}

.action-buttons {
  margin-top: 30px;
  display: flex;
  gap: 10px;
}
</style>
