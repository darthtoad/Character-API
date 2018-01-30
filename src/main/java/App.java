import Dao.Sql2oCharacterCDao;
import Dao.Sql2oEffectDao;
import Dao.Sql2oEquipmentDao;
import Dao.Sql2oSpellDao;
import com.google.gson.Gson;
import models.CharacterC;
import models.Effect;
import models.Equipment;
import models.Spell;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        Sql2oEffectDao effectDao;
        Sql2oSpellDao spellDao;
        Sql2oEquipmentDao equipmentDao;
        Sql2oCharacterCDao characterCDao;
        Connection connection;
        Gson gson = new Gson();

        String connectionString = "jdbc:h2:~/characterAPI.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");

        effectDao = new Sql2oEffectDao(sql2o);
        spellDao = new Sql2oSpellDao(sql2o);
        equipmentDao = new Sql2oEquipmentDao(sql2o);
        characterCDao = new Sql2oCharacterCDao(sql2o);

        connection = sql2o.open();

        //API

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/game", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            return new ModelAndView(model, "hud.hbs");
        }, new HandlebarsTemplateEngine());

        post("/characters/new", "application/json", (request, response) -> {
            CharacterC characterC = gson.fromJson(request.body(), CharacterC.class);
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

        get("spells/:id/effects", "application/json", (request, response) -> {
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

        //FRONTEND ROUTING
        get("/character/new", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "new_character.hbs");
        }, new HandlebarsTemplateEngine());

        post("/character/new", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            CharacterC character = new CharacterC(req.queryParams("name"), req.queryParams("description"), req.queryParams("charClass"), req.queryParams("gender"));
            characterCDao.add(character);
            model.put("character", character);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        get("/character/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            int characterId = Integer.parseInt(req.params("id"));
            model.put("character", characterCDao.findById(characterId));
            return new ModelAndView(model, "character.hbs");
        }, new HandlebarsTemplateEngine());




    }
}
