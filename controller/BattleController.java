package controller;

import model.*;
import view.BattleView;

public class BattleController {
    private PokemonTeam playerTeam;
    private PokemonTeam aiTeam;
    private BattleView view;

    public BattleController(PokemonTeam playerTeam, PokemonTeam aiTeam) {
        this.playerTeam = playerTeam;
        this.aiTeam = aiTeam;
        this.view = new BattleView(this, playerTeam, aiTeam);
    }

    public void startBattle() {
        view.setVisible(true);
    }

    public void playerMove(int index) {
        Pokemon player = playerTeam.getCurrentPokemon();
        Pokemon ai = aiTeam.getCurrentPokemon();
        
        if (player == null || ai == null) return;
        if (index < 0 || index >= player.getMoves().size()) 
            return;
        
        Move move = player.getMoves().get(index);
        int dmg = player.attack(ai, move);
        view.showMessage(player.getName() + " used " + move.getName() + "! It dealt " + dmg + " damage!");
        view.updateHPBars();

        if (ai.isFainted()) {
            // Kiểm tra xem AI có pokemon khác không
            if (aiTeam.switchToNextActivePokemon()) {
                Pokemon nextAiPokemon = aiTeam.getCurrentPokemon();
                view.updateUI(playerTeam.getCurrentPokemon(), nextAiPokemon);
                view.showMessage(ai.getName() + " fainted! Opponent sends out " + nextAiPokemon.getName() + "!");
            } else {
                view.showMessage(ai.getName() + " fainted! You win!");
                view.disableAllButtons();
            }
            return;
        }

        // AI phản công
        Move aiMove = AI.chooseBestMove(ai, player);
        int aiDmg = ai.attack(player, aiMove);
        view.showMessage(ai.getName() + " used " + aiMove.getName() + "! It dealt " + aiDmg + " damage!");
        view.updateHPBars();

        if (player.isFainted()) {
            // Kiểm tra xem player có pokemon khác không
            if (playerTeam.switchToNextActivePokemon()) {
                Pokemon nextPlayerPokemon = playerTeam.getCurrentPokemon();
                view.updateUI(nextPlayerPokemon, ai);
                view.showMessage(player.getName() + " fainted! Go " + nextPlayerPokemon.getName() + "!");
                view.enableMoveButtons();
            } else {
                view.showMessage(player.getName() + " fainted! You lost...");
                view.disableAllButtons();
            }
        }
    }
    
    public void switchPokemon(int pokemonIndex) {
        if (playerTeam.switchPokemon(pokemonIndex)) {
            Pokemon nextPokemon = playerTeam.getCurrentPokemon();
            Pokemon ai = aiTeam.getCurrentPokemon();
            view.updateUI(nextPokemon, ai);
            view.showMessage("Go " + nextPokemon.getName() + "!");
            view.updateHPBars();
            
            // AI tấn công
            Move aiMove = AI.chooseBestMove(ai, nextPokemon);
            int aiDmg = ai.attack(nextPokemon, aiMove);
            view.showMessage(ai.getName() + " used " + aiMove.getName() + "! It dealt " + aiDmg + " damage!");
            view.updateHPBars();
            
            if (nextPokemon.isFainted()) {
                if (playerTeam.switchToNextActivePokemon()) {
                    Pokemon nextPlayerPokemon = playerTeam.getCurrentPokemon();
                    view.updateUI(nextPlayerPokemon, ai);
                    view.showMessage(nextPokemon.getName() + " fainted! Go " + nextPlayerPokemon.getName() + "!");
                    view.enableMoveButtons();
                } else {
                    view.showMessage(nextPokemon.getName() + " fainted! You lost...");
                    view.disableAllButtons();
                }
            }
        } else {
            view.showMessage("Can't switch to that Pokémon!");
        }
    }
    
    public PokemonTeam getPlayerTeam() {
        return playerTeam;
    }
    
    public PokemonTeam getAiTeam() {
        return aiTeam;
    }
}
