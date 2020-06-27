package com.hunsley.feature.client;

import java.lang.reflect.Method;
import java.util.Optional;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SwitchableAdvice {

  @Around("@annotation(Switchable)")
  public Object bypassIfSwitchedOff(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = joinPoint.getTarget().getClass().getMethod(signature.getMethod().getName(),
            signature.getMethod().getParameterTypes());
    Switchable switchable = method.getAnnotation(Switchable.class);
    final String switchableInstanceName = switchable.value();
    final Object interceptedInstance = joinPoint.getThis();

    Optional optional =
        (Optional)interceptedInstance.getClass().getField(switchableInstanceName).get(interceptedInstance);

    if(optional.isEmpty()) {
      return
    }
  }
}
