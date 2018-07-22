package ru.hospitall.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import interfaces.DepartmentRepository;
import interfaces.DoctorRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import ru.hospitall.repository.doctor.DbDoctorRepository;
import ru.hospitall.repository.repository.DbDepartmentRepository;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
public class RepoConfig {

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.mariadb.jdbc.Driver");
        config.setJdbcUrl(dataSourceUrl);
        config.setUsername(userName);
        config.setPassword(password);

        config.setMaximumPoolSize(30);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        return new HikariDataSource(config);
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public DoctorRepository doctorRepository() {
        return new DbDoctorRepository();
    }

    @Bean
    public DepartmentRepository departmentRepository() {
        return new DbDepartmentRepository();
    }
}
