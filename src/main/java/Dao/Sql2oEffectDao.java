package Dao;

import models.Effect;

import java.util.List;

public class Sql2oEffectDao implements EffectDao{
    @Override
    public void add(Effect effect) {

    }

    @Override
    public Effect findById(int id) {
        return null;
    }

    @Override
    public List<Effect> getAll() {
        return null;
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
