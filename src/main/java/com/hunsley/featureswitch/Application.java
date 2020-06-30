package com.hunsley.featureswitch;

import com.hunsley.featureswitch.example.repo.SwitchableFeignRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableFeignClients
@SpringBootApplication
public class Application {

  public static void main (String[] args) {
    ApplicationContext app = SpringApplication.run(Application.class, args);
    SwitchableFeignRepository repo = (SwitchableFeignRepository)app.getBean("switchableFeignRepository");
    String myResponse = repo.getMyResponse("someQueryParam");
    System.out.println(myResponse);
  }
}
