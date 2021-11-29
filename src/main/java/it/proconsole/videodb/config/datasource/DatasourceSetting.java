package it.proconsole.videodb.config.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties
public record DatasourceSetting(Map<Database, DatabaseSetting> datasource) {

}
