package lab6;
/**
 * Класс для обработки аннотации @Validate
 */
public class ValidateHandler {
    /**
     * Выводит список классов, указанных в аннотации @Validate над переданным классом
     * @param clazz класс для проверки
     */

    public static void printValidationClasses(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Класс не может быть null");
        }

        if (clazz.isAnnotationPresent(Validate.class)) {
            Validate annotation = clazz.getAnnotation(Validate.class);
            Class<?>[] classes = annotation.value();

            System.out.println("Классы для валидации (" + clazz.getSimpleName() + "):");
            for (Class<?> c : classes) {
                System.out.println("  - " + c.getName());
            }
        } else {
            System.out.println("Класс " + clazz.getSimpleName() + " не аннотирован @Validate.");
        }
    }
}