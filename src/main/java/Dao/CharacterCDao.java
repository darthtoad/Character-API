package Dao;

import models.CharacterC;
import models.Effect;
import models.Equipment;
import models.Spell;

import java.util.List;

public interface CharacterCDao {

    void add(CharacterC characterC);
    void addEquipmentToCharacterC(Equipment equipment, CharacterC characterC);
    void addSpellToCharacterC(Spell spell, CharacterC characterC);
    void addEffectToCharacterC(Effect effect, CharacterC characterC);

    CharacterC findById(int id);
    List<CharacterC> getAll();
    List<Equipment> getAllEquipmentForACharacter(int id);
    List<Spell> getAllSpellsForACharacter(int id);
    List<Effect> getAllEffectsForACharacter(int id);

    void update(int id, String name, String description, int level, int experience, int HP, int currentHP, int defense, int magicDefense, int strength, int MP, int currentMP, int magic, int dexterity);

    void attack(CharacterC attacker, CharacterC target);
    void castSpell(Spell spell, CharacterC attacking, List<CharacterC> targets);
    void checkForLevelUp(CharacterC characterC);
    void removeEquipmentFromCharacterC(Equipment equipment, CharacterC characterC);
    void removeSpellFromCharacterC(Spell spell, CharacterC characterC);
    void removeEffectFromCharacterC(Effect effect, CharacterC characterC);
    void deleteById(int id);
    void deleteAll();
}
