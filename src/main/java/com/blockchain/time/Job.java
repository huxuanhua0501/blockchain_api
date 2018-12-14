package com.blockchain.time;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @author hu_xuanhua_hua
 * @ClassName: Job
 * @Description: TODO
 * @date 2018-11-27 11:19
 * @versoin 1.0
 **/
@Component
public class Job {

    /**
     *  更新，查询box_id。
     */
    @Scheduled(cron = "${job_one}")
    public void job_one() {

    }


}
