package com.hunsley.featureswitch.example.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Profile("!myFeignClient-off")
@FeignClient(
    name = "myFeignClient",
    url = "http://localhost:7788",
    configuration = MyClientConfig.class, decode404 = true)
@Repository
public interface MyFeignClient {

  @RequestMapping(
      method = RequestMethod.GET,
      value = "/path/to/endpoint",
      consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<String> getMyResponse(String myParam);
}
