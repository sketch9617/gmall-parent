package com.atguigu.gmall.common.retry;

import feign.RetryableException;
import feign.Retryer;

/**
 * @author sketch
 * @date 2022/9/3 18:23
 * @description 自定义feign重试次数逻辑
 */
public class MyRetryer implements Retryer {
    private int cur = 0;
    private int max = 2;

    public MyRetryer() {
        cur = 0;
        max = 2;
    }

    /**
     * 继续重试还是中断重试(一般不重试)
     */
    @Override
    public void continueOrPropagate(RetryableException e) {
        throw e;
    }

    @Override
    public Retryer clone() {
        return this;
    }
}
