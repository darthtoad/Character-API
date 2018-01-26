package Dao;

import models.Effect;

import java.util.List;

public interface EffectDao {
    void add(Effect effect);

    Effect findById(int id);
    List<Effect> getAll();

    void update(int id, String name, String description, int HP, int currentHP, int defense, int magicDefense, int strength, int MP, int currentMP, int magic, int dexterity, String other);

    void deleteById(int id);
    void deleteAll();
}
