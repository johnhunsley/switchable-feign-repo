package com.hunsley;

import com.hunsley.featureswitch.Application;
import com.hunsley.featureswitch.example.repo.SwitchableFeignRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

//@ActiveProfiles("myFeignClient-off")
@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SwitchableTest {

  @Autowired
  private SwitchableFeignRepository switchableFeignRepository;

  @Test
  public void testSwitchable() {
    System.out.println(switchableFeignRepository.getMyResponse("test"));
  }

}
