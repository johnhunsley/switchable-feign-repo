package com.hunsley.feature;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.ReflectionUtils;

/**
 * The logic to be performed at a point cut defined by the {@link Switchable} stereotype. Finds what the switched
 * instance is, check the instance being switched and either continue or do whatever the implementation requires.
 *
 * @author jhunsley
 */
@Aspect
public abstract class AbstractSwitchableAdvice {

  /**
   * Check what is being switch is present or not, on or off, and handle it.
   *
   * @param joinPoint
   * @return whatever is the continuation of the point cut or what ever the bypass implementation does
   *         when the switch is off
   * @throws Throwable
   */
  @Around("@annotation(com.hunsley.feature.Switchable)")
  public final Object doSwitch(ProceedingJoinPoint joinPoint) throws Throwable {

    final String switchableInstanceName = getSwitchableBeanInstanceName(joinPoint);
    Object switchableInstance = getSwitchableBeanInstance(joinPoint, switchableInstanceName);


    if (switchableInstance == null) {
      return bypass(joinPoint);
    }

    if (switchableInstance instanceof Optional) {
      Optional optional = (Optional) switchableInstance;

      if (optional.isEmpty()) {
        return bypass(joinPoint);
      }
    }

    return joinPoint.proceed();
  }

  /**
   * When the switch is off we need to return and object in its place. Whatever is returned must
   * be handled with in the switchable annotated method
   * @return An object to be handled by the caller if the switch is off
   */
  protected abstract Object bypass(JoinPoint joinPoint);

  /**
   * Get the name of the switched instance from the @Switchable annotation being intercepted.
   *
   * @param joinPoint
   * @return The name of the bean in the target object being switched
   * @throws NoSuchMethodException
   */
  private String getSwitchableBeanInstanceName(JoinPoint joinPoint) throws NoSuchMethodException {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = joinPoint.getTarget().getClass().getMethod(signature.getMethod().getName(),
        signature.getMethod().getParameterTypes());
    Switchable switchable = method.getAnnotation(Switchable.class);
    return switchable.value();
  }

  /**
   * Get the switched instance by field name from the point cut target.
   * @param joinPoint
   * @param name
   * @return The switched instanxce
   * @throws IllegalAccessException
   */
  private Object getSwitchableBeanInstance(JoinPoint joinPoint, final String name) throws IllegalAccessException {
    final Object interceptedInstance = joinPoint.getTarget();
    Field switchableField = ReflectionUtils.findField(interceptedInstance.getClass(), name);
    ReflectionUtils.makeAccessible(switchableField);
    return switchableField.get(interceptedInstance);
  }
}
