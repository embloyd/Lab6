package lab6;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Пример сервиса, помеченного аннотацией @Validate
 */
@Validate({String.class, Integer.class, ArrayList.class})
public class OrderService {
}