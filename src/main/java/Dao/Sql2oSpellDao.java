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
        String sql = "";
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
        return null;
    }

    @Override
    public List<Spell> getAll() {
        return null;
    }

    @Override
    public void update(int id, String name, String description, int damage, List<Effect> effects) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void deleteAll() {

    }
}
