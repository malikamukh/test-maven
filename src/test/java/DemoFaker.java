import com.github.javafaker.Faker;

import java.sql.SQLOutput;

public class DemoFaker {

    public static void main(String[] args) {
        Faker faker = new Faker();
        System.out.println(faker.address().firstName());
        System.out.println(faker.address().lastName());
        System.out.println(faker.address().streetAddress());
    }
}
