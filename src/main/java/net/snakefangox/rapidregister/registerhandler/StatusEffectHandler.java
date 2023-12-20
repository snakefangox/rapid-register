package net.snakefangox.rapidregister.registerhandler;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.snakefangox.rapidregister.annotations.RegisterContents;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StatusEffectHandler extends RegisterHandler<StatusEffect> {

	public StatusEffectHandler() {
		super(StatusEffect.class, "mob_effect");
	}

	@Override
	protected void register(StatusEffect obj, Identifier identifier, Field field, RegisterContents classDefaults) {
		Registry.register(Registries.STATUS_EFFECT, identifier, obj);
	}

	@Override
	protected void dataGen(StatusEffect entry, Identifier identifier, Field field, Path assetPath, Path dataPath, RegisterContents classDefaults) {
		addLangKey(identifier.getNamespace(), "effect", identifier);
		ensureDirExists(Paths.get(getTexturePath()));
	}
}
