package com.example.ato.support;

import com.example.ato.properties.DatabaseProperties;
import com.example.ato.repository.mapper.AccountMapper;
import com.example.ato.repository.mapper.HostMapper;
import com.example.ato.repository.mapper.MonitoringMapper;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DatabaseConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfiguration.class);

    @Autowired
    private DatabaseProperties databaseProperties;

    @Bean(name = "dataSource")
    @Primary
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        logger.debug("Creating DB DataSource : {}, {}, {}", databaseProperties.getDriverClassName(), databaseProperties.getUrl(), databaseProperties.getUsername());

        dataSource.setDriverClassName(databaseProperties.getDriverClassName());
        dataSource.setUrl(databaseProperties.getUrl());
        dataSource.setUsername(databaseProperties.getUsername());
        dataSource.setPassword(databaseProperties.getPassword());
        dataSource.setInitialSize(databaseProperties.getInitialSize());
        dataSource.setMinIdle(databaseProperties.getMinIdle());
        dataSource.setMaxTotal(databaseProperties.getMaxIdle());

        return dataSource;
    }

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactoryBean sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource, ApplicationContext applicationContext) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource(databaseProperties.getConfigLocation()));

        return sqlSessionFactoryBean;
    }

    @Bean(name = "sqlSession")
    @Primary
    public SqlSession sqlSession(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public MapperFactoryBean<HostMapper> hostMapper(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<HostMapper> bean = new MapperFactoryBean<>(HostMapper.class);
        bean.setSqlSessionFactory(sqlSessionFactory);

        return bean;
    }

    @Bean
    public MapperFactoryBean<AccountMapper> accountMapper(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<AccountMapper> bean = new MapperFactoryBean<>(AccountMapper.class);
        bean.setSqlSessionFactory(sqlSessionFactory);

        return bean;
    }

    @Bean
    public MapperFactoryBean<MonitoringMapper> monitoringMapper(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<MonitoringMapper> bean = new MapperFactoryBean<>(MonitoringMapper.class);
        bean.setSqlSessionFactory(sqlSessionFactory);

        return bean;
    }
}