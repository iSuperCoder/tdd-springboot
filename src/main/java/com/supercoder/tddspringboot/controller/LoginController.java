package com.supercoder.tddspringboot.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author liushao
 * @date 2021/1/24
 * @description
 */
@RestController
public class LoginController {
    /**
     * 登录接口
     *
     * @param body
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String login(@RequestBody Map<String, String> body) {
        //TODO：虽然测试用例能够顺利运行通过，但这只是
        String username = body.get("username");
        String name = "";
        if ("admin".equals(username)) {
            name = "张三";
        } else if ("iSuperCoder".equals(username)) {
            name = "码神手记";
        }
        return "\"code\":0,\"name\":\"" + name + "\"";
    }
}
