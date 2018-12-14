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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping (value = "/boxStatus")
    public String boxStatus(String box) {
        /*
          封装json
         */
//        List<Map<String, Object>> list = new ArrayList<>();
//        Map<String, Object> map = new HashMap<>();
//        map.put("name", "瓜子二手车");
//        map.put("long", 4);
//        map.put("bt", "2011-11-12");
//        map.put("et", "2012-11-12");
//        list.add(map);
//        map = new HashMap<>();
//        map.put("name", "瓜子一手车");
//        map.put("long", 4);
//        map.put("bt", "2011-11-12");
//        map.put("et", "2012-11-13");
//        list.add(map);
//        Map<String, Object> twoMap = new HashMap<>();
//        twoMap.put("id", "led-id");
//        twoMap.put("name", "xxxx");
//        twoMap.put("status", "false");
//        twoMap.put("lasttime", "2012-11-13");
//        twoMap.put("resol", "");
//        twoMap.put("bt", "2011-11-12");
//        twoMap.put("et", "2012-11-13");
//        twoMap.put("md5", "xxxxxxxsdadsa");
//        twoMap.put("items", list);
//        twoMap.put("type", "1");
//        twoMap.put("time", "2011-11-12");
//        JSONObject json = JSONObject.parseObject(JSON.toJSONString(twoMap));
//        System.err.println(json);
//        //根据 id查询md5,匹配md5,查到了，1.匹配不上，删除库里信息，入库。2.查不到，直接入库。3匹配上了，直接扔了数据
//        String str = json.toJSONString();
        try{


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
            insertMap.put("long", result.get("long"));
            insertMap.put("bt", result.get("bt"));
            insertMap.put("et", result.get("et"));
            insertList.add(insertMap);
        }
        boxStatusService.insBox(boxMap);
        boxStatusService.insMateriel(insertList, box_id);

        return "ture";
        }catch (Exception e){
            e.printStackTrace();

        }
        return "false";
    }
}
