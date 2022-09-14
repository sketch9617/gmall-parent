package com.atguigu.gmall.common.annotation;

import com.atguigu.gmall.common.config.FeignInterceptorConfiguration;
import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

/**
 * @author sketch
 * @description
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(FeignInterceptorConfiguration.class)
public @interface EnableAutoFeignInterceptor {
}
