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

    // Bắt đầu trận đấu
    public void startBattle() {
        view.setVisible(true);
    }
    
    public void playerMove(int index) {
        Pokemon player = playerTeam.getCurrentPokemon();
        Pokemon ai = aiTeam.getCurrentPokemon();
        
        if (player == null || ai == null) return;
        if (index < 0 || index >= player.getMoves().size()) 
            return;
        
        view.disableAllButtons();
        
        Move move = player.getMoves().get(index);
        int dmg = player.attack(ai, move);
        view.queueUsingMoveMessage(player, move.getName());
        view.queueDamageMessage(dmg);
        view.updateHPBars();

        if (ai.isFainted()) {
            view.queuePokemonFaintedMessage(ai);
            // Kiểm tra xem AI có pokemon khác không
            if (aiTeam.switchToNextActivePokemon()) {
                Pokemon nextAiPokemon = aiTeam.getCurrentPokemon();
                view.updateUI(playerTeam.getCurrentPokemon(), nextAiPokemon);
                view.queueAIPokemonSelectedMessage(nextAiPokemon);
                view.startMessageQueue(() -> {
                    view.enableMoveButtons();
                });
            } else {
                view.queueWinMessage();
                view.startMessageQueue(() -> {
                    view.disableAllButtons();
                });
            }
            return;
        }

        // AI phản công
        Move aiMove = AI.chooseBestMove(ai, player);
        int aiDmg = ai.attack(player, aiMove);
        view.queueUsingMoveMessage(ai, aiMove.getName());
        view.queueDamageMessage(aiDmg);
        view.updateHPBars();

        if (player.isFainted()) {
            view.queuePokemonFaintedMessage(player);
            // Kiểm tra xem player có pokemon khác không
            if (playerTeam.switchToNextActivePokemon()) {
                Pokemon nextPlayerPokemon = playerTeam.getCurrentPokemon();
                view.updateUI(nextPlayerPokemon, ai);
                view.queuePokemonPraiseMessage(player);
                view.queuePokemonEnterMessage(nextPlayerPokemon);
                view.startMessageQueue(() -> {
                    view.enableMoveButtons();
                });
            } else {
                view.queueLoseMessage();
                view.startMessageQueue(() -> {
                    view.disableAllButtons();
                });
            }
        } else {
            view.startMessageQueue(() -> {
                view.enableMoveButtons();
            });
        }
    }
    
    public void switchPokemon(int pokemonIndex) {
        if (playerTeam.switchPokemon(pokemonIndex)) {
            view.disableAllButtons();
            Pokemon nextPokemon = playerTeam.getCurrentPokemon();
            Pokemon ai = aiTeam.getCurrentPokemon();
            view.updateUI(nextPokemon, ai);
            view.queuePlayerPokemonSelectedMessage(nextPokemon);
            view.queuePokemonEnterMessage(nextPokemon);
            
            // AI tấn công
            Move aiMove = AI.chooseBestMove(ai, nextPokemon);
            int aiDmg = ai.attack(nextPokemon, aiMove);
            view.queueUsingMoveMessage(ai, aiMove.getName());
            view.queueDamageMessage(aiDmg);
            view.updateHPBars();
            
            if (nextPokemon.isFainted()) {
                view.queuePokemonFaintedMessage(nextPokemon);
                if (playerTeam.switchToNextActivePokemon()) {
                    Pokemon nextPlayerPokemon = playerTeam.getCurrentPokemon();
                    view.updateUI(nextPlayerPokemon, ai);
                    view.queuePokemonPraiseMessage(nextPokemon);
                    view.queuePokemonEnterMessage(nextPlayerPokemon);
                    view.startMessageQueue(() -> {
                        view.enableMoveButtons();
                    });
                } else {
                    view.queueLoseMessage();
                    view.startMessageQueue(() -> {
                        view.disableAllButtons();
                    });
                }
            } else {
                view.startMessageQueue(() -> {
                    view.enableMoveButtons();
                });
            }
        } else {
            view.showCannotSwitchMessage();
        }
    }
    
    public PokemonTeam getPlayerTeam() {
        return playerTeam;
    }
    
    public PokemonTeam getAiTeam() {
        return aiTeam;
    }
}
