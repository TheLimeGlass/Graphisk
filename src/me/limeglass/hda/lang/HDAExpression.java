package me.limeglass.hda.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.event.Event;
import java.lang.reflect.ParameterizedType;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.limeglass.hda.HolographicDisplaysAddon;
import me.limeglass.hda.Syntax;
import me.limeglass.hda.utils.Utils;
import me.limeglass.hda.utils.annotations.AllChangers;
import me.limeglass.hda.utils.annotations.Changers;
import me.limeglass.hda.utils.annotations.DetermineSingle;
import me.limeglass.hda.utils.annotations.Multiple;
import me.limeglass.hda.utils.annotations.Settable;
import me.limeglass.hda.utils.annotations.Single;

public abstract class HDAExpression<T> extends SimpleExpression<T> implements DataChecker {

	private Class<T> expressionClass;
	protected ExpressionData expressions;
	protected int patternMark;
	protected ParseResult parser;
	private List<Object> values = new ArrayList<Object>();
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends T> getReturnType() {
		if (expressionClass == null) expressionClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return expressionClass;
	}
	
	public String[] getSyntax() {
		return Syntax.get(getClass().getSimpleName());
	}
	
	@Override
	public boolean isSingle() {
		if (getClass().isAnnotationPresent(DetermineSingle.class)) {
			String value = getClass().getAnnotation(DetermineSingle.class).value();
			if (value.equals("Determine")) {
				return expressions.getExpressions()[0].isSingle();
			}
			return !parser.expr.contains(value);
		}
		return getClass().isAnnotationPresent(Single.class);
	}

	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		if (expressions != null && getSyntax() != null) this.expressions = new ExpressionData(expressions, getSyntax()[0]);
		this.patternMark = parser.mark;
		this.parser = parser;
		return true;
	}

	@Override
	public String toString(Event event, boolean debug) {
		String modSyntax = Syntax.isModified(getClass()) ? "Modified syntax: " + Arrays.toString(getSyntax()) : Arrays.toString(getSyntax());
		if (expressions != null && event != null) for (Expression<?> expression : expressions.getExpressions()) values.add(expression.getSingle(event));
		if (event != null) HolographicDisplaysAddon.debugMessage(getClass().getSimpleName() + " - " + modSyntax + " (" + event.getEventName() + ")" + " Data: " + Arrays.toString(values.toArray()));
		return getClass().getSimpleName() + " - " + Arrays.toString(getSyntax());
	}
	
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		Class<?>[] returnable = (getClass().isAnnotationPresent(Multiple.class)) ? CollectionUtils.array(Utils.getArrayClass(expressionClass)) : CollectionUtils.array(expressionClass);
		if (getClass().isAnnotationPresent(Settable.class)) returnable = getClass().getAnnotation(Settable.class).value();
		if (getClass().isAnnotationPresent(AllChangers.class)) return returnable;
		if (!getClass().isAnnotationPresent(Changers.class)) return null;
		return (Arrays.asList(getClass().getAnnotation(Changers.class).value()).contains(mode)) ? returnable : null;
	}
	
	public <V> Boolean isNull(Event event, Class<?>... types) {
		return isNull(event, expressions, types);
	}
	
	public Boolean isNull(Event event, int index) {
		return isNull(event, expressions, index);
	}

	public Boolean areNull(Event event) {
		return areNull(event, expressions);
	}
}