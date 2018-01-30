package Dao;

import models.Effect;
import models.Spell;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oSpellDao implements SpellDao{
    private final Sql2o sql2o;

    public Sql2oSpellDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public void populateSpells() {
        Spell magicMissle = new Spell("Magic Missle", "A basic Magic Missle", 3, 2);
        Spell fireBall = new Spell("Fire Ball", "A ball of fire", 5, 4);
        Spell blizzard = new Spell("Blizzard", "A deadly winter storm", 4, 3);
        Spell thunderWave = new Spell("Thunder Wave", "A thunderous wave of thunder", 4, 3);
        Spell bloodSplash = new Spell("Blood Splash", "A splash of dangerous blood", 3, 2);
        Spell waterStrike = new Spell("Water Strike", "A sharp strike of water", 2, 1);
        Spell quake = new Spell("Quake", "A destructive earth quake", 5, 5);
        Spell drown = new Spell("Drown", "A curse that causes the sensation of drowning", 3, 2);
        Spell choke = new Spell("Choke", "A powerful spell that causes the sensation of being unable to breath", 4, 3);
        Spell burst = new Spell("Burst", "An explosive burst of energy", 5, 4);
        Spell frostBolt = new Spell("Frost Bolt", "A chilling bolt of frost", 4, 3);
        Spell curse = new Spell("Curse", "A terrifying curse", 2, 1);

        this.add(magicMissle);

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
    public void addEffectToSpell(Effect effect, Spell spell){
        String sql = "INSERT INTO effects_spells (effectId, spellId) VALUES (:effectId, :spellId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("effectId", effect.getId())
                    .addParameter("spellId", spell.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    };


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
    public List<Effect> getAllEffectsForSpell(int id){
        List<Effect> effects = new ArrayList<>();
        String sql = "SELECT effectId FROM effects_spells WHERE spellId = :spellId";
        try (Connection fred = sql2o.open()) {
            List<Integer> allEffectIds = fred.createQuery(sql)
                    .addParameter("spellId", id)
                    .executeAndFetch(Integer.class);
            for (Integer effectId : allEffectIds) {
                String query2 = "SELECT * FROM effects WHERE id = :effectId";
                effects.add(
                        fred.createQuery(query2)
                                .addParameter("effectId", effectId)
                                .executeAndFetchFirst(Effect.class)
                );
            }
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
        return effects;
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
        String sql = "DELETE FROM spells WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void removeEffectFromSpell(Effect effect, Spell spell) {
        String sql = "DELETE FROM effects_spells WHERE effectId = :effectId AND spellId = :spellId";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("effectId", effect.getId())
                    .addParameter("spellId", spell.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    };

    @Override
    public void removeAllEffectsFromSpell(Spell spell) {
        String sql = "DELETE FROM effects_spells WHERE spellId = :spellId";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("spellId", spell.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM spells";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
