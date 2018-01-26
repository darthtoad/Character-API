package Dao;

import models.Character;
import models.Effect;
import models.Equipment;
import models.Spell;

import java.util.List;

public interface CharacterDao {

    void add(Character character);

    Character findById(int id);
    List<Character> getAll();

    void update(int id, String name, String description, int level, int experience, int HP, int currentHP, int defense, int magicDefense, int strength, int MP, int currentMP, int magic, int dexterity, List<Spell> spells, List<Equipment> equipment, List<Effect> effects);

    void deleteById(int id);
    void deleteAll();
}
