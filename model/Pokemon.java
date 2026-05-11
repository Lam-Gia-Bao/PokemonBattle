package model;

import java.util.ArrayList;
import java.util.List;


public class Pokemon {
    private final String name;
    private final PokemonType type1;
    private final PokemonType type2; // có thể null
    private int hp;
    private final int maxHp;
    private final int atk;
    private final int def;
    private final int speed;
    private final List<Move> moves;

    public Pokemon(String name, PokemonType type, int atk, int def, int speed, int hp) {
        this(name, type, null, atk, def, speed, hp);
    }

    public Pokemon(String name, PokemonType type1, PokemonType type2, int atk, int def, int speed, int hp) {
        this.name = name;
        this.type1 = type1;
        this.type2 = type2;
        this.maxHp = Math.max(1, hp);
        this.hp = this.maxHp;
        this.atk = Math.max(1, atk);
        this.def = Math.max(1, def);
        this.speed = speed;
        this.moves = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    
    public PokemonType getType() {
        return type1;
    }

    public PokemonType getType1() {
        return type1;
    }

    public PokemonType getType2() {
        return type2;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public int getSpeed() {
        return speed;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void addMove(Move move) {
        if (moves.size() < 4 && move != null) moves.add(move);
    }

    public boolean isFainted() {
        return hp <= 0;
    }

    // UC4.4.5: Cập nhật HP của Pokemon sau khi nhận sát thương
    public void receiveDmg(int damage) {
        this.hp -= damage;
        if (this.hp < 0) this.hp = 0;  // Ensure HP never goes below 0
    }

    public void heal(int amount) {
        this.hp += amount;
        if (this.hp > this.maxHp) this.hp = this.maxHp;
    }

    public void resetHP() {
        this.hp = this.maxHp;
    }

    
    // ============================================
    // UC4.4: THỰC HIỆN TẤN CÔNG
    // ============================================
    
    /**
     * UC4.4.1 - Thực hiện tấn công bằng một nước đi
     * 
     * Flow:
     *   UC4.4.2: Gọi Move.calculateDamage() để tính sát thương (UC4.5)
     *   UC4.4.4: Defender nhận sát thương via receiveDmg()
     *   UC4.4.5: Cập nhật HP của Defender
     *   UC4.4.6: Trả về giá trị sát thương
     */
    public int attack(Pokemon target, Move move) {
        if (this.isFainted()) return 0;
        if (move == null || !move.isUsable()) return 0;

        // Sử dụng 1 PP (Power Point) của nước đi
        move.useMove();
        
        // Xử lý nước đi hồi máu (ngoại lệ khác UC4)
        if (move.isHealingMove()) {
            int healAmount = move.getHealAmount();
            this.heal(healAmount);
            return healAmount;
        }
        
        // ========== UC4.4: TẤN CÔNG THƯỜNG =========
        // UC4.4.2: Gọi Move.calculateDamage() để tính sát thương
        // Công thức: Damage = power × (ATK/DEF) × STAB × effectiveness
        int damage = move.calculateDamage(this, target);
        
        // UC4.4.4-5: Defender nhận damage
        // newHP = max(0, currentHP - damage)
        target.receiveDmg(damage);
        
        // UC4.4.6: Trả về giá trị sát thương
        return damage;
    }
}
