package Dao;

import models.Location;

import java.util.List;

/**
 * Created by Guest on 1/31/18.
 */
public interface LocationDao {
    void add(Location location);

    Location findById(int id);
    List<Location> getAll();

    void update(int id, String name, String description);

    void deleteById(int id);
    void deleteAll();
}
