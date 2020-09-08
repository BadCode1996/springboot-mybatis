package com.code.mybatis.mapper;

import com.code.mybatis.bean.Customer;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Srd
 * @date 2020/9/7  1:54
 */
@Repository
public interface CustomerMapper extends Mapper<Customer> {

    /**
     * 保存Customer
     * @param customer
     */
//    @Insert("INSERT INTO customer(name, gender, telephone, address) VALUES(#{name},#{gender},#{telephone},#{address})")
//    void saveCustomer(Customer customer);

    /**
     * 查询所有Customer
     * @return
     */
//    @Select("select * from customer")
//    List<Customer> queryAll();
}
