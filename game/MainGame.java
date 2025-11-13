package game;

import controller.BattleController;
import model.*;

public class MainGame {
    public static void main(String[] args) {
        Pokemon player = PokemonData.pokemonGreninja();
        Pokemon ai = PokemonData.pokemonCharizard();

        BattleController controller = new BattleController(player, ai);
        controller.startBattle();
    }
}
