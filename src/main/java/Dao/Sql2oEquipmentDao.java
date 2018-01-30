package Dao;

import models.Equipment;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oEquipmentDao implements EquipmentDao {
    private final Sql2o sql2o;

    public Sql2oEquipmentDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Equipment equipment) {
        String sql = "INSERT INTO equipment (name, description, strength, magic, dexterity, defense, magicDefense) VALUES (:name, :description, :strength, :magic, :dexterity, :defense, :magicDefense)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(equipment)
                    .executeUpdate()
                    .getKey();
            equipment.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Equipment findById(int id) {
        String sql = "SELECT * FROM equipment WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Equipment.class);
        }
    }

    @Override
    public List<Equipment> getAll() {
        String sql = "SELECT * FROM equipment";
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(sql)
                    .executeAndFetch(Equipment.class);
        }
    }

    @Override
    public void update(int id, String name, String description, int strength, int magic, int dexterity, int defense, int magicDefense) {
        String sql = "UPDATE equipment SET name = :name, description = :description, strength = :strength, magic = :magic, dexterity = :dexterity, defense = :defense, magicDefense = :magicDefense WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("name", name)
                    .addParameter("description", description)
                    .addParameter("strength", strength)
                    .addParameter("magic", magic)
                    .addParameter("dexterity", dexterity)
                    .addParameter("defense", defense)
                    .addParameter("magicDefense", magicDefense)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM equipment WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM equipment";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void populateEquipments() {
        Equipment shortSword = new Equipment("Short Sword", "A short sword", 5, 0, 0, 0, 0);
        this.add(shortSword);
        Equipment mythrilKnife = new Equipment("Mythril Knife", "A knife wrought from mythril", 7, 0, 0, 0, 0);
        this.add(mythrilKnife);
        Equipment runeBlade = new Equipment("Rune Blade", "A sword effective against spellcasters", 4, 0, 0, 0, 0);
        this.add(runeBlade);
        Equipment flameSword = new Equipment("Flame Sword", "A sword that dances with flame", 8, 0, 0, 0, 0);
        this.add(flameSword);
        Equipment scimitar = new Equipment("Scimitar", "A sword with a curved blade", 5, 0, 0, 0, 0);
        this.add(scimitar);
        Equipment ashura = new Equipment("Ashura", "A sword whose name embodies battle", 6, 0, 0, 0, 0);
        this.add(ashura);
        Equipment falchion = new Equipment("Falchion", "A sword that widens toward its tip", 7, 0, 0, 0, 0);
        this.add(falchion);
        Equipment saber = new Equipment("Saber", "A long sword made for piercing", 4, 0, 0, 0, 0);
        this.add(saber);
        Equipment healingStaff = new Equipment("Healing Staff", "A staff that casts Heal when used", 5, 4, 0, 0, 0);
        this.add(healingStaff);
        Equipment goldenStaff = new Equipment("Golden Staff", "A staff that enhances intelligence", 4, 6, 0, 0, 0);
        this.add(goldenStaff);
        Equipment judgmentStaff = new Equipment("Judgment Staff", "A staff that casts Flare when used", 7, 5, 0, 0, 0);
        this.add(judgmentStaff);
        Equipment ironArmor = new Equipment("Iron Armor", "Armor of iron", 0, 0, 0, 7, 0);
        this.add(ironArmor);
        Equipment darkArmor = new Equipment("Dark Armor", "Armor possessing a powerful dark force", 0, 2, 0, 6, 4);
        this.add(ironArmor);
        Equipment flameMail = new Equipment("Flame Mail", "Armor immersed in the power of fire, resistant against ice", 0, 0, 0, 8, 3);
        this.add(flameMail);
        Equipment iceArmor = new Equipment("Ice Armor", "Armor immersed in the power of ice, resistant against fire", 0, 0, 0, 6, 1);
        this.add(iceArmor);
        Equipment diamondArmor = new Equipment("Diamond Armor", "Armor resistant against lightning", 0, 0, 0, 9, 9);
        this.add(iceArmor);

    }
}
