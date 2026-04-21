package org.example.bjtubook.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.bjtubook.entity.Order;

import java.util.List;

public interface OrderService {
    Order create(Order order);
    Order getById(Integer id);
    List<Order> getByBuyerId(Integer buyerId);
    List<Order> getBySellerId(Integer sellerId);
    Order updateStatus(Integer id, Integer status);
    IPage<Order> getPage(int page, int size);
}
