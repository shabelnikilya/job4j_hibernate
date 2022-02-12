package ru.job4j.run;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.lazy.Mark;
import ru.job4j.lazy.ModelCar;

import java.util.ArrayList;
import java.util.List;

public class RunLazyCars {
    public static void main(String[] args) {
        List<Mark> brands = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            brands = session.createQuery(
                    "select distinct m from Mark m join fetch m.modelCars"
            ).list();
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        for (Mark br : brands) {
            for (ModelCar model : br.getModelCars()) {
                System.out.println(model);
            }
        }
    }
}
