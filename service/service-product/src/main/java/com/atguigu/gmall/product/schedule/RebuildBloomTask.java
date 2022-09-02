package com.atguigu.gmall.product.schedule;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.product.bloom.BloomDataQueryService;
import com.atguigu.gmall.product.bloom.BloomOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author sketch
 * @date 2022/9/2 18:27
 * @description 重建布隆(定时任务)
 */
@Service
public class RebuildBloomTask {
    @Autowired
    BloomOpsService bloomService;
    @Autowired
    BloomDataQueryService bloomDataQueryService;

    // * * * * * ? *
    //秒 分 时 日 月 周 年
    @Scheduled(cron = "0 0 3 ? * 3")//每隔7天重建一次
    public void rebuild() {
        bloomService.rebuildBloom(SysRedisConst.BLOOM_SKUID, bloomDataQueryService);
    }
}
