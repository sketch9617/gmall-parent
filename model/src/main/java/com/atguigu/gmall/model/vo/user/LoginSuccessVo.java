package com.atguigu.gmall.model.vo.user;

import lombok.Data;

/**
 * @author sketch
 * @date 2022/9/7 18:54
 * @description
 */
@Data
public class LoginSuccessVo {
    private String token; //用户令牌。
    private String nickName; //用户名
}
