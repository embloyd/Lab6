package lab6;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Аннотация для указания кешируемых областей
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
    /**
     * Список кешируемых областей (например, "users", "products")
     * По умолчанию — пустой массив
     */
    String[] value() default {};
}