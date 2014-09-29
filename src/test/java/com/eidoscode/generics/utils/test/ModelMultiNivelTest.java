package com.eidoscode.generics.utils.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.eidoscode.generics.utils.GenericsUtils;

public class ModelMultiNivelTest {

  @Test
  public void testWithoutProxyExternal2Class() throws Exception {
    ModelThridLevel model = new ModelThridLevel();
    Object obj = GenericsUtils.getSuperClassGenericType(model.getClass(), Model.class, 1);
    assertNotNull(model);
    assertNotNull(obj);
    assertEquals(String.class, model.getX());
    assertEquals(String.class, model.getY());
    assertEquals(String.class, model.getZ());
    assertEquals(String.class, obj);
  }

}
