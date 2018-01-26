package models;

public class Effect {
    private int id;
    private String name;
    private String description;
    private int HP;
    private int currentHP;
    private int defense;
    private int magicDefense;
    private int strength;
    private int MP;
    private int currentMP;
    private int magic;
    private int dexterity;
    private String other;

    public Effect(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Effect(int id, String name, String description, int HP, int currentHP, int defense, int magicDefense, int strength, int MP, int currentMP, int magic, int dexterity, String other) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.HP = HP;
        this.currentHP = currentHP;
        this.defense = defense;
        this.magicDefense = magicDefense;
        this.strength = strength;
        this.MP = MP;
        this.currentMP = currentMP;
        this.magic = magic;
        this.dexterity = dexterity;
        this.other = other;
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

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Effect effect = (Effect) o;

        if (id != effect.id) return false;
        if (HP != effect.HP) return false;
        if (currentHP != effect.currentHP) return false;
        if (defense != effect.defense) return false;
        if (magicDefense != effect.magicDefense) return false;
        if (strength != effect.strength) return false;
        if (MP != effect.MP) return false;
        if (currentMP != effect.currentMP) return false;
        if (magic != effect.magic) return false;
        if (dexterity != effect.dexterity) return false;
        if (!name.equals(effect.name)) return false;
        if (!description.equals(effect.description)) return false;
        return other != null ? other.equals(effect.other) : effect.other == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + HP;
        result = 31 * result + currentHP;
        result = 31 * result + defense;
        result = 31 * result + magicDefense;
        result = 31 * result + strength;
        result = 31 * result + MP;
        result = 31 * result + currentMP;
        result = 31 * result + magic;
        result = 31 * result + dexterity;
        result = 31 * result + (other != null ? other.hashCode() : 0);
        return result;
    }
}
