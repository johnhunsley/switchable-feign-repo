package com.hunsley.feature.repo;

import com.hunsley.feature.client.MyFeignClient;
import com.hunsley.feature.client.Switchable;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public class SwitchableFeignRepository {

  private Optional<MyFeignClient> myFeignClient;

  public SwitchableFeignRepository(Optional<MyFeignClient> myFeignClient) {
    this.myFeignClient = myFeignClient;
  }

  @Switchable("myFeignClient")
  public String getMyResponse(final String param) {
    ResponseEntity<String> response = myFeignClient.get().getMyResponse(param);
    return response.getBody();
  }
}
