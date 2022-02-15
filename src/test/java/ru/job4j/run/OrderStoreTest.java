package ru.job4j.run;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.model.Order;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class OrderStoreTest {

    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/update_001.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @After
    public void clear() throws SQLException {
        pool.getConnection().prepareStatement("DROP TABLE IF EXISTS orders").executeUpdate();
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);

        store.save(Order.of("name1", "description1"));

        List<Order> all = (List<Order>) store.findAll();

        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenSaveOrderAndFindByIdEqualsName() {
        OrdersStore store = new OrdersStore(pool);

        store.save(Order.of("name1", "description1"));

        assertThat(store.findById(1).getName(), is("name1"));
        assertThat(store.findById(1).getDescription(), is("description1"));
    }

    @Test
    public void whenSaveOrderAndUpdateAndCheck() {
        OrdersStore store = new OrdersStore(pool);
        store.save(Order.of("name1", "description1"));
        Order change = Order.of("new name", "new Description");
        store.updateOrder(1, change);
        assertThat(store.findAll().size(), is(1));
        assertThat(store.findById(1).getName(), is(change.getName()));
        assertThat(store.findById(1).getDescription(), is(change.getDescription()));
    }

    @Test
    public void whenSaveThreeOrdersAndFindByName() {
        OrdersStore store = new OrdersStore(pool);
        store.save(Order.of("name1", "description1"));
        store.save(Order.of("new name", "new Description"));
        store.save(Order.of("name1", "description1"));
        assertThat(store.findOrderByName("name1").size(), is(2));
        assertThat(store.findOrderByName("new name").size(), is(1));
        assertThat(store.findOrderByName("name1").get(0).getDescription(), is("description1"));
        assertThat(store.findOrderByName("new name").get(0).getDescription(), is("new Description"));
    }
}