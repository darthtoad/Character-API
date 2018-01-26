package Dao;

import models.Effect;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oEffectDaoTest {
    private Connection connection;
    private Sql2oEffectDao effectDao;

    public Effect setupNewEffect() {
        return new Effect("Poison", "lose HP every turn", 0, -2, 0, 0, 0, 0, 0, 0, 0, "increment");
    }

    public Effect setupNewEffect1() {
        return new Effect("Regen", "Regenerate health every turn", 0, 2, 0, 0, 0, 0, 0, 0, 0, "increment");
    }

    public Effect setupNewEffect2() {
        return new Effect("Shell", "Increases defense and magic defense", 0, 0, 2, 2, 0, 0, 0, 0, 0, "");
    }

    public Effect setupNewEffect3() {
        return new Effect("Up All", "Increases all stats by 1", 1, 1, 1, 1, 1, 1, 1, 1, 1, "");
    }

    @Before
    public void setUp() throws Exception {
        String connectionString  = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        effectDao = new Sql2oEffectDao(sql2o);
        connection = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }
    @Test
    public void addAddsEffectCorrectly() throws Exception {
        Effect testEffect = setupNewEffect();
        int originalId = testEffect.getId();
        effectDao.add(testEffect);
        assertNotEquals(originalId, testEffect.getId());
    }

    @Test
    public void findByIdFindsEffectCorrectly() throws Exception {
        Effect effect = setupNewEffect();
        Effect effect1 = setupNewEffect1();
        Effect effect2 = setupNewEffect2();
        effectDao.add(effect);
        effectDao.add(effect1);
        effectDao.add(effect2);
        assertEquals(effect, effectDao.findById(effect.getId()));
        assertEquals(effect1, effectDao.findById(effect1.getId()));
        assertEquals(effect2, effectDao.findById(effect2.getId()));
    }

    @Test
    public void getAllGetsAllEffects() throws Exception {
        Effect testEffect = setupNewEffect();
        effectDao.add(testEffect);
        assertEquals(1, effectDao.getAll().size());
        Effect testEffect1 = setupNewEffect1();
        effectDao.add(testEffect1);
        assertEquals(2, effectDao.getAll().size());
        assertTrue(effectDao.getAll().contains(testEffect));
        assertTrue(effectDao.getAll().contains(testEffect1));
    }

    @Test
    public void updateUpdatesEffectInformation() throws Exception {
        Effect testEffect = setupNewEffect();
        effectDao.add(testEffect);
        String originalName = effectDao.findById(testEffect.getId()).getName();
        String originalDescription = effectDao.findById(testEffect.getId()).getDescription();
        int originalCurrentHP = effectDao.findById(testEffect.getId()).getCurrentHP();
        String originalOther = effectDao.findById(testEffect.getId()).getOther();
        effectDao.update(testEffect.getId(), "Slow", "Slows target character down", 0, 0, 0, 0, 0, 0, 0, 0, -5, "");
        assertNotEquals(originalName, effectDao.findById(testEffect.getId()).getName());
        assertNotEquals(originalDescription, effectDao.findById(testEffect.getId()).getDescription());
        assertNotEquals(originalOther, effectDao.findById(testEffect.getId()).getOther());
        assertNotEquals(originalName, effectDao.findById(testEffect.getId()).getName());
    }

    @Test
    public void deleteByIdDeletesCorrectly() throws Exception {
        Effect effect = setupNewEffect();
        Effect effect1 = setupNewEffect1();
        Effect effect2 = setupNewEffect2();
        effectDao.add(effect);
        effectDao.add(effect1);
        effectDao.add(effect2);
        effectDao.deleteById(effect1.getId());
        assertFalse(effectDao.getAll().contains(effect1.getId()));
        assertEquals(2, effectDao.getAll().size());
    }

    @Test
    public void deleteAll() throws Exception {
        Effect effect = setupNewEffect();
        Effect effect1 = setupNewEffect1();
        Effect effect2 = setupNewEffect2();
        effectDao.add(effect);
        effectDao.add(effect1);
        effectDao.add(effect2);
        effectDao.deleteAll();
        assertEquals(0, effectDao.getAll().size());
    }

}