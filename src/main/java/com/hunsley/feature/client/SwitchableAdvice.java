package com.hunsley.feature.client;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Aspect
@Component
public class SwitchableAdvice {

  /**
   *
   * @param joinPoint
   * @return
   * @throws Throwable
   */
  @Around("@annotation(Switchable)")
  public Object bypassIfSwitchedOff(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = joinPoint.getTarget().getClass().getMethod(signature.getMethod().getName(), signature.getMethod().getParameterTypes());
    Switchable switchable = method.getAnnotation(Switchable.class);
    final String switchableInstanceName = switchable.value();
    final Object interceptedInstance = joinPoint.getTarget();
    Field switchableField = ReflectionUtils.findField(interceptedInstance.getClass(), switchableInstanceName);
    ReflectionUtils.makeAccessible(switchableField);
    Object switchableInstance = switchableField.get(interceptedInstance);

    if (switchableInstance == null) {
      throw new RuntimeException("switched instance is null");
    }

    if (switchableInstance instanceof Optional) {
      Optional optional = (Optional) switchableInstance;

      if (optional.isEmpty()) {
        System.out.println("switched OFF");
        return null;

      } else {
        System.out.println("switched ON");
        return joinPoint.proceed();
      }

    } else {
      throw new RuntimeException("switched instance is null");
    }
  }
}
