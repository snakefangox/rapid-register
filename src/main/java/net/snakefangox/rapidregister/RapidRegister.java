package net.snakefangox.rapidregister;

import net.fabricmc.api.ModInitializer;
import net.snakefangox.rapidregister.annotations.RegisterContents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;

public class RapidRegister {

	public static final Logger LOGGER = LogManager.getLogger();

	/**
	 * Registers all content declared in classes that are in subpackages from the given initializer.
	 * Useful if your {@link ModInitializer} is in your root source package for your mod.
	 */
	public static void register(ModInitializer modInitializer, String modid) {
		register(modid, modInitializer.getClass().getPackage().getName());
	}

	/**
	 * Registers all content declared in classes that are in subpackages from the given root package.
	 * Useful if your {@link ModInitializer} is in a subpackage or you want to be more specific about
	 * the location of your content packages.
	 */
	public static void register(String rootContentPackage, String modid) {
		Reflections reflections = new Reflections(rootContentPackage, new TypeAnnotationsScanner());
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(RegisterContents.class);
		classes.forEach(c -> registerClass(c, modid));
	}

	private static void registerClass(Class<?> clazz, String modid) {
		Arrays.stream(clazz.getFields()).filter(f -> Modifier.isStatic(f.getModifiers()))
				.forEach(f -> registerField(f, modid));
	}

	private static void registerField(Field field, String modid) {

	}
}
