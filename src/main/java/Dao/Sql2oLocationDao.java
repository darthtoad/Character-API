package Dao;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import models.Location;
import com.google.gson.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

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

    public void createRandomLocation(String nationality) {
        String locationName = "Place";
        Location location = new Location(locationName);
        String url = "https://randomuser.me/api/?inc=location";
        String country = "&nat=" + nationality; //choices: AU, BR, CA, CH, DE, DK, ES, FI, FR, GB, IE, IR, NL, NZ, TR, US
        if (!country.equals("&nat=")) {
            url = url + country;
        }
        String charset = "UTF=8";

        try {
            URL url1 = new URL(url);
            HttpURLConnection request = (HttpURLConnection) url1.openConnection();
            request.connect();
            JsonParser parser = new JsonParser();

            JsonElement json = parser.parse(new InputStreamReader((InputStream) request.getContent()));

            JsonArray apiResponse = json.getAsJsonObject()
                    .getAsJsonArray("results");
            if (apiResponse != null) {
                String jsonLocation = json.getAsJsonObject()
                        .getAsJsonArray("results")
                        .get(0)
                        .getAsJsonObject()
                        .getAsJsonObject("location")
                        .get("city")
                        .getAsString();
                Location random = new Location(jsonLocation);
                this.add(random);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
//        Object newLocation = new Object();
//        try (Connection con = sql2o.open()){

//
//            try {
//                URLConnection connection = new URL(urlToUse).openConnection();
//                connection.setRequestProperty("Accept-Charset", charset);
//                InputStream response = connection.getInputStream();
//                try (Scanner scanner = new Scanner(response)) {
//                    newLocation = scanner.useDelimiter("//A").next();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } catch (Sql2oException ex) {
//            System.out.println(ex);
//        }
//        System.out.println(newLocation);
//        return newLocation;
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
        String sql = "DELETE FROM locations WHERE id = :id";
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
        String sql = "DELETE FROM locations";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
