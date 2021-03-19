package net.snakefangox.rapidregister.storage;

import net.snakefangox.rapidregister.RapidRegister;
import net.snakefangox.rapidregister.annotations.Exclude;
import net.snakefangox.rapidregister.registerhandler.RegisterHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TypeRegisterSet {

	private final List<RegisterHandler<?>> registerHandlers = new ArrayList<>();
	private final Set<Class<?>> coveredTypes = new HashSet<>();

	public void addHandler(RegisterHandler<?> toAdd) {
		if (coveredTypes.contains(toAdd.getType())) {
			RapidRegister.LOGGER.warn("Could not add TypeHandler " + toAdd.getClass().getName() + ", handler already exists for that type");
			return;
		}
		registerHandlers.add(toAdd);
		coveredTypes.add(toAdd.getType());
		registerHandlers.sort(null);
	}

	public void attemptRegister(Class<?> clazz, Field field, String modid) {
		if (field.getAnnotation(Exclude.class) != null) return;
		for (RegisterHandler<?> registerHandler : registerHandlers) {
			if (registerHandler.attemptRegister(field, modid)) return;
		}
		RapidRegister.LOGGER.warn("Field " + field.getName() + " in class " + clazz.getName() + " could not be registered");
	}
}
