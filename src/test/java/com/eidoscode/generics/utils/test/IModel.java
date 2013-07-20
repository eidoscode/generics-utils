package com.eidoscode.generics.utils.test;

/**
 * Interface of the model.
 * 
 * @author eantonini
 *
 * @param <X> Object 
 * @param <Y> Object
 * @param <Z> Object
 */
public interface IModel<X, Y, Z> {

	/**
	 * Return typed Object
	 * @return Object
	 */
	Class<X> getX();

	/**
	 * Return typed Object
	 * @return Object
	 */
	Class<Y> getY();

	/**
	 * Return typed Object
	 * @return Object
	 */
	Class<Z> getZ();

}