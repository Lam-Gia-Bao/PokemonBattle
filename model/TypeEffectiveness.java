package model;

import java.util.EnumMap;
import java.util.Map;

/**
 * Bảng khắc hệ (type effectiveness).
 * Trả về multiplier: 0.0, 0.5, 1.0, 2.0
 */
public final class TypeEffectiveness {
    private static final Map<PokemonType, Map<PokemonType, Double>> CHART = new EnumMap<>(PokemonType.class);

    static {
        // init maps
        for (PokemonType t : PokemonType.values()) {
            CHART.put(t, new EnumMap<>(PokemonType.class));
        }

        // Khắc hệ
        // Hệ lửa
        put(PokemonType.FIRE, PokemonType.GRASS, 2.0);
        put(PokemonType.FIRE, PokemonType.ICE, 2.0);
        put(PokemonType.FIRE, PokemonType.BUG, 2.0);
        put(PokemonType.FIRE, PokemonType.STEEL, 2.0);
        put(PokemonType.FIRE, PokemonType.FIRE, 0.5);
        put(PokemonType.FIRE, PokemonType.WATER, 0.5);
        put(PokemonType.FIRE, PokemonType.ROCK, 0.5);
        put(PokemonType.FIRE, PokemonType.DRAGON, 0.5);
        
        // Hệ nước
        put(PokemonType.WATER, PokemonType.FIRE, 2.0);
        put(PokemonType.WATER, PokemonType.GROUND, 2.0);
        put(PokemonType.WATER, PokemonType.ROCK, 2.0);
        put(PokemonType.WATER, PokemonType.WATER, 0.5);
        put(PokemonType.WATER, PokemonType.GRASS, 0.5);
        put(PokemonType.WATER, PokemonType.DRAGON, 0.5);
        
        // Hệ cỏ
        put(PokemonType.GRASS, PokemonType.WATER, 2.0);
        put(PokemonType.GRASS, PokemonType.GROUND, 2.0);
        put(PokemonType.GRASS, PokemonType.ROCK, 2.0);
        put(PokemonType.GRASS, PokemonType.FIRE, 0.5);
        put(PokemonType.GRASS, PokemonType.GRASS, 0.5);
        put(PokemonType.GRASS, PokemonType.POISON, 0.5);
        put(PokemonType.GRASS, PokemonType.FLYING, 0.5);
        put(PokemonType.GRASS, PokemonType.BUG, 0.5);
        put(PokemonType.GRASS, PokemonType.DRAGON, 0.5);
        put(PokemonType.GRASS, PokemonType.STEEL, 0.5);

        // Hệ điện
        put(PokemonType.ELECTRIC, PokemonType.WATER, 2.0);
        put(PokemonType.ELECTRIC, PokemonType.FLYING, 2.0);
        put(PokemonType.ELECTRIC, PokemonType.ELECTRIC, 0.5);
        put(PokemonType.ELECTRIC, PokemonType.GRASS, 0.5);
        put(PokemonType.ELECTRIC, PokemonType.DRAGON, 0.5);
        put(PokemonType.ELECTRIC, PokemonType.GROUND, 0.0);

        // Hệ đất
        put(PokemonType.GROUND, PokemonType.FIRE, 2.0);
        put(PokemonType.GROUND, PokemonType.ELECTRIC, 2.0);
        put(PokemonType.GROUND, PokemonType.POISON, 2.0);
        put(PokemonType.GROUND, PokemonType.ROCK, 2.0);
        put(PokemonType.GROUND, PokemonType.STEEL, 2.0);
        put(PokemonType.GROUND, PokemonType.GRASS, 0.5);
        put(PokemonType.GROUND, PokemonType.BUG, 0.5);
        put(PokemonType.GROUND, PokemonType.FLYING, 0.0);

        // Hệ bay
        put(PokemonType.FLYING, PokemonType.GRASS, 2.0);
        put(PokemonType.FLYING, PokemonType.FIGHTING, 2.0);
        put(PokemonType.FLYING, PokemonType.BUG, 2.0);
        put(PokemonType.FLYING, PokemonType.ELECTRIC, 0.5);
        put(PokemonType.FLYING, PokemonType.ROCK, 0.5);
        put(PokemonType.FLYING, PokemonType.STEEL, 0.5);

        // Hệ đá
        put(PokemonType.ROCK, PokemonType.FIRE, 2.0);
        put(PokemonType.ROCK, PokemonType.BUG, 2.0);
        put(PokemonType.ROCK, PokemonType.FLYING, 2.0);
        put(PokemonType.ROCK, PokemonType.ICE, 2.0);
        put(PokemonType.ROCK, PokemonType.FIGHTING, 0.5);
        put(PokemonType.ROCK, PokemonType.GROUND, 0.5);
        put(PokemonType.ROCK, PokemonType.STEEL, 0.5);

        // Hệ thép
        put(PokemonType.STEEL, PokemonType.ICE, 2.0);
        put(PokemonType.STEEL, PokemonType.ROCK, 2.0);
        put(PokemonType.STEEL, PokemonType.FAIRY, 2.0);
        put(PokemonType.STEEL, PokemonType.FIRE, 0.5);
        put(PokemonType.STEEL, PokemonType.WATER, 0.5);
        put(PokemonType.STEEL, PokemonType.ELECTRIC, 0.5);
        put(PokemonType.STEEL, PokemonType.STEEL, 0.5);

        // Hệ siêu linh
        put(PokemonType.PSYCHIC, PokemonType.FIGHTING, 2.0);
        put(PokemonType.PSYCHIC, PokemonType.POISON, 2.0);
        put(PokemonType.PSYCHIC, PokemonType.PSYCHIC, 0.5);
        put(PokemonType.PSYCHIC, PokemonType.STEEL, 0.5);
        put(PokemonType.PSYCHIC, PokemonType.DARK, 0.0);
        
        // Hệ ma
        put(PokemonType.GHOST, PokemonType.PSYCHIC, 2.0);
        put(PokemonType.GHOST, PokemonType.GHOST, 2.0);
        put(PokemonType.GHOST, PokemonType.DARK, 0.5);
        put(PokemonType.GHOST, PokemonType.NORMAL, 0.0);

        // Hệ rồng
        put(PokemonType.DRAGON, PokemonType.DRAGON, 2.0);
        put(PokemonType.DRAGON, PokemonType.STEEL, 0.5);
        put(PokemonType.DRAGON, PokemonType.STEEL, 0.0);

        // Hệ tiên
        put(PokemonType.FAIRY, PokemonType.FIGHTING, 2.0);
        put(PokemonType.FAIRY, PokemonType.DRAGON, 2.0);
        put(PokemonType.FAIRY, PokemonType.DARK, 2.0);
        put(PokemonType.FAIRY, PokemonType.FIRE, 0.5);
        put(PokemonType.FAIRY, PokemonType.POISON, 0.5);
        put(PokemonType.FAIRY, PokemonType.STEEL, 0.5);
        
        // Hệ bóng tối
        put(PokemonType.DARK, PokemonType.PSYCHIC, 2.0);
        put(PokemonType.DARK, PokemonType.GHOST, 2.0);
        put(PokemonType.DARK, PokemonType.FIGHTING, 0.5);
        put(PokemonType.DARK, PokemonType.DARK, 0.5);
        put(PokemonType.DARK, PokemonType.FAIRY, 0.5);
        
        // Hệ bọ
        put(PokemonType.BUG, PokemonType.GRASS, 2.0);
        put(PokemonType.BUG, PokemonType.PSYCHIC, 2.0);
        put(PokemonType.BUG, PokemonType.DARK, 2.0);
        put(PokemonType.BUG, PokemonType.FIRE, 0.5);
        put(PokemonType.BUG, PokemonType.FIGHTING, 0.5);
        put(PokemonType.BUG, PokemonType.POISON, 0.5);
        put(PokemonType.BUG, PokemonType.FLYING, 0.5);
        put(PokemonType.BUG, PokemonType.GHOST, 0.5);
        put(PokemonType.BUG, PokemonType.STEEL, 0.5);
        put(PokemonType.BUG, PokemonType.FAIRY, 0.5);
        
        // Hệ băng
        put(PokemonType.ICE, PokemonType.GRASS, 2.0);
        put(PokemonType.ICE, PokemonType.GROUND, 2.0);
        put(PokemonType.ICE, PokemonType.FLYING, 2.0);
        put(PokemonType.ICE, PokemonType.DRAGON, 2.0);
        put(PokemonType.ICE, PokemonType.GRASS, 2.0);
        put(PokemonType.ICE, PokemonType.FIRE, 0.5);
        put(PokemonType.ICE, PokemonType.WATER, 0.5);
        put(PokemonType.ICE, PokemonType.ICE, 0.5);
        put(PokemonType.ICE, PokemonType.STEEL, 0.5);
        
        //Hệ thường
        put(PokemonType.NORMAL, PokemonType.ROCK, 0.5);
        put(PokemonType.NORMAL, PokemonType.STEEL, 0.5);
        put(PokemonType.NORMAL, PokemonType.GHOST, 0.0);
        
        // Hệ giác đấu
        put(PokemonType.FIGHTING, PokemonType.NORMAL, 2.0);
        put(PokemonType.FIGHTING, PokemonType.ICE, 2.0);
        put(PokemonType.FIGHTING, PokemonType.ROCK, 2.0);
        put(PokemonType.FIGHTING, PokemonType.DARK, 2.0);
        put(PokemonType.FIGHTING, PokemonType.STEEL, 2.0);
        put(PokemonType.FIGHTING, PokemonType.POISON, 0.5);
        put(PokemonType.FIGHTING, PokemonType.FLYING, 0.5);
        put(PokemonType.FIGHTING, PokemonType.PSYCHIC, 0.5);
        put(PokemonType.FIGHTING, PokemonType.BUG, 0.5);
        put(PokemonType.FIGHTING, PokemonType.FAIRY, 0.5);
        put(PokemonType.FIGHTING, PokemonType.GHOST, 0.0);
        
        // Hệ độc
        put(PokemonType.POISON, PokemonType.GRASS, 2.0);
        put(PokemonType.POISON, PokemonType.FAIRY, 2.0);
        put(PokemonType.POISON, PokemonType.POISON, 0.5);
        put(PokemonType.POISON, PokemonType.GROUND, 0.5);
        put(PokemonType.POISON, PokemonType.ROCK, 0.5);
        put(PokemonType.POISON, PokemonType.GHOST, 0.5);
        put(PokemonType.POISON, PokemonType.STEEL, 0.5);
        

    }

    private static void put(PokemonType atk, PokemonType def, double mult) {
        CHART.get(atk).put(def, mult);
    }

    // ============================================
    // UC4.5.2-3: LẤY TYPE EFFECTIVENESS
    // ============================================
    
    /**
     * UC4.5.2: Overload 1 - Lấy effectiveness cho 1 type
     * @param attackType Loại của nước đi (ví dụ: ELECTRIC)
     * @param defType Loại của Pokemon phòng thủ (ví dụ: WATER)
     * @return Multiplier: 0.0 (không hiệu quả), 0.5 (yếu), 1.0 (trung), 2.0 (mạnh)
     */
    public static double getMultiplier(PokemonType attackType, PokemonType defType) {
        if (attackType == null || defType == null) return 1.0;
        return CHART.getOrDefault(attackType, Map.of()).getOrDefault(defType, 1.0);
    }
    
    /**
     * UC4.5.3: Overload 2 - Lấy effectiveness cho Dual Type
     * Pokemon có 2 loại → tính theo công thức: m1 × m2
     * 
     * Ví dụ:
     *   - Electric move vs Water/Flying type
     *   - m1 = Electric vs Water = 2.0
     *   - m2 = Electric vs Flying = 2.0
     *   - Kết quả = 2.0 × 2.0 = 4.0 (gấp 4 lần!)
     * 
     * @param attackType Loại của nước đi
     * @param def1 Loại thứ nhất của Pokemon phòng thủ
     * @param def2 Loại thứ hai của Pokemon phòng thủ (có thể null)
     * @return Multiplier (tích của cả 2 type)
     */
    public static double getMultiplier(PokemonType attackType, PokemonType def1, PokemonType def2) {
        if (attackType == null || def1 == null) return 1.0;
        
        // Lấy effectiveness vs type thứ 1
        double m1 = CHART.getOrDefault(attackType, Map.of()).getOrDefault(def1, 1.0);
        
        // Lấy effectiveness vs type thứ 2 (nếu có)
        double m2 = 1.0;
        if (def2 != null) {
            m2 = CHART.getOrDefault(attackType, Map.of()).getOrDefault(def2, 1.0);
        }
        
        // Trả về tích của 2 multiplier
        return m1 * m2;
    }
}
