package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class PixelCommandButton extends JButton {
    public enum Theme {
        PINK(new Color[]{new Color(255, 220, 230), new Color(255, 170, 190), new Color(240, 140, 165), new Color(255, 205, 215)}, new Color(250, 250, 250)),
        ORANGE(new Color[]{new Color(250, 205, 95), new Color(230, 170, 60), new Color(200, 140, 50), new Color(240, 190, 80)}, new Color(250, 245, 235)),
        GREEN(new Color[]{new Color(130, 205, 90), new Color(95, 175, 70), new Color(75, 150, 55), new Color(110, 190, 80)}, new Color(240, 255, 240)),
        BLUE(new Color[]{new Color(130, 185, 230), new Color(85, 150, 205), new Color(65, 120, 175), new Color(110, 165, 215)}, new Color(240, 250, 255)),
        YELLOW(new Color[]{new Color(255, 235, 115), new Color(245, 205, 65), new Color(220, 170, 50), new Color(240, 215, 90)}, new Color(250, 250, 250)),
        RED(new Color[]{new Color(240, 128, 120), new Color(220, 90, 70), new Color(200, 60, 50), new Color(230, 110, 90)}, new Color(250, 250, 250)),
        PURPLE(new Color[]{new Color(200, 160, 200), new Color(170, 110, 170), new Color(150, 80, 150), new Color(185, 130, 185)}, new Color(250, 250, 250)),
        LIGHT_BLUE(new Color[]{new Color(200, 230, 230), new Color(160, 210, 210), new Color(140, 190, 190), new Color(180, 220, 220)}, new Color(250, 250, 250)),
        BROWN(new Color[]{new Color(210, 180, 140), new Color(180, 150, 100), new Color(160, 130, 80), new Color(200, 170, 130)}, new Color(250, 250, 250)),
        LIGHT_GREEN(new Color[]{new Color(200, 220, 80), new Color(170, 190, 50), new Color(150, 170, 30), new Color(185, 205, 60)}, new Color(250, 250, 250)),
        DARK_PURPLE(new Color[]{new Color(160, 130, 200), new Color(120, 80, 160), new Color(100, 60, 140), new Color(140, 100, 180)}, new Color(250, 250, 250)),
        LIGHT_PURPLE(new Color[]{new Color(240, 160, 200), new Color(220, 120, 170), new Color(200, 90, 150), new Color(230, 140, 185)}, new Color(250, 250, 250)),
        SILVER(new Color[]{new Color(220, 220, 230), new Color(190, 190, 200), new Color(170, 170, 180), new Color(205, 205, 215)}, new Color(50, 50, 50)),
        DARK_RED(new Color[]{new Color(200, 60, 50), new Color(170, 40, 30), new Color(150, 20, 10), new Color(185, 50, 40)}, new Color(250, 250, 250)),
        DARK_BROWN(new Color[]{new Color(140, 110, 90), new Color(110, 80, 60), new Color(90, 60, 40), new Color(125, 95, 75)}, new Color(250, 250, 250)),
        GRAY(new Color[]{new Color(190, 190, 190), new Color(160, 160, 160), new Color(140, 140, 140), new Color(175, 175, 175)}, new Color(250, 250, 250)),
        // Pokemon Type Themes
        TYPE_NORMAL(new Color[]{new Color(188, 188, 140), new Color(168, 168, 120), new Color(148, 148, 100), new Color(178, 178, 130)}, new Color(250, 250, 250)),
        TYPE_FIRE(new Color[]{new Color(255, 148, 68), new Color(240, 128, 48), new Color(220, 108, 28), new Color(247, 138, 58)}, new Color(250, 250, 250)),
        TYPE_WATER(new Color[]{new Color(124, 164, 255), new Color(104, 144, 240), new Color(84, 124, 220), new Color(114, 154, 247)}, new Color(250, 250, 250)),
        TYPE_GRASS(new Color[]{new Color(140, 220, 100), new Color(120, 200, 80), new Color(100, 180, 60), new Color(130, 210, 90)}, new Color(250, 250, 250)),
        TYPE_ELECTRIC(new Color[]{new Color(255, 228, 68), new Color(248, 208, 48), new Color(228, 188, 28), new Color(251, 218, 58)}, new Color(250, 250, 250)),
        TYPE_ICE(new Color[]{new Color(172, 236, 236), new Color(152, 216, 216), new Color(132, 196, 196), new Color(162, 226, 226)}, new Color(250, 250, 250)),
        TYPE_FIGHTING(new Color[]{new Color(212, 68, 60), new Color(192, 48, 40), new Color(172, 28, 20), new Color(202, 58, 50)}, new Color(250, 250, 250)),
        TYPE_POISON(new Color[]{new Color(180, 84, 180), new Color(160, 64, 160), new Color(140, 44, 140), new Color(170, 74, 170)}, new Color(250, 250, 250)),
        TYPE_GROUND(new Color[]{new Color(244, 212, 124), new Color(224, 192, 104), new Color(204, 172, 84), new Color(234, 202, 114)}, new Color(50, 50, 50)),
        TYPE_FLYING(new Color[]{new Color(188, 164, 255), new Color(168, 144, 240), new Color(148, 124, 220), new Color(178, 154, 247)}, new Color(250, 250, 250)),
        TYPE_PSYCHIC(new Color[]{new Color(255, 108, 156), new Color(248, 88, 136), new Color(228, 68, 116), new Color(251, 98, 146)}, new Color(250, 250, 250)),
        TYPE_BUG(new Color[]{new Color(188, 204, 52), new Color(168, 184, 32), new Color(148, 164, 12), new Color(178, 194, 42)}, new Color(250, 250, 250)),
        TYPE_ROCK(new Color[]{new Color(204, 180, 76), new Color(184, 160, 56), new Color(164, 140, 36), new Color(194, 170, 66)}, new Color(250, 250, 250)),
        TYPE_GHOST(new Color[]{new Color(132, 108, 172), new Color(112, 88, 152), new Color(92, 68, 132), new Color(122, 98, 162)}, new Color(250, 250, 250)),
        TYPE_DRAGON(new Color[]{new Color(132, 76, 255), new Color(112, 56, 248), new Color(92, 36, 228), new Color(122, 66, 251)}, new Color(250, 250, 250)),
        TYPE_DARK(new Color[]{new Color(132, 108, 92), new Color(112, 88, 72), new Color(92, 68, 52), new Color(122, 98, 82)}, new Color(250, 250, 250)),
        TYPE_STEEL(new Color[]{new Color(204, 204, 228), new Color(184, 184, 208), new Color(164, 164, 188), new Color(194, 194, 218)}, new Color(50, 50, 50)),
        TYPE_FAIRY(new Color[]{new Color(255, 173, 192), new Color(238, 153, 172), new Color(218, 133, 152), new Color(246, 163, 182)}, new Color(250, 250, 250));

        final Color[] bands; // 4 banded colors top->bottom
        final Color text;
        Theme(Color[] bands, Color text) {
            this.bands = bands;
            this.text = text;
        }
    }

    private final Theme theme;
    private String ppText = "";
    private String powerText = "";

    public PixelCommandButton(String text, Theme theme) {
        super(text);
        this.theme = theme;
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        setRolloverEnabled(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public PixelCommandButton(String text, String ppText, Theme theme) {
        super(text);
        this.theme = theme;
        this.ppText = ppText;
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        setRolloverEnabled(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public PixelCommandButton(String text, String ppText, String powerText, Theme theme) {
        super(text);
        this.theme = theme;
        this.ppText = ppText;
        this.powerText = powerText;
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        setRolloverEnabled(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int arc = Math.min(h, 50);

        // Nền và viền
        g2.setColor(new Color(60, 60, 60));
        g2.fillRoundRect(0, 0, w, h, arc, arc);
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(3, 3, w - 6, h - 6, arc - 8, arc - 8);
        g2.setColor(new Color(100, 100, 100));
        g2.fillRoundRect(5, 5, w - 10, h - 10, arc - 12, arc - 12);

        // Mặt trong với gradient nhiều dải màu
        int pad = 8;
        int iw = w - pad * 2;
        int ih = h - pad * 2;
        Shape clip = new RoundRectangle2D.Float(pad, pad, iw, ih, arc - 16, arc - 16);
        g2.setClip(clip);

        Color[] bands = theme.bands;
        // Độ sáng thay đổi khi hover/press/disable
        float brighten = 1.0f;
        if (!isEnabled()) brighten = 0.6f;
        else if (getModel().isPressed()) brighten = 0.85f;
        else if (getModel().isRollover()) brighten = 1.12f;

        int stripes = bands.length;
        for (int i = 0; i < stripes; i++) {
            g2.setColor(adjust(bands[i], brighten));
            int y = pad + (ih * i) / stripes;
            int hSeg = (ih * (i + 1)) / stripes - (ih * i) / stripes;
            g2.fillRect(pad, y, iw, hSeg);
        }
        g2.setClip(null);

        // Đường sáng bên trong
        g2.setColor(new Color(255, 255, 255, 140));
        g2.draw(new RoundRectangle2D.Float(pad + 1, pad + 1, iw - 2, ih - 2, arc - 18, arc - 18));

        // Vẽ text với outline
        String text = getText();
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        
        // Nếu có PP text, vẽ trên 2 dòng
        if (ppText != null && !ppText.isEmpty()) {
            // Font cho PP nhỏ hơn
            Font smallFont = getFont().deriveFont((float)(getFont().getSize() - 4));
            
            // Tính vị trí căn giữa - đưa lên cao hơn
            int tx = (w - fm.stringWidth(text)) / 2;
            int ty = (h / 2 - 16) + fm.getAscent();
            
            // Vẽ dòng 1: tên move
            g2.setColor(new Color(20, 20, 20, isEnabled() ? 220 : 120));
            int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
            int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};
            for (int i = 0; i < dx.length; i++) {
                g2.drawString(text, tx + dx[i], ty + dy[i]);
            }
            g2.setColor(isEnabled() ? theme.text : new Color(220, 220, 220));
            g2.drawString(text, tx, ty);
            
            // Vẽ dòng 2: PP và Power
            g2.setFont(smallFont);
            FontMetrics fm2 = g2.getFontMetrics();
            
            // Ghép PP và Power
            String infoLine = ppText;
            if (powerText != null && !powerText.isEmpty()) {
                infoLine += " DMG:" + powerText;
            }
            
            int tx2 = (w - fm2.stringWidth(infoLine)) / 2;
            int ty2 = ty + fm.getHeight() - 6;
            
            g2.setColor(new Color(20, 20, 20, isEnabled() ? 220 : 120));
            for (int i = 0; i < dx.length; i++) {
                g2.drawString(infoLine, tx2 + dx[i], ty2 + dy[i]);
            }
            g2.setColor(isEnabled() ? theme.text : new Color(220, 220, 220));
            g2.drawString(infoLine, tx2, ty2);
        } else {
            // Vẽ text bình thường nếu không có PP
            int tx = (w - fm.stringWidth(text)) / 2;
            int ty = (h - fm.getHeight()) / 2 + fm.getAscent();
            
            g2.setColor(new Color(20, 20, 20, isEnabled() ? 220 : 120));
            int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
            int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};
            for (int i = 0; i < dx.length; i++) {
                g2.drawString(text, tx + dx[i], ty + dy[i]);
            }
            g2.setColor(isEnabled() ? theme.text : new Color(220, 220, 220));
            g2.drawString(text, tx, ty);
        }

        g2.dispose();
    }

    private static Color adjust(Color c, float f) {
        int r = Math.min(255, Math.max(0, (int) (c.getRed() * f)));
        int g = Math.min(255, Math.max(0, (int) (c.getGreen() * f)));
        int b = Math.min(255, Math.max(0, (int) (c.getBlue() * f)));
        return new Color(r, g, b);
    }
}
