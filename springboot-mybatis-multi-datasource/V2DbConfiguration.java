import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty("c3p0.v2.jdbcUrl")
@MapperScan(sqlSessionFactoryRef = "v2SqlSessionFactory",annotationClass = V2Mapper.class,basePackages = {
        "sdk.api.mapper.v2"
})
public class V2DbConfiguration {

    @Bean("v2DataSource")
    @ConfigurationProperties(prefix = "c3p0.v2")
    public DataSource v2DataSource() {
        DataSource ds = DataSourceBuilder.create().type(ComboPooledDataSource.class).build();
        return ds;
    }

    @Bean("v2SqlSessionFactory")
    public SqlSessionFactoryBean v2SqlSessionFactory(ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(v2DataSource());
        sessionFactory.setMapperLocations(applicationContext.getResources("classpath*:mybatis/v2/*.xml"));
        sessionFactory.setTypeAliasesPackage("xinyongfei.sdk.api.mapper.v2.entities");
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration(){{
            setMapUnderscoreToCamelCase(true);
        }};
        sessionFactory.setConfiguration(configuration);
        return sessionFactory;
    }

    @Bean("v2TransactionManager")
    public DataSourceTransactionManager v2TransactionManager() {
        return new DataSourceTransactionManager(v2DataSource());
    }
}
