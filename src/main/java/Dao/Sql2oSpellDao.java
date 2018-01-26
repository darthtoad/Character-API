package Dao;

import models.Effect;
import models.Spell;

import java.util.List;

public class Sql2oSpellDao implements SpellDao{
    @Override
    public void add(Spell spell) {

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
