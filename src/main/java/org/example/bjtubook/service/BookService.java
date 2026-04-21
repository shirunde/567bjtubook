package org.example.bjtubook.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.bjtubook.entity.Book;

import java.util.List;

public interface BookService {
    Book add(Book book);
    Book getById(Integer id);
    List<Book> getByUserId(Integer userId);
    IPage<Book> getPage(int page, int size, String keyword);
    Book update(Book book);
    void delete(Integer id);
}
