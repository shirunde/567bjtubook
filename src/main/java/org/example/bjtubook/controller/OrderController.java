package org.example.bjtubook.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.bjtubook.entity.Order;
import org.example.bjtubook.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order create(@RequestBody Order order) {
        return orderService.create(order);
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable Integer id) {
        return orderService.getById(id);
    }

    @GetMapping("/buyer/{buyerId}")
    public List<Order> getByBuyerId(@PathVariable Integer buyerId) {
        return orderService.getByBuyerId(buyerId);
    }

    @GetMapping("/seller/{sellerId}")
    public List<Order> getBySellerId(@PathVariable Integer sellerId) {
        return orderService.getBySellerId(sellerId);
    }

    @PutMapping("/status")
    public Order updateStatus(@RequestParam Integer id, @RequestParam Integer status) {
        return orderService.updateStatus(id, status);
    }

    @GetMapping("/page")
    public IPage<Order> getPage(@RequestParam int page, @RequestParam int size) {
        return orderService.getPage(page, size);
    }
}
