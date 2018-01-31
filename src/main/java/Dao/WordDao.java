package Dao;

import models.Word;

import java.util.List;

/**
 * Created by Guest on 1/31/18.
 */
public interface WordDao {
    void add(Word word);

    Word findById(int id);
    List<Word> getAll();

    void update(int id, String name);

    void deleteById(int id);
    void deleteAll();
}
