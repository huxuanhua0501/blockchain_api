package com.blockchain.controller;

import com.blockchain.service.IBlockchainService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * @author hu_xuanhua_hua
 * @ClassName: BlockchainController
 * @Description: TODO
 * @date 2018-08-17 14:39
 * @versoin 1.0
 **/
@RestController
@RequestMapping(value = "/blockchain", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "blockchain", description = "区块链接口", produces = MediaType.APPLICATION_JSON_VALUE)
public class BlockchainController {
    @Autowired
    private IBlockchainService blockchainService;


    @ApiOperation(value = "生成区块链", notes = "生成区块链", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/chain", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> chain(String json, String type) {
        Map<String, Object> resultMap = blockchainService.insertChain(json, type);
        return resultMap;

    }

    @ApiOperation(value = "区块链查数据", notes = "区块链查数据", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/chain", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> chain(String chain) {
        String data = blockchainService.getData(chain);
        Map<String, Object> resultMap = new HashMap<>();

        if (data != null && !"".equals(data)) {
            resultMap.put("status", 200);
            resultMap.put("data", data);
            return resultMap;
        } else {
            resultMap.put("status", 300);
            resultMap.put("data", "没找到对应的区块数据");
            return resultMap;
        }
    }
}
