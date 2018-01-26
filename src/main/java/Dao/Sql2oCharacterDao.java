package Dao;

import models.Character;
import models.Effect;
import models.Equipment;
import models.Spell;

import java.util.List;

public class Sql2oCharacterDao implements CharacterDao {
    @Override
    public void add(Character character) {

    }

    @Override
    public Character findById(int id) {
        return null;
    }

    @Override
    public List<Character> getAll() {
        return null;
    }

    @Override
    public void update(int id, String name, String description, int level, int experience, int HP, int currentHP, int defense, int magicDefense, int strength, int MP, int currentMP, int magic, int dexterity, List<Spell> spells, List<Equipment> equipment, List<Effect> effects) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void deleteAll() {

    }
}
