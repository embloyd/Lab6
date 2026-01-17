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
## Задача 2
### Текст задачи
@Default.  
Разработайте аннотацию @Default, со следующими характеристиками: 
• Целью может быть ТИП или ПОЛЕ 
• Доступна во время исполнения программы 
• Имеет обязательное свойство value типа Class 
Проаннотируйте какой-либо класс данной аннотацией, указав тип по умолчанию. 
Напишите обработчик, который выводит имя указанного класса по умолчанию.
### Алгоритм решения
Аннотация @Default
```java
package lab6;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Аннотация для указания класса по умолчанию
 * Может применяться к классам (типам) и полям
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Default {
    Class<?> value();
}
```
Пример класса, помеченного аннотацией @Default UserService.java
```java
package lab6;

import java.util.HashMap;
/**
 * Пример класса, помеченного аннотацией @Default
 */
@Default(HashMap.class)
public class UserService {

}
```
Обработчик, который читает аннотацию и выводит имя класса DefaultHandler.java
```java
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
```
## Задача 3
### Текст задачи
@ToString. 
Разработайте аннотацию @ToString, со следующими характеристиками: 
• Целью может быть ТИП или ПОЛЕ 
• Доступна во время исполнения программы 
• Имеет необязательное свойство valuec двумя вариантами значений: YES или NO 
• Значение свойства по умолчанию: YES 
Проаннотируйте класс аннотацией @ToString, а одно из полей – с @ToString(Mode.NO). 
Создайте метод, который формирует строковое представление объекта, учитывая только те поля, 
где @ToString имеет значение YES.
### Алгоритм решения
Создаём перечисление Mode.java
```java
package lab6;
/**
 * Режимы для аннотации @ToString
 */
public enum Mode {
    YES, NO
}
```
Аннотация @ToString
```java
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
```
Класс с аннотациями Person.java
```java
package lab6;
/**
 * Пример класса с аннотациями @ToString
 */
@ToString
public class Person {
    private String name;
    private int age;

    @ToString(Mode.NO)
    private String password;

    public Person(String name, int age, String password) {
        this.name = name;
        this.age = age;
        this.password = password;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getPassword() { return password; }
}
```
Обработчик ToStringBuilder.java
```java
package lab6;

import java.lang.reflect.Field;
/**
 * Класс для генерации строкового представления объекта на основе @ToString
 */
public class ToStringBuilder {

    /**
     * Формирует строковое представление объекта, учитывая аннотации @ToString
     * @param obj объект для преобразования
     * @return строка вида "ClassName[field1=value1, field2=value2]"
     */
    public static String build(Object obj) {
        if (obj == null) {
            return "null";
        }

        Class<?> clazz = obj.getClass();

        boolean classIncluded = true;
        if (clazz.isAnnotationPresent(ToString.class)) {
            ToString classAnn = clazz.getAnnotation(ToString.class);
            if (classAnn.value() == Mode.NO) {
                return clazz.getSimpleName() + "{}";
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(clazz.getSimpleName()).append("[");

        boolean first = true;

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);

            boolean includeField = classIncluded;

            if (field.isAnnotationPresent(ToString.class)) {
                ToString fieldAnn = field.getAnnotation(ToString.class);
                includeField = (fieldAnn.value() == Mode.YES);
            }

            if (includeField) {
                if (!first) sb.append(", ");
                try {
                    Object value = field.get(obj);
                    sb.append(field.getName()).append("=").append(value);
                    first = false;
                } catch (IllegalAccessException e) {
                    sb.append(field.getName()).append("=<error>");
                }
            }
        }

        sb.append("]");
        return sb.toString();
    }
}
```
## Задача 4
### Текст задачи
@Validate.  
Разработайте аннотацию @Validate, со следующими характеристиками: 
• Целью может быть ТИП или АННОТАЦИЯ 
• Доступна во время исполнения программы 
• Имеет обязательное свойство value, типа Class[] 
Проаннотируйте класс аннотацией @Validate, передав список типов для проверки. 
Реализуйте обработчик, который выводит, какие классы указаны в аннотации. 
### Алгоритм решения
Аннотация @Validate
```java
package lab6;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Аннотация для указания списка классов, которые должны быть проверены
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Validate {
    /**
     * Список классов для валидации
     */
    Class<?>[] value();
}
```
Класс с аннотацией @Validate OrderService.java
```java
package lab6;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Пример сервиса, помеченного аннотацией @Validate
 */
@Validate({String.class, Integer.class, ArrayList.class})
public class OrderService {
}
```
Обработчик аннотации ValidateHandler.java
```java
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
```
## Задача 5
### Текст задачи
@Two. 
Разработайте аннотацию @Two, со следующими характеристиками: 
• Целью может быть ТИП 
• Доступна во время исполнения программы 
• Имеет два обязательных свойства: first типа String и second типа int 
Проаннотируйте какой-либо класс аннотацией @Two, передав строковое и числовое значения. 
Реализуйте обработчик, который считывает и выводит значения этих свойств.
### Алгоритм решения
Аннотация @Two
```java
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
```
Класс с аннотацией @Two Config.java
```java
package lab6;

/**
 * Пример класса, помеченного аннотацией @Two
 */
@Two(first = "aaabbb", second = 16)
public class Config {
}
```
Обработчик аннотации TwoHandler.java
```java
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
```
## Задача 6
### Текст задачи
@Cache.  
Разработайте аннотацию @Cache, со следующими характеристиками: 
• Целью может быть ТИП 
• Доступна во время исполнения программы 
• Имеет необязательное свойство value, типа String[] 
• Значение свойства по умолчанию: пустой массив 
Проаннотируйте класс аннотацией @Cache, указав несколько кешируемых областей. 
Создайте обработчик, который выводит список всех кешируемых областей или сообщение, что 
список пуст. 
### Алгоритм решения
Аннотация @Cache
```java
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
```
Класс с аннотацией @Cache Service.java
```java
package lab6;
/**
 * Пример сервиса с кешируемыми областями
 */
@Cache({"users", "profiles", "sessions"})
public class Service {
}
```
Обработчик аннотации CacheHandler.java
```java
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
```
