package com.blockchain.consumer;//package com.blockchain.consumer;

import com.alibaba.druid.support.json.JSONUtils;
import com.blockchain.config.MD5Util;
import com.blockchain.config.ShardedJedisUtils;
import com.blockchain.dao.BlockchainDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author hu_xuanhua_hua
 * @ClassName: FabricKafkaConsumer
 * @Description: TODO
 * @date 2018-09-13 10:46
 * @versoin 1.0
 **/
@Component
public class FabricKafkaConsumer {
    @Autowired
    private BlockchainDao blockchainDao;

    @KafkaListener(topics = {"LEDPL"})
    public void receive(String message) {
//        System.out.println("LEDPL--消费消息:" + message);

        Map<String, Object> map = getMap(message);
        String materialId = blockchainDao.getMaterialId(map.get("material_md5").toString());
        if (materialId != null) {
            map.put("material_id", materialId);

            Map<String, Object> resourceIdAndMediaIdMap = blockchainDao.getResourceIdAndMediaId(map.get("bno").toString());
            if (resourceIdAndMediaIdMap != null && resourceIdAndMediaIdMap.size() > 0) {
                map.put("resource_id", resourceIdAndMediaIdMap.get("resource_id"));
                map.put("media_id", resourceIdAndMediaIdMap.get("media_id"));
                String adslotId = blockchainDao.getAdslotId(map.get("resource_id").toString(), map.get("duration").toString());
                if (adslotId != null) {
                    map.put("adslot_id", adslotId);
                }
            }
            Map<String, Object> throwMap = blockchainDao.getThrow(map);
            map.put("throw_id", throwMap.get("throw_id"));
            map.put("city", throwMap.get("city"));
            map.put("customer_id", throwMap.get("customer_id"));
            map.put("md5Key", MD5Util.getMD5String(map.get("adslot_id").toString() + map.get("material_md5").toString() + map.get("date").toString()));
            String time = getDatetime();
            map.put("createtime",getDatetime());
             blockchainDao.insertPlayinfo(map);
             int id = blockchainDao.getId();
            blockchainDao.insertOrUpdateDemo_Report(map);

            ShardedJedis shardedJedis = new ShardedJedisUtils().getShardedJedis();
            Collection<Jedis> collection = shardedJedis.getAllShards();

            Iterator<Jedis> jedis = collection.iterator();
            while (jedis.hasNext()) {
                jedis.next().select(1);
            }
            Map<String, Object> redisMap = new HashMap<>();
            Map<String, Object> insertMap = new HashMap<>();
            insertMap.put("id", id);
            insertMap.put("throw_id", map.get("throw_id"));
            insertMap.put("resource_info_number", map.get("bno"));
            insertMap.put("material_md5", map.get("material_md5"));
            insertMap.put("sdate", map.get("sdate"));
            insertMap.put("edate", map.get("edate"));
            insertMap.put("is_onchain", 2);
            insertMap.put("createtime",time);
            redisMap.put("item_table", "demo_playinfo");
            redisMap.put("item_id", id);
            redisMap.put("chain_key", "demo_playinfo_" + id);
            redisMap.put("chain_data", insertMap);
            redisMap.put("owner_id", map.get("media_id"));
            shardedJedis.lpush("chain_block", JSONUtils.toJSONString(redisMap));
            shardedJedis.close();

        } else {
            System.err.println(message);
        }


    }


    public static void main(String[] args) {
//        String str = "BNAME:19-D32竖屏,BNO:66,ADNAME:2018.6.27-9.22号_好牙口换刊__D32竖屏_20180627171601,ADMD5:6ea0dffc8534db7efc76f7258b79a3bb,DURATION:15,ST:34220,ET:34235";
//
//        String str1 = "BNAME:19-D32竖屏";
//        System.err.println(str1.substring(str1.indexOf(":") + 1));
//
//        System.err.println(new FabricKafkaConsumer().getDateTime("1111"));
//        System.err.println(updateFormat("201893"));
    }


    private Map<String, Object> getMap(String message) {
        String[] strs = message.split(",");
        Map<String, Object> map = new HashMap<>();
        if (strs.length == 7) {
            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
            strs[8] = sdf.format(dt);
        }
        map.put("bname", strs[0].substring(strs[0].indexOf(":") + 1));
        map.put("bno", strs[1].substring(strs[1].indexOf(":") + 1));
        map.put("adname", strs[2].substring(strs[2].indexOf(":") + 1));
        map.put("material_md5", strs[3].substring(strs[3].indexOf(":") + 1));
        map.put("duration", strs[4].substring(strs[4].indexOf(":") + 1));
        map.put("date", strs[7].substring(strs[7].indexOf(":") + 1));
        map.put("sdate", map.get("date") + " " + this.getDateTime(strs[5].substring(strs[5].indexOf(":") + 1)));
        map.put("edate", map.get("date") + " " + this.getDateTime(strs[6].substring(strs[6].indexOf(":") + 1)));

        return map;
    }


    private String getDateTime(String time) {
        String DateTimes = null;
        long mss = Long.parseLong(time);
        String hours = String.valueOf((mss % (60 * 60 * 24)) / (60 * 60));
        String minutes = String.valueOf((mss % (60 * 60)) / 60);
        String seconds = String.valueOf(mss % 60);
        if (Integer.parseInt(hours) < 10) {
            hours = "0" + hours;
        }
        if (Integer.parseInt(minutes) < 10) {
            minutes = "0" + minutes;
        }
        if (Integer.parseInt(seconds) < 10) {
            seconds = "0" + seconds;
        }

        DateTimes = hours + ":" + minutes + ":"
                + seconds;

        return DateTimes;
    }

    private static String updateFormat(String time) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sf = null;
        try {
            cal.setTime(sf.parse(time));//开始时间
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat retrunsf = new SimpleDateFormat("yyyy-MM-dd");
        String datetime = retrunsf.format(cal.getTime());
        return datetime;
    }

    private    String getDatetime() {
        SimpleDateFormat retrunsf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return retrunsf.format(new Date());
    }
}
