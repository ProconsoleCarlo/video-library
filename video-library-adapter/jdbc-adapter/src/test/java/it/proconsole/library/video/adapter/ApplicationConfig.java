package it.proconsole.library.video.adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "it.proconsole.library.video.adapter.jdbc.config"
        }
)
public class ApplicationConfig {
  public static void main(String[] args) {
    SpringApplication.run(ApplicationConfig.class, args);
  }
}