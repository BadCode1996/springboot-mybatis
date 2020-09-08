package com.code.mybatis.service;

import com.code.mybatis.bean.Customer;

import java.util.List;

/**
 * @author Srd
 * @date 2020/9/7  2:17
 */
public interface CustomerService {

    /**
     * 查询所有Customer
     * @return
     */
    List<Customer> queryAll();

    /**
     * 保存Customer
     * @param customer
     */
    void saveCustomer(Customer customer);
}
