package lab6;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для управления включением полей в строковое представление объекта
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ToString {

    /**
     * Указывает, включать ли элемент в toString()
     * По умолчанию — YES
     */
    Mode value() default Mode.YES;
}