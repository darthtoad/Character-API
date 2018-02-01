SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS characters (
    id int PRIMARY KEY auto_increment,
    name VARCHAR,
    description VARCHAR,
    level INTEGER,
    experience INTEGER,
    HP INTEGER,
    currentHP INTEGER,
    defense INTEGER,
    magicDefense INTEGER,
    strength INTEGER,
    MP INTEGER,
    currentMP INTEGER,
    magic INTEGER,
    dexterity INTEGER,
    charClass VARCHAR,
    attacked VARCHAR
);

CREATE TABLE IF NOT EXISTS characters_spells (
    id int PRIMARY KEY auto_increment,
    characterId VARCHAR,
    spellId VARCHAR
);

CREATE TABLE IF NOT EXISTS characters_equipment (
    id int PRIMARY KEY auto_increment,
    characterId VARCHAR,
    equipmentId VARCHAR
);

CREATE TABLE IF NOT EXISTS characters_effects (
    id int PRIMARY KEY auto_increment,
    characterId VARCHAR,
    effectId VARCHAR
);

CREATE TABLE IF NOT EXISTS spells (
    id int PRIMARY KEY auto_increment,
    name VARCHAR,
    description VARCHAR,
    damage INTEGER,
    MP INTEGER,
    effects VARCHAR
);

CREATE TABLE IF NOT EXISTS equipment (
    id int PRIMARY KEY auto_increment,
    name VARCHAR,
    description VARCHAR,
    strength INTEGER,
    magic INTEGER,
    dexterity INTEGER,
    defense INTEGER,
    magicDefense INTEGER
);

CREATE TABLE IF NOT EXISTS effects (
    id int PRIMARY KEY auto_increment,
    name VARCHAR,
    description VARCHAR,
    HP INTEGER,
    currentHP INTEGER,
    defense INTEGER,
    magicDefense INTEGER,
    strength INTEGER,
    MP INTEGER,
    currentMP INTEGER,
    magic INTEGER,
    dexterity INTEGER,
    other VARCHAR
);

CREATE TABLE IF NOT EXISTS effects_spells (
    id int PRIMARY KEY auto_increment,
    effectId INTEGER,
    spellId INTEGER
);

CREATE TABLE IF NOT EXISTS locations (
    id int PRIMARY KEY auto_increment,
    name VARCHAR,
    description VARCHAR
);

CREATE TABLE IF NOT EXISTS words (
    id int PRIMARY KEY auto_increment,
    name VARCHAR
);

CREATE TABLE IF NOT EXISTS items (
    id int PRIMARY KEY auto_increment,
    currentHP INTEGER,
    currentMP INTEGER
);
  
CREATE TABLE IF NOT EXISTS characters_items (
    id int PRIMARY KEY auto_increment,
    itemId INTEGER,
    characterCId INTEGER
);