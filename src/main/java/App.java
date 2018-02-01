import Dao.*;
import com.google.gson.Gson;
import models.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        Sql2oEffectDao effectDao;
        Sql2oSpellDao spellDao;
        Sql2oEquipmentDao equipmentDao;
        Sql2oCharacterCDao characterCDao;
        Sql2oItemDao itemDao;

        Sql2oWordDao wordDao;
        Sql2oLocationDao locationDao;

        Connection connection;
        Gson gson = new Gson();

        String connectionString = "jdbc:h2:~/characterAPI.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");

        effectDao = new Sql2oEffectDao(sql2o);
        spellDao = new Sql2oSpellDao(sql2o);
        equipmentDao = new Sql2oEquipmentDao(sql2o);
        characterCDao = new Sql2oCharacterCDao(sql2o);
        itemDao = new Sql2oItemDao(sql2o);
        wordDao = new Sql2oWordDao(sql2o);
        locationDao = new Sql2oLocationDao(sql2o);

        connection = sql2o.open();

        //API

        post("/characters/new", "application/json", (request, response) -> {
            CharacterC characterC = gson.fromJson(request.body(), CharacterC.class);
            characterCDao.add(characterC);
            response.status(201);
            return gson.toJson(characterC);
        });

        get("/characters/random/new", "application/json", (request, response) -> {
            String name = characterCDao.getNameUsingRandom();
            CharacterC characterC = new CharacterC(name, "NPC");
            characterCDao.add(characterC);
            response.status(201);
            return gson.toJson(characterC);
        });

        post("/characters/:characterId/effects/:effectId", "application/json", (request, response) -> {
            int characterId = Integer.parseInt(request.params("characterId"));
            int effectId = Integer.parseInt(request.params("effectId"));
            characterCDao.addEffectToCharacterC(effectDao.findById(effectId), characterCDao.findById(characterId));
            response.status(201);
            return gson.toJson(String.format("Character '%s' has effect '%s'", characterCDao.findById(characterId).getName(), effectDao.findById(effectId).getName()));
        });

        post("/characters/:characterId/equipment/:equipmentId", "application/json", (request, response) -> {
            int characterId = Integer.parseInt(request.params("characterId"));
            int equipmentId = Integer.parseInt(request.params("equipmentId"));
            characterCDao.addEquipmentToCharacterC(equipmentDao.findById(equipmentId), characterCDao.findById(characterId));
            response.status(201);
            return gson.toJson(String.format("Character '%s' has a/some '%s'", characterCDao.findById(characterId).getName(), equipmentDao.findById(equipmentId).getName()));
        });

        post("/characters/:characterId/spells/:spellId", "application/json", (request, response) -> {
            int characterId = Integer.parseInt(request.params("characterId"));
            int spellId = Integer.parseInt(request.params("spellId"));
            characterCDao.addSpellToCharacterC(spellDao.findById(spellId), characterCDao.findById(characterId));
            response.status(201);
            return gson.toJson(String.format("Character '%s' can use spell '%s'", characterCDao.findById(characterId).getName(), spellDao.findById(spellId).getName()));
        });

        post("/characters/:characterId/items/:itemId", "application/json", (request, response) -> {
            int characterId = Integer.parseInt(request.params("characterId"));
            int itemId = Integer.parseInt(request.params("itemId"));
            itemDao.addItemToCharacterC(itemDao.findById(itemId), characterCDao.findById(characterId));
            response.status(201);
            return gson.toJson(String.format("Character %s has item %s", characterCDao.findById(characterId).getName(), itemDao.findById(itemId).getName()));
        });

        post("/effects/new", "application/json", (request, response) -> {
            Effect effect = gson.fromJson(request.body(), Effect.class);
            effectDao.add(effect);
            response.status(201);
            return gson.toJson(effect);
        });

        post("/equipment/new", "application/json", (request, response) -> {
            Equipment equipment = gson.fromJson(request.body(), Equipment.class);
            equipmentDao.add(equipment);
            response.status(201);
            return gson.toJson(equipment);
        });

        post("/spells/new", "application/json", (request, response) -> {
            Spell spell = gson.fromJson(request.body(), Spell.class);
            spellDao.add(spell);
            response.status(201);
            return gson.toJson(spell);
        });

        post("/spells/:spellId/effects/:effectId", "application/json", (request, response) -> {
            int spellId = Integer.parseInt(request.params("spellId"));
            int effectId = Integer.parseInt(request.params("effectId"));
            spellDao.addEffectToSpell(effectDao.findById(effectId), spellDao.findById(spellId));
            response.status(201);
            return gson.toJson(String.format("Spell '%s' gives the effect '%s'", spellDao.findById(spellId).getName(), effectDao.findById(effectId).getName()));
        });

        post("/locations/new", "application/json", (request, response) -> {
            Location location = gson.fromJson(request.body(), Location.class);
            locationDao.createRandomLocation(location.getDescription());
            response.status(201);
            return gson.toJson(locationDao.findById(locationDao.getAll().size()));
        });

        get("/words/new", "application/json", (request, response) -> {
            wordDao.createRandomWord();
            response.status(201);
            return gson.toJson(wordDao.findById(wordDao.getAll().size()));
        });

        get("/characters", "application/json", (request, response) -> {
            response.status(201);
            return gson.toJson(characterCDao.getAll());
        });

        get("/characters/:id", "application/json", (request, response) -> {
            int characterId = Integer.parseInt(request.params("id"));
            CharacterC characterC = characterCDao.findById(characterId);
            response.status(201);
            return gson.toJson(characterC);
        });

        get("/characters/:id/spells", "application/json", (request, response) -> {
            int characterId = Integer.parseInt(request.params("id"));
            response.status(201);
            return gson.toJson(characterCDao.getAllSpellsForACharacter(characterId));
        });

        get("/characters/:id/equipment", "application/json", (request, response) -> {
            int characterId = Integer.parseInt(request.params("id"));
            response.status(201);
            return gson.toJson(characterCDao.getAllEquipmentForACharacter(characterId));
        });

        get("/characters/:id/effects", "application/json", (request, response) -> {
            int characterId = Integer.parseInt(request.params("id"));
            response.status(201);
            return gson.toJson(characterCDao.getAllEffectsForACharacter(characterId));
        });

        get("/characters/:id/items", "application/json", (request, response) -> {
            int characterId = Integer.parseInt(request.params("id"));
            response.status(201);
            return gson.toJson(itemDao.getAllItemsForCharacters(characterId));
        });

        get("/equipment", "application/json", (request, response) -> {
            response.status(201);
            return gson.toJson(equipmentDao.getAll());
        });

        get("/equipment/:id", "application/json", (request, response) -> {
            int equipmentId = Integer.parseInt(request.params("id"));
            response.status(201);
            return gson.toJson(equipmentDao.findById(equipmentId));
        });

        get("/spells", "application/json", (request, response) -> {
            response.status(201);
            return gson.toJson(spellDao.getAll());
        });

        get("/spells/:id", "application/json", (request, response) -> {
            int spellId = Integer.parseInt(request.params("id"));
            response.status(201);
            return gson.toJson(spellDao.findById(spellId));
        });

        get("/spells/:id/effects", "application/json", (request, response) -> {
            int spellId = Integer.parseInt(request.params("id"));
            response.status(201);
            return gson.toJson(spellDao.getAllEffectsForSpell(spellId));
        });

        get("/effects", "application/json", (request, response) -> {
            response.status(201);
            return gson.toJson(effectDao.getAll());
        });

        get("/effects/:id", "application/json", (request, response) -> {
            int effectId = Integer.parseInt(request.params("id"));
            response.status(201);
            return gson.toJson(effectDao.findById(effectId));
        });

        get("/words/:id", "application/json", (request, response) -> {
            int wordId = Integer.parseInt(request.params("id"));
            response.status(201);
            return gson.toJson(wordDao.findById(wordId));
        });

        get("/locations/:id", "application/json", (request, response) -> {
            int locationId = Integer.parseInt(request.params("id"));
            response.status(201);
            return gson.toJson(locationDao.findById(locationId));
        });

        post("/characters/:id/update", "application/json", (request, response) -> {
            int characterId = Integer.parseInt(request.params("id"));
            CharacterC characterC = gson.fromJson(request.body(), CharacterC.class);
            characterCDao.update(characterId, characterC.getName(), characterC.getDescription(), characterC.getLevel(), characterC.getExperience(), characterC.getHP(), characterC.getCurrentHP(), characterC.getDefense(), characterC.getMagicDefense(), characterC.getStrength(), characterC.getMP(), characterC.getCurrentMP(), characterC.getMagic(), characterC.getDexterity());
            response.status(201);
            return gson.toJson(characterCDao.findById(characterId));
        });

        post("/equipment/:id/update", "application/json", (request, response) -> {
            int equipmentId = Integer.parseInt(request.params("id"));
            Equipment equipment = gson.fromJson(request.body(), Equipment.class);
            equipmentDao.update(equipmentId, equipment.getName(), equipment.getDescription(), equipment.getStrength(), equipment.getMagic(), equipment.getDexterity(), equipment.getDefense(), equipment.getMagicDefense());
            response.status(201);
            return gson.toJson(equipmentDao.findById(equipmentId));
        });

        post("/spell/:id/update", "application/json", (request, response) -> {
            int spellId = Integer.parseInt(request.params("id"));
            Spell spell = gson.fromJson(request.body(), Spell.class);
            spellDao.update(spellId, spell.getName(), spell.getDescription(), spell.getDamage(), spell.getEffects());
            response.status(201);
            return gson.toJson(spellDao.findById(spellId));
        });

        post("/effect/:id/update", "application/json", (request, response) -> {
            int effectId = Integer.parseInt(request.params("id"));
            Effect effect = gson.fromJson(request.body(), Effect.class);
            effectDao.update(effectId, effect.getName(), effect.getDescription(), effect.getHP(), effect.getCurrentHP(), effect.getDefense(), effect.getMagicDefense(), effect.getStrength(), effect.getMP(), effect.getCurrentMP(), effect.getMagic(), effect.getDexterity(), effect.getOther());
            response.status(201);
            return gson.toJson(effectDao.findById(effectId));
        });

        post("/character/delete", "application/json", (request, response) -> {
            characterCDao.deleteAll();
            response.status(201);
            return gson.toJson("All characters have been deleted");
        });

        post("/character/:id/delete", "application/json", (request, response) -> {
            int characterId = Integer.parseInt(request.params("id"));
            characterCDao.deleteById(characterId);
            response.status(201);
            return gson.toJson("Your character has been deleted");
        });

        post("/character/:characterId/item/:itemId/delete", "application/json", (request, response) -> {
            int characterId = Integer.parseInt(request.params("characterId"));
            int itemId = Integer.parseInt(request.params("itemId"));
            itemDao.removeCharacterCFromItem(characterCDao.findById(characterId), itemDao.findById(itemId));
            response.status(201);
            return gson.toJson("Your character has used item");
        });

        post("/effect/delete", "application/json", (request, response) -> {
            effectDao.deleteAll();
            response.status(201);
            return gson.toJson("All effects have been deleted");
        });

        post("/effect/:id/delete", "application/json", (request, response) -> {
            int effectId = Integer.parseInt(request.params("id"));
            effectDao.deleteById(effectId);
            response.status(201);
            return gson.toJson("Your effect has been deleted");
        });

        post("/equipment/delete", "application/json", (request, response) -> {
            equipmentDao.deleteAll();
            response.status(201);
            return gson.toJson("All equipment have been deleted");
        });

        post("/equipment/:id/delete", "application/json", (request, response) -> {
            int equipmentId = Integer.parseInt(request.params("id"));
            equipmentDao.deleteById(equipmentId);
            response.status(201);
            return gson.toJson("Your equipment has been deleted");
        });

        post("/spell/delete", "application/json", (request, response) -> {
            spellDao.deleteAll();
            response.status(201);
            return gson.toJson("All spells have been deleted");
        });

        post("/spell/:id/delete", "application/json", (request, response) -> {
            int spellId = Integer.parseInt(request.params("id"));
            spellDao.deleteById(spellId);
            response.status(201);
            return gson.toJson("Your spell has been deleted");
        });

        post("/locations/delete", "application/json", (request, response) -> {
            locationDao.deleteAll();
            response.status(201);
            return gson.toJson("All locations have been deleted");
        });

        post("/words/delete", "application/json", (request, response) -> {
            wordDao.deleteAll();
            response.status(201);
            return gson.toJson("All words have been deleted");
        });

        //FRONTEND ROUTING
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/game/board1", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String villainName = characterCDao.getNameUsingRandom();
            String npcName1 = characterCDao.getNameUsingRandom();
            CharacterC villain = new CharacterC(villainName, "villain", null);
            model.put("villain", villain);
            CharacterC npc1 = new CharacterC(npcName1, "npc1", null);
//            CharacterC villainName = characterCDao.findById(characterCDao.getAll().size());
            model.put("npc1", npc1);
            wordDao.createRandomWord();
            Word word = wordDao.findById(wordDao.getAll().size());
            model.put("word", word);
            return new ModelAndView(model, "board1.hbs");
        }, new HandlebarsTemplateEngine());

        get("/game/board2", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            locationDao.createRandomLocation("de");
            Location ourLocation = locationDao.findById(locationDao.getAll().size());
            model.put("ourLocation", ourLocation);
            wordDao.createRandomWord();
            Word word = wordDao.findById(wordDao.getAll().size());
            model.put("word", word);
            return new ModelAndView(model, "board2.hbs");
        }, new HandlebarsTemplateEngine());

        get("/game/board3", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "board3.hbs");
        }, new HandlebarsTemplateEngine());

        get("/game/board3.1", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<CharacterC> characters = characterCDao.getAll();
            List<CharacterC> playerCharacter = new ArrayList<>();
            for (CharacterC character : characters) {
                if (character.getCharClass().toLowerCase().equals("fighter") || character.getCharClass().toLowerCase().equals("red mage")) {
                    playerCharacter.add(character);
                }
            }
            for (CharacterC characterC : playerCharacter) {
                characterC.setCurrentMP(characterC.getMP());
                characterC.setCurrentHP(characterC.getHP());
                characterCDao.update(characterC.getId(), characterC.getName(), characterC.getDescription(), characterC.getLevel(), characterC.getExperience(), characterC.getHP(), characterC.getCurrentHP(), characterC.getDefense(), characterC.getMagicDefense(), characterC.getStrength(), characterC.getMP(), characterC.getCurrentMP(), characterC.getMagic(), characterC.getDexterity());
            }
            return new ModelAndView(model, "board3.1.hbs");
        }, new HandlebarsTemplateEngine());

        get("/game/board3.2", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "board3.2.hbs");
        }, new HandlebarsTemplateEngine());

        get("/character/new", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "new_fighter.hbs");
        }, new HandlebarsTemplateEngine());

        post("/character/new", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            effectDao.populateEffects();
            equipmentDao.populateEquipments();
            spellDao.populateSpells();
            itemDao.populateItems();
            CharacterC character = new CharacterC(req.queryParams("name"), "Fighter", "fighter");
            characterCDao.add(character);
            model.put("character", character);
            return new ModelAndView(model, "new_redmage.hbs");
        }, new HandlebarsTemplateEngine());

        post("/game/new", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            CharacterC character = new CharacterC(req.queryParams("name"), "red mage", "red mage");
            characterCDao.add(character);
            characterCDao.addSpellToCharacterC(spellDao.findById(1), characterCDao.findById(character.getId()));
            characterCDao.populateCharacters();
            model.put("character", character);
            CharacterC NPC = new CharacterC(characterCDao.getNameUsingRandom(), "NPC");
            characterCDao.add(NPC);
            model.put("NPC", NPC);
            CharacterC villain = new CharacterC(characterCDao.getNameUsingRandom(), "villain");
            model.put("villain", villain);
            wordDao.createRandomWord();
            Word word = wordDao.findById(wordDao.getAll().size());
            model.put("word", word);
            return new ModelAndView(model, "board1.hbs");
        }, new HandlebarsTemplateEngine());

        get("/character/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            int characterId = Integer.parseInt(req.params("id"));
            model.put("character", characterCDao.findById(characterId));
            return new ModelAndView(model, "character.hbs");
        }, new HandlebarsTemplateEngine());

        get("/gameover", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "death.hbs");
        }, new HandlebarsTemplateEngine());

        get("/game/highroad/1", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            if (characterCDao.findAllByName("Ghoul").size() < 2) {
                characterCDao.copyCharacter(characterCDao.findByName("Ghoul").getId());
            }
            List<CharacterC> characters = characterCDao.getAll();
            List<CharacterC> playerCharacters = new ArrayList<>();
            for (CharacterC character : characters) {
                try {
                    if (character.getCharClass().toLowerCase().equals("fighter") || character.getCharClass().toLowerCase().equals("red mage") && character.getCurrentHP() > 0) {
                        playerCharacters.add(character);
                    }
                } catch (NullPointerException ex) {
                    System.out.println(ex);
                }
            }
            List<CharacterC> enemies = characterCDao.findAllByName("Ghoul");
            List<CharacterC> battleCharacters = new ArrayList<>();
            battleCharacters.addAll(playerCharacters);
            battleCharacters.addAll(enemies);
            List<Integer> turnOrder = characterCDao.findTurnOrder(battleCharacters);
            System.out.println(turnOrder);
            System.out.println(characterCDao.findById(turnOrder.get(0)));
            if (!playerCharacters.contains(characterCDao.findById(turnOrder.get(0)))) {
                model.put("damage", true);
                characterCDao.computerInput(characterCDao.findById(turnOrder.get(0)), playerCharacters);
                characterCDao.updateAttacked(turnOrder.get(0));
                if (!playerCharacters.contains(characterCDao.findById(turnOrder.get(1)))) {
                    characterCDao.computerInput(characterCDao.findById(turnOrder.get(1)), playerCharacters);
                    characterCDao.updateAttacked(turnOrder.get(1));
                }
                turnOrder = characterCDao.findTurnOrder(battleCharacters);
            }

            System.out.println(turnOrder);
            model.put("enemies", enemies);
            for (int i = 0; i < playerCharacters.size(); i++) {
                System.out.println(turnOrder.get(i));
                if (playerCharacters.contains(characterCDao.findById(turnOrder.get(i))) && playerCharacters.get(i).getCurrentHP() > 0) {
                    model.put("currentPC", characterCDao.findById(turnOrder.get(i)));
                    if (characterCDao.getAllSpellsForACharacter(turnOrder.get(i)).size() > 0){
                        model.put("spells", characterCDao.getAllSpellsForACharacter(turnOrder.get(i)));
                    }
                    break;
                }
            }
            return new ModelAndView(model, "highroad1.hbs");
        }, new HandlebarsTemplateEngine());

        post("/game/highroad/1/attack/pc/:pcId/enemy/:enemyId", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<CharacterC> characters = characterCDao.getAll();
            List<CharacterC> playerCharacters = new ArrayList<>();
            for (CharacterC character : characters) {
                try {
                    if (character.getCharClass().toLowerCase().equals("fighter") || character.getCharClass().toLowerCase().equals("red mage") && character.getCurrentHP() > 0) {
                        playerCharacters.add(character);
                    }
                } catch (NullPointerException ex) {
                    System.out.println(ex);
                }
            }
            List<CharacterC> enemies = new ArrayList<>();
            for (CharacterC characterC : characterCDao.findAllByName("Ghoul")) {
                if (characterC.getCurrentHP() > 0) {
                    enemies.add(characterC);
                }
            }
            model.put("enemies", enemies);
            CharacterC enemy = characterCDao.findById(Integer.parseInt(request.params("enemyId")));
            CharacterC PC = characterCDao.findById(Integer.parseInt(request.params("pcId")));
            List<CharacterC> smallEnemy = new ArrayList<>();
            smallEnemy.add(enemy);
            characterCDao.userInput("attack", PC, smallEnemy);
            List<CharacterC> battleCharacters = new ArrayList<>();
            battleCharacters.addAll(playerCharacters);
            battleCharacters.addAll(enemies);
            List<CharacterC> notGone = new ArrayList<>();
//            for (CharacterC characterC : battleCharacters) {
//                try {
//                    if (!characterC.getAttacked().equals("true") && characterC.getCurrentHP() > 0) {
//                        notGone.add(characterC);
//                    }
//                } catch (NullPointerException ex) {
//                    System.out.println(ex);
//                    notGone.add(characterC);
//                }
//
//            }
            List<Integer> turnOrder = characterCDao.findTurnOrder(battleCharacters);
            if (turnOrder.size() == 0) {
                for (CharacterC c : battleCharacters) {
                    characterCDao.findById(c.getId()).setAttacked("false");
                }
            } else {
                if (!playerCharacters.contains(characterCDao.findById(turnOrder.get(0)))) {
                    model.put("damage", true);
                    characterCDao.computerInput(characterCDao.findById(turnOrder.get(0)), playerCharacters);
                    characterCDao.updateAttacked(turnOrder.get(0));
                    try {
                        if (battleCharacters.get(turnOrder.get(1)).getName().equals("ghoul")) {
                        characterCDao.computerInput(characterCDao.findById(turnOrder.get(1)), playerCharacters);
                        characterCDao.updateAttacked(turnOrder.get(1));
                        }
                    } catch (IndexOutOfBoundsException ex) {
                        System.out.println(ex);
                    }

                }
            }
            boolean win = true;
            for (CharacterC thisEnemy : enemies) {
                if (thisEnemy.getCurrentHP() > 0) {
                    win = false;
                }
            }
            if (win) {
                model.put("win", win);
            } else {
                CharacterC currentPC = characterCDao.findById(turnOrder.get(0));
                model.put("currentPC", currentPC);
            }
            return new ModelAndView(model, "highroad1-1.hbs");
        }, new HandlebarsTemplateEngine());


    }
}
