package com.example.demopro;

import com.example.demopro.bean.BlogBean;
import com.example.demopro.bean.UserBean;
import com.example.demopro.bean.UserRolesBean;
import com.example.demopro.service.Impl.BlogServiceImpl;
import com.example.demopro.service.Impl.RedisServiceImpl;
import com.example.demopro.service.Impl.UserRolesServiceImpl;
import com.example.demopro.service.Impl.UserServiceImpl;
import com.example.demopro.utils.JedisPoolUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoproApplicationTests {
    @Autowired
    JedisPoolUtils jedisPoolUtils;

    @Autowired
    RedisServiceImpl redisService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserRolesServiceImpl userRolesService;

    @Autowired
    BlogServiceImpl blogService;

    @Test
    void TestConfig() {
//        UserBean userBean = userService.GetUserByUserName("wanna");
//        System.out.println(userBean.getUsername());
        UserRolesBean userRolesBean = userRolesService.GetUserRolesByUserName("wanna");
        System.out.println(userRolesBean.getRoles());
    }
}
