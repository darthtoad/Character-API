package Dao;

import models.CharacterC;
import models.Effect;
import models.Item;
import models.Spell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oItemDaoTest {
    private Connection connection;
    private Sql2oItemDao itemDao;
    private Sql2oCharacterCDao characterCDao;

    @Before
    public void setUp() throws Exception {
        String connectionString  = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        itemDao = new Sql2oItemDao(sql2o);
        characterCDao = new Sql2oCharacterCDao(sql2o);
        connection = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }
    @Test
    public void addCharacterCToItemAddsCorrectly() throws Exception {
        Item item = setupNewItem();
        Item item1 = setupNewItem1();

        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        characterCDao.add(characterC);
        characterCDao.add(characterC1);
        itemDao.add(item);
        itemDao.add(item1);
        itemDao.addItemToCharacterC(item, characterC);
        itemDao.addItemToCharacterC(item1, characterC1);
        assertEquals(1, itemDao.getAllItemsForCharacters(item.getId()).size());
        System.out.println(itemDao.getAllItemsForCharacters(item.getId()));
        assertTrue(itemDao.getAllItemsForCharacters(characterC.getId()).contains(itemDao.findById(item.getId())));
    }

    @Test
    public void removeCharacterCFromItemRemovesCorrectly() throws Exception {
        Item item = setupNewItem();
        Item item1 = setupNewItem1();

        CharacterC characterC = setupNewCharacterC();
        CharacterC characterC1 = setupNewCharacterC1();
        characterCDao.add(characterC);
        characterCDao.add(characterC1);
        itemDao.add(item);
        itemDao.add(item1);
        itemDao.addItemToCharacterC(item, characterC);
        itemDao.addItemToCharacterC(item1, characterC);
        itemDao.removeCharacterCFromItem(characterC, item);
        assertFalse(itemDao.getAllItemsForCharacters(characterC.getId()).contains(item));
        assertEquals(1, itemDao.getAllItemsForCharacters(characterC.getId()).size());
    }


    public Item setupNewItem() {
        return new Item("Thing",2,4);
    }
    public Item setupNewItem1() {
        return new Item("other thing",4,8);
    }
    public Item setupNewItem2() {
        return new Item("yet another thing",6,7);
    }

    public CharacterC setupNewCharacterC() {
        return new CharacterC("Fog", "A blonde hero who is not name Cloud", 4, 588, 49, 38, 5, 4, 7, 3, 2, 5, 8);
    }

    public CharacterC setupNewCharacterC1() {
        return new CharacterC("Erin", "A red haired girl who is not Aeris", 3, 495, 35, 30, 4, 6, 5, 6, 4, 10, 9);
    }


}