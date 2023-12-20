package net.snakefangox.rapidregister.registerhandler;

import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.snakefangox.rapidregister.annotations.RegisterContents;

import java.lang.reflect.Field;
import java.nio.file.Path;

public class ItemGroupHandler extends RegisterHandler<ItemGroup> {
    public ItemGroupHandler() {
        super(ItemGroup.class);
    }

    @Override
    protected void register(ItemGroup obj, Identifier identifier, Field field, RegisterContents classDefaults) {
        Registry.register(Registries.ITEM_GROUP, identifier, obj);
    }

    @Override
    protected void dataGen(ItemGroup entry, Identifier identifier, Field field, Path assetPath, Path dataPath, RegisterContents classDefaults) {
        addLangKey(identifier);
    }
}
