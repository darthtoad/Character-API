package Dao;

import models.Effect;
import models.Spell;

import java.util.List;

public interface SpellDao {
    void add(Spell spell);
    void addEffectToSpell(Effect effect, Spell spell);

    Spell findById(int id);
    List<Spell> getAll();
    List<Effect> getAllEffectsForSpell(int id);

    void update(int id, String name, String description, int damage, String effects);

    void removeEffectFromSpell(Effect effect, Spell spell);
    void removeAllEffectsFromSpell(Spell spell);
    void deleteById(int id);
    void deleteAll();
}
