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
        return new Spell("Magic Missle", "A missle made of magic that stuns the targer", 5, "1");
    }

    public Spell setupNewSpell1() {
        return new Spell("Haste", "Makes user faster", "2, 3");
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
    public void findByIdFindsSpellCorrectly() throws Exception {
        Spell spell = setupNewSpell();
        Spell spell1 = setupNewSpell1();
        Spell spell2 = setupNewSpell2();
        spellDao.add(spell);
        spellDao.add(spell1);
        spellDao.add(spell2);
        assertEquals(spell, spellDao.findById(spell.getId()));
        assertEquals(spell1, spellDao.findById(spell1.getId()));
        assertEquals(spell2, spellDao.findById(spell2.getId()));
    }

    @Test
    public void getAllGetsAllSpells() throws Exception {
        Spell testSpell = setupNewSpell();
        spellDao.add(testSpell);
        assertEquals(1, spellDao.getAll().size());
        Spell testSpell1 = setupNewSpell1();
        spellDao.add(testSpell1);
        assertEquals(2, spellDao.getAll().size());
        assertTrue(spellDao.getAll().contains(testSpell));
        assertTrue(spellDao.getAll().contains(testSpell1));
    }

    @Test
    public void updateSpellUpdatesSpellInfo() throws Exception {
        Spell testSpell = setupNewSpell();
        spellDao.add(testSpell);
        String originalName = spellDao.findById(testSpell.getId()).getName();
        String originalDescription = spellDao.findById(testSpell.getId()).getDescription();
        int originalDamage = spellDao.findById(testSpell.getId()).getDamage();
        String originalEffects = spellDao.findById(testSpell.getId()).getEffects();
        spellDao.update(testSpell.getId(), "Fire", "Burn target character", 7, "4");
        assertNotEquals(originalDamage, spellDao.findById(testSpell.getId()).getDamage());
        assertNotEquals(originalDescription, spellDao.findById(testSpell.getId()).getDescription());
        assertNotEquals(originalEffects, spellDao.findById(testSpell.getId()).getEffects());
        assertNotEquals(originalName, spellDao.findById(testSpell.getId()).getName());
    }

    @Test
    public void deleteByIdDeletesCorrectly() throws Exception {
        Spell spell = setupNewSpell();
        Spell spell1 = setupNewSpell1();
        Spell spell2 = setupNewSpell2();
        spellDao.add(spell);
        spellDao.add(spell1);
        spellDao.add(spell2);
        spellDao.deleteById(spell1.getId());
        assertFalse(spellDao.getAll().contains(spell1.getId()));
        assertEquals(2, spellDao.getAll().size());
    }

    @Test
    public void deleteAll() throws Exception {
        Spell spell = setupNewSpell();
        Spell spell1 = setupNewSpell1();
        Spell spell2 = setupNewSpell2();
        spellDao.add(spell);
        spellDao.add(spell1);
        spellDao.add(spell2);
        spellDao.deleteAll();
        assertEquals(0, spellDao.getAll().size());
    }

}