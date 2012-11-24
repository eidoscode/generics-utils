package br.com.endrigo.generics.utils;

public interface IModel<X, Y, Z> {

	Class<X> getX();

	Class<Y> getY();

	Class<Z> getZ();

}