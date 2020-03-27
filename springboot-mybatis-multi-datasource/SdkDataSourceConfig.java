import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;


@Configuration
@ConditionalOnProperty("hikari.sdk.jdbc-url")
@MapperScan(
        sqlSessionFactoryRef = SdkDataSourceConfig.SQL_SESSION_FACTORY_NAME,
        basePackages = {
                "sdk.api.mapper.master"
        },
        //sqlSessionTemplateRef = SdkDataSourceConfig.SQL_SESSION_TEMPLATE_NAME,
        annotationClass = SdkMapper.class
)
public class SdkDataSourceConfig {

    public static final String DATASOURCE_NAME = "sdkDataSource";
    public static final String SQL_SESSION_FACTORY_NAME = "sdkSqlSessionFactory";
    public static final String SQL_SESSION_TEMPLATE_NAME = "sdkSqlSessionTemplate";
    public static final String TRANSACTION_MANAGER_NAME = "sdkTransactionManager";

    @Value("${hikari.sdk.mybatisMapperLocation:classpath*:mybatis/*.xml}")
    private String mapperLocation;

    @Value("${hikari.sdk.mybatisTypeAliases:xinyongfei.sdk.api.entity.master}")
    private String typeAliases;

    /**
     * 使用hikariCP连接池
     *
     * @param properties
     * @return
     */
    @Bean(name = DATASOURCE_NAME)
    @ConfigurationProperties(prefix = "hikari.sdk")
    public DataSource dataSource(DataSourceProperties properties) {
        return DataSourceBuilder.create(properties.getClassLoader())
                .type(HikariDataSource.class)
                .driverClassName(properties.determineDriverClassName())
                .url(properties.determineUrl())
                .username(properties.determineUsername())
                .password(properties.determinePassword())
                .build();
    }

    @Bean(name = SQL_SESSION_FACTORY_NAME)
    public SqlSessionFactoryBean sdkSqlSessionFactory(@Qualifier(DATASOURCE_NAME) DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocation));
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage(typeAliases);
        sessionFactory.setConfiguration(new org.apache.ibatis.session.Configuration() {{
            setMapUnderscoreToCamelCase(true);
        }});
        return sessionFactory;
    }

    @Bean(name = TRANSACTION_MANAGER_NAME)
    public DataSourceTransactionManager sdkTransactionManager(@Qualifier(DATASOURCE_NAME) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = SQL_SESSION_TEMPLATE_NAME)
    public SqlSessionTemplate sentinelSqlSessionTemplate(@Qualifier(SQL_SESSION_FACTORY_NAME) SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
