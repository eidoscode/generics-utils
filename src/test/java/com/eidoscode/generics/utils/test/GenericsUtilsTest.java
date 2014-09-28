package com.eidoscode.generics.utils.test;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.Test;

import com.eidoscode.generics.utils.GenericsUtils;

public class GenericsUtilsTest {

  @Test(expected = NullPointerException.class)
  public void testNullPointerExceptionClazzSuperClassGenericType() {
    GenericsUtils.getSuperClassGenericType(null, null);
  }

  @Test
  public void testNullPointerExceptionClazzTypeSuperClassGenericType() {
    GenericsUtils.getSuperClassGenericType(Object.class, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIndexSuperClassGenericType() {
    GenericsUtils.getSuperClassGenericType(Object.class, Object.class, -1);
  }

  @Test
  public void testGetClassGenericType() {
    Class<?> clazz = GenericsUtils.getClass(Object.class, Object.class);
    assertEquals(Object.class, clazz);
  }

  @Test
  public void testGetClassNullGenericType() {
    Class<?> clazz = GenericsUtils.getClass(Object.class, null);
    assertEquals(Object.class, clazz);
  }

  @Test(expected = NullPointerException.class)
  public void testGetClassNPEGenericType() {
    GenericsUtils.getClass(null, null);
  }

  @Test
  public void testAllModifiersTrue() throws NoSuchFieldException, SecurityException {
    Field field = ModelModifier.class.getDeclaredField("TEST");
    boolean mustTrue = GenericsUtils.checkModifiers(field, true, Modifier.STATIC, Modifier.FINAL);
    assertEquals(true, mustTrue);
    mustTrue = GenericsUtils.checkModifiers(field, true, Modifier.STATIC);
    assertEquals(true, mustTrue);
  }

  @Test
  public void testAllModifiersFalse() throws NoSuchFieldException, SecurityException {
    Field field = ModelModifier.class.getDeclaredField("TEST2");
    boolean mustFalse = GenericsUtils.checkModifiers(field, true, Modifier.STATIC, Modifier.FINAL);
    assertEquals(false, mustFalse);
    mustFalse = GenericsUtils.checkModifiers(field, true, Modifier.FINAL);
    assertEquals(false, mustFalse);
  }
}
