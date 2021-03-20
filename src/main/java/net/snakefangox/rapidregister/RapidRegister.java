package net.snakefangox.rapidregister;

import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.block.Block;
import net.snakefangox.rapidregister.registerhandler.*;
import net.snakefangox.rapidregister.registerhandler.dynamic.BiomeHandler;
import net.snakefangox.rapidregister.registerhandler.dynamic.DimensionTypeHandler;
import net.snakefangox.rapidregister.registerhandler.FeatureHandler;
import net.snakefangox.rapidregister.storage.TypeRegisterSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class RapidRegister implements ModInitializer {

	public static final Logger LOGGER = LogManager.getLogger();
	public static final String DATA_GEN_ARG = "rapidRegister.dataGen";
	public static final String LANG_ARG = "rapidRegister.lang";
	public static final String EN_US = "en_us";

	private static final TypeRegisterSet typeRegister = new TypeRegisterSet();
	private static Path dataGenPath = null;
	private static String lang = EN_US;
	private static boolean runDataGen = false;

	@Override
	public void onInitialize() {
		ServerLifecycleEvents.SERVER_STARTING.register(typeRegister::onServerStart);
		register("modid", TestClass.class);
	}

	/**
	 * Registers all content declared in the given classes.
	 * All classes must be marked with {@link net.snakefangox.rapidregister.annotations.RegisterContents}
	 */
	public static void register(String modid, Class<?>... classes) {
		Arrays.stream(classes).forEach(c -> registerClass(c, modid));
	}

	private static void registerClass(Class<?> clazz, String modid) {
		Arrays.stream(clazz.getFields()).filter(f -> Modifier.isStatic(f.getModifiers()))
				.forEach(f -> typeRegister.attemptRegister(clazz, f, modid));
	}

	public static void addRegisterHandler(RegisterHandler<?> registerHandler) {
		typeRegister.addHandler(registerHandler);
	}

	private static void checkDataGen() {
		String path = System.getProperty(DATA_GEN_ARG);
		lang = System.getProperty(LANG_ARG, EN_US);
		if (path != null) dataGenPath = Paths.get(path).toAbsolutePath().normalize();
		runDataGen = dataGenPath != null && FabricLoader.getInstance().isDevelopmentEnvironment();
	}

	public static boolean runDataGen() {
		return runDataGen;
	}

	public static Path getResourcePath() {
		return dataGenPath;
	}

	public static Path getAssetPath(String modid) {
		return Paths.get(getResourcePath().toString(), "assets", modid);
	}

	public static Path getDataPath(String modid) {
		return Paths.get(getResourcePath().toString(), "data", modid);
	}

	public static String getLang() {
		return lang;
	}

	private static void registerDefaultHandlers() {
		addRegisterHandler(new ItemHandler<>(Item.class));
		addRegisterHandler(new ItemHandler<>(ToolItem.class));
		addRegisterHandler(new BlockHandler<>(Block.class));
		addRegisterHandler(new BlockEntityTypeHandler());
		addRegisterHandler(new ScreenHandlerTypeHandler());
		addRegisterHandler(new EntityTypeHandler());
		addRegisterHandler(new SoundHandler());
		addRegisterHandler(new StatusEffectHandler());
		addRegisterHandler(new FeatureHandler());
		addRegisterHandler(new BiomeHandler());
		addRegisterHandler(new DimensionTypeHandler());
	}

	static {
		checkDataGen();
		registerDefaultHandlers();
	}
}
