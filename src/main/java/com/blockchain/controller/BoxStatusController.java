package com.blockchain.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blockchain.config.MD5Util;
import com.blockchain.config.PubMethod;
import com.blockchain.service.IBoxStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author hu_xuanhua_hua
 * @ClassName: BoxStatus
 * @Description: TODO
 * @date 2018-12-06 14:52
 * @versoin 1.0
 **/
@RestController
public class BoxStatusController {
    @Autowired
    private IBoxStatusService boxStatusService;

    @PostMapping(value = "/boxStatus")
    public String boxStatus(String box) {
        /*
          封装json
         */
//        List<Map<String, Object>> list = new ArrayList<>();
//        Map<String, Object> map = new HashMap<>();
//        map.put("name", "瓜子二手车");
//        map.put("long", 4);
//        map.put("bt", "2018-11-12");
//        map.put("et", "2019-11-12");
//        list.add(map);
//        map = new HashMap<>();
//        map.put("name", "瓜子一手车");
//        map.put("long", 4);
//        map.put("bt", "2018-11-12");
//        map.put("et", "2019-11-13");
//        list.add(map);
//        Map<String, Object> twoMap = new HashMap<>();
//        twoMap.put("id", "led-id");
//        twoMap.put("name", "xxxx");
//        twoMap.put("status", "false");
//        twoMap.put("lasttime", "2012-11-13");
//        twoMap.put("resol", "");
//        twoMap.put("bt", "2018-12-14 08:00:00");
//        twoMap.put("et", "2018-12-14 23:00:00");
//        twoMap.put("md5", "xxxxxxxsdadsa");
//        twoMap.put("items", list);
//        twoMap.put("type", "1");
//        twoMap.put("time", "2011-11-12");
//        JSONObject json = JSONObject.parseObject(JSON.toJSONString(twoMap));
//        System.err.println(json);
//        //根据 id查询md5,匹配md5,查到了，1.匹配不上，删除库里信息，入库。2.查不到，直接入库。3匹配上了，直接扔了数据
//        String str = json.toJSONString();
        try {


            System.err.println(box);

            JSONObject jsonObject = JSONObject.parseObject(box);
            String box_id = jsonObject.getString("id");
//        String md5 = boxStatusService.getMd5(box_id);
            boxStatusService.delBox(box_id);

            Map<String, Object> boxMap = new HashMap<>();
            boxMap.put("box_id", jsonObject.getString("id"));

            boxMap.put("type", Integer.parseInt(jsonObject.getString("type")));
            boxMap.put("box_name", jsonObject.getString("name"));
            int box_status = 0;
            if (Boolean.parseBoolean(jsonObject.getString("status"))) {
                box_status = 1;
            } else {
                box_status = 2;
            }
            boxMap.put("box_status", box_status);
            boxMap.put("last_time", jsonObject.getString("lasttime"));
            boxMap.put("resol", jsonObject.getString("resol"));
            boxMap.put("bt", jsonObject.getString("bt"));
            boxMap.put("et", jsonObject.getString("et"));
            boxMap.put("md5", jsonObject.getString("md5"));
            boxMap.put("time", jsonObject.getString("time"));
            String items = jsonObject.getString("items");
            List<Map> mapList = JSONObject.parseArray(items, Map.class);
            List<Map<String, Object>> insertList = new ArrayList<>();
            for (Map result : mapList) {
                Map<String, Object> insertMap = new HashMap<>();
                insertMap.put("name", result.get("name"));
                insertMap.put("md5", result.get("md5"));
                insertMap.put("long", result.get("long"));
                insertMap.put("bt", result.get("bt"));
                insertMap.put("et", result.get("et"));
                insertList.add(insertMap);
            }
            boxStatusService.insBox(boxMap);
            boxStatusService.insMateriel(insertList, box_id);
            //统计
            long second = accessTime(boxMap.get("bt").toString(), boxMap.get("et").toString()) / 60;
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//            String day = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            List<Map<String, Object>> insertMaterielList = new ArrayList<>();
            for (int i = 0; i < 90; i++) {
                Map<String, Object> insertMaterielMap = new HashMap<>();
                insertMaterielMap.put("box_id", boxMap.get("box_id"));
                insertMaterielMap.put("type", boxMap.get("type"));
                insertMaterielMap.put("set_day", getDate(i));
                insertMaterielMap.put("status", boxMap.get("box_status"));
                insertMaterielMap.put("name", boxMap.get("box_name"));
                int materielTime = 0;
                for (int j = 0; j < insertList.size(); j++) {
                    if (accessTime(insertList.get(j).get("bt").toString() + " 00:00:00", time) >= 0 && accessTime(time, insertList.get(j).get("et").toString() + " 00:00:00") <= 0) {
                        materielTime = +Integer.parseInt(insertList.get(j).get("long").toString());
                    }
                }

                insertMaterielMap.put("rate", second - materielTime);
                insertMaterielList.add(insertMaterielMap);
            }

            boxStatusService.delSchedule(box_id);
            System.err.println(JSON.toJSONString(insertMaterielList));
            boxStatusService.insSchedule(insertMaterielList);
            return "ture";
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "false";
    }

    /**
     * 获取时间差(饱和度时60次，每次需要多少秒)
     *
     * @param firsttime
     * @param secondtime
     * @return
     * @throws ParseException
     */
    private long accessTime(String firsttime, String secondtime) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = df.parse(secondtime);
        Date d2 = df.parse(firsttime);
        long diff = d1.getTime() - d2.getTime();
        long min = diff / 1000;
        return min;

    }

    private String getDate(int date) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, date);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        return yesterday;
    }

//    public static void main(String[] args) {
//        try {
//            System.err.println(accessTime("2018-11-30 10:07:07", "2018-11-30 10:09:07"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
}
