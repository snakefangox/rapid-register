package net.snakefangox.rapidregister.storage;

import net.minecraft.server.MinecraftServer;
import net.snakefangox.rapidregister.RapidRegister;
import net.snakefangox.rapidregister.annotations.Exclude;
import net.snakefangox.rapidregister.annotations.RegisterContents;
import net.snakefangox.rapidregister.registerhandler.dynamic.DynamicRegisterHandler;
import net.snakefangox.rapidregister.registerhandler.RegisterHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TypeRegisterSet {

	private final List<RegisterHandler<?>> registerHandlers = new ArrayList<>();
	private final List<DynamicRegisterHandler<?>> dynamicRegisterHandlers = new ArrayList<>();
	private final Set<Class<?>> coveredTypes = new HashSet<>();

	public void addHandler(RegisterHandler<?> toAdd) {
		if (coveredTypes.contains(toAdd.getType())) {
			RapidRegister.LOGGER.warn("Could not add TypeHandler " + toAdd.getClass().getName() + ", handler already exists for that type");
			return;
		}
		registerHandlers.add(toAdd);
		if (toAdd instanceof DynamicRegisterHandler<?>) dynamicRegisterHandlers.add((DynamicRegisterHandler<?>) toAdd);
		coveredTypes.add(toAdd.getType());
		registerHandlers.sort(null);
	}

	public void attemptRegister(Class<?> clazz, Field field, String modid) {
		if (field.getAnnotation(Exclude.class) != null) return;
		RegisterContents register = clazz.getAnnotation(RegisterContents.class);
		if (register == null) return;
		for (RegisterHandler<?> registerHandler : registerHandlers) {
			if (registerHandler.attemptRegister(register, field, modid)) return;
		}
		RapidRegister.LOGGER.warn("No matching RegisterHandler found for field " + field.getName() + " in class " + clazz.getName());
	}

	public void onServerStart(MinecraftServer server) {
		dynamicRegisterHandlers.forEach(dynamicRegisterHandler -> dynamicRegisterHandler.lateRegister(server));
	}
}
