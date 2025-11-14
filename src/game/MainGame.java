package game;

import controller.BattleController;
import model.*;

public class MainGame {
    public static void main(String[] args) {
        // Tạo team cho player
        PokemonTeam playerTeam = new PokemonTeam();
        playerTeam.addPokemon(PokemonData.pokemonGreninja());
        playerTeam.addPokemon(PokemonData.pokemonRayquaza());
        playerTeam.addPokemon(PokemonData.pokemonPikachu());
        playerTeam.addPokemon(PokemonData.pokemonArceus());
        
        // Tạo team cho AI
        PokemonTeam aiTeam = new PokemonTeam();
        aiTeam.addPokemon(PokemonData.pokemonCharizard());
        aiTeam.addPokemon(PokemonData.pokemonGiratina());
        aiTeam.addPokemon(PokemonData.pokemonHaxorus());
        aiTeam.addPokemon(PokemonData.pokemonJirachi());

        BattleController controller = new BattleController(playerTeam, aiTeam);
        controller.startBattle();
    }
}
