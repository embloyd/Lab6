# Мурай Анастасия ИТ-3 Лабораторная №6
# Задание 1
## Задача 1
### Текст задачи
@Invoke.  
Разработайте аннотацию @Invoke, со следующими характеристиками: 
• Целью может быть только МЕТОД 
• Доступна во время исполнения программы 
• Не имеет свойств 
Создайте класс, содержащий несколько методов, и проаннотируйте хотя бы один из них 
аннотацией @Invoke. 
Реализуйте обработчик (через Reflection API), который находит методы, отмеченные 
аннотацией @Invoke, и вызывает их автоматически.
### Алгоритм решения
Аннотация @Invoke
```java
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
```
Класс с проаннотированными методами DemoService.java
```java
package lab6;
public class DemoService {

    //метод, который будет автоматически вызван обработчиком
    @Invoke
    public void greet() {
        System.out.println("Метод 1 выполнен");
    }

    @Invoke
    public void logStatus() {
        System.out.println("Метод 2 выполнен");
    }

    public void internalMethod() {
        System.out.println("Этот метод не вызывается автоматически");
    }
}
```
Обработчик аннотаций InvokeHandler.java
```java
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
```
