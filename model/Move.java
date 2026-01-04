package model;
//Move - giữ cấu trúc đơn giản (name, type, power, pp)
 
public class Move {
    private final String name;
    private final PokemonType type;
    private final int power;
    private final int maxPp;
    private int pp;
    private final boolean isHealingMove;
    private final int healAmount; // Số máu hồi (0 nếu không phải skill hồi)

    public Move(String name, PokemonType type, int power, int pp) {
        this(name, type, power, pp, false, 0);
    }

    public Move(String name, PokemonType type, int power, int pp, boolean isHealingMove, int healAmount) {
        this.name = name;
        this.type = type;
        this.power = power;
        this.maxPp = pp;
        this.pp = pp;
        this.isHealingMove = isHealingMove;
        this.healAmount = healAmount;
    }

    public String getName() {
        return name;
    }

    public PokemonType getType() {
        return type;
    }

    public int getPower() {
        return power;
    }

    public int getPp() {
        return pp;
    }

    public int getMaxPp() {
        return maxPp;
    }

    public void useMove() {
        if (pp > 0) pp--;
    }

    public boolean isUsable() {
        return pp > 0;
    }

    public boolean isHealingMove() {
        return isHealingMove;
    }

    public int getHealAmount() {
        return healAmount;
    }

    /**
     * Tính damage:
     * Damage = round( power * (A / D) * STAB * effectiveness )
     * - A = attacker.atk 
     * - D = defender.def (>=1)
     * - STAB = 1.5 nếu attacker có cùng 1 trong 2 type với move.type
     * - effectiveness từ TypeEffectiveness.getMultiplier(...)
     */
    public int calculateDamage(Pokemon attacker, Pokemon defender) {
        if (attacker == null || defender == null) return 0;

        double A = Math.max(1.0, attacker.getAtk());
        double D = Math.max(1.0, defender.getDef()); // tránh chia 0
        double base = (double) this.power * (A / D);

        // STAB: hệ của kỹ năng cùng hệ với hệ của pokemon
        double stab = 1.0;
        PokemonType t1 = attacker.getType1();
        PokemonType t2 = attacker.getType2();
        if (t1 != null && this.type == t1) stab = 1.5;
        else if (t2 != null && this.type == t2) stab = 1.5;

        // effectiveness: so sánh move.type vs defender's types
        double eff = TypeEffectiveness.getMultiplier(this.type, defender.getType1(), defender.getType2());

        double raw = base * stab * eff;
        int dmg = (int) Math.round(raw);
        if (dmg < 1) dmg = 1;
        return dmg;
    }
}
