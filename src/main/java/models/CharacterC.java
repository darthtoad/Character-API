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

    public CharacterC(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public CharacterC(String name, String description, String charClass) {
        this.name = name;
        this.description = description;
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
        if (!name.equals(that.name)) return false;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }
}
