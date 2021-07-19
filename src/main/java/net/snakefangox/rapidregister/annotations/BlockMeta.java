package net.snakefangox.rapidregister.annotations;

import net.minecraft.util.Identifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Contains extra metadata for a block being registered.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BlockMeta {
	boolean registerItem() default true;

	/** For modded blocks use the ItemGroup's {@link Identifier#toString} here */
	String blockItemGroup() default "building_blocks";

	int maxCount() default 64;

	int rarity() default 0;
}
