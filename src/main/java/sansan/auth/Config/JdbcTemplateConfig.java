package sansan.auth.Config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcTemplateConfig {
    @Bean(name = "jdbcCustomer")
    public JdbcTemplate jdbcCustomer(@Qualifier("customer") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Value("${sansan.host-data-source}")
    private String data_source;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean(name = "customer")
    public DataSource customerDataSource() {
        return DataSourceBuilder.create()
                .url(data_source + "/customer")
                .username(username)
                .password(password)
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
}