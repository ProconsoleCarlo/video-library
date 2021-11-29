package it.proconsole.videodb.config.datasource;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(DatasourceSetting.class)
public class DatabaseConfig {
  @Bean
  public DatabaseSetting videoDbSettings(DatasourceSetting datasourceSetting) {
    return datasourceSetting.datasource().get(Database.VIDEO_DB);
  }

  @Bean
  public DataSource videoDbDataSource(DatabaseSetting videoDbSettings) {
    return new DriverManagerDataSource(videoDbSettings.url(), videoDbSettings.username(), videoDbSettings.password());
  }
}
