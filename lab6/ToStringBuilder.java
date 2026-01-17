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