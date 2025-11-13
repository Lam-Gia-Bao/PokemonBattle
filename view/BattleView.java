package view;

import javax.swing.*;

import model.Pokemon;
import model.PokemonTeam;
import controller.BattleController;

public class BattleView extends JFrame {
    private BattleController controller;
    private PokemonTeam playerTeam;
    private PokemonTeam aiTeam;
    private LoadImgView loadImg;
    private HealthBarView playerBar;
    private HealthBarView aiBar;
    private MessageView message;
    private CommandView command;

    public BattleView(BattleController controller, PokemonTeam playerTeam, PokemonTeam aiTeam) {
        this.controller = controller;
        this.playerTeam = playerTeam;
        this.aiTeam = aiTeam;
        initUI();
    }

    private void initUI() {
        setTitle("Pokemon Battle");
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        
        Pokemon currentPlayer = playerTeam.getCurrentPokemon();
        Pokemon currentAi = aiTeam.getCurrentPokemon();
        
        loadImg = new LoadImgView();
        loadImg.loadPokemonImages(currentPlayer, currentAi);
        loadImg.setLayout(null);

        aiBar = new HealthBarView(currentAi, false);
        playerBar = new HealthBarView(currentPlayer, true);
        message = new MessageView("What will " + currentPlayer.getName() + " do?");
        command = new CommandView(controller, currentPlayer, playerTeam, message);

        loadImg.add(aiBar);
        loadImg.add(playerBar);
        loadImg.add(message);
        loadImg.add(command);

        add(loadImg);
    }

    public void updateHPBars() {
        playerBar.updateHP();
        aiBar.updateHP();
    }

    public void showMessage(String msg) {
        message.setMessage(msg);
    }

    public void disableAllButtons() {
        command.disableAll();
    }
    
    public void updateUI(Pokemon newPlayer, Pokemon newAi) {
        playerBar.setPokemon(newPlayer);
        aiBar.setPokemon(newAi);
        loadImg.loadPokemonImages(newPlayer, newAi);
        loadImg.repaint();
        command.updateMovePanel(newPlayer);
        updateHPBars();
    }
    
    public void enableMoveButtons() {
        // enable full player interaction (main buttons + move buttons)
        command.enablePlayerInteraction();
    }
    
    // Delegate methods từ MessageView
    public void showAttackMessage(Pokemon attacker, String moveName, int damage) {
        message.showAttackMessage(attacker, moveName, damage);
    }
    
    public void showPokemonFaintedWithReplacement(Pokemon faintedPokemon, Pokemon nextPokemon, boolean isOpponent) {
        message.showPokemonFaintedWithReplacement(faintedPokemon, nextPokemon, isOpponent);
    }
    
    public void showGameOverMessage(Pokemon faintedPokemon, boolean playerWon) {
        message.showGameOverMessage(faintedPokemon, playerWon);
    }
    
    public void showSwitchPokemonMessage(Pokemon newPokemon) {
        message.showSwitchPokemonMessage(newPokemon);
    }
    
    public void showCannotSwitchMessage() {
        message.showCannotSwitchMessage();
    }
    
    public void showNoBagItemsMessage() {
        message.showNoBagItemsMessage();
    }
    
    public void showCannotRunMessage() {
        message.showCannotRunMessage();
    }
    
    public void addMessageToQueue(String msg) {
        message.addMessageToQueue(msg);
    }
    
    public void startMessageQueue(Runnable onComplete) {
        message.startMessageQueue(onComplete);
    }
    
    public void clearMessageQueue() {
        message.clearQueue();
    }

    public BattleController getController() {
        return controller;
    }
}
