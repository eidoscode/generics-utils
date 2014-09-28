package com.eidoscode.generics.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.log4j.Logger;

/**
 * The purpose of this utility class is to make it easier to capture the
 * generalization of an class. <br/>
 * This will help you if your class is going to be executed throw a proxy
 * execution such as EJB.<br/>
 * 
 * @author antonini
 * @since 1.0
 * @version 1.0.2
 */
public final class GenericsUtils {

  private static final Logger LOGGER = Logger.getLogger(GenericsUtils.class);

  /**
   * Hide constructor.
   */
  private GenericsUtils() {
  }

  /**
   * Copy the content inside the source object to the destination object.
   * 
   * @param source
   *          Source object.
   * @param destination
   *          Destination object.
   * @throws IllegalAccessException
   *           Throw if there is some problem accessing the source or
   *           destination fields.
   * @throws IllegalArgumentException
   *           Throw if there is some problem accessing the source or
   *           destination fields.
   * @throws SecurityException
   *           Throw if there is some problem accessing the source or
   *           destination fields.
   * @throws NoSuchFieldException
   *           Throw if there is some problem accessing the source or
   *           destination fields.
   * @throws NullPointerException
   *           Throw it if the source of destination is null.
   */
  public static <T, K extends T> void copyContent(T source, K destination) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException,
      SecurityException {
    if (source == null) {
      throw new NullPointerException("The source parameter is mandatory");
    }
    if (destination == null) {
      throw new NullPointerException("The destination parameter is mandatory");
    }
    Class<?> cSource = source.getClass();
    Field[] sFields = cSource.getDeclaredFields();
    Class<?> cDestination = destination.getClass();

    for (Field sField : sFields) {

      if (checkModifiers(sField, false, Modifier.STATIC)) {
        continue;
      }
      // Avoiding concurrency problem
      synchronized (cSource) {
        final String fieldName = sField.getName();

        // Save the current state of the field
        final boolean sOrigAccessible = sField.isAccessible();

        // Destination field
        final Field dField = cDestination.getDeclaredField(fieldName);
        final boolean dOrigAccessible = dField.isAccessible();

        // Make it accessible
        sField.setAccessible(true);
        dField.setAccessible(true);

        // Copy content
        Object value = sField.get(source);
        dField.set(destination, value);

        // Restore the state of the Field
        sField.setAccessible(sOrigAccessible);
        dField.setAccessible(dOrigAccessible);
      }
    }
  }

  /**
   * Check the field modifiers. If one of the given modifiers is present it
   * returns true.
   * 
   * @param field
   *          Field that want to be checked.
   * @param checkAll
   *          If <code>true</code> the given field must contains ALL the given
   *          modifiers, but if <code>false</code> means that if the given field
   *          contains one of the given modifiers it will return true.
   * @param modifiers
   *          List of modifiers.
   * @return Read the content on the parameter checkAll because that parameter
   *         can change the possible result.
   */
  public static boolean checkModifiers(Field field, boolean checkAll, int... modifiers) {
    final int fieldModifiers = field.getModifiers();

    boolean retValue = false;
    if (checkAll) {
      retValue = true;
    }
    for (int i = 0; i < modifiers.length && ((checkAll && retValue) || (!checkAll && !retValue)); i++) {
      int modifier = modifiers[i];
      retValue = (fieldModifiers & modifier) != 0;
    }
    return retValue;
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
   * </code> On this sample, inside an instance of the GarageSportCar, the value
   * on the carType variable is going to be the class SportCar.
   * 
   * @param clazz
   *          Class to search the parameter passed throw the generalization.
   *          Normally is going to be "getClass()".
   * @param clazzType
   *          The Class that need to be implemented and you are looking for on
   *          the first level of implementation.
   * @return The class type of the first parameter passed on the generalization
   *         of the class. If not found, is going to return <code>null</code>.
   */
  public static <T> Class<T> getSuperClassGenericType(Class<?> clazz, Class<?> clazzType) {
    return getSuperClassGenericType(clazz, clazzType, 0);
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
   * </code> On this sample, inside an instance of the GarageSportCar, the value
   * on the carType variable is going to be the class SportCar and the value on
   * the priceType variable is going to be the class ExpensivePrice.
   * 
   * @param clazz
   *          Class to search the parameter passed throw the generalization.
   *          Normally is going to be "getClass()".
   * @param clazzType
   *          The Class that need to be implemented and you are looking for on
   *          the first level of implementation.
   * @param index
   *          The index of the parameter that you want. Remember, this is a base
   *          zero index.
   * @return The class type of the first parameter passed on the generalization
   *         of the class. If not found, is going to return <code>null</code>.
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
          Type type = params[index];
          if (type instanceof Class) {
            retValue = (Class<T>) type;
          }
        }
      }
    }
    return retValue;
  }

  /**
   * Keep looking for the super class until it finds the implementation class of
   * the class type sent by parameter.
   * 
   * @param clazz
   *          Class that is going to be checked.
   * @param clazzType
   *          Type of the class that will be checked.
   * @return The implementation class.
   */
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
