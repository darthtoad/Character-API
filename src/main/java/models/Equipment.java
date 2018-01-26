package models;

public class Equipment {
    private int id;
    private String name;
    private String description;
    private int strength;
    private int magic;
    private int dexterity;
    private int defense;
    private int magicDefense;

    public Equipment(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Equipment(String name, String description, int strength, int magic, int dexterity, int defense, int magicDefense) {
        this.name = name;
        this.description = description;
        this.strength = strength;
        this.magic = magic;
        this.dexterity = dexterity;
        this.defense = defense;
        this.magicDefense = magicDefense;
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

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Equipment equipment = (Equipment) o;

        if (id != equipment.id) return false;
        if (strength != equipment.strength) return false;
        if (magic != equipment.magic) return false;
        if (dexterity != equipment.dexterity) return false;
        if (defense != equipment.defense) return false;
        if (magicDefense != equipment.magicDefense) return false;
        if (!name.equals(equipment.name)) return false;
        return description.equals(equipment.description);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + strength;
        result = 31 * result + magic;
        result = 31 * result + dexterity;
        result = 31 * result + defense;
        result = 31 * result + magicDefense;
        return result;
    }
}
