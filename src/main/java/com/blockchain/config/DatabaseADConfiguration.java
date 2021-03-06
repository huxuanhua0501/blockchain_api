package com.blockchain.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


/**
 * 配置数据源
 */
@Configuration
@MapperScan(basePackages = "com.blockchain.dao", sqlSessionTemplateRef = "zentaoSqlSessionTemplate")
public class DatabaseADConfiguration {
//    @Primary
    @Bean(name = "zentaoDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.ad")
    public DataSource setDataSource() {
        return new DruidDataSource();
    }

//    @Primary
    @Bean(name = "zentaoTransactionManager")
    public DataSourceTransactionManager setTransactionManager(@Qualifier("zentaoDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

//    @Primary
    @Bean(name = "zentaoSqlSessionFactory")
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("zentaoDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        return bean.getObject();
    }

    @Primary
    @Bean(name = "zentaoSqlSessionTemplate")
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("zentaoSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}