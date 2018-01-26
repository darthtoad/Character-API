package Dao;

import models.Equipment;
import org.sql2o.Sql2o;

import java.util.List;

public class Sql2oEquipmentDao implements EquipmentDao {
    private final Sql2o sql2o;

    public Sql2oEquipmentDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Equipment equipment) {

    }

    @Override
    public Equipment findById(int id) {
        return null;
    }

    @Override
    public List<Equipment> getAll() {
        return null;
    }

    @Override
    public void update(int id, String name, String description, int strength, int magic, int dexterity, int defense, int magicDefense) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void deleteAll() {

    }
}
