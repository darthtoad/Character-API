package Dao;

import models.Equipment;

import java.util.List;

public interface EquipmentDao {
    void add(Equipment equipment);

    Equipment findById(int id);
    List<Equipment> getAll();

    void update(int id, String name, String description, int strength, int magic, int dexterity, int defense, int magicDefense);

    void deleteById(int id);
    void deleteAll();
}
