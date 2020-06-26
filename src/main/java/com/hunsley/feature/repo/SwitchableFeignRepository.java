package com.hunsley.feature.repo;

import com.hunsley.feature.client.MyFeignClient;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class SwitchableFeignRepository {

  private final Optional<MyFeignClient> myFeignClient;

  public SwitchableFeignRepository(Optional<MyFeignClient> myFeignClient) {
    this.myFeignClient = myFeignClient;
  }

  public String getMyResponse(final String param) {

    if(!myFeignClient.isPresent()) return "no feign client, skipping ....";
    return myFeignClient.get().getMyResponse(param).getBody();
  }
}
