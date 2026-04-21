<template>
  <div class="home">
    <el-container>
      <el-header height="60px" class="header">
        <div class="logo">北京交通大学二手书交易平台</div>
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
      
      <el-main>
        <!-- 横幅 -->
        <div class="banner">
          <div class="banner-content">
            <h1>北京交通大学二手书交易平台</h1>
            <p>让知识在校园里流动，让资源得到充分利用</p>
          </div>
        </div>
        
        <!-- 搜索和筛选 -->
        <div class="search-filter">
          <div class="search-box">
            <el-input
              v-model="keyword"
              placeholder="搜索书籍..."
              prefix-icon="el-icon-search"
              style="width: 400px"
            >
              <template #append>
                <el-button type="primary" @click="search">搜索</el-button>
              </template>
            </el-input>
          </div>
          
          <!-- 分类筛选 -->
          <div class="filter-box">
            <el-select v-model="selectedType" placeholder="按类型筛选" @change="filterBooks">
              <el-option label="全部类型" value="" />
              <el-option label="教材" value="教材" />
              <el-option label="教辅" value="教辅" />
              <el-option label="课外书" value="课外书" />
              <el-option label="其他" value="其他" />
            </el-select>
            
            <el-select v-model="selectedCollege" placeholder="按学院筛选" @change="filterBooks">
              <el-option label="全部学院" value="" />
              <el-option label="电子信息工程学院" value="电子信息工程学院" />
              <el-option label="计算机与信息技术学院" value="计算机与信息技术学院" />
              <el-option label="经济管理学院" value="经济管理学院" />
              <el-option label="交通运输学院" value="交通运输学院" />
              <el-option label="机械与电子控制工程学院" value="机械与电子控制工程学院" />
            </el-select>
            
            <el-select v-model="selectedGrade" placeholder="按年级筛选" @change="filterBooks">
              <el-option label="全部年级" value="" />
              <el-option label="大一" value="大一" />
              <el-option label="大二" value="大二" />
              <el-option label="大三" value="大三" />
              <el-option label="大四" value="大四" />
              <el-option label="研究生" value="研究生" />
            </el-select>
          </div>
        </div>
        
        <!-- 书籍列表 -->
        <div class="book-list">
          <el-card
            v-for="book in books"
            :key="book.id"
            class="book-card"
            @click="goToDetail(book.id)"
            shadow="hover"
          >
            <template #header>
              <div class="book-title">{{ book.title }}</div>
            </template>
            <div class="book-info">
              <div class="book-meta">
                <span class="author">作者：{{ book.author }}</span>
                <span class="price">¥{{ book.price }}</span>
              </div>
              <div class="book-tags">
                <el-tag size="small" effect="plain">{{ book.type }}</el-tag>
                <el-tag size="small" effect="plain">{{ book.college }}</el-tag>
                <el-tag size="small" effect="plain">{{ book.grade }}</el-tag>
              </div>
              <p class="book-description">{{ book.description }}</p>
              <div class="book-footer">
                <span class="publish-time">{{ book.createdAt }}</span>
                <el-button type="primary" size="small" @click.stop="contactSeller(book.contact)">联系卖家</el-button>
              </div>
            </div>
          </el-card>
        </div>
        
        <!-- 分页 -->
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
      </el-main>
    </el-container>
  </div>
</template>

<script>
import { bookApi } from '../api/book'
import { ArrowDown } from '@element-plus/icons-vue'

export default {
  name: 'Home',
  components: {
    ArrowDown
  },
  data() {
    return {
      books: [],
      keyword: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      isLogin: false,
      isAdmin: false,
      username: '',
      selectedType: '',
      selectedCollege: '',
      selectedGrade: ''
    }
  },
  mounted() {
    this.loadBooks()
    this.checkLoginStatus()
  },
  methods: {
    loadBooks() {
      bookApi.getBooks(this.currentPage, this.pageSize)
        .then(response => {
          this.books = response.records || []
          this.total = response.total || 0
        })
        .catch(error => {
          console.error('加载书籍失败:', error)
          this.$message.error('加载书籍失败，请稍后重试')
        })
    },
    search() {
      if (this.keyword) {
        bookApi.searchBooks(this.keyword, this.currentPage, this.pageSize)
          .then(response => {
            this.books = response.records || []
            this.total = response.total || 0
          })
          .catch(error => {
            console.error('搜索书籍失败:', error)
            this.$message.error('搜索书籍失败，请稍后重试')
          })
      } else {
        this.loadBooks()
      }
    },
    filterBooks() {
      // 这里应该调用后端API进行筛选
      // 暂时使用前端筛选
      this.loadBooks()
    },
    goToDetail(id) {
      this.$router.push(`/book/${id}`)
    },
    contactSeller(contact) {
      this.$message.info(`卖家联系方式：${contact}`)
    },
    handleSizeChange(size) {
      this.pageSize = size
      this.loadBooks()
    },
    handleCurrentChange(current) {
      this.currentPage = current
      this.loadBooks()
    },
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
.home {
  min-height: 100vh;
  background-color: #f5f7fa;
}

.header {
  background-color: #409EFF;
  color: white;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.logo {
  font-size: 24px;
  font-weight: bold;
}

.nav {
  flex: 1;
  margin-left: 50px;
}

.banner {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 60px 0;
  text-align: center;
  margin-bottom: 30px;
  border-radius: 0 0 20px 20px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.banner-content h1 {
  font-size: 36px;
  font-weight: bold;
  margin-bottom: 10px;
  animation: fadeInDown 0.8s ease-in-out;
}

.banner-content p {
  font-size: 18px;
  opacity: 0.9;
  animation: fadeInUp 0.8s ease-in-out 0.2s both;
}

.search-filter {
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  margin-bottom: 30px;
  animation: fadeIn 0.8s ease-in-out 0.4s both;
}

.search-box {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.filter-box {
  display: flex;
  gap: 20px;
  justify-content: center;
  flex-wrap: wrap;
}

.filter-box .el-select {
  width: 200px;
}

.book-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.book-card {
  cursor: pointer;
  transition: all 0.3s ease;
  border-radius: 10px;
  overflow: hidden;
}

.book-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
}

.book-title {
  font-size: 18px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 10px;
}

.book-info {
  margin-top: 10px;
}

.book-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.author {
  color: #606266;
  font-size: 14px;
}

.price {
  color: #F56C6C;
  font-size: 18px;
  font-weight: bold;
}

.book-tags {
  margin-bottom: 10px;
  display: flex;
  flex-wrap: wrap;
  gap: 5px;
}

.book-description {
  margin-top: 10px;
  color: #606266;
  font-size: 14px;
  line-height: 1.4;
  height: 60px;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}

.book-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 15px;
  padding-top: 10px;
  border-top: 1px solid #f0f0f0;
}

.publish-time {
  color: #909399;
  font-size: 12px;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 30px;
  margin-bottom: 30px;
}

/* 动画 */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes fadeInDown {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
