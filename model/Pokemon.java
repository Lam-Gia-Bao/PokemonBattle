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

    public void receiveDmg(int damage) {
        this.hp -= damage;
        if (this.hp < 0) this.hp = 0;
    }

    public void heal(int amount) {
        this.hp += amount;
        if (this.hp > this.maxHp) this.hp = this.maxHp;
    }

    
    public int attack(Pokemon target, Move move) {
        if (this.isFainted()) return 0;
        if (move == null || !move.isUsable()) return 0;

        move.useMove();
        
        // Nếu là skill hồi máu, hồi cho bản thân
        if (move.isHealingMove()) {
            int healAmount = move.getHealAmount();
            this.heal(healAmount);
            return healAmount;
        }
        
        // Nếu là skill tấn công
        int damage = move.calculateDamage(this, target);
        target.receiveDmg(damage);
        return damage;
    }
}
