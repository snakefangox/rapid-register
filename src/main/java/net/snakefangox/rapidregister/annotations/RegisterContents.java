package net.snakefangox.rapidregister.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Required on classes that are going to be passed to {@link net.snakefangox.rapidregister.RapidRegister#register(String, Class[])}.
 * Also allows for some class wide defaults.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RegisterContents {
	BlockMeta defaultBlockMeta() default @BlockMeta();
}
