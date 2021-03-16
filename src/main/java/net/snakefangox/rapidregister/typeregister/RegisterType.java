package net.snakefangox.rapidregister.typeregister;

import net.minecraft.util.Identifier;
import net.snakefangox.rapidregister.RapidRegister;

import java.lang.reflect.Field;
import java.util.Locale;

public abstract class RegisterType<T> {

	private final Class<T> type;

	protected RegisterType(Class<T> type) {
		this.type = type;
	}

	/** Tries to register the provided field, returns true if the field can be registered by this handler */
	public final boolean attemptRegister(Field field, String modid) {
		try {
			field.setAccessible(true);
			Object obj = field.get(null);
			if (type.isInstance(obj)) {
				Identifier identifier = new Identifier(modid, field.getName().toLowerCase(Locale.ROOT));
				register(obj, identifier);
				return true;
			}
		} catch (IllegalAccessException e) {
			RapidRegister.LOGGER.warn("Could not register " + field.getName() + " due to " + e.getLocalizedMessage());
		} finally {
			field.setAccessible(false);
		}
		return false;
	}

	protected abstract void register(Object obj, Identifier identifier);
}
