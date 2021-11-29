package it.proconsole.library.video.rest.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(DatabaseSetting.class)
public class ApplicationConfig {
  @Bean
  public DataSource videoLibraryDataSource(DatabaseSetting databaseSetting) {
    return new DriverManagerDataSource(databaseSetting.url(), databaseSetting.username(), databaseSetting.password());
  }
}
