package com.jcarias.git.converters;

public interface Converter<S,T> {
	T convert(S object);
}
