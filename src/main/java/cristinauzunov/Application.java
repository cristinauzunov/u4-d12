package cristinauzunov;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Application {
private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("u4d12");
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
