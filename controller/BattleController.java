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
        
        view.disableAllButtons();
        
        Move move = player.getMoves().get(index);
        int dmg = player.attack(ai, move);
        view.addMessageToQueue(player.getName() + " đã sử dụng tuyệt chiêu " + move.getName());
        view.addMessageToQueue("Đã gây " + dmg + " sát thương!");
        view.updateHPBars();

        if (ai.isFainted()) {
            view.addMessageToQueue(ai.getName() + " đã bị hạ gục!");
            // Kiểm tra xem AI có pokemon khác không
            if (aiTeam.switchToNextActivePokemon()) {
                Pokemon nextAiPokemon = aiTeam.getCurrentPokemon();
                view.updateUI(playerTeam.getCurrentPokemon(), nextAiPokemon);
                view.addMessageToQueue("AI đã chọn " + nextAiPokemon.getName());
                view.startMessageQueue(() -> {
                    view.enableMoveButtons();
                });
            } else {
                view.addMessageToQueue("Bạn đã chiến thắng!");
                view.startMessageQueue(() -> {
                    view.disableAllButtons();
                });
            }
            return;
        }

        // AI phản công
        Move aiMove = AI.chooseBestMove(ai, player);
        int aiDmg = ai.attack(player, aiMove);
        view.addMessageToQueue(ai.getName() + " đã sử dụng tuyệt chiêu " + aiMove.getName());
        view.addMessageToQueue("Đã gây " + aiDmg + " sát thương!");
        view.updateHPBars();

        if (player.isFainted()) {
            view.addMessageToQueue(player.getName() + " đã bị hạ gục!");
            // Kiểm tra xem player có pokemon khác không
            if (playerTeam.switchToNextActivePokemon()) {
                Pokemon nextPlayerPokemon = playerTeam.getCurrentPokemon();
                view.updateUI(nextPlayerPokemon, ai);
                view.addMessageToQueue("Làm tốt lắm! " + player.getName());
                view.addMessageToQueue("Tiến lên! " + nextPlayerPokemon.getName());
                view.startMessageQueue(() -> {
                    view.enableMoveButtons();
                });
            } else {
                view.addMessageToQueue("Bạn đã thua...");
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
            Pokemon nextPokemon = playerTeam.getCurrentPokemon();
            Pokemon ai = aiTeam.getCurrentPokemon();
            view.updateUI(nextPokemon, ai);
            view.addMessageToQueue("Player đã lựa chọn " + nextPokemon.getName());
            view.addMessageToQueue("Tiến lên! " + nextPokemon.getName());
            view.updateHPBars();
            
            // AI tấn công
            Move aiMove = AI.chooseBestMove(ai, nextPokemon);
            int aiDmg = ai.attack(nextPokemon, aiMove);
            view.addMessageToQueue(ai.getName() + " đã sử dụng tuyệt chiêu " + aiMove.getName());
            view.addMessageToQueue("Đã gây " + aiDmg + " sát thương!");
            view.updateHPBars();
            
            if (nextPokemon.isFainted()) {
                view.addMessageToQueue(nextPokemon.getName() + " đã bị hạ gục!");
                if (playerTeam.switchToNextActivePokemon()) {
                    Pokemon nextPlayerPokemon = playerTeam.getCurrentPokemon();
                    view.updateUI(nextPlayerPokemon, ai);
                    view.addMessageToQueue("Làm tốt lắm! " + nextPokemon.getName());
                    view.addMessageToQueue("Tiến lên! " + nextPlayerPokemon.getName());
                    view.startMessageQueue(() -> {
                        view.enableMoveButtons();
                    });
                } else {
                    view.addMessageToQueue("Bạn đã thua...");
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
