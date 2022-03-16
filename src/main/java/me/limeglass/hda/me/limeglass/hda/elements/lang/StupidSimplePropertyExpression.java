package me.limeglass.hda.elements.lang;

import com.google.common.reflect.TypeToken;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.registrations.Classes;

public abstract class StupidSimplePropertyExpression<F, T> extends SimplePropertyExpression<F, T> {

	private String property, fromType;
	private boolean plurals, the;

	@SuppressWarnings({ "serial", "unchecked" })
	public StupidSimplePropertyExpression(String property) {
		this(property, Classes.getExactClassName((Class<T>) new TypeToken<T>(){}.getType()));
	}

	public StupidSimplePropertyExpression(String property, String fromType) {
		this.property = property;
		this.fromType = fromType;
	}

	public StupidSimplePropertyExpression<F, T> the() {
		this.the = true;
		return this;
	}

	public StupidSimplePropertyExpression<F, T> plurals() {
		this.fromType = fromType + "s";
		this.plurals = true;
		return the();
	}

	@SuppressWarnings({ "unchecked", "serial" })
	public void register() {
		if (the)
			property = "[the] " + property;
		if (plurals)
			property = "[all [of]] " + property;
		register((Class<? extends Expression<T>>)getClass(), (Class<T>) new TypeToken<T>(){}.getType(), property, fromType);
	}

	@SuppressWarnings({ "serial", "unchecked" })
	@Override
	public Class<? extends T> getReturnType() {
		return (Class<? extends T>) new TypeToken<T>(){}.getType();
	}

	@Override
	protected String getPropertyName() {
		return getClass().getName();
	}

}
