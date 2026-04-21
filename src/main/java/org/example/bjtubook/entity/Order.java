package org.example.bjtubook.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("orders")
public class Order {
    private Integer id;
    private Integer bookId;
    private Integer buyerId;
    private Integer sellerId;
    private Double price;
    private Integer status;
    private Date createdAt;
}
