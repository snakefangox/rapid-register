package net.snakefangox.rapidregister.registerhandler;

import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.snakefangox.rapidregister.annotations.RegisterContents;

import java.lang.reflect.Field;
import java.nio.file.Path;

@SuppressWarnings("rawtypes")
public class EntityTypeHandler extends RegisterHandler<EntityType> {

	public EntityTypeHandler() {
		super(EntityType.class, "entity");
	}

	@Override
	protected void register(EntityType obj, Identifier identifier, Field field, RegisterContents classDefaults) {
		Registry.register(Registry.ENTITY_TYPE, identifier, obj);
	}

	@Override
	protected void dataGen(EntityType entry, Identifier identifier, Field field, Path assetPath, Path dataPath, RegisterContents classDefaults) {
		addLangKey(identifier);
	}
}
