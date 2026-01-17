package lab6;

import java.util.Arrays;
/**
 * Класс для обработки аннотации @Cache
 */
public class CacheHandler {

    /**
     * Выводит список кешируемых областей из аннотации @Cache над классом
     * @param clazz класс для проверки
     */
    public static void printCacheRegions(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Класс не может быть null");
        }

        if (clazz.isAnnotationPresent(Cache.class)) {
            Cache annotation = clazz.getAnnotation(Cache.class);
            String[] regions = annotation.value();

            if (regions.length == 0) {
                System.out.println("Класс " + clazz.getSimpleName() + ": список кешируемых областей пуст");
            } else {
                System.out.println("Класс " + clazz.getSimpleName() + " кэширует области:");
                for (String region : regions) {
                    System.out.println("  - " + region);
                }
            }
        } else {
            System.out.println("Класс " + clazz.getSimpleName() + " не аннотирован @Cache.");
        }
    }
}