package lab6;

public class Main {
    public static void main(String[] args) {
        System.out.println("---Задание 1: Аннотация---");
        System.out.println("-@Invoke-");

        try {
            DemoService service = new DemoService();
            System.out.println("Вызываем методы с @Invoke:");
            InvokeHandler.invokeAnnotatedMethods(service);
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }

        System.out.println("-@Default-");
        DefaultHandler.printDefaultClassName(UserService.class);

        System.out.println("-@ToString-");

        Person person = new Person("Борис", 40, "123");
        String result = ToStringBuilder.build(person);
        System.out.println(result);

        System.out.println("-@Validate-");
        ValidateHandler.printValidationClasses(OrderService.class);

        System.out.println("-@Two-");
        TwoHandler.printTwoValues(Config.class);

        System.out.println("-@Cache-");
        CacheHandler.printCacheRegions(Service.class);
    }
}