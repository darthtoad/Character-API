package Dao;

import models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oCharacterCDaoTest {
    private Connection connection;
    private Sql2oCharacterCDao characterCDao;
    private Sql2oEquipmentDao equipmentDao;
    private Sql2oSpellDao spellDao;
    private Sql2oEffectDao effectDao;

    public Equipment setupNewEquipment() {
        return new Equipment("Sword of Awesome", "Sword", 4, 0, 0, 0, 0);
    }

    public Equipment setupNewEquipment1() {
        return new Equipment("Shield of Awesome", "Sheild", 0, 0, -1, 4, 2);
    }

    public Spell setupNewSpell() {
        return new Spell("Magic Missle", "A missle made of magic that stuns the targer", 5, "1");
    }

    public Spell setupNewSpell1() {
        return new Spell("Haste", "Makes user faster", "2, 3");
    }

    public Effect setupNewEffect() {
        return new Effect("Poison", "lose HP every turn", 0, -2, 0, 0, 0, 0, 0, 0, 0, "increment");
    }

    public Effect setupNewEffect1() {
        return new Effect("Regen", "Regenerate health every turn", 0, 2, 0, 0, 0, 0, 0, 0, 0, "increment");
    }

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
        equipmentDao = new Sql2oEquipmentDao(sql2o);
        spellDao = new Sql2oSpellDao(sql2o);
        effectDao = new Sql2oEffectDao(sql2o);
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
    public void addEquipmentToCharacterAssociatesCorrectly() throws Exception {
        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        Equipment equipment = setupNewEquipment();
        characterCDao.add(characterC);
        characterCDao.add(characterC1);
        equipmentDao.add(equipment);
        characterCDao.addEquipmentToCharacterC(equipment, characterC);
        assertEquals(1, characterCDao.getAllEquipmentForACharacter(characterC.getId()).size());
        assertTrue(characterCDao.getAllEquipmentForACharacter(characterC.getId()).contains(equipment));
    }

    @Test
    public void addSpellToCharacterAssociatesCorrectly() throws Exception {
        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        Spell spell = setupNewSpell();
        characterCDao.add(characterC);
        characterCDao.add(characterC1);
        spellDao.add(spell);
        characterCDao.addSpellToCharacterC(spell, characterC);
        assertEquals(1, characterCDao.getAllSpellsForACharacter(characterC.getId()).size());
        assertTrue(characterCDao.getAllSpellsForACharacter(characterC.getId()).contains(spell));
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
    public void getAllGetsAllCharactersCorrectly() throws Exception {
        CharacterC testCharacterC = setupNewCharacterC();
        characterCDao.add(testCharacterC);
        assertEquals(1, characterCDao.getAll().size());
        CharacterC testCharacterC1 = setupNewCharacterC1();
        characterCDao.add(testCharacterC1);
        assertEquals(2, characterCDao.getAll().size());
        assertTrue(characterCDao.getAll().contains(testCharacterC));
        assertTrue(characterCDao.getAll().contains(testCharacterC1));
    }

    @Test
    public void getAllEquipmentForACharacterGetsAll() throws Exception {
        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        Equipment equipment = setupNewEquipment();
        Equipment equipment1 = setupNewEquipment1();
        characterCDao.add(characterC);
        characterCDao.add(characterC1);
        equipmentDao.add(equipment);
        equipmentDao.add(equipment1);
        characterCDao.addEquipmentToCharacterC(equipment, characterC);

        assertEquals(2, characterCDao.getAll().size());
        assertEquals(1, characterCDao.getAllEquipmentForACharacter(characterC.getId()).size());
        assertFalse(characterCDao.getAllEquipmentForACharacter(characterC.getId()).contains(equipment1));
    }

    @Test
    public void getAllSpellsForACharacterGetsAll() throws Exception {
        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        Spell spell = setupNewSpell();
        Spell spell1 = setupNewSpell1();
        characterCDao.add(characterC);
        characterCDao.add(characterC1);
        spellDao.add(spell);
        spellDao.add(spell1);
        characterCDao.addSpellToCharacterC(spell, characterC);

        assertEquals(2, characterCDao.getAll().size());
        assertEquals(1, characterCDao.getAllSpellsForACharacter(characterC.getId()).size());
        assertFalse(characterCDao.getAllSpellsForACharacter(characterC.getId()).contains(spell1));
    }

    @Test
    public void updateUpdatesCharacterInfoCorrectly() throws Exception {
        CharacterC testCharacterC = setupNewCharacterC();
        characterCDao.add(testCharacterC);
        String originalName = characterCDao.findById(testCharacterC.getId()).getName();
        String originalDescription = characterCDao.findById(testCharacterC.getId()).getDescription();
        int originalLevel = characterCDao.findById(testCharacterC.getId()).getLevel();
        String originalEffects = characterCDao.findById(testCharacterC.getId()).getEffects();
        characterCDao.update(testCharacterC.getId(), "Fire", "He likes to burn things", 3, 50, 5, 5, 5, 5, 5, 5, 5, 5, 5, "1, 4, 5", "", "144");
        assertNotEquals(originalLevel, characterCDao.findById(testCharacterC.getId()).getLevel());
        assertNotEquals(originalDescription, characterCDao.findById(testCharacterC.getId()).getDescription());
        assertNotEquals(originalEffects, characterCDao.findById(testCharacterC.getId()).getEffects());
        assertNotEquals(originalName, characterCDao.findById(testCharacterC.getId()).getName());
    }

    @Test
    public void deleteByIdDeletesCharacterCorrectly() throws Exception {
        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        CharacterC characterC2 = setupNewCharacterC2();
        characterCDao.add(characterC);
        characterCDao.add(characterC1);
        characterCDao.add(characterC2);
        characterCDao.deleteById(characterC1.getId());
        assertFalse(characterCDao.getAll().contains(characterC1.getId()));
        assertEquals(2, characterCDao.getAll().size());
    }

    @Test
    public void deleteAllDeletesAllCharacters() throws Exception {
        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        CharacterC characterC2 = setupNewCharacterC2();
        characterCDao.add(characterC);
        characterCDao.add(characterC1);
        characterCDao.add(characterC2);
        characterCDao.deleteAll();
        assertEquals(0, characterCDao.getAll().size());
    }

}