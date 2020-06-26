package com.hunsley.feature.repo;

import com.hunsley.feature.client.MyFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("switchableFeignRepository")
public class SwitchableFeignRepository {

  @Autowired
  private MyFeignClient myFeignClient;

  public String getMyResponse(final String param) {
    return myFeignClient.getMyResponse(param).getBody();
  }
}
