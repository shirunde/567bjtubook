package org.example.bjtubook.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("books")
public class Book {
    private Integer id;
    private String title;
    private String author;
    private String isbn;
    private Double price;
    private String description;
    private Integer status;
    @TableField("user_id")
    private Integer userId;
    @TableField("created_at")
    private Date createdAt;
    private String contact;
    private String version;
    private String type;
    private String college;
    private String major;
    private String grade;
    private String image;
}
