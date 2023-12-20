package net.snakefangox.rapidregister.registerhandler;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.snakefangox.rapidregister.annotations.RegisterContents;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings("rawtypes")
public class ScreenHandlerTypeHandler extends RegisterHandler<ScreenHandlerType> {

	public ScreenHandlerTypeHandler() {
		super(ScreenHandlerType.class, "gui");
	}

	@Override
	protected void register(ScreenHandlerType obj, Identifier identifier, Field field, RegisterContents classDefaults) {
		Registry.register(Registries.SCREEN_HANDLER, identifier, obj);
	}

	@Override
	protected void dataGen(ScreenHandlerType entry, Identifier identifier, Field field, Path assetPath, Path dataPath, RegisterContents classDefaults) {
		addLangKey(identifier.getNamespace(), "container", identifier);
		ensureDirExists(Paths.get(getTexturePath()));
	}
}
