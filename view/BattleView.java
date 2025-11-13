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
        command = new CommandView(controller, currentPlayer, playerTeam);

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
        command.enableMainButtons(true);
    }

    public BattleController getController() {
        return controller;
    }
}
