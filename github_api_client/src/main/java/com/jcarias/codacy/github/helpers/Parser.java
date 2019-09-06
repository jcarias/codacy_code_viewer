package com.jcarias.codacy.github.helpers;

/**
 * Generist Parser
 * @param <T> Target Type
 * @param <S> Source Type
 */
public interface Parser<T, S>  {
	public T parse(S sourceObject);
}
