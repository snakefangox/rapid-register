package net.snakefangox.rapidregister.registerhandler.dynamic;

import com.mojang.serialization.Lifecycle;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.MutableRegistry;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.snakefangox.rapidregister.annotations.RegisterContents;
import net.snakefangox.rapidregister.registerhandler.RegisterHandler;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Stack;

public abstract class DynamicRegisterHandler<T> extends RegisterHandler<T> {

	private final Stack<Storage> toRegister = new Stack<>();

	public DynamicRegisterHandler(Class<T> type) {
		super(type);
	}

	@Override
	protected final void register(T obj, Identifier identifier, Field field, RegisterContents classDefaults) {
		toRegister.push(new Storage(obj, identifier, field, classDefaults));
	}

	public final void lateRegister(MinecraftServer server) {
		while (!toRegister.isEmpty()) register(server, server.getRegistryManager(), toRegister.pop());
	}

	protected abstract void register(MinecraftServer server, DynamicRegistryManager manager, Storage storage);

	@Override
	protected void dataGen(T entry, Identifier identifier, Field field, Path assetPath, Path dataPath, RegisterContents classDefaults) {
	}

	protected final void addToDynRegistry(DynamicRegistryManager manager, RegistryKey<? extends Registry<T>> registryKey, Storage storage) {
		((MutableRegistry<T>) manager.get(registryKey)).add(storage.getRegistryKey(registryKey), storage.obj, Lifecycle.stable());
	}

	protected final class Storage {
		public final T obj;
		public final Identifier identifier;
		public final Field field;
		public final RegisterContents classDefaults;

		public Storage(T obj, Identifier identifier, Field field, RegisterContents classDefaults) {
			this.obj = obj;
			this.identifier = identifier;
			this.field = field;
			this.classDefaults = classDefaults;
		}

		public RegistryKey<T> getRegistryKey(RegistryKey<? extends Registry<T>> registry) {
			return RegistryKey.of(registry, identifier);
		}
	}
}
