package Dao;

import models.CharacterC;
import models.Effect;
import models.Item;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oItemDao implements ItemDao {
    private final Sql2o sql2o;

    public Sql2oItemDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Item item) {
        String sql = "INSERT INTO items (currentHP, currentMP) VALUES (:currentHP, :currentMP)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(item)
                    .executeUpdate()
                    .getKey();
            item.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addItemToCharacterC(Item item, CharacterC characterC) {
        String sql = "INSERT INTO characters_items (itemId, characterCId) VALUES (:itemId, :characterCId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("characterCId", characterC.getId())
                    .addParameter("itemId", item.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Item findById(int id) {
        String sql = "SELECT * FROM items WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Item.class);
        }
    }

    @Override
    public List<Item> getAll() {
        String sql = "SELECT * FROM items";
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(sql)
                    .executeAndFetch(Item.class);
        }
    }

    @Override
    public List<Item> getAllItemsForCharacters(int id) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT itemId FROM characters_items WHERE characterCId = :characterCId";
        try (Connection conn = sql2o.open()) {
            List<Integer> allItemIds = conn.createQuery(sql)
                    .addParameter("characterCId", id)
                    .executeAndFetch(Integer.class);
            for (Integer itemId : allItemIds) {
                String query2 = "SELECT * FROM items WHERE id = :itemId";
                items.add(
                        conn.createQuery(query2)
                                .addParameter("itemId", itemId)
                                .executeAndFetchFirst(Item.class)
                );
            }
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
        return items;
    }

    @Override
    public void update(int id, int currentHP, int currentMP) {
        String sql = "UPDATE items SET  currentHP = :currentHP, currentMP = :currentMP WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("currentHP", currentHP)
                    .addParameter("currentMP", currentMP)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void removeCharacterCFromItem(CharacterC characterC, Item item) {
        String sql1 = "UPDATE characters SET currentHP = :currentHP, currentMP = :currentMP WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql1)
                    .addParameter("currentHP", characterC.getCurrentHP() + item.getCurrentHP())
                    .addParameter("currentMP", characterC.getCurrentMP() + item.getCurrentMP())
                    .addParameter("id", characterC.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
        String sql = "DELETE FROM characters_items WHERE characterCId = :characterCId AND itemId = :itemId";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("characterCId", characterC.getId())
                    .addParameter("itemId", item.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void removeAllCharacterCFromItem(Item item) {
        String sql = "DELETE FROM characters_items WHERE itemId = :itemId";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("itemId", item.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM items WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM items";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    public void populateItems() {
        Item lesserHealingPotion = new Item("Lesser Healing Potion",3, 0);
        Item healingPotion = new Item("Healing Potion",5, 0);
        Item majorHealingPotion = new Item("Major Healing Potion",10, 0);
        Item mightyHealingPotion = new Item("Mighty Healing Potion",20, 0);
        Item lesserManaPotion = new Item("Lesser Mana Potion",0, 3);
        Item manaPotion = new Item("Mana Potion",0, 5);
        Item majorManaPotion = new Item("Major Mana Potion",0, 10);
        Item mightyManaPotion = new Item("Mighty Mana Potion",0, 20);
        Item lesserMixturePotion = new Item("Lesser Mixture Potion",3, 3);
        Item mixturePotion = new Item("Mixture Potion",5, 5);
        Item majorMixturePotion = new Item("Major Mixture Potion",10, 10);
        Item mightyMixturePotion = new Item("Mighty Mixture Potion",20, 20);
        Item strangeLesserHealingPotion = new Item("Strange Lesser Healing Potion",-3, 0);
        Item strangeHealingPotion = new Item("Strange Healing Potion",-5, 0);
        Item strangeMajorHealingPotion = new Item("Strange Major Healing Potion",-10, 0);
        Item strangeMightyHealingPotion = new Item("Strange Mighty Healing Potion",-20, 0);
        Item strangeLesserManaPotion = new Item("Strange Lesser Mana Potion",0, -3);
        Item strangeManaPotion = new Item("Strange Mana Potion",0, -5);
        Item strangeMajorManaPotion = new Item("Strange Major Mana Potion",0, -10);
        Item strangeMightyManaPotion = new Item("Strange Mighty Mana Potion",0, -20);
        Item apple = new Item("Apple",1, 1);
        Item cake = new Item("Cake",3, 2);
        Item acorn = new Item("Acorn",1, 0);
        Item pumpkin = new Item("Pumpkin",4, 4);
        Item stew = new Item("Stew",10, 5);
        Item bread = new Item("Bread",3, 3);
        Item cookedMeat = new Item("Cooked Meat",5, 5);
        Item milk = new Item("Milk",2, 5);
        Item juice = new Item("Juice",2, 7);
        Item springWater = new Item("Spring Water",1, 10);
        Item smellyGarbage = new Item("Smelly Garbage",-1, -1);
        Item goblinArm = new Item("Goblin Arm",-5, -3);
        Item wolfDroppings = new Item("Wolf Droppings",-4, -5);
        Item pirateEye = new Item("Pirate Eye",-2, -4);
        Item trollToeNail = new Item("Troll Toenail",-5, -10);
        Item scorpionTail = new Item("Scorpion Tail",-10, 10);
        Item ghostPlasma = new Item("Ghost Plasma",-7, 7);

        this.add(lesserHealingPotion);
        this.add(healingPotion);
        this.add(majorHealingPotion);
        this.add(mightyHealingPotion);
        this.add(lesserManaPotion);
        this.add(manaPotion);
        this.add(majorManaPotion);
        this.add(mightyManaPotion);
        this.add(lesserMixturePotion);
        this.add(mixturePotion);
        this.add(majorMixturePotion);
        this.add(mightyMixturePotion);
        this.add(strangeLesserHealingPotion);
        this.add(strangeHealingPotion);
        this.add(strangeMajorHealingPotion);
        this.add(strangeMightyHealingPotion);
        this.add(strangeLesserManaPotion);
        this.add(strangeManaPotion);
        this.add(strangeMajorManaPotion);
        this.add(strangeMightyManaPotion);
        this.add(apple);
        this.add(cake);
        this.add(acorn);
        this.add(pumpkin);
        this.add(stew);
        this.add(bread);
        this.add(cookedMeat);
        this.add(milk);
        this.add(juice);
        this.add(springWater);
        this.add(smellyGarbage);
        this.add(goblinArm);
        this.add(wolfDroppings);
        this.add(pirateEye);
        this.add(trollToeNail);
        this.add(scorpionTail);
        this.add(ghostPlasma);

    }
}