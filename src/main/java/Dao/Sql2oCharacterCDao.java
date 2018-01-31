package Dao;

//import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.*;
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
    private Sql2oSpellDao spellDao;

    public Sql2oCharacterCDao(Sql2o sql2o) {
        this.sql2o = sql2o;
        this.spellDao = new Sql2oSpellDao(sql2o);
    }

    @Override
    public void add(CharacterC characterC) {
        if (characterC.getCharClass() != null && characterC.getCharClass() != "") {
            if (characterC.getCharClass().toLowerCase().equals("fighter")) {
                characterC.setLevel(1);
                characterC.setHP(15);
                characterC.setCurrentHP(15);
                characterC.setDefense(4);
                characterC.setMagicDefense(2);
                characterC.setStrength(6);
                characterC.setDexterity(4);
            }
            if (characterC.getCharClass().toLowerCase().equals("red mage")) {
                characterC.setLevel(1);
                characterC.setHP(12);
                characterC.setCurrentHP(12);
                characterC.setMP(6);
                characterC.setCurrentMP(6);
                characterC.setStrength(4);
                characterC.setDefense(3);
                characterC.setMagicDefense(4);
                characterC.setMagic(6);
                characterC.setDexterity(4);
            }
        }

        String sql = "INSERT INTO characters (name, description, level, experience, HP, currentHP, defense, magicDefense, strength, MP, currentMP, magic, dexterity) VALUES (:name, :description, :level, :experience, :HP, :currentHP, :defense, :magicDefense, :strength, :MP, :currentMP, :magic, :dexterity)";
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
        characterC.setDefense(characterC.getDefense() + equipment.getDefense());
        characterC.setMagicDefense(characterC.getMagicDefense() + equipment.getMagicDefense());
        characterC.setMagic(characterC.getMagic() + equipment.getMagic());
        characterC.setStrength(characterC.getStrength() + equipment.getStrength());
        characterC.setDexterity(characterC.getDexterity() + equipment.getDexterity());
        String sql1 = "UPDATE characters SET defense = :defense, magicDefense = :magicDefense, strength = :strength, magic = :magic, dexterity = :dexterity WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql1)
                    .addParameter("defense", characterC.getDefense())
                    .addParameter("magicDefense", characterC.getMagicDefense())
                    .addParameter("strength", characterC.getStrength())
                    .addParameter("magic", characterC.getMagic())
                    .addParameter("dexterity", characterC.getDexterity())
                    .addParameter("id", characterC.getId())
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

        characterC.setHP(characterC.getHP() + effect.getHP());
        characterC.setCurrentHP(characterC.getCurrentHP() + effect.getCurrentHP());
        characterC.setDefense(characterC.getDefense() + effect.getDefense());
        characterC.setMagicDefense(characterC.getMagicDefense() + effect.getMagicDefense());
        characterC.setStrength(characterC.getStrength() + effect.getStrength());
        characterC.setMP(characterC.getMP() + effect.getMP());
        characterC.setCurrentMP(characterC.getCurrentMP() + effect.getCurrentMP());
        characterC.setMagic(characterC.getMagic() + effect.getMagic());
        characterC.setDexterity(characterC.getDexterity() + effect.getDexterity());

        String sql1 = "UPDATE characters SET HP = :HP, currentHP = :currentHP, defense = :defense, magicDefense = :magicDefense, strength = :strength, MP = :MP, currentMP = :currentMP, magic = :magic, dexterity = :dexterity WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql1)
                    .addParameter("HP", characterC.getHP())
                    .addParameter("currentHP", characterC.getCurrentHP())
                    .addParameter("defense", characterC.getDefense())
                    .addParameter("magicDefense", characterC.getMagicDefense())
                    .addParameter("strength", characterC.getStrength())
                    .addParameter("MP", characterC.getMP())
                    .addParameter("currentMP", characterC.getCurrentMP())
                    .addParameter("magic", characterC.getMagic())
                    .addParameter("dexterity", characterC.getDexterity())
                    .addParameter("id", characterC.getId())
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
    public void update(int id, String name, String description, int level, int experience, int HP, int currentHP, int defense, int magicDefense, int strength, int MP, int currentMP, int magic, int dexterity) {
        String sql = "UPDATE characters SET name = :name, description = :description, level = :level, experience = :experience, HP = :HP, currentHP = :currentHP, defense = :defense, magicDefense = :magicDefense, strength = :strength, MP = :MP, currentMP = :currentMP, magic = :magic, dexterity = :dexterity  WHERE id = :id";
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
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void attack(CharacterC attacker, CharacterC target) {
        int damage = attacker.getStrength();
        int accuracy = attacker.getDexterity();
        int defense = target.getDefense();
        int evasion = target.getDexterity();
        int toHit = accuracy * (int) (Math.floor(Math.random()) + 1);
        damage = damage * (int) (Math.floor(Math.random()) + 1);
        if (toHit > evasion) {
            if (damage - defense > 0) {
                target.setCurrentHP(target.getCurrentHP() - (damage - defense));
            }
        }
        String sql = "UPDATE characters SET currentHP = :currentHP WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("currentHP", target.getCurrentHP())
                    .addParameter("id", target.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
        if (target.getCurrentHP() <= 0) {
            attacker.setExperience(attacker.getExperience() + (int) (.25 * target.getExperience()));
            String sql1 = "UPDATE characters SET experience = :experience WHERE id = :id";
            try (Connection connection = sql2o.open()) {
                connection.createQuery(sql1)
                        .addParameter("experience", attacker.getExperience())
                        .addParameter("id", attacker.getId())
                        .executeUpdate();
            } catch (Sql2oException ex) {
                System.out.println(ex);
            }
            this.checkForLevelUp(attacker);
        }
    }

    @Override
    public void castSpell(Spell spell, CharacterC caster, List<CharacterC> targets) {
        int damage = spell.getDamage();
        damage = damage * (int) (Math.floor(Math.random()) + 1);
        int toHit = caster.getMagic();
        caster.setCurrentMP(caster.getCurrentHP() - spell.getMP());
        String sql1 = "UPDATE characters SET currentMP = :currentMP WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql1)
                    .addParameter("currentMP", caster.getCurrentMP())
                    .addParameter("id", caster.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
        for (CharacterC target : targets) {
            int defense = target.getMagicDefense();
            if (toHit > target.getDexterity()) {
                if (damage - defense > 0) {
                    target.setCurrentHP(target.getCurrentHP() - (damage - defense));
                }
            }
            String sql = "UPDATE characters SET currentHP = :currentHP WHERE id = :id";
            try (Connection connection = sql2o.open()) {
                connection.createQuery(sql)
                        .addParameter("currentHP", target.getCurrentHP())
                        .addParameter("id", target.getId())
                        .executeUpdate();
            } catch (Sql2oException ex) {
                System.out.println(ex);
            };
            if (target.getCurrentHP() <= 0) {
                caster.setExperience(caster.getExperience() + (int) (.25 * target.getExperience()));
                String sql2 = "UPDATE characters SET experience = :experience WHERE id = :id";
                try (Connection connection = sql2o.open()) {
                    connection.createQuery(sql2)
                            .addParameter("experience", caster.getExperience())
                            .addParameter("id", caster.getId())
                            .executeUpdate();
                } catch (Sql2oException ex) {
                    System.out.println(ex);
                }
                this.checkForLevelUp(caster);
            }
        }
    }

    public List<Integer> findTurnOrder(List<CharacterC> characters) {
        List<Integer> turnOrder = new ArrayList<>();
        int originalSize = characters.size();
        for (int i = 0; i < originalSize; i++) {
            int bestDex = 0;
            int id = 0;
            for (CharacterC characterC : characters) {
                if (characterC.getDexterity() > 0 && bestDex < characterC.getDexterity()) {
                    bestDex = characterC.getDexterity();
                    id = characterC.getId();
                }
            }
            turnOrder.add(id);
            characters.remove(this.findById(id));
        }
        return turnOrder;
    }

    public boolean runAway(CharacterC runner, List<CharacterC> enemies) {
        int bestEnemy = 0;
        for (CharacterC characterC : enemies) {
            if (characterC.getDexterity() > bestEnemy) {
                bestEnemy = characterC.getDexterity();
            }
        }
        if (runner.getDexterity() > bestEnemy) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void checkForLevelUp(CharacterC characterC) {
        if (characterC.getExperience() > 50 && characterC.getLevel() < 2) {
            characterC.setCurrentHP(characterC.getHP());
            characterC.setCurrentMP(characterC.getMP());
            characterC.setLevel(2);
            if (characterC.getCharClass() != null) {
                if (characterC.getCharClass().toLowerCase().equals("fighter")) {
                    characterC.setHP(characterC.getHP() + 5);
                    if (characterC.getCharClass().toLowerCase().equals("red mage")) {
                        characterC.setMagic(characterC.getMagic() + 3);
                    }
                }
            } else {
                characterC.setDefense(characterC.getDefense() + 2);
            }
        }
        if (characterC.getExperience() > 100 && characterC.getLevel() < 3) {
            characterC.setCurrentHP(characterC.getHP());
            characterC.setCurrentMP(characterC.getMP());
            characterC.setLevel(3);
            if (characterC.getCharClass() != null) {
                if (characterC.getCharClass().toLowerCase().equals("fighter")) {
                    characterC.setStrength(characterC.getStrength() + 3);
                }
                if (characterC.getCharClass().toLowerCase().equals("red mage")) {
                    characterC.setMagicDefense(characterC.getMagicDefense() + 3);
                    this.addSpellToCharacterC(spellDao.findById(3), characterC);
                }
            } else {
                characterC.setMagicDefense(characterC.getMagicDefense() + 2);
            }
        }
        if (characterC.getExperience() > 150 && characterC.getLevel() < 4) {
            characterC.setCurrentHP(characterC.getHP());
            characterC.setCurrentMP(characterC.getMP());
            characterC.setLevel(4);
            if (characterC.getCharClass() != null) {
                if (characterC.getCharClass().toLowerCase().equals("fighter")) {
                    characterC.setDexterity(characterC.getDexterity() + 3);
                }
                if (characterC.getCharClass().toLowerCase().equals("red mage")) {
                    characterC.setMP(characterC.getMP() + 5);
                }
            } else {
                characterC.setDexterity(characterC.getDexterity() + 3);
            }
        }
        if (characterC.getExperience() > 250 && characterC.getLevel() < 5) {
            characterC.setCurrentHP(characterC.getHP());
            characterC.setCurrentMP(characterC.getMP());
            characterC.setLevel(5);
            if (characterC.getCharClass() != null) {
                if (characterC.getCharClass().toLowerCase().equals("fighter")) {
                    characterC.setMagicDefense(characterC.getMagicDefense() + 3);
                }
                if (characterC.getCharClass().toLowerCase().equals("red mage")) {
                    characterC.setDexterity(characterC.getDexterity() + 3);
                }
            } else {
                characterC.setMagic(characterC.getMagic() + 3);
            }
        }
        if (characterC.getExperience() > 400 && characterC.getLevel() < 6) {
            characterC.setCurrentHP(characterC.getHP());
            characterC.setCurrentMP(characterC.getMP());
            characterC.setLevel(6);
            if (characterC.getCharClass() != null) {
                if (characterC.getCharClass().toLowerCase().equals("fighter")) {
                    characterC.setStrength(characterC.getStrength() + 7);
                }
                if (characterC.getCharClass().toLowerCase().equals("red mage")) {
                    characterC.setMagic(characterC.getMagic() + 7);
                }
            } else {
                characterC.setStrength(characterC.getStrength() + 3);
            }
        }
        if (characterC.getExperience() > 600 && characterC.getLevel() < 7) {
            characterC.setCurrentHP(characterC.getHP());
            characterC.setCurrentMP(characterC.getMP());
            characterC.setLevel(7);
            characterC.setHP(characterC.getHP() + 10);
        }
        if (characterC.getLevel() > 850 && characterC.getLevel() < 8) {
            characterC.setCurrentHP(characterC.getHP());
            characterC.setCurrentMP(characterC.getMP());
            characterC.setLevel(8);
            if (characterC.getCharClass() != null) {
                if (characterC.getCharClass().toLowerCase().equals("fighter")) {
                    characterC.setMagicDefense(characterC.getDefense() + 5);
                }
                if (characterC.getCharClass().toLowerCase().equals("red mage")) {
                    characterC.setStrength(characterC.getStrength() + 7);
                    this.addSpellToCharacterC(spellDao.findById(6), characterC);
                }
            } else {
                characterC.setDefense(characterC.getDefense() + 3);
            }
        }
        if (characterC.getLevel() > 1150 && characterC.getLevel() < 9) {
            characterC.setCurrentHP(characterC.getHP());
            characterC.setCurrentMP(characterC.getMP());
            characterC.setLevel(9);
            if (characterC.getCharClass() != null) {
                if (characterC.getCharClass().toLowerCase().equals("fighter")) {
                    characterC.setStrength(characterC.getStrength() + 10);
                    characterC.setHP(characterC.getHP() + 10);
                }
                if (characterC.getCharClass().toLowerCase().equals("red mage")) {
                    characterC.setMagic(characterC.getMagic() + 7);
                    characterC.setStrength(characterC.getStrength() + 4);
                    characterC.setHP(characterC.getHP() + 10);
                    this.addSpellToCharacterC(spellDao.findById(7), characterC);
                }
            } else {
                characterC.setMagic(characterC.getMagic() + 5);
                characterC.setStrength(characterC.getStrength() + 5);
                characterC.setHP(characterC.getHP() + 10);
                characterC.setDexterity(characterC.getDexterity() + 5);
            }
        }
        String sql = "UPDATE characters SET level = :level, experience = :experience, HP = :HP, currentHP = :currentHP, defense = :defense, magicDefense = :magicDefense, strength = :strength, MP = :MP, currentMP = :currentMP, magic = :magic, dexterity = :dexterity WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("level", characterC.getLevel())
                    .addParameter("experience", characterC.getExperience())
                    .addParameter("HP", characterC.getHP())
                    .addParameter("currentHP", characterC.getCurrentHP())
                    .addParameter("defense", characterC.getDefense())
                    .addParameter("magicDefense", characterC.getMagicDefense())
                    .addParameter("strength", characterC.getStrength())
                    .addParameter("MP", characterC.getMP())
                    .addParameter("currentMP", characterC.getCurrentMP())
                    .addParameter("magic", characterC.getMagic())
                    .addParameter("dexterity", characterC.getDexterity())
                    .addParameter("id", characterC.getId())
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
        characterC.setDefense(characterC.getDefense() - equipment.getDefense());
        characterC.setMagicDefense(characterC.getMagicDefense() - equipment.getMagicDefense());
        characterC.setMagic(characterC.getMagic() - equipment.getMagic());
        characterC.setStrength(characterC.getStrength() - equipment.getStrength());
        characterC.setDexterity(characterC.getDexterity() - equipment.getDexterity());
        String sql1 = "UPDATE characters SET defense = :defense, magicDefense = :magicDefense, strength = :strength, magic = :magic, dexterity = :dexterity WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql1)
                    .addParameter("defense", characterC.getDefense())
                    .addParameter("magicDefense", characterC.getMagicDefense())
                    .addParameter("strength", characterC.getStrength())
                    .addParameter("magic", characterC.getMagic())
                    .addParameter("dexterity", characterC.getDexterity())
                    .addParameter("id", characterC.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

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
    }

    public void removeEffectFromCharacterC(Effect effect, CharacterC characterC) {
        String sql = "DELETE FROM characters_effects WHERE effectId = :effectId AND characterId = :characterId";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("effectId", effect.getId())
                    .addParameter("characterId", characterC.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    public void removeAllEquipmentFromCharacterC(CharacterC characterC) {
        List<Equipment> allEquipment = this.getAllEquipmentForACharacter(characterC.getId());
        String sql = "DELETE FROM characters_equipment WHERE characterId = :characterId";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("characterId", characterC.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

        for(Equipment equipment : allEquipment) {
            characterC.setDefense(characterC.getDefense() - equipment.getDefense());
            characterC.setMagicDefense(characterC.getMagicDefense() - equipment.getMagicDefense());
            characterC.setMagic(characterC.getMagic() - equipment.getMagic());
            characterC.setStrength(characterC.getStrength() - equipment.getStrength());
            characterC.setDexterity(characterC.getDexterity() - equipment.getDexterity());
            String sql1 = "UPDATE characters SET defense = :defense, magicDefense = :magicDefense, strength = :strength, magic = :magic, dexterity = :dexterity WHERE id = :id";
            try (Connection connection = sql2o.open()) {
                connection.createQuery(sql1)
                        .addParameter("defense", characterC.getDefense())
                        .addParameter("magicDefense", characterC.getMagicDefense())
                        .addParameter("strength", characterC.getStrength())
                        .addParameter("magic", characterC.getMagic())
                        .addParameter("dexterity", characterC.getDexterity())
                        .addParameter("id", characterC.getId())
                        .executeUpdate();
            } catch (Sql2oException ex) {
                System.out.println(ex);
            }
        }
    }

    public void removeAllSpellsFromCharacterC(CharacterC characterC){
        String sql = "DELETE FROM characters_spells WHERE characterId = :characterId";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("characterId", characterC.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    public void removeAllEffectsFromCharacterC(CharacterC characterC) {
        String sql = "DELETE FROM characters_effects WHERE characterId = :characterId";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("characterId", characterC.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    public String getNameUsingRandom(){
        String name = "";
        String route = "https://randomuser.me/api/?";
        String nationality = "&nat=us";
        String property = "inc=name";
        String apiRequest = (route + property + nationality).replaceAll(" ", "+");
        try {
            URL url = new URL(apiRequest);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            JsonParser parser = new JsonParser();
            JsonElement json = parser.parse(new InputStreamReader((InputStream) request.getContent()));
            name = json.getAsJsonObject().get("results")
                    .getAsJsonArray().get(0)
                    .getAsJsonObject().get("name")
                    .getAsJsonObject().get("first")
                    .getAsString();
            System.out.println(name);

        }catch (IOException e) {
            e.printStackTrace();
        }
        return name;

    }
    public void userInput(String string, CharacterC characterC, List<CharacterC> targets) {
        if (string.toLowerCase().equals("attack")) {
            this.attack(characterC, targets.get(0));
        }
        for (Spell spell : this.getAllSpellsForACharacter(characterC.getId())) {
            if (spell.getName().toLowerCase().equals(string)) {
                this.castSpell(spell, characterC, targets);
                break;
            }
        }
        if (string.toLowerCase().equals("run away")) {
            this.runAway(characterC, targets);
        }
    }

    public void computerInput(CharacterC enemy, List<CharacterC> targets) {
        int magicNumber = (int) Math.random() * (targets.size() - 1);
        int magicNumber1 = (int) Math.random() * (targets.size() - 1);
        if (enemy.getMagic() * (Math.random() + 1) > targets.get(magicNumber).getMagicDefense() && this.getAllSpellsForACharacter(enemy.getId()).size() > 0) {
            this.castSpell(this.getAllSpellsForACharacter(enemy.getId()).get(magicNumber1), enemy, targets);
        } else if (enemy.getCurrentHP() > 5) {
            this.attack(enemy, targets.get(magicNumber));
        } else {
            this.runAway(enemy, targets);
        }
    }

//    public void battle(List<CharacterC> PCs, List<CharacterC> enemies) {
//        List<CharacterC> allCharacters = new ArrayList<CharacterC>() {
//            {
//                addAll(PCs);
//                addAll(enemies);
//            }
//        };
//        List<Integer> order = this.findTurnOrder(allCharacters);
//        int currentTurn = 0;
//        while (PCs.size() > 0 && enemies.size() > 0) {
//            if (PCs.get(currentTurn).getCharClass() != null || PCs.get(currentTurn).getCharClass() != "") {
//
//            } else {
//
//            }
//
//            if (currentTurn >= order.size()) {
//                currentTurn = 0;
//            }
//        }
//    }

    public void populateCharacters() {
        CharacterC goblin = new CharacterC("Goblin", "A nasty Goblin", 1, 20, 10, 10, 1, 0, 2, 0, 0, 0, 2);
        CharacterC goblinGuard = new CharacterC("Goblin Guard", "A nasty Goblin who seems to be in charge", 2, 80, 20, 20, 2, 1, 3, 0, 0, 0, 3);
        CharacterC ghoul = new CharacterC("Ghoul", "A smelly monstrosity", 1, 40, 12, 12, 1, 1, 4, 0, 0, 0, 3);
        CharacterC elemental = new CharacterC("Element", "A sentient power of nature", 1, 20, 15, 15, 0, 3, 0, 15, 15, 3, 3);
        CharacterC blackKnight = new CharacterC("Black Knight", "What was once a holy knight, captured and tormented by the abyss. Nothing remains besides anger towards all", 2, 100, 30, 30, 3, 2, 4, 10, 10, 2, 1);
        CharacterC blackWidow = new CharacterC("Black Widow", "Kill it with fire!!!", 1, 40, 15, 15, 3, 0, 4, 0, 0, 0, 4);
        CharacterC cobra = new CharacterC("Cobra", "Snakes... I hate snakes...", 1, 20, 10, 10, 2, 0, 2, 0, 0, 0, 5);
        CharacterC deathKnight = new CharacterC("Death Knight", "A knight that kills all who may challenge thee", 3, 200, 40, 40, 4, 3, 5, 15, 15, 3, 2);
        CharacterC medusa = new CharacterC("Medusa", "Don't look in her eyes!", 5, 600 , 30, 30, 4, 6, 3, 35, 35, 6, 2);
        CharacterC wolf = new CharacterC("Wolf", "A normal, regular, uninteresting wolf", 1, 20, 10, 10, 1, 0, 3, 0, 0, 0, 4);
        CharacterC alphaWolf = new CharacterC("Alpha Wolf", "The head of the pack!", 2, 80, 20, 20, 2, 1, 3, 0, 0, 0, 6);
        CharacterC troll = new CharacterC("Troll", "Looks a bit like Danny Devito", 3, 400, 50, 50, 4, 2, 3, 0, 0, 0, 1);
        CharacterC werewolf = new CharacterC("Werewolf", "What wolf?", 3, 160, 30, 30, 1, 1, 4, 5, 5, 1, 5);
        CharacterC scorpion = new CharacterC("Scorpion", "Sub-Zero's mortal enemy", 1, 40, 15, 15, 3, 1, 3, 10, 10, 3, 4);
        CharacterC reaper = new CharacterC("Reaper", "'Die, Dieee, DIIIEEEE'", 6, 600, 30, 30, 3, 6, 3, 30, 30, 6, 3);
        CharacterC pirate = new CharacterC("Pirate", "Why couldn't Samantha take her kid with her to see a pirate movie? Because it was rated RRRRRRR", 2, 40, 20, 20, 3, 1, 4, 0, 0, 0, 4);
        CharacterC ogre = new CharacterC("Ogre", "Ogre? I hardly know her!", 4, 400, 40, 40, 6, 4, 5, 0, 0, 0, 1);
        CharacterC killerShark = new CharacterC("Killer Shark", "A land shark. How did it grow legs?", 2, 40, 20, 20, 1, 0, 3, 0, 0, 0, 7);
        CharacterC ghost = new CharacterC("Ghost", "oooOOOOoooOOoooooOOOOOOOooooOOOOOOoooooOOOooooOOOooooOOOOOOO!!!", 1, 40, 15, 10, 1, 8, 1, 30, 30, 5, 3);
        CharacterC unicorn = new CharacterC("Unicorn", "I thought these were extinct... Well, time to finish the job.", 4, 400, 30, 30, 4, 7, 4, 50, 50, 7, 7);
        CharacterC chaos = new CharacterC("Chaos", "'So even as you die again and again, I shall return! Born again in this endless circle that I have created!'", 9, 3000, 100, 100, 10, 10, 8, 75, 75, 8, 8);

        this.add(goblin);
        this.add(goblinGuard);
        this.add(ghoul);
        this.add(elemental);
        this.add(blackKnight);
        this.add(blackWidow);
        this.add(cobra);
        this.add(deathKnight);
        this.add(medusa);
        this.add(wolf);
        this.add(alphaWolf);
        this.add(troll);
        this.add(werewolf);
        this.add(scorpion);
        this.add(reaper);
        this.add(pirate);
        this.add(ogre);
        this.add(killerShark);
        this.add(ghost);
        this.add(unicorn);
        this.add(chaos);

    }
}

