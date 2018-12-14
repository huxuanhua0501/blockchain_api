package com.blockchain.service;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author hu_xuanhua_hua
 * @ClassName: BoxStatus
 * @Description: TODO
 * @date 2018-12-06 14:52
 * @versoin 1.0
 **/

public interface IBoxStatusService {
    String getMd5(String box_id);

    void delBox(String box_id);

    void insBox(Map<String, Object> map);

    void insMateriel(List<Map<String, Object>> list, String box_id);

    void delSchedule(String box_id);

    void insSchedule(List<Map<String, Object>> list);

}
