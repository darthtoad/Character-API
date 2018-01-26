package models;

import java.util.ArrayList;
import java.util.List;

public class Spell {
    private int id;
    private String name;
    private String description;
    private int damage;
    private String effects;

    public Spell(String name, String description, int damage) {

        this.name = name;
        this.description = description;
        this.damage = damage;
    }

    public Spell(String name, String description, String effects) {
        this.name = name;
        this.description = description;
        this.effects = effects;
    }

    public Spell(String name, String description, int damage, String effects) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEffects() {
        return effects;
    }

    public void setEffects(String effects) {
        this.effects = effects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Spell spell = (Spell) o;

        if (damage != spell.damage) return false;
        if (!name.equals(spell.name)) return false;
        if (description != null ? !description.equals(spell.description) : spell.description != null) return false;
        return effects != null ? effects.equals(spell.effects) : spell.effects == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + damage;
        result = 31 * result + (effects != null ? effects.hashCode() : 0);
        return result;
    }
}
