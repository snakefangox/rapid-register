package net.snakefangox.rapidregister.registerhandler;

import net.minecraft.util.Identifier;
import net.snakefangox.rapidregister.RapidRegister;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Locale;

public abstract class RegisterHandler<T> implements Comparable<RegisterHandler<?>> {

	private final Class<T> type;

	public RegisterHandler(Class<T> type) {
		this.type = type;
	}

	/** Tries to register the provided field, returns true if the field can be registered by this handler */
	@SuppressWarnings("unchecked")
	public final boolean attemptRegister(Field field, String modid) {
		try {
			field.setAccessible(true);
			Object obj = field.get(null);
			if (type.isInstance(obj)) {
				T entry = (T) obj;
				Identifier identifier = new Identifier(modid, field.getName().toLowerCase(Locale.ROOT));
				register(entry, identifier, field.getAnnotations());
				if (RapidRegister.shouldRunDataGen(modid))
					dataGen(entry, identifier, field.getAnnotations(), RapidRegister.getAssetPath(modid), RapidRegister.getDataPath(modid));
				return true;
			}
		} catch (IllegalAccessException e) {
			RapidRegister.LOGGER.warn("Could not register " + field.getName() + " due to " + e.getLocalizedMessage());
		} finally {
			field.setAccessible(false);
		}
		return false;
	}

	/**
	 * We want to try and register with more specific RegisterTypes first,
	 * I.E. we want to go ArmourItem then Item not the other way around.
	 */
	@Override
	public final int compareTo(@NotNull RegisterHandler<?> o) {
		boolean isSuper = type.isAssignableFrom(o.type);
		boolean isExtend = o.type.isAssignableFrom(type);
		if (isExtend) return -1;
		if (isSuper) return 1;
		return 0;
	}

	//TODO Create method to get or create dir path

	protected abstract void register(T obj, Identifier identifier, Annotation[] annotations);

	protected abstract void dataGen(T entry, Identifier identifier, Annotation[] annotations, String assetPath, String dataPath);

	public Class<T> getType() {
		return type;
	}
}
