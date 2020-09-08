package com.code.mybatis.mapper;

import com.code.mybatis.bean.Customer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
    @Insert("INSERT INTO customer(name, gender, telephone, address) VALUES(#{name},#{gender},#{telephone},#{address})")
    void saveCustomer(Customer customer);

    /**
     * 查询所有Customer
     * @return
     */
    @Select("select * from customer")
    List<Customer> queryAll();
}
