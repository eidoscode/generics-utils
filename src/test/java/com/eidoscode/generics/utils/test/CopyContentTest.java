package com.eidoscode.generics.utils.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.eidoscode.generics.utils.GenericsUtils;

public class CopyContentTest {

  @Test(expected = NullPointerException.class)
  public void testNullSourceCopyContent() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
    GenericsUtils.copyContent(null, null);
  }

  @Test(expected = NullPointerException.class)
  public void testNullDestinationCopyContent() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
    GenericsUtils.copyContent(new Object(), null);
  }

  @Test
  public void testCopyContent() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
    Model<String, Integer, Boolean> source = new Model<String, Integer, Boolean>();
    source.def = "ABC";
    Model<String, Integer, Boolean> destination = new Model<String, Integer, Boolean>();
    GenericsUtils.copyContent(source, destination);
    assertEquals(source.def, destination.def);
  }
}
