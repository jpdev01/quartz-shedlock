package com.shedlock.example.shedlock.shedlock;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.support.KeepAliveLockProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.concurrent.Executors;

@Configuration
public class LockConfiguration {

    @Bean
    public LockProvider lockProvider(DataSource dataSource) {
        return new KeepAliveLockProvider(getJdbcTemplateLockProvider(dataSource), Executors.newSingleThreadScheduledExecutor());
    }

    private JdbcTemplateLockProvider getJdbcTemplateLockProvider(DataSource dataSource) {
        return new JdbcTemplateLockProvider(
                JdbcTemplateLockProvider.Configuration.builder()
                        .withJdbcTemplate(new JdbcTemplate(dataSource))
                        .withDbUpperCase(false)
                        .usingDbTime()
                        .build()
        );
    }
}
