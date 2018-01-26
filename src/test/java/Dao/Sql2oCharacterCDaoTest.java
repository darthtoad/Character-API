package Dao;

import models.CharacterC;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oCharacterCDaoTest {
    private Connection connection;
    private Sql2oCharacterCDao characterCDao;

    public CharacterC setupNewCharacterC() {
        return new CharacterC("Fog", "A blonde hero who is not name Cloud", 4, 588, 49, 38, 5, 4, 7, 3, 2, 5, 8, "1, 3, 4", "2, 5, 7", "3");
    }

    public CharacterC setupNewCharacterC1() {
        return new CharacterC("Erin", "A red haired girl who is not Aeris", 3, 495, 35, 30, 4, 6, 5, 6, 4, 10, 9, "5, 6, 7, 10, 15", "1, 9, 17", "");
    }

    public CharacterC setupNewCharacterC2() {
        return new CharacterC("Barretaccus", "Not Mr. T or Barret", 5, 600, 66, 44, 5, 2, 10, 1, 0, 3, 5, "2", "10, 15, 20, 25", "1");
    }

    @Before
    public void setUp() throws Exception {
        String connectionString  = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        characterCDao = new Sql2oCharacterCDao(sql2o);
        connection = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }
    @Test
    public void addAddsCharacterCorrectly() throws Exception {
        CharacterC testCharacterC = setupNewCharacterC();
        int originalId = testCharacterC.getId();
        characterCDao.add(testCharacterC);
        assertNotEquals(originalId, testCharacterC.getId());
    }

    @Test
    public void findByIdFindsCharacterCorrectly() throws Exception {
        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        CharacterC characterC2 = setupNewCharacterC2();
        characterCDao.add(characterC);
        characterCDao.add(characterC1);
        characterCDao.add(characterC2);
        assertEquals(characterC, characterCDao.findById(characterC.getId()));
        assertEquals(characterC1, characterCDao.findById(characterC1.getId()));
        assertEquals(characterC2, characterCDao.findById(characterC2.getId()));
    }

    @Test
    public void getAll() throws Exception {
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void deleteById() throws Exception {
    }

    @Test
    public void deleteAll() throws Exception {
    }

}