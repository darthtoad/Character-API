package Dao;

import models.CharacterC;
import models.Effect;
import models.Equipment;
import models.Spell;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
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
    public void addEquipmentToCharacterC(Equipment equipment, CharacterC characterC){
        String sql = "INSERT INTO characters_equipment (equipmentid, characterid) VALUES (:equipmentId, :characterId)";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("equipmentId", equipment.getId())
                    .addParameter("characterId", characterC.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addSpellToCharacterC(Spell spell, CharacterC characterC){
        String sql = "INSERT INTO characters_spells (spellid, characterid) VALUES (:spellId, :characterId)";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("spellId", spell.getId())
                    .addParameter("characterId", characterC.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addEffectToCharacterC(Effect effect, CharacterC characterC){
        String sql = "INSERT INTO characters_effects (effectid, characterid) VALUES (:effectId, :characterId)";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("effectId", effect.getId())
                    .addParameter("characterId", characterC.getId())
                    .executeUpdate();
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
        }
    }

    @Override
    public List<Equipment> getAllEquipmentForACharacter(int id) {
        List<Equipment> equipment = new ArrayList<>();
        String sql = "SELECT equipmentId FROM characters_equipment WHERE characterId = :characterId";

        try (Connection fred = sql2o.open()) {
            List<Integer> allEquipmentIds = fred.createQuery(sql)
                    .addParameter("characterId", id)
                    .executeAndFetch(Integer.class);
            for (Integer equipmentId : allEquipmentIds) {
                String query2 = "SELECT * FROM equipment WHERE id = :equipmentId";
                equipment.add(
                        fred.createQuery(query2)
                                .addParameter("equipmentId", equipmentId)
                                .executeAndFetchFirst(Equipment.class)
                );
            }
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
        return equipment;
    }

    @Override
    public List<Spell> getAllSpellsForACharacter(int id) {
        List<Spell> spells = new ArrayList<>();
        String sql = "SELECT spellId FROM characters_spells WHERE characterId = :characterId";
        try (Connection fred = sql2o.open()) {
            List<Integer> allSpellIds = fred.createQuery(sql)
                    .addParameter("characterId", id)
                    .executeAndFetch(Integer.class);
            for (Integer spellId : allSpellIds) {
                String query2 = "SELECT * FROM spells WHERE id = :spellId";
                spells.add(
                        fred.createQuery(query2)
                                .addParameter("spellId", spellId)
                                .executeAndFetchFirst(Spell.class)
                );
            }
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }        return spells;
    }

    @Override
    public List<Effect> getAllEffectsForACharacter(int id) {
        List<Effect> effects = new ArrayList<>();
        String sql = "SELECT effectId FROM characters_effects WHERE characterId = :characterId";
        try (Connection fred = sql2o.open()) {
            List<Integer> allEffectIds = fred.createQuery(sql)
                    .addParameter("characterId", id)
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
        String sql = "DELETE FROM characters";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    public void removeEquipmentFromCharacterC(Equipment equipment, CharacterC characterC) {
        String sql = "DELETE FROM characters_equipment WHERE equipmentId = :equipmentId AND characterId = :characterId";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("equipmentId", equipment.getId())
                    .addParameter("characterId", characterC.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    };

    public void removeSpellFromCharacterC(Spell spell, CharacterC characterC){
        String sql = "DELETE FROM characters_spells WHERE spellId = :spellId AND characterId = :characterId";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("spellId", spell.getId())
                    .addParameter("characterId", characterC.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    };

    public void removeEffectFromCharacterC(Effect effect, CharacterC characterC) {
//        String sql = "DELETE FROM characters_effects WHERE effectId = :effectId AND characterId = :characterId";
//        try (Connection connection = sql2o.open()) {
//            connection.createQuery(sql)
//                    .addParameter("effectId", effect.getId())
//                    .addParameter("characterId", characterC.getId())
//                    .executeUpdate();
//        } catch (Sql2oException ex) {
//            System.out.println(ex);
//        }
    };
}
