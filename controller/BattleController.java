package controller;

import model.*;
import view.BattleView;

public class BattleController {
    private Pokemon player;
    private Pokemon ai;
    private BattleView view;

    public BattleController(Pokemon player, Pokemon ai) {
        this.player = player;
        this.ai = ai;
        this.view = new BattleView(this, player, ai);
    }

    public void startBattle() {
        view.setVisible(true);
    }

    public void playerMove(int index) {
        if (index < 0 || index >= player.getMoves().size()) 
        	return;
        Move move = player.getMoves().get(index);
        int dmg = player.attack(ai, move);
        view.showMessage(player.getName() + " used " + move.getName() + "! It dealt " + dmg + " damage!");
        view.updateHPBars();

        if (ai.isFainted()) {
            view.showMessage(ai.getName() + " fainted! You win!");
            view.disableAllButtons();
            return;
        }

        // AI phản công
        Move aiMove = AI.chooseBestMove(ai, player);
        int aiDmg = ai.attack(player, aiMove);
        view.showMessage(ai.getName() + " used " + aiMove.getName() + "! It dealt " + aiDmg + " damage!");
        view.updateHPBars();

        if (player.isFainted()) {
            view.showMessage(player.getName() + " fainted! You lost...");
            view.disableAllButtons();
        }
    }
}
