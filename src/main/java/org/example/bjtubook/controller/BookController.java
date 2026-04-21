package org.example.bjtubook.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.bjtubook.entity.Book;
import org.example.bjtubook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public Book add(@RequestBody Book book) {
        return bookService.add(book);
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable Integer id) {
        return bookService.getById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Book> getByUserId(@PathVariable Integer userId) {
        return bookService.getByUserId(userId);
    }

    @GetMapping("/page")
    public IPage<Book> getPage(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) String keyword) {
        return bookService.getPage(page, size, keyword);
    }

    @PutMapping
    public Book update(@RequestBody Book book) {
        return bookService.update(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        bookService.delete(id);
    }

    @GetMapping("/all")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
}
