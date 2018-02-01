package Dao;

import models.CharacterC;
import models.Item;

import java.util.List;

public interface ItemDao {
    void add(Item item);
    void addItemToCharacterC(Item item, CharacterC characterC);

    Item findById(int id);
    List<Item> getAll();
    List<Item> getAllItemsForCharacters(int id);

    void update(int id, int currentHP, int currentMP);

    void removeCharacterCFromItem(CharacterC characterC, Item item);
    void removeAllCharacterCFromItem(CharacterC characterC);

    void deleteById(int id);
    void deleteAll();
}
