package org.example.bjtubook.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.bjtubook.entity.Order;
import org.example.bjtubook.mapper.OrderMapper;
import org.example.bjtubook.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Order create(Order order) {
        // 设置默认状态为待支付
        order.setStatus(0);
        orderMapper.insert(order);
        return order;
    }

    @Override
    public Order getById(Integer id) {
        return orderMapper.selectById(id);
    }

    @Override
    public List<Order> getByBuyerId(Integer buyerId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("buyer_id", buyerId);
        return orderMapper.selectList(wrapper);
    }

    @Override
    public List<Order> getBySellerId(Integer sellerId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("seller_id", sellerId);
        return orderMapper.selectList(wrapper);
    }

    @Override
    public Order updateStatus(Integer id, Integer status) {
        Order order = orderMapper.selectById(id);
        if (order != null) {
            order.setStatus(status);
            orderMapper.updateById(order);
        }
        return order;
    }

    @Override
    public IPage<Order> getPage(int page, int size) {
        Page<Order> pageInfo = new Page<>(page, size);
        return orderMapper.selectPage(pageInfo, null);
    }
}
