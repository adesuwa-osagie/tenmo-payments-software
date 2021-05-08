package com.techelevator.tenmo.dao;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.SQLException;

import static org.junit.Assert.*;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JdbcUserDAOIntegrationTest {




    /*private static SingleConnectionDataSource dataSource;
    private JdbcUserDAO userDAO;

    @BeforeClass
    public static void setupDataSource(){
        dataSource = new SingleConnectionDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/tenmo");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        dataSource.setAutoCommit(false);
    }

    @AfterClass
    public static void closerDataSource() throws SQLException{
        dataSource.destroy();
    }

    @Before
    public void setup(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    }

    @After
    public static void rollback() throws SQLException{
        dataSource.getConnection().rollback();
    }

    @Test
    public void findIdByUsername() {
    }

    @Test
    public void findAll() {
    }

    @Test
    public void findByUsername() {
    }

    @Test
    public void create() {
    }*/
}