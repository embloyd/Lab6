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