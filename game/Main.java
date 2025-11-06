package game;

import controller.BattleController;
import model.*;

public class Main {
    public static void main(String[] args) {
        Pokemon pikachu = new Pokemon("Pikachu", PokemonType.ELECTRIC, 83, 55, 40, 100);
        pikachu.addMove(new Move("Thunder Shock", PokemonType.ELECTRIC, 40, 10));
        pikachu.addMove(new Move("Quick Attack", PokemonType.NORMAL, 30, 20));
        pikachu.addMove(new Move("Thunderbolt", PokemonType.ELECTRIC, 60, 5));
        pikachu.addMove(new Move("Iron Tail", PokemonType.NORMAL, 50, 15));

        Pokemon pidgey = new Pokemon("Pidgey", PokemonType.FLYING, 60, 45, 35, 100);
        pidgey.addMove(new Move("Gust", PokemonType.FLYING, 40, 10));
        pidgey.addMove(new Move("Tackle", PokemonType.NORMAL, 35, 20));
        pidgey.addMove(new Move("Quick Attack", PokemonType.NORMAL, 30, 20));
        pidgey.addMove(new Move("Wing Attack", PokemonType.FLYING, 50, 10));

        BattleController controller = new BattleController(pikachu, pidgey);
        controller.startBattle();
    }
}
