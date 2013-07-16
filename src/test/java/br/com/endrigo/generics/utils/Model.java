package br.com.endrigo.generics.utils;

import com.eidoscode.generics.utils.GenericsUtils;


/**
 * Model.
 * 
 * @author eantonini
 *
 * @param <X> Object 
 * @param <Y> Object
 * @param <Z> Object
 */
public class Model<X, Y, Z> implements IModel<X, Y, Z> {

	private final Class<X> x;
	private final Class<Y> y;
	private final Class<Z> z;

	/**
	 * Constructor.
	 */
	public Model() {
		x = GenericsUtils.getSuperClassGenericType(getClass(), Model.class);
		y = GenericsUtils.getSuperClassGenericType(getClass(), Model.class, 1);
		z = GenericsUtils.getSuperClassGenericType(getClass(), Model.class, 2);
	}

	/**
	 * Return typed Object
	 * @return Object
	 */
	@Override
	public Class<X> getX() {
		return x;
	}

	/**
	 * Return typed Object
	 * @return Object
	 */
	@Override
	public Class<Y> getY() {
		return y;
	}

	/**
	 * Return typed Object
	 * @return Object
	 */
	@Override
	public Class<Z> getZ() {
		return z;
	}

}
