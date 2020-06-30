package com.hunsley.featureswitch.example.repo;

import com.hunsley.featureswitch.AbstractSwitchableAdvice;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

@Component
public class MyFeignClientSwitch extends AbstractSwitchableAdvice {

  @Override
  public Object bypass(JoinPoint joinPoint) {
    System.out.println(
        "The feign call has been bypassed so lets just return a null, but make sure the repo can handle it");
    return null;
  }
}
