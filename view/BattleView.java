package view;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

import model.Pokemon;
import controller.BattleController;

public class BattleView extends JFrame {
    private BattleController controller;
    private Pokemon player;
    private Pokemon ai;
    private LoadImgView loadImg;
    private HealthBarView playerBar;
    private HealthBarView aiBar;
    private MessageView message;
    private CommandView command;

    public BattleView(BattleController controller, Pokemon player, Pokemon ai) {
        this.controller = controller;
        this.player = player;
        this.ai = ai;
        initUI();
    }

    private void initUI() {
        setTitle("Pokemon Battle");
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        
        loadImg = new LoadImgView();
        loadImg.setLayout(null);

        aiBar = new HealthBarView(ai, false);
        playerBar = new HealthBarView(player, true);
        message = new MessageView("What will " + player.getName() + " do?");
        command = new CommandView(controller, player);

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

    public BattleController getController() {
        return controller;
    }
}
