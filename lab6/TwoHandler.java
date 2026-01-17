package lab6;
/**
 * Класс для обработки аннотации @Two
 */
public class TwoHandler {

    /**
     * Читает аннотацию @Two над классом и выводит её значения
     * @param clazz класс для проверки
     */
    public static void printTwoValues(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Класс не может быть null");
        }

        if (clazz.isAnnotationPresent(Two.class)) {
            Two annotation = clazz.getAnnotation(Two.class);
            String first = annotation.first();
            int second = annotation.second();

            System.out.println("Значения аннотации @Two для класса " + clazz.getSimpleName() + ":");
            System.out.println("  first = \"" + first + "\"");
            System.out.println("  second = " + second);
        } else {
            System.out.println("Класс " + clazz.getSimpleName() + " не аннотирован @Two.");
        }
    }
}