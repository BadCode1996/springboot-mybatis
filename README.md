# springboot-mybatis
SpringBoot整合Mybatis。使用了3中方式：xml配置文件，注解配置，通用mapper配置

## 使用配置文件整合Mybatis

### 创建CustomerMapper.xml配置文件

- 在resources目录下，新建 `mapper/CustomerMapper.xml`配置文件

  ~~~xml
  <?xml version="1.0" encoding="UTF-8"?>
  <!DOCTYPE mapper PUBLIC
          "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  <mapper namespace="com.code.mybatis.mapper.CustomerMapper">
      <insert id="saveCustomer" parameterType="com.code.mybatis.bean.Customer">
          INSERT INTO customer(
          name, gender, telephone, address)
          VALUES
          ( #{name}, #{gender}, #{telephone}, #{address});
      </insert>
  
      <select id="queryAll" resultType="com.code.mybatis.bean.Customer">
          select * from customer
      </select>
  </mapper>
  ~~~

### 修改application.yml

- 修改application.yml，添加配置Mybatis

  ~~~yml
  #mybatis的相关配置
  mybatis:
    #mapper配置文件路径
    mapper-locations: classpath:mapper/*.xml
    type-aliases-package: com.code.mybatis.bean
    #开启驼峰命名
    configuration:
      map-underscore-to-camel-case: true
  ~~~

### 运行Application启动器

使用Postman进行测试

- 测试接口：http://localhost:8080/cus/list

  ![image.png](https://i.loli.net/2020/09/08/KYf7Qc9L8XZNt2s.png)

- 测试接口：http://localhost:8080/cus/save ，图中

  ![image.png](https://i.loli.net/2020/09/08/ngSfazDAQMXubqT.png)

  查看数据库

  ![image.png](https://i.loli.net/2020/09/08/YoWAhDizBFSHlEJ.png)

  

## 有些懒，不想写这么麻烦的sqlMapper.xml配置文件怎么办

可以使用注解来进行sql配置

## 使用注解整合Mybatis

> 代码恢复到[准备数据](/posts/20090801.html/#准备数据)

### 修改CustomerMapper

修改CustomerMapper.java，添加`@insert`注解 和 `@Select`注解，并插入sql语句

~~~java
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
~~~

### 启动测试

略。。。

又少写了很多代码，开心😺

## 使用通用Mapper

### 通用Mapper介绍

地址：https://mapperhelper.github.io/docs/

![image.png](https://i.loli.net/2020/09/08/zr4cyHVhDaTNdv8.png)

### 使用通用Mapper

#### 引入依赖

首先在项目的 pom.xml 中添加通用 Mapper 依赖：

~~~xml
<dependency>
    <groupId>tk.mybatis</groupId>
    <artifactId>mapper-spring-boot-starter</artifactId>
    <version>2.1.5</version>
</dependency>
~~~



#### 修改Application 

修改Application ，修改MapperScan的包

注意官方的包名和这里 tk 包名的区别：

- **tk**.mybatis.spring.mapper.MapperScannerConfigurer
- **org**.mybatis.spring.mapper.MapperScannerConfigurer

只有第一部分从 **org** 换成了 **tk**。

~~~java
package com.code.sbmdemo;

// 修改MapperScan的包
//import org.mybatis.spring.annotation.MapperScan;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.code.sbmdemo.mapper")
public class SbmDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SbmDemoApplication.class, args);
    }
}
~~~

#### 修改实体类Customer

~~~java
// 使用@Table(name = "tableName")进行指定表名
@Table(name = "customer")
public class Customer implements Serializable {

    // 给主键添加了 @Id，标记该字段为数据库主键
    @Id
    // 这个注解适用于主键自增的情况
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private char gender;
    private String telephone;
    private String address;
    // get and set
}
~~~

<div class="snote error">
    <p>id使用了<code>int</code>类型， gender使用了<code>char</code>类型，会导致出错，换成包装类型</p>
    <p>int --> Integer, char --> Character</p>
</div>

查询时出错：id全为0，gender乱码，因为使用了基本数据类型，而应该使用包装类型

![image.png](https://i.loli.net/2020/09/08/WkKAOm6ZeE3IwDB.png)

#### 修改CustomerServiceImpl

删除了CustomerMapper内的自定义的方法，使用了通用Mapper提供的方法

~~~java
package com.code.sbmdemo.service.impl;

import com.code.sbmdemo.bean.Customer;
import com.code.sbmdemo.mapper.CustomerMapper;
import com.code.sbmdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
//        return customerMapper.queryAll();
        return customerMapper.selectAll();
    }

    /**
     * 保存Customer
     * @param customer
     */
    @Override
    public void saveCustomer(Customer customer) {
//        customerMapper.saveCustomer(customer);
        customerMapper.insert(customer);
    }
}
~~~



#### 修改CustomerMapper

删除了自定义的方法，接口继承`tk.mybatis.mapper.common.Mapper`

~~~java
package com.code.sbmdemo.mapper;

import com.code.sbmdemo.bean.Customer;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface CustomerMapper extends Mapper<Customer> {
}
~~~

## 启动测试

![image.png](https://i.loli.net/2020/09/08/pufct4MDnLgek1d.png)
