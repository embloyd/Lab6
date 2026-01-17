package lab6;


import java.lang.reflect.Method;

public class InvokeHandler {

    /**
     * Находит все методы с @Invoke и вызывает их
     */
    public static void invokeAnnotatedMethods(Object target) {
        if (target == null) {
            throw new IllegalArgumentException("Целевой объект не может быть null");
        }

        Class<?> clazz = target.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Invoke.class)) {
                try {
                    //Проверяем, что метод не требует параметров
                    if (method.getParameterCount() != 0) {
                        System.err.println("метод " + method.getName() +
                                " помечен @Invoke, но имеет параметры");
                        continue;
                    }

                    method.setAccessible(true); //даёт доступ даже к private
                    method.invoke(target);       //вызываем метод
                } catch (Exception e) {
                    System.err.println("Ошибка при вызове метода " + method.getName() + ": " + e.getMessage());
                }
            }
        }
    }
}