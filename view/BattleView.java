package view;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Pokemon;
import model.PokemonTeam;
import model.PokemonData;
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
    private AIMoveView aiMoveView;
    private TypeChartView typeChartView;
    private boolean gameOver = false;

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
        command = new CommandView(controller, currentPlayer, playerTeam, controller.getPlayerInventory(), message, this);
        aiMoveView = new AIMoveView(currentAi);

        loadImg.add(aiBar);
        loadImg.add(playerBar);
        loadImg.add(command);
        loadImg.add(message);
        loadImg.add(aiMoveView);
        
        // Tạo TypeChartView sẵn nhưng ẩn
        typeChartView = new TypeChartView(() -> hideTypeChart(), this);
        typeChartView.setVisible(false);
        loadImg.add(typeChartView);

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

    // ============================================
    // UC4.6: CẬP NHẬT GIAO DIỆN
    // ============================================
    
    // UC4.6.3: Cập nhật thanh máu (HP bars) cho cả hai Pokemon
    public void updateHPBars() {
        playerBar.updateHP();      // Cập nhật HP bar của Player
        aiBar.updateHP();          // Cập nhật HP bar của AI
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
        aiMoveView.setPokemon(newAi);
        loadImg.loadPokemonImages(newPlayer, newAi);
        loadImg.repaint();
        command.updateMovePanel(newPlayer);
        updateHPBars();
    }
    
    public void enableMoveButtons() {
        command.enablePlayerInteraction();
    }

    // UC4.6.5: Cập nhật PP của các nước đi của AI
    public void updateAIMoveDisplay() {
        if (aiMoveView != null) {
            aiMoveView.updateMoves();
        }
    }
    
    // UC4.6.4: Cập nhật PP của các nước đi của Player
    public void updatePlayerMoveDisplay() {
        if (command != null) {
            command.updateMoveButtons(playerTeam.getCurrentPokemon());
        }
    }

    public void updateInventoryDisplay() {
        if (command != null) {
            command.updateBagPanel();
        }
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
    
    // ============================================
    // UC4.6.1-2: HÀNG ĐỢI THÔNG BÁO
    // ============================================
    // Hiển thị các thông báo lần lượt (queue)
    // Ví dụ: "Pikachu used Thunderbolt!" → "It's super effective! 45 damage!" → ...
    
    // UC4.6.1: Hiển thị thông báo nước đi được sử dụng
    public void queueUsingMoveMessage(Pokemon attacker, String moveName) {
        message.queueUsingMoveMessage(attacker, moveName);
    }
    
    // UC4.6.2: Hiển thị thông báo sát thương + hiệu ứng khắc hệ
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
    
    public void showTypeChart() {
        if (typeChartView != null) {
            // Đưa TypeChartView lên trước
            loadImg.setComponentZOrder(typeChartView, 0);
            typeChartView.setVisible(true);
            loadImg.repaint();
        }
    }
    
    public void hideTypeChart() {
        if (typeChartView != null) {
            typeChartView.setVisible(false);
            loadImg.repaint();
        }
    }
    
    public LoadImgView getLoadImg() {
        return loadImg;
    }
    
    public void showResultView(boolean isVictory) {
        if (gameOver) return;
        gameOver = true;
        
        ResultView result = new ResultView(isVictory);
        
        // Chơi lại với Pokemon cũ
        result.setOnReplayListener(() -> {
            dispose();
            PokemonTeam newPlayerTeam = new PokemonTeam();
            PokemonTeam newAiTeam = new PokemonTeam();
            
            // Copy Pokemon từ team cũ
            for (Pokemon p : playerTeam.getTeam()) {
                newPlayerTeam.addPokemon(p);
            }
            for (Pokemon p : aiTeam.getTeam()) {
                newAiTeam.addPokemon(p);
            }
            
            BattleController newController = new BattleController(newPlayerTeam, newAiTeam);
            
            // Reset HP cho tất cả Pokemon
            for (Pokemon p : newPlayerTeam.getTeam()) {
                p.resetHP();
            }
            for (Pokemon p : newAiTeam.getTeam()) {
                p.resetHP();
            }
            
            newController.startBattle();
        });
        
        // Chọn đội hình lại
        result.setOnSelectPokemonListener(() -> {
            dispose();
            PokemonSelectionView selection = new PokemonSelectionView();
            selection.addStartButtonListener(e2 -> {
                startGameWithNewTeam(selection.getSelectedPokemons());
                selection.closeSelection();
            });
        });
        
        // Quay về Menu
        result.setOnMenuListener(() -> {
            dispose();
            new MenuView();
        });
    }
    
    private void startGameWithNewTeam(List<Pokemon> playerPokemons) {
        // Tạo team cho player với pokemon đã chọn
        PokemonTeam newPlayerTeam = new PokemonTeam();
        for (Pokemon pokemon : playerPokemons) {
            newPlayerTeam.addPokemon(pokemon);
        }
        
        // Tạo team cho AI với 4 pokemon random
        List<Pokemon> randomAIPokemons = getRandomPokemons(4);
        PokemonTeam newAiTeam = new PokemonTeam();
        for (Pokemon pokemon : randomAIPokemons) {
            newAiTeam.addPokemon(pokemon);
        }

        BattleController newController = new BattleController(newPlayerTeam, newAiTeam);
        newController.startBattle();
    }
    
    private List<Pokemon> getRandomPokemons(int count) {
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
    
    public CommandView getCommand() {
        return command;
    }
}
