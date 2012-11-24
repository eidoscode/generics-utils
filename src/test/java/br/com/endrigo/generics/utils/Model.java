package br.com.endrigo.generics.utils;

import br.com.endrigo.generics.utils.GenericsUtils;

public class Model<X, Y, Z> implements IModel<X, Y, Z> {

	private final Class<X> x;
	private final Class<Y> y;
	private final Class<Z> z;

	public Model() {
		x = GenericsUtils.getSuperClassGenericType(getClass());
		y = GenericsUtils.getSuperClassGenericType(getClass(), 1);
		z = GenericsUtils.getSuperClassGenericType(getClass(), 2);
	}

	@Override
	public Class<X> getX() {
		return x;
	}

	@Override
	public Class<Y> getY() {
		return y;
	}

	@Override
	public Class<Z> getZ() {
		return z;
	}

}
