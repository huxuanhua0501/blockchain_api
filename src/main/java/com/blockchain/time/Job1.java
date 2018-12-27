package com.blockchain.time;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blockchain.config.HttpUtils;
import com.blockchain.dao.BoxStatusDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author hu_xuanhua_hua
 * @ClassName: Job
 * @Description: TODO
 * @date 2018-12-25 15:32
 * @versoin 1.0
 **/
@Component
@EnableScheduling
public class Job1 {
    @Autowired
    private BoxStatusDao boxStatusDao;

    @Scheduled(cron = "${job_one1}")
    public void job_one() {

        Map<String, String> map = new HashMap<>();
        map.put("nd", "1545730642586");
        map.put("rows", "13000");
        map.put("page", "1");
        map.put("sord", "asc");
        map.put("flag", "gets");
        map.put("startDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        map.put("endDate", "2099-01-20");
        map.put("placeType", "bike");
        map.put("numType", "1");
        map.put("num", "13000");
        map.put("lock_order", "0");
        map.put("lock_use", "0");
        map.put("psas", "0");
        map.put("sidx", "");
        map.put("size", "");
        map.put("region", "");
        map.put("target", "");
        map.put("boardType", "");
        map.put("placeName", "");
        map.put("roadName", "");
        map.put("boardCode", "");
        map.put("degreeOrder", "");
        map.put("_search", "false");
        try {
            List<Map<String, Object>> mapList = new ArrayList<>();
            String url = "http://y.xlchina.cn:2002/orm/EmptyPoint.aspx";
            String str = HttpUtils.HttpPost(map, url);
            JSONArray jsonArray = JSONObject.parseObject(str).getJSONArray("rows");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                Map<String, Object> insertMap = new HashMap<>();
                insertMap.put("placetypename", json.getString("placetypename"));
                insertMap.put("address", json.getString("address"));
                insertMap.put("BoardTypeName", json.getString("BoardTypeName"));
                insertMap.put("remark", json.getString("remark"));
                insertMap.put("placename", json.getString("placename"));
                insertMap.put("PSAs", json.getString("PSAs"));
                insertMap.put("boardtype", json.getString("boardtype"));
                insertMap.put("size", json.getString("size"));
                insertMap.put("thestate", json.getString("thestate"));
                insertMap.put("price", json.getString("price"));
                insertMap.put("regionname", json.getString("regionname"));
                insertMap.put("boardid", json.getString("boardid"));
                insertMap.put("boardcode", json.getString("boardcode"));
                insertMap.put("degreename", json.getString("degreename"));
                insertMap.put("roadname", json.getString("roadname"));
                mapList.add(insertMap);
            }
            boxStatusDao.insCord_point_no(mapList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
