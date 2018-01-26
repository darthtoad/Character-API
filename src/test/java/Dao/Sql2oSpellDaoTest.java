package Dao;

import models.Spell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;


public class Sql2oSpellDaoTest {
    private Connection connection;
    private Sql2oSpellDao spellDao;

    public Spell setupNewSpell() {
        return new Spell("Magic Missle", "A missle made of magic that stuns the targer", 5, "stun");
    }

    public Spell setupNewSpell1() {
        return new Spell("Haste", "Makes user faster", "+5 dexterity");
    }

    public Spell setupNewSpell2() {
        return new Spell("Magic Rock", "Throws a magic rock at a target", 10);
    }

    @Before
    public void setUp() throws Exception {
        String connectionString  = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        spellDao = new Sql2oSpellDao(sql2o);
        connection = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }

    @Test
    public void addAddsSpellCorrectly() throws Exception {
        Spell testSpell = setupNewSpell();
        int originalId = testSpell.getId();
        spellDao.add(testSpell);
        assertNotEquals(originalId, testSpell.getId());
    }

    @Test
    public void findById() throws Exception {
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