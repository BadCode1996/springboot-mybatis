package com.code.mybatis.controller;

import com.code.mybatis.bean.Customer;
import com.code.mybatis.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Srd
 * @date 2020/9/7  2:14
 */
@RestController
@RequestMapping("cus")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("list")
    public List<Customer> queryAll(){
        return customerService.queryAll();
    }

    @PostMapping("save")
    public void saveCustomer(Customer customer){
        System.out.println("customer = " + customer);
        customerService.saveCustomer(customer);
    }
}
