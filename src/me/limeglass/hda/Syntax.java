package me.limeglass.hda;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.util.SimpleEvent;
import me.limeglass.hda.utils.Utils;
import me.limeglass.hda.utils.annotations.AllChangers;
import me.limeglass.hda.utils.annotations.Changers;
import me.limeglass.hda.utils.annotations.Disabled;

public class Syntax {

	private static HashMap<String, String[]> modified = new HashMap<String, String[]>();
	private static HashMap<String, String[]> completeSyntax = new HashMap<String, String[]>();

	public static String[] register(Class<?> syntaxClass, String... syntax) {
		if (syntaxClass.isAnnotationPresent(Disabled.class)) return null;
		String type = "Expressions";
		if (Condition.class.isAssignableFrom(syntaxClass)) type = "Conditions";
		else if (Effect.class.isAssignableFrom(syntaxClass)) type = "Effects";
		else if (SimpleEvent.class.isAssignableFrom(syntaxClass)) type = "Events";
		else if (PropertyExpression.class.isAssignableFrom(syntaxClass)) type = "PropertyExpressions";
		String node = "Syntax." + type + "." + syntaxClass.getSimpleName() + ".";
		if (!HolographicDisplaysAddon.getConfiguration("syntax").isSet(node + "enabled")) {
			HolographicDisplaysAddon.getConfiguration("syntax").set(node + "enabled", true);
			HolographicDisplaysAddon.save("syntax");
		}
		if (syntaxClass.isAnnotationPresent(Changers.class) || syntaxClass.isAnnotationPresent(AllChangers.class)) {
			if (syntaxClass.isAnnotationPresent(AllChangers.class)) HolographicDisplaysAddon.getConfiguration("syntax").set(node + "changers", "All changers");
			else {
				ChangeMode[] changers = syntaxClass.getAnnotation(Changers.class).value();
				HolographicDisplaysAddon.getConfiguration("syntax").set(node + "changers", Arrays.toString(changers));
			}
			HolographicDisplaysAddon.save("syntax");
		}
		if (syntaxClass.isAnnotationPresent(Description.class)) {
			String[] descriptions = syntaxClass.getAnnotation(Description.class).value();
			HolographicDisplaysAddon.getConfiguration("syntax").set(node + "description", descriptions[0]);
			HolographicDisplaysAddon.save("syntax");
		}
		if (!HolographicDisplaysAddon.getConfiguration("syntax").getBoolean(node + "enabled")) {
			if (HolographicDisplaysAddon.getInstance().getConfig().getBoolean("NotRegisteredSyntax", false)) HolographicDisplaysAddon.consoleMessage(node.toString() + " didn't register!");
			return null;
		}
		if (!HolographicDisplaysAddon.getConfiguration("syntax").isSet(node + "syntax")) {
			HolographicDisplaysAddon.getConfiguration("syntax").set(node + "syntax", syntax);
			HolographicDisplaysAddon.save("syntax");
			return add(syntaxClass.getSimpleName(), syntax);
		}
		List<String> data = HolographicDisplaysAddon.getConfiguration("syntax").getStringList(node + "syntax");
		if (!Utils.compareArrays(data.toArray(new String[data.size()]), syntax)) modified.put(syntaxClass.getSimpleName(), syntax);
		if (HolographicDisplaysAddon.getConfiguration("syntax").isList(node + "syntax")) {
			List<String> syntaxes = HolographicDisplaysAddon.getConfiguration("syntax").getStringList(node + "syntax");
			return add(syntaxClass.getSimpleName(), syntaxes.toArray(new String[syntaxes.size()]));
		}
		return add(syntaxClass.getSimpleName(), new String[]{HolographicDisplaysAddon.getConfiguration("syntax").getString(node + "syntax")});
	}
	
	public static Boolean isModified(@SuppressWarnings("rawtypes") Class syntaxClass) {
		return modified.containsKey(syntaxClass.getSimpleName());
	}
	
	public static String[] get(String syntaxClass) {
		return completeSyntax.get(syntaxClass);
	}
	
	private static String[] add(String syntaxClass, String... syntax) {
		if (!completeSyntax.containsValue(syntax)) {
			completeSyntax.put(syntaxClass, syntax);
		}
		return syntax;
	}
}