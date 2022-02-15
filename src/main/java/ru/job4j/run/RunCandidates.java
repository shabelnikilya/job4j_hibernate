package ru.job4j.run;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.model.Candidate;

public class RunCandidates {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            saveCandidates(session);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static void saveCandidates(Session session) {
            Candidate first = new Candidate("Roma", "Rosenergoatom", 40000);
            Candidate second = new Candidate("Elena", "lukoil", 70000);
            Candidate third = new Candidate("Konstantin", "Лента", 45000);
            session.save(first);
            session.save(second);
            session.save(third);
    }

    public static void selectAllCandidates(Session session) {
            Query query = session.createQuery("from Candidate");
            for (Object candidate : query.list()) {
                System.out.println(candidate);
            }
    }

    public static void selectCandidatesByIdAndByName(Session session) {
            Query queryId = session.createQuery("from Candidate c where c.id = 2");
            System.out.println(queryId.uniqueResult());
            Query queryName = session.createQuery("from Candidate c where c.name = 'Roma'");
            System.out.println(queryName.uniqueResult());
    }

    public static void updateCandidates(Session session) {
            Query query = session.createQuery(
                    "update Candidate c set c.name = :nameParam"
                            + ", c.experience = :expParam, c.salary = :salaryParam where c.id = :idParam");
            query.setParameter("nameParam", "Inocentui");
            query.setParameter("expParam", "Transneft");
            query.setParameter("salaryParam", 130000);
            query.setParameter("idParam", 1);
            query.executeUpdate();
    }

    public static void deleteCandidate(Session session) {
            Query query = session.createQuery("delete from Candidate c where c.id = :idParam");
            query.setParameter("idParam", 3);
            query.executeUpdate();
    }

    public static void insertCandidate(Session session) {
        Query query = session.createQuery(
                "insert into Candidate (name, experience, salary) "
                        + "select 'new', 'new experience', c.salary + 100 "
                        + "from Candidate c where c.id = :idParam ");
        query.setParameter("idParam", 2);
        query.executeUpdate();
    }
}
