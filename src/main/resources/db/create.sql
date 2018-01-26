SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS characters (
    int id PRIMARY KEY auto_increment,
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
    spells VARCHAR,
    equipment VARCHAR,
    effects VARCHAR
);

CREATE TABLE IF NOT EXISTS characters_spells (
    int id PRIMARY KEY auto_increment,
    characterId VARCHAR,
    spellId VARCHAR
);

CREATE TABLE IF NOT EXISTS characters_equipment (
    int id PRIMARY KEY auto_increment,
    characterId VARCHAR,
    spellId VARCHAR
)

CREATE TABLE IF NOT EXISTS characters_effects (
    int id PRIMARY KEY auto_increment,
    characterId VARCHAR,
    effectId VARCHAR
)

CREATE TABLE IF NOT EXISTS spells (
    int id PRIMARY KEY auto_increment,
    name VARCHAR,
    description VARCHAR,
    damage INTEGER,
    effects VARCHAR
);

CREATE TABLE IF NOT EXISTS equipment (
    int id PRIMARY KEY auto_increment,
    name VARCHAR,
    description VARCHAR,
    strength INTEGER,
    magic INTEGER,
    dexterity INTEGER,
    defense INTEGER,
    magicDefense INTEGER
);

CREATE TABLE IF NOT EXISTS effects (
    int id PRIMARY KEY auto_increment,
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