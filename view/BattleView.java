package view;

import javax.swing.*;

import model.Pokemon;
import model.PokemonTeam;
import controller.BattleController;

public class BattleView extends JFrame {
    private final BattleController controller;
    private final PokemonTeam playerTeam;
    private final PokemonTeam aiTeam;
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
        message = new MessageView("");
        command = new CommandView(controller, currentPlayer, playerTeam, message);

        loadImg.add(aiBar);
        loadImg.add(playerBar);
        loadImg.add(command);
        loadImg.add(message);

        add(loadImg);
        
        showStartBattleMessages(currentPlayer, currentAi);
    }
    
    //Hiển thị message lần lượt khi bắt đầu trận đấu
    private void showStartBattleMessages(Pokemon playerPokemon, Pokemon aiPokemon) {
        //Khóa các nút hành động cho đến khi hết message bắt đầu trận đấu
        command.disableAll();
        
        addMessageToQueue("Player đã chọn " + playerPokemon.getName() + ".");
        addMessageToQueue("AI đã chọn " + aiPokemon.getName() + ".");
        addMessageToQueue("Tiến lên! " + playerPokemon.getName() + "!");
        addMessageToQueue(playerPokemon.getName() + " sẽ làm gì?");
        startMessageQueue(() -> {
            //Sau khi hết message bắt đầu trận đấu, cho phép người chơi bắt đầu lượt chơi
            command.enablePlayerInteraction();
        });
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
        command.enablePlayerInteraction();
    }
    
    //Gọi các thoại từ MessageView
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
    
    //Hàng đợi các message để các thoại có thể hiển thị lần lượt
    public void queueUsingMoveMessage(Pokemon attacker, String moveName) {
        message.queueUsingMoveMessage(attacker, moveName);
    }
    
    public void queueDamageMessage(int damage, String moveName) {
        message.queueDamageMessage(damage, moveName);
    }
    
    public void queuePokemonFaintedMessage(Pokemon pokemon) {
        message.queuePokemonFaintedMessage(pokemon);
    }
    
    public void queueAIPokemonSelectedMessage(Pokemon pokemon) {
        message.queueAIPokemonSelectedMessage(pokemon);
    }
    
    public void queuePlayerPokemonSelectedMessage(Pokemon pokemon) {
        message.queuePlayerPokemonSelectedMessage(pokemon);
    }
    
    public void queuePokemonEnterMessage(Pokemon pokemon) {
        message.queuePokemonEnterMessage(pokemon);
    }
    
    public void queuePokemonPraiseMessage(Pokemon pokemon) {
        message.queuePokemonPraiseMessage(pokemon);
    }
    
    public void queueWinMessage() {
        message.queueWinMessage();
    }
    
    public void queueLoseMessage() {
        message.queueLoseMessage();
    }

    public BattleController getController() {
        return controller;
    }
}
