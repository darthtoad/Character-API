package Dao;

import models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.List;

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
        return new Spell("Magic Missle", "A missle made of magic that stuns the targer", 5, 1, "1");
    }

    public Spell setupNewSpell1() {
        return new Spell("Haste", "Makes user faster", 5, "2, 3");
    }

    public Effect setupNewEffect() {
        return new Effect("Poison", "lose HP every turn", 0, -2, 0, 0, 0, 0, 0, 0, 0, "increment");
    }

    public Effect setupNewEffect1() {
        return new Effect("Regen", "Regenerate health every turn", 0, 2, 0, 0, 0, 0, 0, 0, 0, "increment");
    }

    public CharacterC setupNewCharacterC() {
        return new CharacterC("Fog", "A blonde hero who is not name Cloud", 4, 588, 49, 38, 5, 4, 7, 3, 2, 5, 8);
    }

    public CharacterC setupNewCharacterC1() {
        return new CharacterC("Erin", "A red haired girl who is not Aeris", 3, 495, 35, 30, 4, 6, 5, 6, 4, 10, 9);
    }

    public CharacterC setupNewCharacterC2() {
        return new CharacterC("Barretaccus", "Not Mr. T or Barret", 5, 600, 66, 44, 5, 2, 10, 1, 0, 3, 5);
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
    public void addCharacterWithClassAddsCorrectly() throws Exception {
        CharacterC fighter = new CharacterC("Fighter", "A fighter", "fighter");
        characterCDao.add(fighter);
        assertEquals(6, fighter.getStrength());
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
    public void addEquipmentToCharacterChangesStats() throws Exception {
        Equipment equipment = new Equipment("Sword of Awesome", "Sword", 4, 0, 0, 0, 0);
        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        characterCDao.add(characterC);
        int originalStrength = characterC.getStrength();
        equipmentDao.add(equipment);
        characterCDao.addEquipmentToCharacterC(equipmentDao.findById(equipment.getId()), characterCDao.findById(characterC.getId()));
        assertNotEquals(originalStrength, characterCDao.findById(characterC.getId()));
        assertEquals(characterCDao.findById(characterC.getId()).getStrength(), originalStrength + equipment.getStrength());
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
        assertTrue(characterCDao.getAllSpellsForACharacter(characterC.getId()).contains(spellDao.findById(spell.getId())));
    }

    @Test
    public void addEffectToCharacterAssociatesCorrectly() throws Exception {
        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        Effect effect = setupNewEffect();
        characterCDao.add(characterC);
        characterCDao.add(characterC1);
        effectDao.add(effect);
        characterCDao.addEffectToCharacterC(effect, characterC);
        assertEquals(1, characterCDao.getAllEffectsForACharacter(characterC.getId()).size());
        assertTrue(characterCDao.getAllEffectsForACharacter(characterC.getId()).contains(effect));
    }

    @Test
    public void addEffectChangesStats() throws Exception {
        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        Effect effect = setupNewEffect();
        characterCDao.add(characterC);
        characterCDao.add(characterC1);
        effectDao.add(effect);
        int originalCurrentHP = characterCDao.findById(characterC.getId()).getCurrentHP();
        characterCDao.addEffectToCharacterC(effect, characterC);
        assertNotEquals(originalCurrentHP, characterC.getCurrentHP());
        assertEquals(1, characterCDao.getAllEffectsForACharacter(characterC.getId()).size());
        assertTrue(characterCDao.getAllEffectsForACharacter(characterC.getId()).contains(effect));
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
    public void getAllEffectsForACharacterGetsAll() throws Exception {
        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        Effect effect = setupNewEffect();
        Effect effect1 = setupNewEffect1();
        characterCDao.add(characterC);
        characterCDao.add(characterC1);
        effectDao.add(effect);
        effectDao.add(effect1);
        characterCDao.addEffectToCharacterC(effect, characterC);

        assertEquals(2, characterCDao.getAll().size());
        assertEquals(1, characterCDao.getAllEffectsForACharacter(characterC.getId()).size());
        assertFalse(characterCDao.getAllEffectsForACharacter(characterC.getId()).contains(effect1));
    }

    @Test
    public void updateUpdatesCharacterInfoCorrectly() throws Exception {
        CharacterC testCharacterC = setupNewCharacterC();
        characterCDao.add(testCharacterC);
        String originalName = characterCDao.findById(testCharacterC.getId()).getName();
        String originalDescription = characterCDao.findById(testCharacterC.getId()).getDescription();
        int originalLevel = characterCDao.findById(testCharacterC.getId()).getLevel();
        characterCDao.update(testCharacterC.getId(), "Fire", "He likes to burn things", 3, 50, 5, 5, 5, 5, 5, 5, 5, 5, 5);
        assertNotEquals(originalLevel, characterCDao.findById(testCharacterC.getId()).getLevel());
        assertNotEquals(originalDescription, characterCDao.findById(testCharacterC.getId()).getDescription());
        assertNotEquals(originalName, characterCDao.findById(testCharacterC.getId()).getName());
    }

    @Test
    public void checkForLevelUpWorks() throws Exception {
        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        characterCDao.add(characterC);
        int originalLevel = characterC.getLevel();
        int originalStrength = characterC.getStrength();
        characterCDao.checkForLevelUp(characterC);
        assertNotEquals(originalLevel, characterCDao.findById(characterC.getId()).getLevel());
        assertEquals(6, characterCDao.findById(characterC.getId()).getLevel());
        assertNotEquals(originalStrength, characterCDao.findById(characterC.getId()).getStrength());
    }

    @Test
    public void checkForLevelUpWorksWithClass() throws Exception {
        CharacterC characterC = new CharacterC("Mr Poopy-Poop", "A big piece of poop", 1, 1000, 1, 1, 1, 1, 1, 1, 1, 1, 1,"Fighter");
        CharacterC characterC1 = setupNewCharacterC1();
        characterCDao.add(characterC);
        int originalLevel = characterC.getLevel();
        int originalStrength = characterC.getStrength();
        characterCDao.checkForLevelUp(characterC);
        assertNotEquals(originalLevel, characterCDao.findById(characterC.getId()).getLevel());
        assertEquals(7, characterCDao.findById(characterC.getId()).getLevel());
        assertNotEquals(originalStrength, characterCDao.findById(characterC.getId()).getStrength());
    }

    @Test
    public void castSpellChangesMP() throws Exception {
        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        List<CharacterC> charactersList = new ArrayList<>();
        charactersList.add(characterC1);
        Spell spell = setupNewSpell();
        Spell spell1 = setupNewSpell1();
        int originalMP = characterC.getCurrentMP();
        characterCDao.castSpell(spell, characterC, charactersList);
        assertNotEquals(originalMP, characterC.getCurrentMP());
    }

    @Test
    public void findTurnOrderFindsTurnOrder() throws Exception {
        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        CharacterC characterC2 = setupNewCharacterC2();
        CharacterC characterC3 = new CharacterC("Paul", "A guy named Paul", 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        characterCDao.add(characterC);
        characterCDao.add(characterC1);
        characterCDao.add(characterC2);
        characterCDao.add(characterC3);
        List<CharacterC> characterCList = new ArrayList<>();
        characterCList.add(characterC);
        characterCList.add(characterC1);
        characterCList.add(characterC2);
        characterCList.add(characterC3);
        List<Integer> turnOrder = characterCDao.findTurnOrder(characterCList);
        assertTrue(turnOrder.get(0) == 2);
        assertTrue(turnOrder.get(1) == 1);
        assertTrue(turnOrder.get(2) == 3);
        assertTrue(turnOrder.get(3) == 4);
    }

    @Test
    public void runAwayRunsAway() throws Exception {
        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        CharacterC characterC2 = setupNewCharacterC2();
        CharacterC characterC3 = new CharacterC("Paul", "A guy named Paul", 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        characterCDao.add(characterC);
        characterCDao.add(characterC1);
        characterCDao.add(characterC2);
        characterCDao.add(characterC3);
        List<CharacterC> enemies = new ArrayList<>();
        enemies.add(characterC2);
        enemies.add(characterC3);
        assertTrue(characterCDao.runAway(characterC1, enemies));
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

    @Test
    public void removeEquipmentFromCharacterRemovesAssociation() throws Exception {
        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        CharacterC characterC2 = setupNewCharacterC2();
        characterCDao.add(characterC);
        characterCDao.add(characterC1);
        characterCDao.add(characterC2);
        Equipment equipment = setupNewEquipment();
        Equipment equipment1 = setupNewEquipment1();
        equipmentDao.add(equipment);
        equipmentDao.add(equipment1);
        characterCDao.addEquipmentToCharacterC(equipment, characterC);
        assertEquals(1, characterCDao.getAllEquipmentForACharacter(characterC.getId()).size());
        assertTrue(characterCDao.getAllEquipmentForACharacter(characterC.getId()).contains(equipment));
        characterCDao.removeEquipmentFromCharacterC(equipment, characterC);
        assertEquals(0, characterCDao.getAllEquipmentForACharacter(characterC.getId()).size());
        assertFalse(characterCDao.getAllEquipmentForACharacter(characterC.getId()).contains(equipment));
    }

    @Test
    public void removeEquipmentFromCharacterChangesStats() throws Exception {
        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        CharacterC characterC2 = setupNewCharacterC2();
        characterCDao.add(characterC);
        characterCDao.add(characterC1);
        characterCDao.add(characterC2);
        Equipment equipment = setupNewEquipment();
        Equipment equipment1 = setupNewEquipment1();
        equipmentDao.add(equipment);
        equipmentDao.add(equipment1);
        characterCDao.addEquipmentToCharacterC(equipment, characterC);
        assertEquals(1, characterCDao.getAllEquipmentForACharacter(characterC.getId()).size());
        assertTrue(characterCDao.getAllEquipmentForACharacter(characterC.getId()).contains(equipment));
        int originalStrength = characterC.getStrength();
        characterCDao.removeEquipmentFromCharacterC(equipment, characterC);
        assertEquals(0, characterCDao.getAllEquipmentForACharacter(characterC.getId()).size());
        assertFalse(characterCDao.getAllEquipmentForACharacter(characterC.getId()).contains(equipment));
        assertNotEquals(originalStrength, characterCDao.findById(characterC.getId()).getStrength());
    }

    @Test
    public void removeSpellFromCharacterRemovesAssociationCorrectly() throws Exception {
        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        Spell spell = setupNewSpell();
        characterCDao.add(characterC);
        characterCDao.add(characterC1);
        spellDao.add(spell);
        characterCDao.addSpellToCharacterC(spell, characterC);
        assertEquals(1, characterCDao.getAllSpellsForACharacter(characterC.getId()).size());
        assertTrue(characterCDao.getAllSpellsForACharacter(characterC.getId()).contains(spellDao.findById(spell.getId())));
        characterCDao.removeSpellFromCharacterC(spell, characterC);
        assertEquals(0, characterCDao.getAllSpellsForACharacter(characterC.getId()).size());
        assertFalse(characterCDao.getAllSpellsForACharacter(characterC.getId()).contains(spell));
    }

    @Test
    public void removeEffectFromCharacterRemovesAssociationCorrectly() throws Exception {
        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        Effect effect = setupNewEffect();
        characterCDao.add(characterC);
        characterCDao.add(characterC1);
        effectDao.add(effect);
        characterCDao.addEffectToCharacterC(effect, characterC);
        assertEquals(1, characterCDao.getAllEffectsForACharacter(characterC.getId()).size());
        assertTrue(characterCDao.getAllEffectsForACharacter(characterC.getId()).contains(effect));
        characterCDao.removeEffectFromCharacterC(effect, characterC);
        assertEquals(0, characterCDao.getAllEffectsForACharacter(characterC.getId()).size());
        assertFalse(characterCDao.getAllEffectsForACharacter(characterC.getId()).contains(effect));
    }

    @Test
    public void removeAllEquipmentFromCharacterRemovesAll() throws Exception {
        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        CharacterC characterC2 = setupNewCharacterC2();
        characterCDao.add(characterC);
        characterCDao.add(characterC1);
        characterCDao.add(characterC2);
        Equipment equipment = setupNewEquipment();
        Equipment equipment1 = setupNewEquipment1();
        equipmentDao.add(equipment);
        equipmentDao.add(equipment1);
        characterCDao.addEquipmentToCharacterC(equipment, characterC);
        characterCDao.addEquipmentToCharacterC(equipment1, characterC);
        assertEquals(2, characterCDao.getAllEquipmentForACharacter(characterC.getId()).size());
        assertTrue(characterCDao.getAllEquipmentForACharacter(characterC.getId()).contains(equipment));
        assertTrue(characterCDao.getAllEquipmentForACharacter(characterC.getId()).contains(equipment1));
        characterCDao.removeAllEquipmentFromCharacterC(characterC);
        assertEquals(0, characterCDao.getAllEquipmentForACharacter(characterC.getId()).size());
        assertFalse(characterCDao.getAllEquipmentForACharacter(characterC.getId()).contains(equipment));
        assertFalse(characterCDao.getAllEquipmentForACharacter(characterC.getId()).contains(equipment1));
    }

    @Test
    public void removeAllEquipmentChangesStats() throws Exception {
        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        CharacterC characterC2 = setupNewCharacterC2();
        characterCDao.add(characterC);
        characterCDao.add(characterC1);
        characterCDao.add(characterC2);
        Equipment equipment = setupNewEquipment();
        Equipment equipment1 = setupNewEquipment1();
        equipmentDao.add(equipment);
        equipmentDao.add(equipment1);
        characterCDao.addEquipmentToCharacterC(equipment, characterC);
        characterCDao.addEquipmentToCharacterC(equipment1, characterC);
        assertEquals(2, characterCDao.getAllEquipmentForACharacter(characterC.getId()).size());
        assertTrue(characterCDao.getAllEquipmentForACharacter(characterC.getId()).contains(equipment));
        assertTrue(characterCDao.getAllEquipmentForACharacter(characterC.getId()).contains(equipment1));
        int originalStrength = characterCDao.findById(characterC.getId()).getStrength();
        int originalDefense = characterCDao.findById(characterC.getId()).getDefense();
        characterCDao.removeAllEquipmentFromCharacterC(characterC);
        assertEquals(0, characterCDao.getAllEquipmentForACharacter(characterC.getId()).size());
        assertFalse(characterCDao.getAllEquipmentForACharacter(characterC.getId()).contains(equipment));
        assertFalse(characterCDao.getAllEquipmentForACharacter(characterC.getId()).contains(equipment1));
        assertNotEquals(originalDefense, characterCDao.findById(characterC.getId()).getDefense());
        assertNotEquals(originalStrength, characterCDao.findById(characterC.getId()).getStrength());
    }

    @Test
    public void removeAllSpellsFromCharacterRemovesAssociationCorrectly() throws Exception {
        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        Spell spell = setupNewSpell();
        Spell spell1 = setupNewSpell1();
        characterCDao.add(characterC);
        characterCDao.add(characterC1);
        spellDao.add(spell);
        spellDao.add(spell1);
        characterCDao.addSpellToCharacterC(spell, characterC);
        characterCDao.addSpellToCharacterC(spell1, characterC);
        assertEquals(2, characterCDao.getAllSpellsForACharacter(characterC.getId()).size());
        assertTrue(characterCDao.getAllSpellsForACharacter(characterC.getId()).contains(spellDao.findById(spell.getId())));
        assertTrue(characterCDao.getAllSpellsForACharacter(characterC.getId()).contains(spellDao.findById(spell1.getId())));
        characterCDao.removeAllSpellsFromCharacterC(characterC);
        assertEquals(0, characterCDao.getAllSpellsForACharacter(characterC.getId()).size());
        assertFalse(characterCDao.getAllSpellsForACharacter(characterC.getId()).contains(spell));
        assertFalse(characterCDao.getAllSpellsForACharacter(characterC.getId()).contains(spell1));
    }

    @Test
    public void removeAllEffectsFromCharacterRemovesAssociationCorrectly() throws Exception {
        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        Effect effect = setupNewEffect();
        Effect effect1 = setupNewEffect1();
        characterCDao.add(characterC);
        characterCDao.add(characterC1);
        effectDao.add(effect);
        effectDao.add(effect1);
        characterCDao.addEffectToCharacterC(effect, characterC);
        characterCDao.addEffectToCharacterC(effect1, characterC);
        assertEquals(2, characterCDao.getAllEffectsForACharacter(characterC.getId()).size());
        assertTrue(characterCDao.getAllEffectsForACharacter(characterC.getId()).contains(effect));
        assertTrue(characterCDao.getAllEffectsForACharacter(characterC.getId()).contains(effect1));
        characterCDao.removeAllEffectsFromCharacterC(characterC);
        assertEquals(0, characterCDao.getAllSpellsForACharacter(characterC.getId()).size());
        assertFalse(characterCDao.getAllSpellsForACharacter(characterC.getId()).contains(effect));
        assertFalse(characterCDao.getAllSpellsForACharacter(characterC.getId()).contains(effect1));
    }

    @Test
    public void userInputChangesState() throws Exception {
        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        CharacterC characterC2 = setupNewCharacterC2();
        CharacterC characterC3 = new CharacterC("Paul", "A guy named Paul", 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        characterCDao.add(characterC);
        characterCDao.add(characterC1);
        characterCDao.add(characterC2);
        characterCDao.add(characterC3);
        List<CharacterC> enemies = new ArrayList<>();
        enemies.add(characterC3);
        int originalHP = characterC3.getCurrentHP();
        characterCDao.userInput("attack", characterC, enemies);
        assertNotEquals(originalHP, characterC3.getCurrentHP());
    }

    @Test
    public void computerInputChangesState() throws Exception {
        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        CharacterC characterC2 = setupNewCharacterC2();
        CharacterC characterC3 = new CharacterC("Paul", "A guy named Paul", 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        characterCDao.add(characterC);
        characterCDao.add(characterC1);
        characterCDao.add(characterC2);
        characterCDao.add(characterC3);
        List<CharacterC> enemies = new ArrayList<>();
        enemies.add(characterC3);
        int originalHP = characterC3.getCurrentHP();
        characterCDao.computerInput(characterC, enemies);
        assertNotEquals(originalHP, characterC3.getCurrentHP());
    }
}