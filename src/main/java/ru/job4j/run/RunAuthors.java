package ru.job4j.run;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Author;
import ru.job4j.model.Book;

import java.util.List;

public class RunAuthors {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry)
                                    .buildMetadata()
                                    .buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            Author firstAuth = new Author("Булгаков М.А.");
            Author secondAuth = new Author("Толстой Л.Н.");
            Book first = new Book("Мастер и маргарита");
            Book second = new Book("Собачье сердце");
            Book third = new Book("Морфий");
            Book four = new Book("Война и мир");
            List.of(first, second, third)
                    .forEach(firstAuth::addBook);
            secondAuth.addBook(four);
            session.persist(firstAuth);
            session.persist(secondAuth);
            Author deleteAuthor = session.get(Author.class, 2);
            session.remove(deleteAuthor);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
