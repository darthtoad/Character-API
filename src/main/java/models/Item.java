package models;

import java.util.Objects;

public class Item {
    private int id;
    private String name;
    private int currentHP;
    private int currentMP;

    public Item(String name, int currentHP, int currentMP){
        this.name = name;
        this.currentHP = currentHP;
        this.currentMP = currentMP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public int getCurrentMP() {
        return currentMP;
    }

    public void setCurrentMP(int currentMP) {
        this.currentMP = currentMP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (id != item.id) return false;
        if (currentHP != item.currentHP) return false;
        if (currentMP != item.currentMP) return false;
        return name != null ? name.equals(item.name) : item.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + currentHP;
        result = 31 * result + currentMP;
        return result;
    }
}
