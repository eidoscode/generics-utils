package br.com.endrigo.generics.utils;

/**
 * @deprecated Use the {@link com.eidoscode.generics.utils.GenericsUtils}
 *             instead of this class
 * @author eantonini
 * 
 */
@Deprecated
public class GenericsUtils {

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
	 * </code> On this sample, inside an instance of the GarageSportCar, the
	 * value on the carType variable is going to be the class SportCar.
	 * 
	 * @param clazz
	 *            Class to search the parameter passed throw the generalization.
	 *            Normally is going to be "getClass()".
	 * @param clazzType
	 *            The Class that need to be implemented and you are looking for
	 *            on the first level of implementation.
	 * @return The class type of the first parameter passed on the
	 *         generalization of the class. If not found, is going to return
	 *         <code>null</code>.
	 */
	@Deprecated
	public static <T> Class<T> getSuperClassGenericType(Class<?> clazz,
			Class<?> clazzType) {
		return com.eidoscode.generics.utils.GenericsUtils
				.getSuperClassGenericType(clazz, clazzType, 0);
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
	 * </code> On this sample, inside an instance of the GarageSportCar, the
	 * value on the carType variable is going to be the class SportCar and the
	 * value on the priceType variable is going to be the class ExpensivePrice.
	 * 
	 * @param clazz
	 *            Class to search the parameter passed throw the generalization.
	 *            Normally is going to be "getClass()".
	 * @param clazzType
	 *            The Class that need to be implemented and you are looking for
	 *            on the first level of implementation.
	 * @param index
	 *            The index of the parameter that you want. Remember, this is a
	 *            base zero index.
	 * @return The class type of the first parameter passed on the
	 *         generalization of the class. If not found, is going to return
	 *         <code>null</code>.
	 */
	@Deprecated
	public static <X, T> Class<T> getSuperClassGenericType(
			final Class<?> clazz, Class<X> clazzType, int index) {
		return com.eidoscode.generics.utils.GenericsUtils
				.getSuperClassGenericType(clazz, clazzType, index);
	}

}
