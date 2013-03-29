package br.com.endrigo.generics.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * 
 * Unit test to check an object that is not behind a proxy.
 * 
 * @author eantonini
 * @since 1.0
 * @version 1.0
 * 
 */
public class GenericsUtilsWithoutProxyTest {

	public void testWithoutProxyExternal1Class() {
		ModelNoProxy model = new ModelNoProxy();

		assertNotNull(model);
		assertEquals(String.class, model.getX());
		assertEquals(Integer.class, model.getY());
		assertEquals(Boolean.class, model.getZ());
	}

	public void testWithoutProxyExternal2Class() {
		IModel<String, Integer, Boolean> model = new ModelNoProxy();

		assertNotNull(model);
		assertEquals(String.class, model.getX());
		assertEquals(Integer.class, model.getY());
		assertEquals(Boolean.class, model.getZ());
	}

}
