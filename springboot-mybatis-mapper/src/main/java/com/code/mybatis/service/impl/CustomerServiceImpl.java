package com.code.mybatis.service.impl;

import com.code.mybatis.bean.Customer;
import com.code.mybatis.mapper.CustomerMapper;
import com.code.mybatis.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Srd
 * @date 2020/9/7  2:18
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 查询所有Customer
     * @return
     */
    @Override
    public List<Customer> queryAll() {
        return customerMapper.selectAll();
    }

    /**
     * 保存Customer
     * @param customer
     */
    @Override
    public void saveCustomer(Customer customer) {
        customerMapper.insert(customer);
    }
}
