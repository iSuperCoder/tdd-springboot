package com.supercoder.tddspringboot.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginControllerTest {
    @Autowired
    private LoginController loginController;
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() throws Exception {
        assertThat(loginController).isNotNull();
    }

    /**
     * 登录请求成功的case，写case就相当于在进行接口规范设计
     * <p>
     * 输入正确的用户名和密码，返回登录成功的信息
     *
     * @param username 用户名
     * @param password 密码
     * @throws Exception
     */
    @ParameterizedTest
    @CsvSource({
            "admin,123456,张三",
            "iSuperCoder,123456,码神手记"
    })
    public void testLoginSuccess(String username, String password,String name) throws Exception {
        Map data = new HashMap();
        data.put("username", username);
        data.put("password", password);
        //POST请求登录接口
        ResponseEntity<String> resp = this.testRestTemplate.postForEntity("http://localhost:" + port + "/", data, String.class);
        HttpStatus status = resp.getStatusCode();
        MediaType mediaType = resp.getHeaders().getContentType();
        String body=resp.getBody();
        //断言1：返回Http状态码应当是200
        assertThat(HttpStatus.OK.equals(status)).isTrue();
        //断言2：ContentType应当时 application/json
        assertThat(MediaType.APPLICATION_JSON.equals(mediaType)).isTrue();
        //断言3：响应数据应包含 "code":0
        assertThat(body.contains("\"code\":0")).isTrue();
        //断言4：返回数据中应包括当前用户的姓名
        assertThat(body.contains("\"name\":\""+name+"\"")).isTrue();

    }
}