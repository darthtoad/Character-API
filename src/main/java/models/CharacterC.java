package models;

import java.util.ArrayList;
import java.util.List;

public class CharacterC {
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
    private String charClass;

    public CharacterC(String name, String description, String charClass) {
        this.name = name;
        this.description = description;
        this.charClass = charClass;
    }

    public CharacterC(String name, String description, int level, int experience, int HP, int currentHP, int defense, int magicDefense, int strength, int MP, int currentMP, int magic, int dexterity, String charClass) {
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
        this.charClass = charClass;
    }

    public CharacterC(String name, String description, int level, int experience, int HP, int currentHP, int defense, int magicDefense, int strength, int MP, int currentMP, int magic, int dexterity) {
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

    }



    public String getCharClass() {
        return charClass;
    }

    public void setCharClass(String charClass) {
        this.charClass = charClass;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CharacterC that = (CharacterC) o;

        if (id != that.id) return false;
        if (level != that.level) return false;
        if (experience != that.experience) return false;
        if (HP != that.HP) return false;
        if (currentHP != that.currentHP) return false;
        if (defense != that.defense) return false;
        if (magicDefense != that.magicDefense) return false;
        if (strength != that.strength) return false;
        if (MP != that.MP) return false;
        if (currentMP != that.currentMP) return false;
        if (magic != that.magic) return false;
        if (dexterity != that.dexterity) return false;
        if (!description.equals(that.description)) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
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
        return result;
    }

}
