package com.code.mybatis.mapper;

import com.code.mybatis.bean.Customer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Srd
 * @date 2020/9/7  1:54
 */
@Mapper
public interface CustomerMapper {

    /**
     * 保存Customer
     * @param customer
     */
    void saveCustomer(Customer customer);

    /**
     * 查询所有Customer
     * @return
     */
    List<Customer> queryAll();
}
