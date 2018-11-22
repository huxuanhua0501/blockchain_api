package com.blockchain.service.impl;

import com.blockchain.dao.BlockchainDao;
import com.blockchain.service.IBlockchainService;
import io.swagger.models.auth.In;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hu_xuanhua_hua
 * @ClassName: BlockchainService
 * @Description: TODO
 * @date 2018-08-17 14:38
 * @versoin 1.0
 **/
@Service
public class BlockchainService implements IBlockchainService {
    @Autowired
    private BlockchainDao blockchainDao;

    @Override
    public List<Map<String, Object>> getCity() {
        System.err.println(blockchainDao.getCity());
        return null;
    }

    @Override
    public Map<String, Object> insertChain(String json, String type) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> mapChain = blockchainDao.getChain();
        map.put("upper_chain", sha1(json));
        map.put("height", 1);
        Map<String, Object> resultMap = new HashMap<>();
        if (mapChain != null && mapChain.size() > 0) {
            if (sha1(json).equals(mapChain.get("current_chain"))) {
                resultMap.put("status", 300);
                resultMap.put("data", "区块数据已经存在");
                return resultMap;
            }
            map.put("upper_chain", mapChain.get("current_chain"));
            map.put("height", 1 + Integer.parseInt(map.get("height").toString()));
        }
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        map.put("size", df.format((float) json.getBytes().length / 1024) + "kb");
        map.put("current_chain", sha1(json));
        map.put("chain_content", json);
        map.put("chain_type", type);
        blockchainDao.insertChain(map);
        resultMap.put("status", 200);
        resultMap.put("data", "入链成功");
        return resultMap;
    }

    @Override
    public String getData(String chain) {
        return blockchainDao.getData(chain);
    }

    public static void main(String[] args) {
        System.err.println(sha1("123"));
    }

    private static String sha1(String decript) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes("UTF-8"));
            byte[] messageDigest = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte message : messageDigest) {
                String shaHex = Integer.toHexString(message & 0xFF);
                if (shaHex.length() < 2)
                    hexString.append(0);

                hexString.append(shaHex);
            }
            return hexString.toString().toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
