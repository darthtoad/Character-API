package Dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import models.Word;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Guest on 1/31/18.
 */
public class Sql2oWordDao implements WordDao {
    private final Sql2o sql2o;

    public Sql2oWordDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Word word) {
        String sql = "INSERT INTO words (name) VALUES (:name)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(word)
                    .executeUpdate()
                    .getKey();
            word.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    public void createRandomWord() {
        Word word;
        String url = "http://api.wordnik.com/v4/words.json/randomWord?includePartOfSpeech=noun&api_key=a2a73e7b926c924fad7001ca3111acd55af2ffabf50eb4ae5";
        String charset = "UTF=8";

        try {
            URL url1 = new URL(url);
            HttpURLConnection request = (HttpURLConnection) url1.openConnection();
            request.connect();
            JsonParser parser = new JsonParser();

            JsonElement json = parser.parse(new InputStreamReader((InputStream) request.getContent()));

            JsonObject apiResponse = json.getAsJsonObject();
            if (apiResponse != null) {
                String jsonWord = json.getAsJsonObject()
                        .get("word")
                        .getAsString();
                word = new Word(jsonWord);
                this.add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Object newWord = new Object();
//        try (Connection con = sql2o.open()){

//
//            try {
//                URLConnection connection = new URL(urlToUse).openConnection();
//                connection.setRequestProperty("Accept-Charset", charset);
//                InputStream response = connection.getInputStream();
//                try (Scanner scanner = new Scanner(response)) {
//                    newWord = scanner.useDelimiter("//A").next();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } catch (Sql2oException ex) {
//            System.out.println(ex);
//        }
//        System.out.println(newWord);
//        return newWord;
    }

    @Override
    public Word findById(int id) {
        String sql = "SELECT * FROM words WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Word.class);
        }
    }

    @Override
    public List<Word> getAll() {
        String sql = "SELECT * FROM words";
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(sql)
                    .executeAndFetch(Word.class);
        }
    }

    @Override
    public void update(int id, String name) {
        String sql = "UPDATE words SET name = :name WHERE id = :id";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("name", name)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM words WHERE id = :id";
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
        String sql = "DELETE FROM words";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}