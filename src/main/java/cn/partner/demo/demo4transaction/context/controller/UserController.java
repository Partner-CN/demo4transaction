/**
 * Created by Yonsin on 17/12/22.
 */
package cn.partner.demo.demo4transaction.context.controller;

import cn.partner.demo.demo4transaction.context.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private @Autowired UserService userService;

    @GetMapping("/")
    public String test() {
        return userService.process();
    }

}
