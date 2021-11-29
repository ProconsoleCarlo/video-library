package it.proconsole.library.video.rest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("datasource.video-library")
public record DatabaseSetting(String url, String username, String password) {

}
