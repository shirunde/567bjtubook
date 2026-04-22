package org.example.bjtubook.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.bjtubook.entity.Book;
import org.example.bjtubook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        // 确保上传目录存在
        String uploadDir = "c:\\Users\\24189\\Desktop\\bjtubook\\frontend\\public\\images\\";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 生成唯一文件名
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

        // 保存文件
        File dest = new File(uploadDir + uniqueFilename);
        file.transferTo(dest);

        // 返回图片访问路径
        return "/images/" + uniqueFilename;
    }
}
