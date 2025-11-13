package game;

import controller.BattleController;
import model.*;

public class MainGame {
    public static void main(String[] args) {
        // Tạo team cho player
        PokemonTeam playerTeam = new PokemonTeam();
        playerTeam.addPokemon(PokemonData.pokemonGreninja());
        playerTeam.addPokemon(PokemonData.pokemonCharizard());
        playerTeam.addPokemon(PokemonData.pokemonPikachu());
        playerTeam.addPokemon(PokemonData.pokemonSuicune());
        
        // Tạo team cho AI
        PokemonTeam aiTeam = new PokemonTeam();
        aiTeam.addPokemon(PokemonData.pokemonCharizard());
        aiTeam.addPokemon(PokemonData.pokemonPidgey());
        aiTeam.addPokemon(PokemonData.pokemonHaxorus());
        aiTeam.addPokemon(PokemonData.pokemonGreninja());

        BattleController controller = new BattleController(playerTeam, aiTeam);
        controller.startBattle();
    }
}
