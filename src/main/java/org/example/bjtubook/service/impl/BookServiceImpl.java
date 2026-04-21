package org.example.bjtubook.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.bjtubook.entity.Book;
import org.example.bjtubook.mapper.BookMapper;
import org.example.bjtubook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public Book add(Book book) {
        // 设置默认状态为在售
        book.setStatus(0);
        bookMapper.insert(book);
        return book;
    }

    @Override
    public Book getById(Integer id) {
        return bookMapper.selectById(id);
    }

    @Override
    public List<Book> getByUserId(Integer userId) {
        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return bookMapper.selectList(wrapper);
    }

    @Override
    public IPage<Book> getPage(int page, int size, String keyword) {
        Page<Book> pageInfo = new Page<>(page, size);
        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        
        // 如果有关键词，进行模糊查询
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like("title", keyword).or().like("author", keyword).or().like("isbn", keyword);
        }
        
        // 只查询在售的书籍
        wrapper.eq("status", 0);
        
        return bookMapper.selectPage(pageInfo, wrapper);
    }

    @Override
    public Book update(Book book) {
        bookMapper.updateById(book);
        return book;
    }

    @Override
    public void delete(Integer id) {
        bookMapper.deleteById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookMapper.selectList(null);
    }
}
