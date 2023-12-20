package net.snakefangox.rapidregister.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Contains extra metadata for a block being registered.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ItemMeta {
	boolean registerItem() default true;

	/** For modded blocks use the ItemGroup's namespace:id here */
	String itemGroup() default "building_blocks";

	/** This is only used for block items */
	int maxCount() default 64;

	/** This is only used for block items */
	int rarity() default 0;
}
