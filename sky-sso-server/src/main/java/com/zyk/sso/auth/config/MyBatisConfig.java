package com.zyk.sso.auth.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class MyBatisConfig {

    @Autowired
    private MybatisProperties mybatisProperties;

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("druidDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

        List<Resource> resources = new ArrayList();

        String[] mapperLocations = mybatisProperties.getMapperLocations();
        for (int i = 0; i < mapperLocations.length; i++) {
            Resource[] mappers = resourceResolver.getResources(mapperLocations[i]);
            resources.addAll(Arrays.asList(mappers));
        }
        Resource config = resourceResolver.getResource(mybatisProperties.getConfigLocation());

        bean.setConfigLocation(config);

        bean.setMapperLocations(resources.toArray(new Resource[resources.size()]));

        return bean.getObject();
    }


    @Bean("zykSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
