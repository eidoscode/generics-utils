package com.eidoscode.generics.utils.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Test;

/**
 * 
 * Unit test to check an object that is not behind a proxy.
 * 
 * @author antonini
 * @since 1.0
 * @version 1.0
 * 
 */
public class GenericsUtilsWithProxyTest {

  @SuppressWarnings("unchecked")
  @Test
  public void testWithoutProxyExternal2Class() throws Exception {
    Class<ModelWithProxy> cW = ModelWithProxy.class;
    IModel<String, Integer, Boolean> model = cW.newInstance();

    model = getProxy(IModel.class, model);
    assertNotNull(model);
    assertEquals(String.class, model.getX());
    assertEquals(Integer.class, model.getY());
    assertEquals(Boolean.class, model.getZ());
  }

  @SuppressWarnings("unchecked")
  public static <T> T getProxy(Class<T> intf, final T obj) {
    return (T) Proxy.newProxyInstance(obj.getClass().getClassLoader(), new Class[] { intf }, new InvocationHandler() {
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(obj, args);
      }
    });
  }
}
