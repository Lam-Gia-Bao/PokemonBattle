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
    
    // ============================================
    // UC4: CHỌN NƯỚC ĐI & TẤN CÔNG
    // ============================================
    
    /**
     * UC4.1.2 - Xử lý khi người chơi chọn nước đi
     * Flow:
     *   UC4.2: Lấy Pokemon hiện tại (người chơi & AI)
     *   UC4.3: Validation & Lấy Move object
     *   UC4.4: Thực hiện tấn công (gọi Pokemon.attack)
     *   UC4.5: Tính sát thương (Move.calculateDamage)
     *   UC4.6: Cập nhật giao diện
     *   UC4.7: Kiểm tra kết quả (Pokemon còn sống hay hạ gục)
     */
    public void playerMove(int index) {
        // UC4.2: Lấy Pokemon hiện tại của cả hai đội
        Pokemon player = playerTeam.getCurrentPokemon();
        Pokemon ai = aiTeam.getCurrentPokemon();
        
        // UC4.3.1: Kiểm tra validation - Pokemon phải hợp lệ
        if (player == null || ai == null) return;
        if (index < 0 || index >= player.getMoves().size()) 
            return;
        
        // UC4.6: Vô hiệu hóa tất cả nút bấm trong khi thực hiện hành động
        view.disableAllButtons();
        
        // UC4.3.2: Lấy Move object từ danh sách 4 nước đi
        Move move = player.getMoves().get(index);
        
        // UC4.4: Thực hiện tấn công
        // - Gọi Pokemon.attack(defender, move)
        // - Trong đó gọi Move.calculateDamage() để tính sát thương (UC4.5)
        // - Defender nhận damage qua receiveDmg()
        int dmg = player.attack(ai, move);
        
        // UC4.6: Cập nhật giao diện
        view.queueUsingMoveMessage(player, move.getName());    // Hiển thị thông báo "Pikachu used Thunderbolt!"
        view.queueDamageMessage(dmg, move.getName());          // Hiển thị "It's super effective! 45 damage!"
        view.updateHPBars();                                    // Cập nhật thanh máu
        view.updatePlayerMoveDisplay();                         // Cập nhật PP của các nước đi
        view.updateAIMoveDisplay();                             // Cập nhật PP của các nước đi AI

        // UC4.7.1: Kiểm tra xem AI Pokemon bị hạ gục chưa
        if (ai.isFainted()) {
            view.queuePokemonFaintedMessage(ai);
            
            // UC4.7.8-10: Kiểm tra xem AI còn Pokemon khác không
            if (aiTeam.switchToNextActivePokemon()) {
                // UC4.7.9-14: Còn Pokemon khác - Thay Pokemon mới
                Pokemon nextAiPokemon = aiTeam.getCurrentPokemon();
                view.updateUI(playerTeam.getCurrentPokemon(), nextAiPokemon);
                view.queueAIPokemonSelectedMessage(nextAiPokemon);
                view.startMessageQueue(() -> {
                    view.enableMoveButtons();
                });
            } else {
                // UC4.7.15-20: Hết Pokemon - Người chơi thắng
                view.queueWinMessage();
                view.startMessageQueue(() -> {
                    view.disableAllButtons();
                    view.showResultView(true);  // Hiển thị màn hình thắng
                });
            }
            return;
        }
        
        // UC4.7.2-5: Nếu Defender còn sống → AI phản công
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
            view.queuePokemonPraiseMessage(nextPokemon);
            view.queuePokemonEnterMessage(nextPokemon);
            
            aiMove();
        } else {
            view.showCannotSwitchMessage();
        }
    }

    // ============================================
    // UC4.7.3: LƯỢt CỦA AI (Phản công)
    // ============================================
    // Lượt của AI tấn công Player
    // (Flow tương tự UC4, nhưng thay bằng AI.chooseBestMove quyết định nước đi)
    private void aiMove() {
        Pokemon player = playerTeam.getCurrentPokemon();
        Pokemon ai = aiTeam.getCurrentPokemon();

        // UC4.7.3: AI quyết định nước đi tốt nhất (dùng Minimax/Alpha-Beta)
        Move aiMove = AI.chooseBestMove(ai, player);
        
        // Thực hiện tấn công (tương tự UC4.4-5: calculateDamage + receiveDmg)
        int aiDmg = ai.attack(player, aiMove);

        // UC4.7.3: Cập nhật giao diện (tương tự UC4.6)
        view.queueUsingMoveMessage(ai, aiMove.getName());
        view.queueDamageMessage(aiDmg, aiMove.getName());
        view.updateHPBars();
        // Cập nhật PP của moves sau khi tấn công
        view.updatePlayerMoveDisplay();
        view.updateAIMoveDisplay();

        // UC4.7.1: Kiểm tra xem Player Pokemon bị hạ gục chưa
        if (player.isFainted()) {
            view.queuePokemonFaintedMessage(player);
            
            // UC4.7.8-10: Kiểm tra xem Player còn Pokemon khác không
            if (playerTeam.switchToNextActivePokemon()) {
                // UC4.7.9-14: Còn Pokemon khác - Thay Pokemon mới
                Pokemon nextPlayerPokemon = playerTeam.getCurrentPokemon();
                view.updateUI(nextPlayerPokemon, ai);
                view.queuePokemonPraiseMessage(player);
                view.queuePokemonEnterMessage(nextPlayerPokemon);
                view.startMessageQueue(() -> view.enableMoveButtons());
            } else {
                // UC4.7.15-20: Hết Pokemon - AI thắng
                view.queueLoseMessage();
                view.startMessageQueue(() -> view.disableAllButtons());
                view.startMessageQueue(() -> {
                    view.showResultView(false);  // Hiển thị màn hình thua
                });
            }
        } else {
            // UC4.7.2-5: Nếu Player còn sống → Chờ lượt tiếp theo
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
