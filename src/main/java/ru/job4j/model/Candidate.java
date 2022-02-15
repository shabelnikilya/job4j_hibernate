package ru.job4j.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String experience;
    private int salary;
    @OneToOne
    private DatabaseVacancies databaseVacancies;

    public Candidate() {
    }

    public Candidate(String name, String experience, int salary) {
        this.name = name;
        this.experience = experience;
        this.salary = salary;
    }

    public Candidate(String name, String experience, int salary, DatabaseVacancies databaseVacancies) {
        this.name = name;
        this.experience = experience;
        this.salary = salary;
        this.databaseVacancies = databaseVacancies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public DatabaseVacancies getDatabaseVacancies() {
        return databaseVacancies;
    }

    public void setDatabaseVacancies(DatabaseVacancies databaseVacancies) {
        this.databaseVacancies = databaseVacancies;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Candidate{" + "id=" + id
                + ", name='" + name + '\''
                + ", experience='" + experience + '\''
                + ", salary=" + salary + '}';
    }
}
