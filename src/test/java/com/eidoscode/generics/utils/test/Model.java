package com.eidoscode.generics.utils.test;

import com.eidoscode.generics.utils.GenericsUtils;

/**
 * Model.
 * 
 * @author antonini
 * 
 * @param <X>
 *          Object
 * @param <Y>
 *          Object
 * @param <Z>
 *          Object
 */
public class Model<X, Y, Z> implements IModel<X, Y, Z> {

  private final Class<X> x;
  private final Class<Y> y;
  private final Class<Z> z;

  public static final String PUB_STAT_FIN = "PUB_STAT_FIN";
  public static String PUB_STAT = "PUB_STAT";
  public final String finPub = "PUB_FIN";
  public String pub = "PUB";

  private static final String PRIV_STAT_FIN = "PRIV_STAT_FIN";
  private static String PRIV_STAT = "PRIV_STAT";
  private final String privPub = "PRIV_FIN";
  private String priv = "PRIV";

  static final String DEF_STAT_FIN = "DEF_STAT_FIN";
  static String DEF_STAT = "DEF_STAT";
  final String defPub = "DEF_FIN";
  String def = "DEF";

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
   * 
   * @return Object
   */
  @Override
  public Class<X> getX() {
    return x;
  }

  /**
   * Return typed Object
   * 
   * @return Object
   */
  @Override
  public Class<Y> getY() {
    return y;
  }

  /**
   * Return typed Object
   * 
   * @return Object
   */
  @Override
  public Class<Z> getZ() {
    return z;
  }

  public static String getPubStatFin() {
    return PUB_STAT_FIN;
  }

  public static String getPUB_STAT() {
    return PUB_STAT;
  }

  public String getFinPub() {
    return finPub;
  }

  public String getPub() {
    return pub;
  }

  public static String getPrivStatFin() {
    return PRIV_STAT_FIN;
  }

  public static String getPRIV_STAT() {
    return PRIV_STAT;
  }

  public String getPrivPub() {
    return privPub;
  }

  public String getPriv() {
    return priv;
  }

  public static String getDefStatFin() {
    return DEF_STAT_FIN;
  }

  public static String getDEF_STAT() {
    return DEF_STAT;
  }

  public String getDefPub() {
    return defPub;
  }

  public String getDef() {
    return def;
  }

}
