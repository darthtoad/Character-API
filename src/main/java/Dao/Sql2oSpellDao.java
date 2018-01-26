package Dao;

import models.Effect;
import models.Spell;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oSpellDao implements SpellDao{
    private final Sql2o sql2o;

    public Sql2oSpellDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Spell spell) {
        String sql = "INSERT INTO spells (name, description, damage, effects) VALUES (:name, :description, :damage, :effects)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(spell)
                    .executeUpdate()
                    .getKey();
            spell.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Spell findById(int id) {
        String sql = "SELECT * FROM spells WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Spell.class);
        }

    }

    @Override
    public List<Spell> getAll() {
        String sql = "SELECT * FROM spells";
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(sql)
                    .executeAndFetch(Spell.class);
        }
    }

    @Override
    public void update(int id, String name, String description, int damage, String effects) {
        String sql = "UPDATE spells SET name = :name, description = :description, damage = :damage, effects = :effects WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("name", name)
                    .addParameter("description", description)
                    .addParameter("damage", damage)
                    .addParameter("effects", effects)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void deleteAll() {

    }
}
