package models;

import java.util.ArrayList;
import java.util.List;

public class Character {
    private int id;
    private String name;
    private String description;
    private int level;
    private int experience;
    private int HP;
    private int currentHP;
    private int defense;
    private int magicDefense;
    private int strength;
    private int MP;
    private int currentMP;
    private int magic;
    private int dexterity;
    private List<Spell> spells = new ArrayList<>();
    private List<Equipment> equipment = new ArrayList<>();
    private List<Effect> effects = new ArrayList<>();

    public Character(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Character(String name, String description, int level, int experience, int HP, int currentHP, int defense, int magicDefense, int strength, int MP, int currentMP, int magic, int dexterity, List<Spell> spells, List<Equipment> equipment, List<Effect> effects) {
        this.name = name;
        this.description = description;
        this.level = level;
        this.experience = experience;
        this.HP = HP;
        this.currentHP = currentHP;
        this.defense = defense;
        this.magicDefense = magicDefense;
        this.strength = strength;
        this.MP = MP;
        this.currentMP = currentMP;
        this.magic = magic;
        this.dexterity = dexterity;
        this.spells = spells;
        this.equipment = equipment;
        this.effects = effects;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getMagicDefense() {
        return magicDefense;
    }

    public void setMagicDefense(int magicDefense) {
        this.magicDefense = magicDefense;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getMP() {
        return MP;
    }

    public void setMP(int MP) {
        this.MP = MP;
    }

    public int getCurrentMP() {
        return currentMP;
    }

    public void setCurrentMP(int currentMP) {
        this.currentMP = currentMP;
    }

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public List<Spell> getSpells() {
        return spells;
    }

    public void setSpells(List<Spell> spells) {
        this.spells = spells;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
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

        Character character = (Character) o;

        if (id != character.id) return false;
        if (level != character.level) return false;
        if (experience != character.experience) return false;
        if (HP != character.HP) return false;
        if (currentHP != character.currentHP) return false;
        if (defense != character.defense) return false;
        if (magicDefense != character.magicDefense) return false;
        if (strength != character.strength) return false;
        if (MP != character.MP) return false;
        if (currentMP != character.currentMP) return false;
        if (magic != character.magic) return false;
        if (dexterity != character.dexterity) return false;
        if (!name.equals(character.name)) return false;
        if (!description.equals(character.description)) return false;
        if (spells != null ? !spells.equals(character.spells) : character.spells != null) return false;
        if (equipment != null ? !equipment.equals(character.equipment) : character.equipment != null) return false;
        return effects != null ? effects.equals(character.effects) : character.effects == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + level;
        result = 31 * result + experience;
        result = 31 * result + HP;
        result = 31 * result + currentHP;
        result = 31 * result + defense;
        result = 31 * result + magicDefense;
        result = 31 * result + strength;
        result = 31 * result + MP;
        result = 31 * result + currentMP;
        result = 31 * result + magic;
        result = 31 * result + dexterity;
        result = 31 * result + (spells != null ? spells.hashCode() : 0);
        result = 31 * result + (equipment != null ? equipment.hashCode() : 0);
        result = 31 * result + (effects != null ? effects.hashCode() : 0);
        return result;
    }

}
