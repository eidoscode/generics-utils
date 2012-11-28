package br.com.endrigo.generics.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.log4j.Logger;

/**
 * The purpose of this utility class is to make it easier to capture the generalization of an class. <br/>
 * This will help if your class is going to be executed throw a proxy execution such as EJB. <br/>
 *
 * @author eantonini
 * @since 1.0
 * @version 1.0
 */
public final class GenericsUtils {

	private static final Logger LOGGER = Logger.getLogger(GenericsUtils.class);

	/**
	 * The purpose of this method is to bring the first generic type informed on the super class. <br/>
	 * <br/>
	 * Example: <br/>
	 * <br/>
	 * This is your implemented class.<br/>
	 * <code>
	 * <pre>
	 * class GarageSportCar extends Garage<SportCar> {
	 * 		// Other source code...
	 * }
	 * </pre>
	 * </code> <br/>
	 * This is your implemented class.<br/>
	 * <code>
	 * <pre>
	 * class Garage<CarType extends Car> {
	 *
	 * 		private Class<CarType> carType;
	 *
	 * 		Garage() {
	 * 			carType = GenericsUtils.getSuperClassGenericType(getClass());
	 * 		}
	 * }
	 * </pre>
	 * </code> On this sample, inside an instance of the GarageSportCar, the value on the carType variable is
	 * going to be the class SportCar.
	 *
	 * @param clazz
	 *            Class to search the parameter passed throw the generalization. Normally is going to be
	 *            "getClass()".
	 * @param clazzType
	 *            The Class that need to be implemented and you are looking for on the first level of
	 *            implementation.
	 * @return The class type of the first parameter passed on the generalization of the class. If not found,
	 *         is going to return <code>null</code>.
	 */
	public static <T> Class<T> getSuperClassGenericType(Class<?> clazz, Class<?> clazzType) {
		return getSuperClassGenericType(clazz, clazzType, 0);
	}

	/**
	 * The purpose of this method is to bring the first generic type informed on the super class. <br/>
	 * <br/>
	 * Example: <br/>
	 * <br/>
	 * This is your implemented class.<br/>
	 * <code>
	 * <pre>
	 * class GarageExpensiveSportCar extends Garage<SportCar, ExpensivePrice> {
	 *
	 * 		// Other source code...
	 * }
	 * </pre>
	 * </code> <br/>
	 * This is your implemented class.<br/>
	 * <code>
	 * <pre>
	 * class Garage<CarType extends Car, PriceType extends Pricing> {
	 *
	 * 		private Class<CarType> carType;
	 * 		private Class<PriceType> priceType;
	 *
	 * 		Garage() {
	 * 			carType = GenericsUtils.getSuperClassGenericType(getClass(), 0);
	 * 			priceType = GenericsUtils.getSuperClassGenericType(getClass(), 1);
	 * 		}
	 * }
	 * </pre>
	 * </code> On this sample, inside an instance of the GarageSportCar, the value on the carType variable is
	 * going to be the class SportCar and the value on the priceType variable is going to be the class
	 * ExpensivePrice.
	 *
	 * @param clazz
	 *            Class to search the parameter passed throw the generalization. Normally is going to be
	 *            "getClass()".
	 * @param clazzType
	 *            The Class that need to be implemented and you are looking for on the first level of
	 *            implementation.
	 * @param index
	 *            The index of the parameter that you want. Remember, this is a base zero index.
	 * @return The class type of the first parameter passed on the generalization of the class. If not found,
	 *         is going to return <code>null</code>.
	 */
	@SuppressWarnings("unchecked")
	public static <X, T> Class<T> getSuperClassGenericType(final Class<?> clazz, Class<X> clazzType, int index) {
		if (clazz == null) {
			throw new NullPointerException("The class argument is mandatory.");
		}
		if (index < 0) {
			throw new IllegalArgumentException("The index value can not be less than 0.");
		}

		Class<T> retValue = null;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Original class: \'" + clazz.getName() + "\' Looking for an class that extends this class: \'" + clazzType.getName() + "\'");
		}
		Class<?> retClazz = getClass(clazz, clazzType);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Found the class: \'" + ((retClazz != null) ? retClazz.getName() : "null") + "\' that extends this class: \'" + clazzType.getName() + "\'");
		}
		
		if (retClazz != null) {
			Type genericType = retClazz.getGenericSuperclass();
			if (genericType instanceof ParameterizedType) {
				Type[] params = ((ParameterizedType) genericType).getActualTypeArguments();
				if (index < params.length) {
					retValue = (Class<T>) params[index];
				}
			}
		}

		return retValue;
	}

	private static Class<?> getClass(final Class<?> clazz, Class<?> clazzType) {
		Class<?> retClass = null;

		if (clazz != null && clazzType.isAssignableFrom(clazz)) {
			retClass = clazz;
		}

		if (clazzType != null) {
			if (clazz.getSuperclass() != null) {
				Class<?> checkedClass = null;
				checkedClass = getClass(clazz.getSuperclass(), clazzType);
				if (checkedClass != null) {
					retClass = checkedClass;
				} else if (clazz.isAssignableFrom(clazzType)) {
					retClass = checkedClass;
				}
			}
		}
		return retClass;
	}

}
