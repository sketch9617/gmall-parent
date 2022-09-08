package com.atguigu.gmall.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author sketch
 * @date 2022/9/7 18:44
 * @description
 */
@Controller
public class LoginController {
    /**
     * 跳转登录页
     */
    @GetMapping("/login.html")
    public String loginPage(@RequestParam("originUrl") String originUrl, Model model){
        model.addAttribute("originUrl",originUrl);
        return "login";
    }
}
