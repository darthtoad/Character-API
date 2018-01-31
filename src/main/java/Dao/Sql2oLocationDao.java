package Dao;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import models.Location;

import java.util.List;

/**
 * Created by Guest on 1/31/18.
 */
public class Sql2oLocationDao implements LocationDao {
    private final Sql2o sql2o;

    public Sql2oLocationDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Location location) {
        String sql = "INSERT INTO locations (name, description) VALUES (:name, :description)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(location)
                    .executeUpdate()
                    .getKey();
            location.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Location findById(int id) {
        String sql = "SELECT * FROM locations WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Location.class);
        }
    }

    @Override
    public List<Location> getAll() {
        String sql = "SELECT * FROM locations";
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(sql)
                    .executeAndFetch(Location.class);
        }
    }

    @Override
    public void update(int id, String name, String description) {
        String sql = "UPDATE locations SET name = :name, description = :description WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("name", name)
                    .addParameter("description", description)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM location WHERE id = :id";
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
        String sql = "DELETE FROM location";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
