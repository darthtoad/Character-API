package models;

import java.util.ArrayList;
import java.util.List;

public class Spell {
    private String name;
    private String description;
    private int damage;
    private List<Effect> effects = new ArrayList<>();

    public Spell(String name, String description, int damage) {

        this.name = name;
        this.description = description;
        this.damage = damage;
    }

    public Spell(String name, String description, int damage, List<Effect> effects) {
        this.name = name;
        this.description = description;
        this.damage = damage;
        this.effects = effects;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public void setEffects(List<Effect> effects) {
        this.effects = effects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Spell spell = (Spell) o;

        if (damage != spell.damage) return false;
        if (!name.equals(spell.name)) return false;
        if (!description.equals(spell.description)) return false;
        return effects != null ? effects.equals(spell.effects) : spell.effects == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + damage;
        result = 31 * result + (effects != null ? effects.hashCode() : 0);
        return result;
    }
}
