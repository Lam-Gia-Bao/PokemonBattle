package controller;

import model.*;
import view.BattleView;

public class BattleController {
    private final PokemonTeam playerTeam;
    private final PokemonTeam aiTeam;
    private final BattleView view;

    public BattleController(PokemonTeam playerTeam, PokemonTeam aiTeam) {
        this.playerTeam = playerTeam;
        this.aiTeam = aiTeam;
        this.view = new BattleView(this, playerTeam, aiTeam);
    }

    // Bắt đầu trận đấu
    public void startBattle() {
        view.setVisible(true);
    }
    
    //Lượt của player
    public void playerMove(int index) {
        Pokemon player = playerTeam.getCurrentPokemon();
        Pokemon ai = aiTeam.getCurrentPokemon();
        
        //Kiểm tra ngoại lệ (nếu pokemon không hợp lệ hoặc tất cả pokemon đã hạ gục) -> không làm gì cả
        if (player == null || ai == null) return;
        if (index < 0 || index >= player.getMoves().size()) 
            return;
        
        view.disableAllButtons();
        
        Move move = player.getMoves().get(index);
        int dmg = player.attack(ai, move);
        view.queueUsingMoveMessage(player, move.getName());
        view.queueDamageMessage(dmg, move.getName());
        view.updateHPBars();

        if (ai.isFainted()) {
            view.queuePokemonFaintedMessage(ai);
            // Kiểm tra xem AI còn pokemon khác không
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
        aiMove();
    }
    
    //Khi player đổi pokemon, player sẽ bị mất lượt tấn công và sẽ đến lượt AI tấn công
    public void switchPokemon(int pokemonIndex) {
        if (playerTeam.switchPokemon(pokemonIndex)) {
            view.disableAllButtons();
            Pokemon nextPokemon = playerTeam.getCurrentPokemon();
            Pokemon ai = aiTeam.getCurrentPokemon();
            view.updateUI(nextPokemon, ai);
            view.queuePlayerPokemonSelectedMessage(nextPokemon);
            view.queuePokemonEnterMessage(nextPokemon);
            
            aiMove();
        } else {
            view.showCannotSwitchMessage();
        }
    }

    //Lượt của AI
    private void aiMove() {
        Pokemon player = playerTeam.getCurrentPokemon();
        Pokemon ai = aiTeam.getCurrentPokemon();

        Move aiMove = AI.chooseBestMove(ai, player);
        int aiDmg = ai.attack(player, aiMove);

        view.queueUsingMoveMessage(ai, aiMove.getName());
        view.queueDamageMessage(aiDmg, aiMove.getName());
        view.updateHPBars();

        if (player.isFainted()) {
            view.queuePokemonFaintedMessage(player);
            if (playerTeam.switchToNextActivePokemon()) {
                Pokemon nextPlayerPokemon = playerTeam.getCurrentPokemon();
                view.updateUI(nextPlayerPokemon, ai);
                view.queuePokemonPraiseMessage(player);
                view.queuePokemonEnterMessage(nextPlayerPokemon);
                view.startMessageQueue(() -> view.enableMoveButtons());
            } else {
                view.queueLoseMessage();
                view.startMessageQueue(() -> view.disableAllButtons());
            }
        } else {
            view.startMessageQueue(() -> view.enableMoveButtons());
        }
    }
    
    public PokemonTeam getPlayerTeam() {
        return playerTeam;
    }
    
    public PokemonTeam getAiTeam() {
        return aiTeam;
    }
}
