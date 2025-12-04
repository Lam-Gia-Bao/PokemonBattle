package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import model.Move;
import model.Pokemon;
import model.PokemonType;

public class AIMoveView extends JPanel {
    private Pokemon aiPokemon;

    public AIMoveView(Pokemon aiPokemon) {
        this.aiPokemon = aiPokemon;
        setLayout(new GridLayout(2, 2, 8, 8));
        setOpaque(false);
        setBounds(10, 10, 400, 140);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int pad = 4;
        int arc = 28;

        // Tầng ngoài (đen)
        g2.setColor(new Color(70, 70, 70));
        g2.fillRoundRect(pad, pad, w - pad * 2, h - pad * 2, arc, arc);

        // Tầng trắng
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(pad + 2, pad + 2, w - (pad + 2) * 2, h - (pad + 2) * 2, arc - 8, arc - 8);

        // Tầng xám
        g2.setColor(new Color(110, 110, 110));
        g2.fillRoundRect(pad + 3, pad + 3, w - (pad + 3) * 2, h - (pad + 3) * 2, arc - 10, arc - 10);

        g2.dispose();

        // Vẽ children
        super.paintComponent(g);
    }

    public void updateMoveDisplay() {
        removeAll();
        
        for (int i = 0; i < aiPokemon.getMoves().size(); i++) {
            Move move = aiPokemon.getMoves().get(i);
            AIMoveButton.Theme moveTheme = getTypeTheme(move.getType());
            String ppInfo = "PP:" + move.getPp() + "/" + move.getMaxPp();
            String powerInfo = String.valueOf(move.getPower());
            
            AIMoveButton moveBtn = new AIMoveButton(move.getName(), ppInfo, powerInfo, moveTheme);
            moveBtn.setFont(new Font("Arial", Font.BOLD, 14));
            add(moveBtn);
        }
        
        revalidate();
        repaint();
    }

    private AIMoveButton.Theme getTypeTheme(PokemonType type) {
        switch (type) {
            case NORMAL:
                return AIMoveButton.Theme.GRAY;
            case FIRE:
                return AIMoveButton.Theme.ORANGE;
            case WATER:
                return AIMoveButton.Theme.BLUE;
            case GRASS:
                return AIMoveButton.Theme.GREEN;
            case ELECTRIC:
                return AIMoveButton.Theme.YELLOW;
            case ICE:
                return AIMoveButton.Theme.LIGHT_BLUE;
            case FIGHTING:
                return AIMoveButton.Theme.DARK_RED;
            case POISON:
                return AIMoveButton.Theme.PURPLE;
            case GROUND:
                return AIMoveButton.Theme.BROWN;
            case FLYING:
                return AIMoveButton.Theme.LIGHT_PURPLE;
            case PSYCHIC:
                return AIMoveButton.Theme.PINK;
            case BUG:
                return AIMoveButton.Theme.LIGHT_GREEN;
            case ROCK:
                return AIMoveButton.Theme.DARK_BROWN;
            case GHOST:
                return AIMoveButton.Theme.DARK_PURPLE;
            case DRAGON:
                return AIMoveButton.Theme.BLUE;
            case DARK:
                return AIMoveButton.Theme.DARK_BROWN;
            case STEEL:
                return AIMoveButton.Theme.SILVER;
            case FAIRY:
                return AIMoveButton.Theme.LIGHT_PURPLE;
            default:
                return AIMoveButton.Theme.GRAY;
        }
    }

    public void setPokemon(Pokemon pokemon) {
        this.aiPokemon = pokemon;
        updateMoveDisplay();
    }

    public void updateMoves() {
        updateMoveDisplay();
    }

    // Custom button tương tự CommandView
    static class AIMoveButton extends JButton {
        enum Theme {
            PINK(new Color[]{new Color(255, 220, 230), new Color(255, 170, 190), new Color(240, 140, 165), new Color(255, 205, 215)}, new Color(250, 250, 250)),
            ORANGE(new Color[]{new Color(250, 205, 95), new Color(230, 170, 60), new Color(200, 140, 50), new Color(240, 190, 80)}, new Color(250, 245, 235)),
            GREEN(new Color[]{new Color(130, 205, 90), new Color(95, 175, 70), new Color(75, 150, 55), new Color(110, 190, 80)}, new Color(240, 255, 240)),
            BLUE(new Color[]{new Color(130, 185, 230), new Color(85, 150, 205), new Color(65, 120, 175), new Color(110, 165, 215)}, new Color(240, 250, 255)),
            YELLOW(new Color[]{new Color(255, 235, 115), new Color(245, 205, 65), new Color(220, 170, 50), new Color(240, 215, 90)}, new Color(50, 50, 50)),
            RED(new Color[]{new Color(240, 128, 120), new Color(220, 90, 70), new Color(200, 60, 50), new Color(230, 110, 90)}, new Color(250, 250, 250)),
            PURPLE(new Color[]{new Color(200, 160, 200), new Color(170, 110, 170), new Color(150, 80, 150), new Color(185, 130, 185)}, new Color(250, 250, 250)),
            LIGHT_BLUE(new Color[]{new Color(200, 230, 230), new Color(160, 210, 210), new Color(140, 190, 190), new Color(180, 220, 220)}, new Color(50, 50, 50)),
            BROWN(new Color[]{new Color(210, 180, 140), new Color(180, 150, 100), new Color(160, 130, 80), new Color(200, 170, 130)}, new Color(250, 250, 250)),
            LIGHT_GREEN(new Color[]{new Color(200, 220, 80), new Color(170, 190, 50), new Color(150, 170, 30), new Color(185, 205, 60)}, new Color(50, 50, 50)),
            DARK_PURPLE(new Color[]{new Color(160, 130, 200), new Color(120, 80, 160), new Color(100, 60, 140), new Color(140, 100, 180)}, new Color(250, 250, 250)),
            LIGHT_PURPLE(new Color[]{new Color(240, 160, 200), new Color(220, 120, 170), new Color(200, 90, 150), new Color(230, 140, 185)}, new Color(250, 250, 250)),
            SILVER(new Color[]{new Color(220, 220, 230), new Color(190, 190, 200), new Color(170, 170, 180), new Color(205, 205, 215)}, new Color(50, 50, 50)),
            DARK_RED(new Color[]{new Color(200, 60, 50), new Color(170, 40, 30), new Color(150, 20, 10), new Color(185, 50, 40)}, new Color(250, 250, 250)),
            DARK_BROWN(new Color[]{new Color(140, 110, 90), new Color(110, 80, 60), new Color(90, 60, 40), new Color(125, 95, 75)}, new Color(250, 250, 250)),
            GRAY(new Color[]{new Color(190, 190, 190), new Color(160, 160, 160), new Color(140, 140, 140), new Color(175, 175, 175)}, new Color(250, 250, 250));

            final Color[] bands;
            final Color text;
            Theme(Color[] bands, Color text) {
                this.bands = bands;
                this.text = text;
            }
        }

        private final Theme theme;
        private String ppText = "";
        private String powerText = "";

        AIMoveButton(String text, String ppText, String powerText, Theme theme) {
            super(text);
            this.theme = theme;
            this.ppText = ppText;
            this.powerText = powerText;
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setOpaque(false);
            setRolloverEnabled(true);
            setEnabled(false); // AI moves không clickable
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int arc = Math.min(h, 50);

            // Outer bezel
            g2.setColor(new Color(60, 60, 60));
            g2.fillRoundRect(0, 0, w, h, arc, arc);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(3, 3, w - 6, h - 6, arc - 8, arc - 8);
            g2.setColor(new Color(100, 100, 100));
            g2.fillRoundRect(5, 5, w - 10, h - 10, arc - 12, arc - 12);

            // Inner face
            int pad = 8;
            int iw = w - pad * 2;
            int ih = h - pad * 2;
            Shape clip = new RoundRectangle2D.Float(pad, pad, iw, ih, arc - 16, arc - 16);
            g2.setClip(clip);

            Color[] bands = theme.bands;
            int stripes = bands.length;
            for (int i = 0; i < stripes; i++) {
                g2.setColor(bands[i]);
                int y = pad + (ih * i) / stripes;
                int hSeg = (ih * (i + 1)) / stripes - (ih * i) / stripes;
                g2.fillRect(pad, y, iw, hSeg);
            }
            g2.setClip(null);

            // Shine line
            g2.setColor(new Color(255, 255, 255, 140));
            g2.draw(new RoundRectangle2D.Float(pad + 1, pad + 1, iw - 2, ih - 2, arc - 18, arc - 18));

            // Text
            String text = getText();
            g2.setFont(getFont());
            FontMetrics fm = g2.getFontMetrics();

            if (ppText != null && !ppText.isEmpty()) {
                Font smallFont = getFont().deriveFont((float)(getFont().getSize() - 4));

                int tx = (w - fm.stringWidth(text)) / 2;
                int ty = (h / 2 - 8) + fm.getAscent();

                g2.setColor(new Color(20, 20, 20, 220));
                int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
                int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};
                for (int i = 0; i < dx.length; i++) {
                    g2.drawString(text, tx + dx[i], ty + dy[i]);
                }
                g2.setColor(theme.text);
                g2.drawString(text, tx, ty);

                g2.setFont(smallFont);
                FontMetrics fm2 = g2.getFontMetrics();

                String infoLine = ppText;
                if (powerText != null && !powerText.isEmpty()) {
                    infoLine += " DMG:" + powerText;
                }

                int tx2 = (w - fm2.stringWidth(infoLine)) / 2;
                int ty2 = ty + fm.getHeight() - 6;

                g2.setColor(new Color(20, 20, 20, 220));
                for (int i = 0; i < dx.length; i++) {
                    g2.drawString(infoLine, tx2 + dx[i], ty2 + dy[i]);
                }
                g2.setColor(theme.text);
                g2.drawString(infoLine, tx2, ty2);
            }

            g2.dispose();
        }
    }
}
