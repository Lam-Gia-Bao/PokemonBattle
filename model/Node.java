package model;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private Pokemon aiPokemon;
    private Pokemon playerPokemon;
    private Move lastMove;
    private List<Node> children;
    
    public Node(Pokemon aiPokemon, Pokemon playerPokemon, Move lastMove) {
        this.aiPokemon = copyPokemon(aiPokemon);
        this.playerPokemon = copyPokemon(playerPokemon);
        this.lastMove = lastMove;
        this.children = new ArrayList<>();
    }
    
    // Copy pokemon để không ảnh hưởng đến trạng thái thực
    private Pokemon copyPokemon(Pokemon original) {
        Pokemon copy = new Pokemon(
            original.getName(),
            original.getType(),
            original.getAtk(),
            original.getDef(),
            original.getSpeed(),
            original.getMaxHp()
        );
        // Lấy HP hiện tại khi copy
        int hpLoss = original.getMaxHp() - original.getHp();
        for (int i = 0; i < hpLoss; i++) {
            copy.receiveDmg(1);
        }
        // Copy moves (bao gồm cả healing moves)
        for (Move m : original.getMoves()) {
            if (m.isHealingMove()) {
                copy.addMove(new Move(m.getName(), m.getType(), m.getPower(), m.getPp(), true, m.getHealAmount()));
            } else {
                copy.addMove(new Move(m.getName(), m.getType(), m.getPower(), m.getPp()));
            }
        }
        return copy;
    }
    
    public Pokemon getAiPokemon() {
        return aiPokemon;
    }
    
    public Pokemon getPlayerPokemon() {
        return playerPokemon;
    }
    
    public Move getLastMove() {
        return lastMove;
    }
    
    public List<Node> getChildren() {
        return children;
    }
    
    // Tạo các state con từ state hiện tại
    public void generateChildren(boolean isAiTurn) {
        children.clear();
        Pokemon attacker = isAiTurn ? aiPokemon : playerPokemon;
        Pokemon defender = isAiTurn ? playerPokemon : aiPokemon;
        
        for (Move move : attacker.getMoves()) {
            if (!move.isUsable()) continue;
            
            Node childState = new Node(aiPokemon, playerPokemon, move);
            childState.simulateAttack(attacker, defender, move);
            children.add(childState);
        }
    }
    
    // Mô phỏng tấn công trong state con
    private void simulateAttack(Pokemon attacker, Pokemon defender, Move move) {
        Pokemon attackerCopy = isAiPokemon(attacker) ? aiPokemon : playerPokemon;
        Pokemon defenderCopy = isAiPokemon(defender) ? aiPokemon : playerPokemon;
        attackerCopy.attack(defenderCopy, move);
    }
    
    private boolean isAiPokemon(Pokemon ai) {
        return ai == aiPokemon;
    }
    
    // Hàm đánh giá 
    public int point() {
        if (playerPokemon.isFainted()) return 10000; // AI thắng
        if (aiPokemon.isFainted()) return -10000; // Player thắng
        
        // Đánh giá dựa trên chênh lệch HP
        int hpDiff = aiPokemon.getHp() - playerPokemon.getHp();
        return hpDiff * 10;
    }
    
    public boolean isOver() {
        return aiPokemon.isFainted() || playerPokemon.isFainted();
    }
}
