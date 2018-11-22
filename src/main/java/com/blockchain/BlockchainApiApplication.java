package com.blockchain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.blockchain.dao")
public class BlockchainApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlockchainApiApplication.class, args);
	}
}
