package Dao;

import models.Effect;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oEffectDao implements EffectDao{
    private final Sql2o sql2o;

    public Sql2oEffectDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
    @Override
    public void add(Effect effect) {
        String sql = "INSERT INTO effects (name, description, HP, currentHP, defense, magicDefense, strength, MP, currentMP, magic, dexterity, other) VALUES (:name, :description, :HP, :currentHP, :defense, :magicDefense, :strength, :MP, :currentMP, :magic, :dexterity, :other)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(effect)
                    .executeUpdate()
                    .getKey();
            effect.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Effect findById(int id) {
        String sql = "SELECT * FROM effects WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Effect.class);
        }
    }

    @Override
    public List<Effect> getAll() {
        String sql = "SELECT * FROM effects";
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(sql)
                    .executeAndFetch(Effect.class);
        }
    }

    @Override
    public void update(int id, String name, String description, int HP, int currentHP, int defense, int magicDefense, int strength, int MP, int currentMP, int magic, int dexterity, String other) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void deleteAll() {

    }
}
