package com.blockchain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author hu_xuanhua_hua
 * @ClassName: BlockChainDao
 * @Description: TODO
 * @date 2018-08-17 14:34
 * @versoin 1.0
 **/
@Mapper
public interface BoxStatusDao {
    String getMd5(String box_id);

    void delBox(String box_id);

    void insBox(@Param("map") Map<String, Object> map);

    void insMateriel(@Param("list")List<Map<String,Object>> list,@Param("box_id") String box_id);

}
