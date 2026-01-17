package lab6;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для пометки методов, которые должны быть вызваны автоматически.
 */
@Target(ElementType.METHOD)        //применяется только к методам
@Retention(RetentionPolicy.RUNTIME) //сохраняется до времени выполнения
public @interface Invoke {
}