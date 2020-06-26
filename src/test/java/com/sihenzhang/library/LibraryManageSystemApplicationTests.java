package com.sihenzhang.library;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LibraryManageSystemApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void generatePassword() {
        String password = "123456";
        String salt = IdUtil.fastSimpleUUID();
        String encodedPassword = SecureUtil.md5(password + salt);
        System.out.println(encodedPassword);
        System.out.println(salt);
    }

}
