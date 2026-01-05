package model;

public class PokemonData {
	public static Pokemon pokemonPikachu() {
	Pokemon pikachu = new Pokemon("Pikachu", PokemonType.ELECTRIC, 103, 76, 94, 274);
    pikachu.addMove(new Move("Thunder", PokemonType.ELECTRIC, 110, 5));
    pikachu.addMove(new Move("Quick Attack", PokemonType.NORMAL, 40, 25));
    pikachu.addMove(new Move("Thunderbolt", PokemonType.ELECTRIC, 90, 15));
    pikachu.addMove(new Move("Iron Tail", PokemonType.STEEL, 100, 15));
    return pikachu;
	}
    
    public static Pokemon pokemonPidgey() {
    Pokemon pidgey = new Pokemon("Pidgey", PokemonType.FLYING, 85, 76, 67, 284);
    pidgey.addMove(new Move("Gust", PokemonType.FLYING, 40, 10));
    pidgey.addMove(new Move("Tackle", PokemonType.NORMAL, 35, 20));
    pidgey.addMove(new Move("Quick Attack", PokemonType.NORMAL, 40, 25));
    pidgey.addMove(new Move("Wing Attack", PokemonType.FLYING, 60, 35));
    return pidgey;
    }
    
    public static Pokemon pokemonCharizard() {
    Pokemon charizard = new Pokemon("Charizard", PokemonType.FIRE,200, 144, 155, 360);
    charizard.addMove(new Move("Dragon Breath", PokemonType.DRAGON, 60, 20));
    charizard.addMove(new Move("FlameThrower", PokemonType.FIRE, 100, 15));
    charizard.addMove(new Move("Flare Blitz", PokemonType.FIRE, 120, 5));
    charizard.addMove(new Move("Recover", PokemonType.NORMAL, 0, 5, true, 150));
    return charizard;
	}
    public static Pokemon pokemonSuicune() {
    Pokemon suicune = new Pokemon("Suicune", PokemonType.WATER, 166, 211, 211, 404);
    suicune.addMove(new Move("Blizzard", PokemonType.ICE, 110, 5));
    suicune.addMove(new Move("Water Pulse", PokemonType.WATER, 60, 20));
    suicune.addMove(new Move("Crunch", PokemonType.DARK, 80, 15));
    suicune.addMove(new Move("Surf", PokemonType.WATER, 90, 15));
    return suicune;
    }
    public static Pokemon pokemonGreninja() {
    Pokemon greninja = new Pokemon("Greninja", PokemonType.WATER, 189, 256, 265, 348);
    greninja.addMove(new Move("Quick Attack", PokemonType.NORMAL, 40, 25));
    greninja.addMove(new Move("Water Shuriken", PokemonType.WATER, 15, 20));
    greninja.addMove(new Move("Night Slash", PokemonType.DARK, 70, 15));
    greninja.addMove(new Move("Hydro Pump", PokemonType.WATER, 110, 5));
    return greninja;
    }
    public static Pokemon pokemonHaxorus() {
    Pokemon haxorus = new Pokemon("Haxorus", PokemonType.DRAGON, 269, 166, 130, 356);
    haxorus.addMove(new Move("Outrage", PokemonType.DRAGON, 120, 10));
    haxorus.addMove(new Move("Dragon Pulse", PokemonType.DRAGON, 85, 10));
    haxorus.addMove(new Move("Crunch", PokemonType.DARK, 80, 15));
    haxorus.addMove(new Move("Dragon Claw", PokemonType.DRAGON, 80, 15));
    return haxorus;
    }
    public static Pokemon pokemonGiratina() {
    Pokemon giratina = new Pokemon("Giratina", PokemonType.GHOST, 184, 220, 220, 504);
    giratina.addMove(new Move("Shadow Force", PokemonType.GHOST, 120, 5));
    giratina.addMove(new Move("Dragon Breath", PokemonType.DRAGON, 60, 20));
    giratina.addMove(new Move("Dragon Claw", PokemonType.DRAGON, 80, 15));
    giratina.addMove(new Move("Shadow Claw", PokemonType.GHOST, 70, 15));
    return giratina;
    }
    public static Pokemon pokemonGengar() {
    Pokemon gengar = new Pokemon("Gengar", PokemonType.GHOST, 238, 112, 139, 324);
    gengar.addMove(new Move("Phantom Force", PokemonType.GHOST, 90, 10));
    gengar.addMove(new Move("Sludge Wave", PokemonType.POISON, 95, 10));
    gengar.addMove(new Move("Dark Pulse", PokemonType.DARK, 80, 15));
    gengar.addMove(new Move("Shadow Ball", PokemonType.GHOST, 80, 15));
    return gengar;
    }
    public static Pokemon pokemonSteelix() {
    Pokemon steelix = new Pokemon("Steelix", PokemonType.STEEL, 157, 364, 121, 354);
    steelix.addMove(new Move("Iron Head", PokemonType.STEEL, 80, 15));
    steelix.addMove(new Move("Iron Tail", PokemonType.STEEL, 100, 15));
    steelix.addMove(new Move("Rock Slide", PokemonType.ROCK, 75, 10));
    steelix.addMove(new Move("Crunch", PokemonType.DARK, 80, 15));
    return steelix;
    }
    public static Pokemon pokemonMagearna() {
    Pokemon magearna = new Pokemon("Magearna", PokemonType.STEEL, 238, 211, 211, 364);
    magearna.addMove(new Move("Flash Canon", PokemonType.STEEL, 80, 10));
    magearna.addMove(new Move("Psy Shock", PokemonType.PSYCHIC, 80, 10));
    magearna.addMove(new Move("Aurora Beam", PokemonType.ICE, 65, 20));
    magearna.addMove(new Move("Fleur Canon", PokemonType.FAIRY, 130, 5));
    return magearna;
    }
    public static Pokemon pokemonSerperior() {
    Pokemon serperior = new Pokemon("Serperior", PokemonType.GRASS, 139, 175, 175, 354);
    serperior.addMove(new Move("Leaf Storm", PokemonType.GRASS, 130, 5));
    serperior.addMove(new Move("Giga Drain", PokemonType.GRASS, 75, 10));
    serperior.addMove(new Move("Leaf Blade", PokemonType.GRASS, 90, 15));
    serperior.addMove(new Move("Slam", PokemonType.NORMAL, 80, 20));
    return serperior;
    }
    public static Pokemon pokemonSceptile() {
    Pokemon sceptile = new Pokemon("Sceptile", PokemonType.GRASS, 193, 121, 157, 344);
    sceptile.addMove(new Move("Leaf Storm", PokemonType.GRASS, 130, 5));
    sceptile.addMove(new Move("Giga Drain", PokemonType.GRASS, 75, 10));
    sceptile.addMove(new Move("Energy Ball", PokemonType.GRASS, 90, 15));
    sceptile.addMove(new Move("Throat Chop", PokemonType.DARK, 80, 15));
    return sceptile;
    }
    public static Pokemon pokemonVolcarona() {
    Pokemon volcarona = new Pokemon("Volcarona", PokemonType.BUG, 247, 121, 193, 374);
    volcarona.addMove(new Move("Heat Wave", PokemonType.FIRE, 95, 10));
    volcarona.addMove(new Move("Bug Buzz", PokemonType.BUG, 90, 10));
    volcarona.addMove(new Move("Hurricane", PokemonType.FLYING, 110, 10));
    volcarona.addMove(new Move("Fire Blast", PokemonType.FIRE, 110, 5));
    return volcarona;
    }
    public static Pokemon pokemonScizor() {
    Pokemon scizor = new Pokemon("Scizor", PokemonType.BUG, 238, 184, 148, 344);
    scizor.addMove(new Move("Iron Head", PokemonType.STEEL, 80, 15));
    scizor.addMove(new Move("X-Scissor", PokemonType.BUG, 80, 15));
    scizor.addMove(new Move("Brick Break", PokemonType.FIGHTING, 75, 15));
    scizor.addMove(new Move("U-turn", PokemonType.BUG, 70, 20));
    return scizor;
    }
    public static Pokemon pokemonVileplume() {
    Pokemon vileplume = new Pokemon("Vileplume", PokemonType.POISON, 202, 157, 166, 354);
    vileplume.addMove(new Move("Petal Dance", PokemonType.GRASS, 120, 10));
    vileplume.addMove(new Move("Acid", PokemonType.POISON, 40, 30));
    vileplume.addMove(new Move("Giga Drain", PokemonType.GRASS, 75, 10));
    vileplume.addMove(new Move("Sludge Wave", PokemonType.POISON, 95, 10));
    return vileplume;
    }
    public static Pokemon pokemonNidoking() {
    Pokemon nidoking = new Pokemon("Nidoking", PokemonType.POISON, 202, 157, 166, 354);
    nidoking.addMove(new Move("Mega Horn", PokemonType.BUG, 120, 10));
    nidoking.addMove(new Move("Sludge Bom", PokemonType.POISON, 90, 10));
    nidoking.addMove(new Move("Poison Tail", PokemonType.POISON, 50, 25));
    nidoking.addMove(new Move("Poison jab", PokemonType.POISON, 80, 20));
    return nidoking;
    }
    public static Pokemon pokemonMewtwo() {
    Pokemon mewtwo = new Pokemon("Mewtwo", PokemonType.PSYCHIC, 281, 166, 166, 416);
    mewtwo.addMove(new Move("Spystrike", PokemonType.PSYCHIC, 100, 10));
    mewtwo.addMove(new Move("Spychic", PokemonType.PSYCHIC, 90, 10));
    mewtwo.addMove(new Move("Fire Blast", PokemonType.FIRE, 110, 5));
    mewtwo.addMove(new Move("Aura Phere", PokemonType.FIGHTING, 80, 20));
    return mewtwo;
    }
    public static Pokemon pokemonJirachi() {
    Pokemon jirachi = new Pokemon("Jirachi", PokemonType.PSYCHIC, 184, 184, 184, 404);
    jirachi.addMove(new Move("Meteor Beam", PokemonType.PSYCHIC, 120, 10));
    jirachi.addMove(new Move("Spychic", PokemonType.PSYCHIC, 90, 10));
    jirachi.addMove(new Move("Doom Desire", PokemonType.STEEL, 140, 5));
    jirachi.addMove(new Move("Recover", PokemonType.NORMAL, 0, 5, true, 150));
    return jirachi;
    }
    public static Pokemon pokemonGyarados() {
    Pokemon gyarados = new Pokemon("Gyarados", PokemonType.FLYING, 229, 146, 184, 394);
    gyarados.addMove(new Move("Crunch", PokemonType.DARK, 80, 15));
    gyarados.addMove(new Move("Hurricane", PokemonType.FLYING, 110, 10));
    gyarados.addMove(new Move("Hydro Pump", PokemonType.WATER, 110, 5));
    gyarados.addMove(new Move("Hyper Beam", PokemonType.NORMAL, 150, 5));
    return gyarados;
    }
    public static Pokemon pokemonRayquaza() {
    Pokemon rayquaza = new Pokemon("Rayquaza", PokemonType.FLYING, 274, 166, 166, 414);
    rayquaza.addMove(new Move("Dragon Ascent", PokemonType.FLYING, 120, 5));
    rayquaza.addMove(new Move("Hurricane", PokemonType.FLYING, 110, 10));
    rayquaza.addMove(new Move("Outrage", PokemonType.DRAGON, 120, 10));
    rayquaza.addMove(new Move("Roost", PokemonType.NORMAL, 0, 5, true, 150));
    return rayquaza;
    }
    public static Pokemon pokemonZekrom() {
    Pokemon zekrom = new Pokemon("Zekrom", PokemonType.ELECTRIC, 274, 220, 184, 404);
    zekrom.addMove(new Move("Thunder", PokemonType.ELECTRIC, 110, 5));
    zekrom.addMove(new Move("Crunch", PokemonType.DARK, 80, 15));
    zekrom.addMove(new Move("Outrage", PokemonType.DRAGON, 120, 10));
    zekrom.addMove(new Move("Bolt Strike", PokemonType.ELECTRIC, 130, 5));
    return zekrom;
    }
    public static Pokemon pokemonReshiram() {
    Pokemon reshiram = new Pokemon("Reshiram", PokemonType.FIRE, 274, 184, 220, 404);
    reshiram.addMove(new Move("Fire Blast", PokemonType.FIRE, 110, 5));
    reshiram.addMove(new Move("Crunch", PokemonType.DARK, 80, 15));
    reshiram.addMove(new Move("Outrage", PokemonType.DRAGON, 120, 10));
    reshiram.addMove(new Move("Blue Flare", PokemonType.FIRE, 130, 5));
    return reshiram;
    }
    public static Pokemon pokemonKyurem() {
    Pokemon kuyrem = new Pokemon("Kuyrem", PokemonType.ICE, 238, 166, 166, 454);
    kuyrem.addMove(new Move("Blizzard", PokemonType.FIRE, 110, 5));
    kuyrem.addMove(new Move("Hyper Voice", PokemonType.NORMAL, 90, 10));
    kuyrem.addMove(new Move("Outrage", PokemonType.DRAGON, 120, 10));
    kuyrem.addMove(new Move("Glaciate", PokemonType.ICE, 65, 10));
    return kuyrem;
    }
    public static Pokemon pokemonSnorlax() {
    Pokemon snorlax = new Pokemon("Snorlax", PokemonType.NORMAL, 202, 121, 202, 524);
    snorlax.addMove(new Move("Body Slam", PokemonType.NORMAL, 85, 15));
    snorlax.addMove(new Move("Giga Impaact", PokemonType.NORMAL, 150, 5));
    snorlax.addMove(new Move("Crunch", PokemonType.DARK, 80, 15));
    snorlax.addMove(new Move("Hammer Arm", PokemonType.FIGHTING, 100, 10));
    return snorlax;
    }
    public static Pokemon pokemonArceus() {
    Pokemon arceus = new Pokemon("Arceus", PokemonType.NORMAL, 202, 121, 202, 524);
    arceus.addMove(new Move("Nature's Gift", PokemonType.NORMAL, 0, 5, true, 100));
    arceus.addMove(new Move("Spychic", PokemonType.PSYCHIC, 90, 10));
    arceus.addMove(new Move("Hyper Beam", PokemonType.NORMAL, 150, 5));
    arceus.addMove(new Move("Judgment", PokemonType.NORMAL, 100, 10));
    return arceus;
    }
    public static Pokemon pokemonLapras() {
    Pokemon lapras = new Pokemon("Lapras", PokemonType.ICE, 157, 148, 175, 464);
    lapras.addMove(new Move("Blizzard", PokemonType.FIRE, 110, 5));
    lapras.addMove(new Move("Hydro Pump", PokemonType.WATER, 110, 5));
    lapras.addMove(new Move("Body Slam", PokemonType.NORMAL, 85, 15));
    lapras.addMove(new Move("Ice Beam", PokemonType.ICE, 90, 10));
    return lapras;
    }
    public static Pokemon pokemonDrapion() {
    Pokemon drapion = new Pokemon("Drapion", PokemonType.DARK, 166, 202, 139, 344);
    drapion.addMove(new Move("Crunch", PokemonType.DARK, 80, 15));
    drapion.addMove(new Move("Poison jab", PokemonType.POISON, 80, 20));
    drapion.addMove(new Move("Dark Pulse", PokemonType.DARK, 80, 15));
    drapion.addMove(new Move("X-Scissor", PokemonType.BUG, 80, 15));
    return drapion;
    }
    public static Pokemon pokemonAbsol() {
    Pokemon absol = new Pokemon("Absol", PokemonType.DARK, 238, 112, 112, 334);
    absol.addMove(new Move("Quick Attack", PokemonType.NORMAL, 40, 25));
    absol.addMove(new Move("Night Slash", PokemonType.DARK, 70, 15));
    absol.addMove(new Move("Dark Pulse", PokemonType.DARK, 80, 15));
    absol.addMove(new Move("X-Scissor", PokemonType.BUG, 80, 15));
    return absol;
    }
    public static Pokemon pokemonTyranitar() {
    Pokemon tyranitar = new Pokemon("Tyranitar", PokemonType.ROCK, 245, 110, 100, 404);
    tyranitar.addMove(new Move("Crunch", PokemonType.DARK, 80, 15));
    tyranitar.addMove(new Move("Giga Impaact", PokemonType.NORMAL, 150, 5));
    tyranitar.addMove(new Move("Earthquake", PokemonType.GROUND, 100, 10));
    tyranitar.addMove(new Move("Stone Edge", PokemonType.ROCK, 100, 5));
    return tyranitar;
    }
    public static Pokemon pokemonArcheops() {
    Pokemon archeops = new Pokemon("Archeops", PokemonType.ROCK, 256, 121, 121, 354);
    archeops.addMove(new Move("Crunch", PokemonType.DARK, 80, 15));
    archeops.addMove(new Move("Rock Slide", PokemonType.ROCK, 75, 10));
    archeops.addMove(new Move("U-turn", PokemonType.BUG, 70, 20));
    archeops.addMove(new Move("Pluck", PokemonType.FLYING, 60, 20));
    return archeops;
    }
    public static Pokemon pokemonMachamp() {
    Pokemon machamp = new Pokemon("Machamp", PokemonType.FIGHTING, 238, 148, 157, 384);
    machamp.addMove(new Move("Close Combat", PokemonType.FIGHTING, 120, 5));
    machamp.addMove(new Move("Stone Edge", PokemonType.ROCK, 100, 5));
    machamp.addMove(new Move("Brick Break", PokemonType.FIGHTING, 75, 15));
    machamp.addMove(new Move("Dynamic Punch", PokemonType.FIGHTING, 100, 5));
    return machamp;
    }
    public static Pokemon pokemonLucario() {
    Pokemon machamp = new Pokemon("Machamp", PokemonType.FIGHTING, 202, 130, 130, 344);
    machamp.addMove(new Move("Aura Phere", PokemonType.FIGHTING, 80, 20));
    machamp.addMove(new Move("Extreme Speed", PokemonType.NORMAL, 80, 5));
    machamp.addMove(new Move("Earthquake", PokemonType.GROUND, 100, 10));
    machamp.addMove(new Move("Close Combat", PokemonType.FIGHTING, 120, 5));
    return machamp;
    }
    public static Pokemon pokemonGolem() {
    Pokemon golem = new Pokemon("Golem", PokemonType.GROUND, 220, 238, 121, 364);
    golem.addMove(new Move("Bulldoze", PokemonType.GROUND, 60, 20));
    golem.addMove(new Move("Brick Break", PokemonType.FIGHTING, 75, 15));
    golem.addMove(new Move("Earthquake", PokemonType.GROUND, 100, 10));
    golem.addMove(new Move("Stone Edge", PokemonType.ROCK, 100, 5));
    return golem;
    }
    public static Pokemon pokemonRhydon() {
    Pokemon rhydon = new Pokemon("Rhydon", PokemonType.GROUND, 220, 238, 121, 364);
    rhydon.addMove(new Move("Mega Horn", PokemonType.BUG, 120, 10));
    rhydon.addMove(new Move("Hammer Arm", PokemonType.FIGHTING, 100, 10));
    rhydon.addMove(new Move("Earthquake", PokemonType.GROUND, 100, 10));
    rhydon.addMove(new Move("Stone Edge", PokemonType.ROCK, 100, 5));
    return rhydon;
    }
    public static Pokemon pokemonDiancie() {
    Pokemon diancie = new Pokemon("Diancie", PokemonType.FAIRY, 184, 274, 274, 304);
    diancie.addMove(new Move("Moon Blast", PokemonType.FAIRY, 95, 15));
    diancie.addMove(new Move("Dazzling Gleam", PokemonType.FAIRY, 80, 10));
    diancie.addMove(new Move("Flash Canon", PokemonType.STEEL, 80, 10));
    diancie.addMove(new Move("Stone Edge", PokemonType.ROCK, 100, 5));
    return diancie;
    }
    public static Pokemon pokemonGardevoir() {
    Pokemon gardevoir = new Pokemon("Gardevoir", PokemonType.FAIRY, 184, 274, 274, 304);
    gardevoir.addMove(new Move("Moon Blast", PokemonType.FAIRY, 95, 15));
    gardevoir.addMove(new Move("Dazzling Gleam", PokemonType.FAIRY, 80, 10));
    gardevoir.addMove(new Move("Flash Canon", PokemonType.STEEL, 80, 10));
    gardevoir.addMove(new Move("Spychic", PokemonType.PSYCHIC, 90, 10));
    return gardevoir;
    }
}
