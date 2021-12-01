package it.proconsole.library.video;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "it.proconsole.library.video.rest.config",
                "it.proconsole.library.video.rest.controller",
                "it.proconsole.library.video.adapter.jdbc.config"
        }
)
public class ApplicationConfig {
  public static void main(String[] args) {
    SpringApplication.run(ApplicationConfig.class, args);
  }
}
