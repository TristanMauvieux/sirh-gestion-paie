package dev.paie.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("dev.paie.service")
@Import(DataSourceMySQLConfif.class)
@EnableTransactionManagement
public class ServiceConfig {

}
