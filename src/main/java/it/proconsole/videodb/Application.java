package it.proconsole.videodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "it.proconsole.videodb.config",
                "it.proconsole.videodb.config.datasource",
                "it.proconsole.videodb.rest.controller"
        }
)
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
