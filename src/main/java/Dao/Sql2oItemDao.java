package Dao;

import models.CharacterC;
import models.Effect;
import models.Item;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oItemDao implements ItemDao{
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
    public List<CharacterC> getAllCharacterCForItem(int id) {
        List<CharacterC> characters = new ArrayList<>();
        String sql = "SELECT characterCId FROM characters_items WHERE itemId = :itemId";
        try (Connection conn = sql2o.open()) {
            List<Integer> allCharacterIds = conn.createQuery(sql)
                    .addParameter("itemId", id)
                    .executeAndFetch(Integer.class);
            for (Integer characterId : allCharacterIds) {
                String query2 = "SELECT * FROM characters WHERE id = :characterId";
                characters.add(
                        conn.createQuery(query2)
                                .addParameter("characterId", characterId)
                                .executeAndFetchFirst(CharacterC.class)
                );
            }
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
        return characters;
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
}
