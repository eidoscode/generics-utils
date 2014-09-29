package com.eidoscode.generics.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The purpose of this utility class is to make it easier to capture the
 * generalization of an class. <br/>
 * This will help you if your class is going to be executed throw a proxy
 * execution such as EJB.<br/>
 * 
 * @author antonini
 * @since 1.0
 * @version 1.0.3
 */
public final class GenericsUtils {

  // private static final Logger LOGGER = Logger.getLogger(GenericsUtils.class);

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
   * // Other source code...
   * }
   * </pre>
   * </code> <br/>
   * This is your implemented class.<br/>
   * <code>
   * <pre>
   * class Garage<CarType extends Car, PriceType extends Pricing> {
   * 
   * private Class<CarType> carType;
   * private Class<PriceType> priceType;
   * 
   * abstract Garage() {
   * carType = GenericsUtils.getSuperClassGenericType(getClass(), Garage.class, 0);
   * priceType = GenericsUtils.getSuperClassGenericType(getClass(), Garage.class, 1);
   * }
   * }
   * </pre>
   * </code> On this sample, inside an instance of the GarageSportCar, the value
   * on the carType variable is going to be the class SportCar and the value on
   * the priceType variable is going to be the class ExpensivePrice. <br/>
   * <br/>
   * <i>Note: The source of the following parameter was a merge between the
   * original source code and the code provided on the StackOverflow thread
   * http://stackoverflow.com/a/17301917/1501876.</i>
   * 
   * @param targetClazz
   *          Class to search the parameter passed throw the generalization.
   *          Normally is going to be "getClass()" or the desired object.
   * @param baseClazz
   *          The Class that need to be implemented and you are looking for on
   *          the first level of implementation.
   * @param index
   *          The index of the parameter that you want. Remember, this is a base
   *          zero index.
   * @param actualArgs
   *          The actual type arguments passed to the targetClazz (recursive
   *          helper parameter).
   * @return The class type of the first parameter passed on the generalization
   *         of the class. If not found, is going to return <code>null</code>.
   */
  @SuppressWarnings("unchecked")
  public static <T> Class<T> getSuperClassGenericType(final Class<?> targetClazz, Class<?> baseClazz, int index, Type... actualArgs) {
    if (targetClazz == null) {
      throw new NullPointerException("The class argument is mandatory.");
    }
    if (index < 0) {
      throw new IllegalArgumentException("The index value can not be less than 0.");
    }

    // If actual types are omitted, the type parameters will be used instead.
    if (actualArgs.length == 0) {
      actualArgs = targetClazz.getTypeParameters();
    }
    targetClazz.getGenericSuperclass();
    // map type parameters into the actual types
    Map<String, Type> typeVariables = new HashMap<String, Type>();
    for (int i = 0; i < actualArgs.length; i++) {
      TypeVariable<?> typeVariable = (TypeVariable<?>) targetClazz.getTypeParameters()[i];
      typeVariables.put(typeVariable.getName(), actualArgs[i]);
    }

    // Find direct ancestors (superclass, interfaces)
    List<Type> ancestors = new LinkedList<Type>();
    if (targetClazz.getGenericSuperclass() != null) {
      ancestors.add(targetClazz.getGenericSuperclass());
    }
    for (Type t : targetClazz.getGenericInterfaces()) {
      ancestors.add(t);
    }

    // Recurse into ancestors (superclass, interfaces)
    for (Type type : ancestors) {
      if (type instanceof Class<?>) {
        // ancestor is non-parameterized. Recurse only if it matches the base
        // class.
        Class<?> ancestorClass = (Class<?>) type;
        if (baseClazz.isAssignableFrom(ancestorClass)) {
          return getSuperClassGenericType((Class<? extends T>) ancestorClass, baseClazz, index);
        }
      }
      if (type instanceof ParameterizedType) {
        // ancestor is parameterized. Recurse only if the raw type matches the
        // base class.
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type rawType = parameterizedType.getRawType();
        if (rawType instanceof Class<?>) {
          Class<?> rawTypeClass = (Class<?>) rawType;
          if (baseClazz.isAssignableFrom(rawTypeClass)) {

            // loop through all type arguments and replace type variables with
            // the actually known types
            List<Type> resolvedTypes = new LinkedList<Type>();
            for (Type t : parameterizedType.getActualTypeArguments()) {
              if (t instanceof TypeVariable<?>) {
                Type resolvedType = typeVariables.get(((TypeVariable<?>) t).getName());
                resolvedTypes.add(resolvedType != null ? resolvedType : t);
              } else {
                resolvedTypes.add(t);
              }
            }

            return getSuperClassGenericType((Class<? extends T>) rawTypeClass, baseClazz, index, resolvedTypes.toArray(new Type[] {}));
          }
        }
      }
    }

    // we have a result if we reached the base class.
    if (targetClazz.equals(baseClazz)) {
      Type retType = actualArgs[index];
      // It is not been possible to resolve same level of type.
      // if (retType instanceof TypeVariable<?>) {
      // TypeVariable<?> typeVariable = (TypeVariable<?>) retType;
      // } else
      if (retType instanceof Class<?>) {
        return (Class<T>) retType;
      }
    }
    return null;
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
  public static Class<?> getClass(final Class<?> clazz, Class<?> clazzType) {
    if (clazz == null) {
      throw new NullPointerException("The class argument is mandatory.");
    }

    Class<?> retClass = null;

    if (clazzType == null || clazzType.isAssignableFrom(clazz)) {
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
