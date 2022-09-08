package com.atguigu.gmall.user.service;

import com.atguigu.gmall.model.user.UserInfo;
import com.atguigu.gmall.model.vo.user.LoginSuccessVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author TANGLED
* @description 针对表【user_info(用户表)】的数据库操作Service
* @createDate 2022-09-07 18:36:32
*/
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 用户登录
     * @param info
     */
    LoginSuccessVo login(UserInfo info);

    /**
     * 用户注销
     * @param token
     */
    void logout(String token);
}
