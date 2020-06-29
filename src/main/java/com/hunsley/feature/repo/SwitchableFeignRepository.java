package com.hunsley.feature.repo;

import com.hunsley.feature.client.MyFeignClient;
import com.hunsley.feature.client.Switchable;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SwitchableFeignRepository {

  private Optional<MyFeignClient> myFeignClient;

  @Autowired
  public SwitchableFeignRepository(Optional<MyFeignClient> myFeignClient) {
    this.myFeignClient = myFeignClient;
  }

  @Switchable("myFeignClient")
  public String getMyResponse(final String param) {
//    if(myFeignClient.isEmpty()) {
//      System.out.println("skipping......");
//      return null;
//    }
    return myFeignClient.get().getMyResponse(param).getBody();
  }
}
