package com.blockchain.service;

import java.util.List;
import java.util.Map;

/**
 * @author hu_xuanhua_hua
 * @ClassName: IBlockchainService
 * @Description: TODO
 * @date 2018-08-17 14:37
 * @versoin 1.0
 **/
public interface IBlockchainService {
    List<Map<String, Object>> getCity();

    Map<String,Object> insertChain(String json, String type);

    String getData(String chain);

}
