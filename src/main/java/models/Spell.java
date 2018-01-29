package models;

import java.util.ArrayList;
import java.util.List;

public class Spell {
    private int id;
    private String name;
    private String description;
    private int damage;
    private int MP;
    private String effects;

    public Spell(String name, String description, int damage, int MP) {

        this.name = name;
        this.description = description;
        this.damage = damage;
        this.MP = MP;
    }

    public Spell(String name, String description, int MP, String effects) {
        this.name = name;
        this.description = description;
        this.MP = MP;
        this.effects = effects;
    }

    public Spell(String name, String description, int damage, int MP, String effects) {
        this.name = name;
        this.description = description;
        this.damage = damage;
        this.MP = MP;
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

    public int getMP() {
        return MP;
    }

    public void setMP(int MP) {
        this.MP = MP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Spell spell = (Spell) o;

        if (id != spell.id) return false;
        if (!name.equals(spell.name)) return false;
        return description.equals(spell.description);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }
}
