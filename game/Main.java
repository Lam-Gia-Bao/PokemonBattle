package game;

import controller.BattleController;
import model.*;

public class Main {
    public static void main(String[] args) {
        Pokemon player = PokemonStat.pokemonReshiram();
        Pokemon ai = PokemonStat.pokemonMewtwo();

        BattleController controller = new BattleController(player, ai);
        controller.startBattle();
    }
}
