package ru.job4j.run;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Brand;
import ru.job4j.model.Car;

import java.util.List;

public class RunCars {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Car first = new Car("ВАЗ 2110");
            Car second = new Car("ВАЗ 2111");
            Car third = new Car("ВАЗ 2112");
            Car four = new Car("ВАЗ 2113");
            Car five = new Car("ВАЗ 2114");
            List<Car> cars = List.of(first, second, third, four, five);
            Brand brand = new Brand("Lada", cars);
            session.save(brand);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
