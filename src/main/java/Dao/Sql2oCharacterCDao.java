package Dao;

import models.CharacterC;
import models.Effect;
import models.Equipment;
import models.Spell;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oCharacterCDao implements CharacterCDao {
    private final Sql2o sql2o;

    public Sql2oCharacterCDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(CharacterC characterC) {
        String sql = "INSERT INTO characters (name, description, level, experience, HP, currentHP, defense, magicDefense, strength, MP, currentMP, magic, dexterity, spells, equipment, effects) VALUES (:name, :description, :level, :experience, :HP, :currentHP, :defense, :magicDefense, :strength, :MP, :currentMP, :magic, :dexterity, :spells, :equipment, :effects)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(characterC)
                    .executeUpdate()
                    .getKey();
            characterC.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public CharacterC findById(int id) {
        String sql = "SELECT * FROM characters WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(CharacterC.class);
        }
    }

    @Override
    public List<CharacterC> getAll() {
        String sql = "SELECT * FROM characters";
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(sql)
                    .executeAndFetch(CharacterC.class);
        }    }

    @Override
    public void update(int id, String name, String description, int level, int experience, int HP, int currentHP, int defense, int magicDefense, int strength, int MP, int currentMP, int magic, int dexterity, String spells, String equipment, String effects) {
        String sql = "UPDATE characters SET name = :name, description = :description, level = :level, experience = :experience, HP = :HP, currentHP = :currentHP, defense = :defense, magicDefense = :magicDefense, strength = :strength, MP = :MP, currentMP = :currentMP, magic = :magic, dexterity = :dexterity, spells = :spells, equipment = :equipment, effects = :effects WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("name", name)
                    .addParameter("description", description)
                    .addParameter("level", level)
                    .addParameter("experience", experience)
                    .addParameter("HP", HP)
                    .addParameter("currentHP", currentHP)
                    .addParameter("defense", defense)
                    .addParameter("magicDefense", magicDefense)
                    .addParameter("strength", strength)
                    .addParameter("MP", MP)
                    .addParameter("currentMP", currentMP)
                    .addParameter("magic", magic)
                    .addParameter("dexterity", dexterity)
                    .addParameter("spells", spells)
                    .addParameter("equipment", equipment)
                    .addParameter("effects", effects)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM characters WHERE id = :id";
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

    }
}
