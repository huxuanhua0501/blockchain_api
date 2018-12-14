package com.blockchain.service.impl;

import com.blockchain.dao.BoxStatusDao;
import com.blockchain.service.IBoxStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author hu_xuanhua_hua
 * @ClassName: BoxStatusService
 * @Description: TODO
 * @date 2018-12-11 15:50
 * @versoin 1.0
 **/
@Service
public class BoxStatusService implements IBoxStatusService {
    @Autowired
    private BoxStatusDao boxStatusDao;

    @Override
    public String getMd5(String box_id) {
        return boxStatusDao.getMd5(box_id);
    }

    @Override
    public void delBox(String box_id) {
        boxStatusDao.delBox(box_id);
    }

    @Override
    public void insBox(Map<String, Object> map) {
        boxStatusDao.insBox(map);
    }

    @Override
    public void insMateriel(List<Map<String, Object>> list, String box_id) {
        boxStatusDao.insMateriel(list, box_id);
    }

    @Override
    public void delSchedule(String box_id) {
        boxStatusDao.delSchedule(box_id);
    }

    @Override
    public void insSchedule(List<Map<String, Object>> list) {
        boxStatusDao.insSchedule(list);
    }
}
