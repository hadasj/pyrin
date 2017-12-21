package cz.i.config;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import cz.i.dao.DimensionDao;
import cz.i.dao.DimensionValueDao;
import cz.i.dao.DimensionValueLinkDao;
import cz.i.dao.FactDao;
import cz.i.dao.FactValueDao;
import cz.i.dao.ValueDao;

/**
 * @author jan.hadas@i.cz
 */
@Configuration
public class MyBatisConfig {

    @Bean
    @ConfigurationProperties(prefix = "datasource")
    public HikariConfig pgHikariConfig() {
        return new HikariConfig();
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = pgHikariConfig();
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setConfigLocation(new ClassPathResource("mybatis-configuration.xml"));

        return sessionFactory.getObject();
    }

    @Bean
    public DimensionDao dimensionDao() throws Exception {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sessionTemplate.getMapper(DimensionDao.class);
    }

    @Bean
    public DimensionValueDao dimensionValueDao() throws Exception {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sessionTemplate.getMapper(DimensionValueDao.class);
    }

    @Bean
    public DimensionValueLinkDao dimensionValueLinkDao() throws Exception {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sessionTemplate.getMapper(DimensionValueLinkDao.class);
    }

    @Bean
    public FactDao factDao() throws Exception {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sessionTemplate.getMapper(FactDao.class);
    }

    @Bean
    public FactValueDao factValueDao() throws Exception {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sessionTemplate.getMapper(FactValueDao.class);
    }

    @Bean
    public ValueDao valueDao() throws Exception {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sessionTemplate.getMapper(ValueDao.class);
    }
}
