package br.com.endrigo.generics.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.log4j.Logger;

/**
 * The purpose of this utility class is to make it easier to capture the
 * generalization of an class. <br/>
 * This will help if your class is going to be executed throw a proxy execution
 * such as EJB. <br/>
 * 
 * @author antonini
 * @since 1.0
 * @version 1.0
 */
public final class GenericsUtils {

	private static final Logger LOGGER = Logger.getLogger(GenericsUtils.class);

	/**
	 * The purpose of this method is to bring the first generic type informed on
	 * the super class. <br/>
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
	 * </code>
	 * <br/>
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
	 * </code>
	 * On this sample, inside an instance of the GarageSportCar, the value on the carType variable is going to be the class SportCar.
	 *  
	 * @param clazz Class to search the parameter passed throw the generalization. Normally is going to be "getClass()".
	 * 
	 * @return The class type of the first parameter passed on the generalization of the class. If not found, is going to return <code>null</code>.
	 */
	public static <T> Class<T> getSuperClassGenericType(Class<?> clazz) {
		return getSuperClassGenericType(clazz, 0);
	}

	/**
	 * The purpose of this method is to bring the first generic type informed on
	 * the super class. <br/>
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
	 * </code>
	 * <br/>
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
	 * </code>
	 * On this sample, inside an instance of the GarageSportCar, the value on the carType variable is going to be the class SportCar and the value on the priceType variable is going to be the class ExpensivePrice.
	 *  
	 * @param clazz Class to search the parameter passed throw the generalization. Normally is going to be "getClass()".
	 * @param index The index of the parameter that you want. Remember, this is a base zero index.
	 * 
	 * @return The class type of the first parameter passed on the generalization of the class. If not found, is going to return <code>null</code>.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getSuperClassGenericType(Class<?> clazz,
			int index) {
		if (clazz == null) {
			throw new NullPointerException("The class argument is mandatory.");
		}
		if (index < 0) {
			throw new IllegalArgumentException("The index value can not be lower then 0.");
		}
		
		Class<T> retValue = null;
		System.out.println(">> " + clazz.getName());
		clazz = getClass(clazz);

		Type genericType = clazz.getGenericSuperclass();
		if (genericType instanceof ParameterizedType) {
			Type[] params = ((ParameterizedType) genericType).getActualTypeArguments();
			if (index < params.length) {
				retValue = (Class<T>) params[index];
			}
		}
		
		return retValue;
	}

	private static Class<?> getClass(final Class<?> clazz) {
		Class<?> retClass = clazz;
		if (clazz.getName().contains("$")) {
			retClass = getClass((Class<?>) clazz.getGenericSuperclass());
		}
		LOGGER.debug("The ");
		return retClass;
	}
}
