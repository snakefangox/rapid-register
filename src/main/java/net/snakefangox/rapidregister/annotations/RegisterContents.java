package net.snakefangox.rapidregister.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to indicate a class will have every field
 * that is not marked with {@link Exclude}
 * passed through the register.
 * Doesn't do anything itself, only a reminder.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface RegisterContents {
}
