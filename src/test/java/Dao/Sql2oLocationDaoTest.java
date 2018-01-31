package Dao;

import models.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

/**
 * Created by Guest on 1/31/18.
 */
public class Sql2oLocationDaoTest {
    private Connection connection;
    private Sql2oLocationDao locationDao;

    @Before
    public void setUp() throws Exception {
        String connectionString  = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        locationDao = new Sql2oLocationDao(sql2o);
        connection = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }

    public Location setupNewLocation() {
        return new Location("Midgar", "A place");
    }

    public Location setupNewLocation1() {
        return new Location("Nijland", "A place");
    }

    @Test
    public void addAddsCorrectly() throws Exception {
        Location location = setupNewLocation();
        int originalId = location.getId();
        locationDao.add(location);
        assertNotEquals(originalId, location.getId());
    }

    @Test
    public void findByIdFindsCorrectly() throws Exception {
        Location location = setupNewLocation();
        Location location1 = setupNewLocation1();
        locationDao.add(location);
        locationDao.add(location1);
        assertEquals(location, locationDao.findById(location.getId()));
        assertEquals(location1, locationDao.findById(location1.getId()));
    }

    @Test
    public void getAllGetsAllCorrectly() throws Exception {
        Location location = setupNewLocation();
        Location location1 = setupNewLocation1();
        locationDao.add(location);
        locationDao.add(location1);
        assertTrue(locationDao.getAll().contains(location));
        assertTrue(locationDao.getAll().contains(location1));
    }

    @Test
    public void updateUpdatesCorrectly() throws Exception {
        Location location = setupNewLocation();
        Location location1 = setupNewLocation1();
        locationDao.add(location);
        locationDao.add(location1);
        String originalName = location.getName();
        String originalDescription = location.getDescription();
        locationDao.update(location.getId(), "New Zealand", "An island. Or two islands. Or two island surrounded by smaller islands.");
        assertNotEquals(originalDescription, locationDao.findById(location.getId()).getDescription());
        assertNotEquals(originalName, locationDao.findById(location.getId()).getName());
    }

    @Test
    public void deleteByIdDeletesCorrectly() throws Exception {
    }

    @Test
    public void deleteAllDeletesAllCorrectly() throws Exception {
    }

}