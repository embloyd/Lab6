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