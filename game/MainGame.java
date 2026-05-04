package game;

import controller.BattleController;
import model.*;
import view.MenuView;
import view.PokemonSelectionView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainGame {
    public static void main(String[] args) {
        MenuView menu = new MenuView();
        
        menu.addStartButtonListener(e -> {
            showPokemonSelection();
            menu.closeMenu();
        });
        
        menu.addExitButtonListener(e -> {
            System.exit(0);
        });
    }
    
    private static void showPokemonSelection() {
        PokemonSelectionView selection = new PokemonSelectionView();
        
        selection.addStartButtonListener(e -> {
            startGame(selection.getSelectedPokemons());
            selection.closeSelection();
        });
    }
    
    private static void startGame(java.util.List<Pokemon> playerPokemons) {
        // Tạo team cho player với pokemon đã chọn
        PokemonTeam playerTeam = new PokemonTeam();
        for (Pokemon pokemon : playerPokemons) {
            playerTeam.addPokemon(pokemon);
        }
        
        // Tạo team cho AI với 4 pokemon random
        PokemonTeam aiTeam = new PokemonTeam();
        List<Pokemon> randomAIPokemons = getRandomPokemons(4);
        for (Pokemon pokemon : randomAIPokemons) {
            aiTeam.addPokemon(pokemon);
        }

        BattleController controller = new BattleController(playerTeam, aiTeam);
        controller.startBattle();
    }
    
    private static List<Pokemon> getRandomPokemons(int count) {
        // Danh sách tất cả pokemon
        List<Pokemon> allPokemons = new ArrayList<>();
        allPokemons.add(PokemonData.pokemonPikachu());
        allPokemons.add(PokemonData.pokemonPidgey());
        allPokemons.add(PokemonData.pokemonCharizard());
        allPokemons.add(PokemonData.pokemonSuicune());
        allPokemons.add(PokemonData.pokemonGreninja());
        allPokemons.add(PokemonData.pokemonHaxorus());
        allPokemons.add(PokemonData.pokemonGiratina());
        allPokemons.add(PokemonData.pokemonGengar());
        allPokemons.add(PokemonData.pokemonSteelix());
        allPokemons.add(PokemonData.pokemonMagearna());
        allPokemons.add(PokemonData.pokemonSerperior());
        allPokemons.add(PokemonData.pokemonSceptile());
        allPokemons.add(PokemonData.pokemonVolcarona());
        allPokemons.add(PokemonData.pokemonScizor());
        allPokemons.add(PokemonData.pokemonVileplume());
        allPokemons.add(PokemonData.pokemonNidoking());
        allPokemons.add(PokemonData.pokemonMewtwo());
        allPokemons.add(PokemonData.pokemonJirachi());
        allPokemons.add(PokemonData.pokemonGyarados());
        allPokemons.add(PokemonData.pokemonRayquaza());
        allPokemons.add(PokemonData.pokemonZekrom());
        allPokemons.add(PokemonData.pokemonReshiram());
        allPokemons.add(PokemonData.pokemonKyurem());
        allPokemons.add(PokemonData.pokemonSnorlax());
        allPokemons.add(PokemonData.pokemonArceus());
        allPokemons.add(PokemonData.pokemonLapras());
        allPokemons.add(PokemonData.pokemonDrapion());
        allPokemons.add(PokemonData.pokemonAbsol());
        allPokemons.add(PokemonData.pokemonTyranitar());
        allPokemons.add(PokemonData.pokemonArcheops());
        allPokemons.add(PokemonData.pokemonMachamp());
        allPokemons.add(PokemonData.pokemonLucario());
        allPokemons.add(PokemonData.pokemonGolem());
        allPokemons.add(PokemonData.pokemonRhydon());
        allPokemons.add(PokemonData.pokemonGardevoir());
        
        // Xáo trộn danh sách
        Collections.shuffle(allPokemons);
        
        // Lấy 4 pokemon đầu tiên từ danh sách đã xáo trộn
        return allPokemons.subList(0, count);
    }
}
