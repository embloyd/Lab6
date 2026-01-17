package lab6;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Аннотация с двумя обязательными полями: строкой и числом
 */
@Target(ElementType.TYPE)          //применяется только к классам
@Retention(RetentionPolicy.RUNTIME) //доступна во время выполнения
public @interface Two {
    /**
     * Обязательное строковое значение
     */
    String first();

    /**
     * Обязательное целочисленное значение
     */
    int second();
}