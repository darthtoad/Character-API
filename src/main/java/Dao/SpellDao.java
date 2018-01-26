package Dao;

import models.Effect;
import models.Spell;

import java.util.List;

public interface SpellDao {
    void add(Spell spell);

    Spell findById(int id);
    List<Spell> getAll();

    void update(int id, String name, String description, int damage, List<Effect> effects);

    void deleteById(int id);
    void deleteAll();
}
