package models;

import java.util.Objects;

public class Item {
    private int id;
    private int currentHP;
    private int currentMP;

    public Item(int currentHP, int currentMP){
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (id != item.id) return false;
        if (currentHP != item.currentHP) return false;
        return (Integer)currentMP != null ? currentMP != item.currentMP : (Integer)item.currentMP == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + currentHP;
        result = 31 * result + currentMP;
        return result;
    }
}
