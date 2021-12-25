package it.proconsole.library.video;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "it.proconsole.library.video.rest.config",
                "it.proconsole.library.video.rest.controller",
                "it.proconsole.library.video.rest.controller.jdbc",
                "it.proconsole.library.video.rest.controller.jpa",
                "it.proconsole.library.video.adapter.jdbc.config",
                "it.proconsole.library.video.adapter.jpa.config",
                "it.proconsole.library.video.adapter.xlsx.config"
        }
)
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
