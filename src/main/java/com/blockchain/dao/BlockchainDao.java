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
//@Repository
@Mapper
public interface BlockchainDao {
    List<Map<String, Object>> getCity();

    void insertChain(@Param("map") Map<String, Object> map);

    Map<String, Object> getChain();

    String getData(@Param("chain") String chain);

    String getMaterialId(@Param("material_md5") String md5);

    Map<String, Object> getResourceIdAndMediaId(@Param("bno") String bno);

    String getAdslotId(@Param("resource_id") String resource_id, @Param("duration") String duration);

    Map<String, Object> getThrow(@Param("map") Map<String, Object> map);

    void insertPlayinfo(@Param("map") Map<String, Object> map);
    int getId();

    void insertOrUpdateDemo_Report(@Param("map") Map<String, Object> map);
}
