package model;
//Move - giữ cấu trúc đơn giản (name, type, power, pp)
 
public class Move {
    private final String name;
    private final PokemonType type;
    private final int power;
    private final int maxPp;
    private int pp;
    private final boolean isHealingMove;
    private final int healAmount; // Số máu hồi nếu là move hồi máu

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

    // ============================================
    // UC4.5: TÍNH TOÁN SÁT THƯƠNG
    // ============================================
    
    /**
     * UC4.5: Tính sát thương của nước đi
     * 
     * Công thức:
     *   Damage = round( power × (ATK / DEF) × STAB × effectiveness )
     * 
     * Tham số:
     *   - power: Sức mạnh cơ bản của nước đi (ví dụ: Thunderbolt = 90)
     *   - A (ATK): Chỉ số tấn công của attacker
     *   - D (DEF): Chỉ số phòng thủ của defender (tối thiểu 1 để tránh chia 0)
     *   - STAB: Same Type Attack Bonus = 1.5 nếu move.type trùng với type của Pokemon
     *   - effectiveness: Nhân tố từ bảng khắc hệ (0.0, 0.5, 1.0, 2.0)
     * 
     * Flow:
     *   UC4.5.1: Tính base damage = power × (ATK / DEF)
     *   UC4.5.2-3: Gọi TypeEffectiveness.getMultiplier() lấy effectiveness
     *   UC4.5.4: Áp dụng STAB (1.5x nếu move.type = Pokemon.type)
     *   UC4.5.5: Tính final damage = round(base × STAB × effectiveness)
     */
    public int calculateDamage(Pokemon attacker, Pokemon defender) {
        if (attacker == null || defender == null) return 0;

        // UC4.5.1: Tính Base Damage = power × (ATK / DEF)
        double A = Math.max(1.0, attacker.getAtk());
        double D = Math.max(1.0, defender.getDef());  // Tránh chia cho 0
        double base = (double) this.power * (A / D);

        // UC4.5.4: Áp dụng STAB (Same Type Attack Bonus)
        // STAB = 1.5 nếu nước đi có cùng type với một trong 2 type của Pokemon
        double stab = 1.0;
        PokemonType t1 = attacker.getType1();
        PokemonType t2 = attacker.getType2();
        if (t1 != null && this.type == t1) stab = 1.5;
        else if (t2 != null && this.type == t2) stab = 1.5;

        // UC4.5.2-3: Lấy Effectiveness từ bảng khắc hệ
        // Trả về: 0.0 (không hiệu quả), 0.5 (yếu), 1.0 (trung), 2.0 (mạnh)
        double eff = TypeEffectiveness.getMultiplier(this.type, defender.getType1(), defender.getType2());

        // UC4.5.5: Tính final damage
        double raw = base * stab * eff;
        int dmg = (int) Math.round(raw);
        if (dmg < 1) dmg = 1;  // Minimum 1 damage
        return dmg;
    }
}
