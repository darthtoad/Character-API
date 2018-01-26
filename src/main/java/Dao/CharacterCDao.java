package Dao;

import models.CharacterC;
import models.Effect;
import models.Equipment;
import models.Spell;

import java.util.List;

public interface CharacterCDao {

    void add(CharacterC characterC);

    CharacterC findById(int id);
    List<CharacterC> getAll();

    void update(int id, String name, String description, int level, int experience, int HP, int currentHP, int defense, int magicDefense, int strength, int MP, int currentMP, int magic, int dexterity, String spells, String equipment, String effects);

    void deleteById(int id);
    void deleteAll();
}
