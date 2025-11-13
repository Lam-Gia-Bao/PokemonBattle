package game;

import controller.BattleController;
import model.*;

public class MainGame {
    public static void main(String[] args) {
        Pokemon player = PokemonData.pokemonReshiram();
        Pokemon ai = PokemonData.pokemonMewtwo();

        BattleController controller = new BattleController(player, ai);
        controller.startBattle();
    }
}
