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
public class Job2 {
    @Autowired
    private BoxStatusDao boxStatusDao;

    @Scheduled(cron = "${job_one2}")
    public void job_one() {

        Map<String, String> map = new HashMap<>();
        map.put("nd", "1545730642586");
        map.put("rows", "13000");
        map.put("page", "1");
        map.put("sord", "asc");
        map.put("flag", "gets");
        map.put("startdate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        map.put("enddate", "2099-01-20");
        map.put("mediatype", "");
        map.put("resourcetype", "");
        map.put("occupancytype", "100");
        map.put("signstate", "0");
        map.put("showoption", "1,2");
        map.put("clientids", "0");
        map.put("projectaddress", "");
        map.put("roadname", "");
        map.put("projectid", "");
        map.put("resourceids", "");

        
        
        try {
            List<Map<String, Object>> mapList = new ArrayList<>();
            String url = "http://y.xlchina.cn:2002/orm/usepoint.aspx";
            String str = HttpUtils.HttpPost(map, url);
            JSONArray jsonArray = JSONObject.parseObject(str).getJSONArray("rows");
           
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                Map<String, Object> insertMap = new HashMap<>();
                insertMap.put("regionName", json.getString("RegionName"));
                insertMap.put("placeName", json.getString("PlaceName"));
                insertMap.put("boardCode", json.getString("BoardCode"));
                insertMap.put("degreeName", json.getString("DegreeName"));
                insertMap.put("address", json.getString("Address"));
                insertMap.put("sourceType", json.getString("SourceType"));
                insertMap.put("sourceSize", json.getString("SourceSize"));
                insertMap.put("projectType", json.getString("ProjectType"));
                insertMap.put("remark", json.getString("Remark"));
                insertMap.put("isActive", json.getString("IsActive"));
                insertMap.put("placeCode", json.getString("PlaceCode"));
                insertMap.put("startDate", json.getString("StartDate"));
                insertMap.put("endDate", json.getString("EndDate"));
                insertMap.put("planCode", json.getString("PlanCode"));
                insertMap.put("planName", json.getString("PlanName"));
                insertMap.put("clientName", json.getString("ClientName"));
                insertMap.put("productName", json.getString("ProductName"));
                insertMap.put("deadline", json.getString("Deadline"));
                insertMap.put("theStateName", json.getString("TheStateName"));
                insertMap.put("roadName", json.getString("RoadName"));
                mapList.add(insertMap);
            }
            boxStatusDao.insCord_point_yes(mapList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
