package lab6;

public class DefaultHandler {

    public static void printDefaultClassName(Class<?> clazz) {
        /**
         * Выводит имя класса, указанного в аннотации @Default над переданным классом
         * <param name="clazz">Класс, который может быть аннотирован @Default</param>
         * <exception cref="IllegalArgumentException">Если clazz равен null</exception>
         */
        if (clazz == null) {
            throw new IllegalArgumentException("Класс не может быть null");
        }

        if (clazz.isAnnotationPresent(Default.class)) {
            Default annotation = clazz.getAnnotation(Default.class);
            Class<?> defaultClass = annotation.value();
            System.out.println("Класс по умолчанию: " + defaultClass.getName());
        } else {
            System.out.println("Аннотация @Default не найдена.");
        }
    }
}