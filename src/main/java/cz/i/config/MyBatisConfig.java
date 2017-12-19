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

import cz.i.dao.DimensionMapper;
import cz.i.dao.DimensionValueLinkMapper;
import cz.i.dao.DimensionValueMapper;
import cz.i.dao.FactMapper;
import cz.i.dao.FactValueMapper;
import cz.i.dao.ValueMapper;

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
    public DimensionMapper dimensionMapper() throws Exception {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sessionTemplate.getMapper(DimensionMapper.class);
    }

    @Bean
    public DimensionValueMapper dimensionValueMapper() throws Exception {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sessionTemplate.getMapper(DimensionValueMapper.class);
    }

    @Bean
    public DimensionValueLinkMapper dimensionValueLinkMapper() throws Exception {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sessionTemplate.getMapper(DimensionValueLinkMapper.class);
    }

    @Bean
    public FactMapper factMapper() throws Exception {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sessionTemplate.getMapper(FactMapper.class);
    }

    @Bean
    public FactValueMapper factValueMapper() throws Exception {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sessionTemplate.getMapper(FactValueMapper.class);
    }

    @Bean
    public ValueMapper valueMapper() throws Exception {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sessionTemplate.getMapper(ValueMapper.class);
    }
}
