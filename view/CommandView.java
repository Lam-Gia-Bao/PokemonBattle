package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import model.Move;
import model.Pokemon;
import model.PokemonTeam;
import model.PokemonType;
import controller.BattleController;

public class CommandView extends JPanel {
    private JButton fightBtn, bagBtn, pokeBtn, runBtn;
    private JPanel movePanel;
    private JButton[] moveButtons;
    private JPanel pokemonPanel;
    private JButton[] pokemonButtons;
    private final BattleController controller;
    private final MessageView message;

    public CommandView(BattleController controller, Pokemon player, PokemonTeam playerTeam, MessageView messageView) {
        this.controller = controller;
        this.message = messageView;
        setLayout(null);
        setOpaque(false);
        setBounds(820, 520, 410, 160);

        int btnW = 180, btnH = 50;
        fightBtn = createButton("FIGHT", 15, 15, btnW, btnH);
        bagBtn = createButton("BAG", 220, 15, btnW, btnH);
        pokeBtn = createButton("POKÉMON", 15, 75, btnW, btnH);
        runBtn = createButton("RUN", 220, 75, btnW, btnH);

        add(fightBtn);
        add(bagBtn);
        add(pokeBtn);
        add(runBtn);

        // Panel chiêu thức
        movePanel = new JPanel(new GridLayout(2, 2, 8, 8));
        movePanel.setBounds(10, 10, 400, 140);
        movePanel.setOpaque(false);
        movePanel.setVisible(false);
        moveButtons = new JButton[player.getMoves().size()];

        for (int i = 0; i < moveButtons.length; i++) {
            final int idx = i;
            Move move = player.getMoves().get(i);
            PixelCommandButton.Theme moveTheme = getTypeTheme(move.getType());
            String ppInfo = "PP:" + move.getPp() + "/" + move.getMaxPp();
            String powerInfo = String.valueOf(move.getPower());
            moveButtons[i] = new PixelCommandButton(move.getName(), ppInfo, powerInfo, moveTheme);
            moveButtons[i].setFont(new Font("Arial", Font.BOLD, 18));
            moveButtons[i].addActionListener(e -> {
                showMovePanel(false);
                enableMainButtons(true);
                controller.playerMove(idx);
            });
            movePanel.add(moveButtons[i]);
        }

        add(movePanel);
        
        // Panel lựa chọn pokemon
        pokemonPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        pokemonPanel.setBounds(10, 10, 400, 140);
        pokemonPanel.setOpaque(false);
        pokemonPanel.setVisible(false);
        pokemonButtons = new JButton[playerTeam.getTeamSize()];
        
        for (int i = 0; i < pokemonButtons.length; i++) {
            final int idx = i;
            Pokemon poke = playerTeam.getTeam().get(i);
            String status = poke.isFainted() ? " (FAINTED)" : "";
            pokemonButtons[i] = new PixelCommandButton(poke.getName() + status, PixelCommandButton.Theme.GRAY);
            pokemonButtons[i].setFont(new Font("Arial", Font.BOLD, 16));
            pokemonButtons[i].setEnabled(!poke.isFainted() && idx != playerTeam.getCurrentIndex());
            
            pokemonButtons[i].addActionListener(e -> {
                showPokemonPanel(false);
                enableMainButtons(true);
                controller.switchPokemon(idx);
                updateMovePanel(controller.getPlayerTeam().getCurrentPokemon());
            });
            pokemonPanel.add(pokemonButtons[i]);
        }
        
        add(pokemonPanel);

        // Sự kiện nút chính
        fightBtn.addActionListener(e -> {
            showMovePanel(true);
            enableMainButtons(false);
        });
        bagBtn.addActionListener(e -> this.message.showNoBagItemsMessage());
        pokeBtn.addActionListener(e -> {
            showPokemonPanel(true);
            enableMainButtons(false);
        });
        runBtn.addActionListener(e -> this.message.showCannotRunMessage());
    }

    private JButton createButton(String text, int x, int y, int w, int h) {
        PixelCommandButton.Theme theme;
        switch (text) {
            case "FIGHT":
                theme = PixelCommandButton.Theme.PINK;
                break;
            case "BAG":
                theme = PixelCommandButton.Theme.ORANGE;
                break;
            case "POKÉMON":
                theme = PixelCommandButton.Theme.GREEN;
                break;
            case "RUN":
                theme = PixelCommandButton.Theme.BLUE;
                break;
            default:
                theme = PixelCommandButton.Theme.GRAY;
        }
        PixelCommandButton btn = new PixelCommandButton(text, theme);
        btn.setFont(new Font("Arial", Font.BOLD, 22));
        btn.setBounds(x, y, w, h);
        return btn;
    }

    public void showMovePanel(boolean show) {
        fightBtn.setVisible(!show);
        bagBtn.setVisible(!show);
        pokeBtn.setVisible(!show);
        runBtn.setVisible(!show);
        
        movePanel.setVisible(show);
        if (show && moveButtons != null) {
            // ensure move buttons are enabled when showing the panel
            for (JButton b : moveButtons) {
                if (b != null)
                    b.setEnabled(true);
            }
        }
        repaint();
    }
    
    public void showPokemonPanel(boolean show) {
        fightBtn.setVisible(!show);
        bagBtn.setVisible(!show);
        pokeBtn.setVisible(!show);
        runBtn.setVisible(!show);
        
        pokemonPanel.setVisible(show);
        repaint();
    }

    public void enableMainButtons(boolean enable) {
        fightBtn.setEnabled(enable);
        bagBtn.setEnabled(enable);
        pokeBtn.setEnabled(enable);
        runBtn.setEnabled(enable);
    }

    // Cho phép người chơi tương tác khi hết message và đến lượt chơi
    public void enablePlayerInteraction() {
        enableMainButtons(true);
        if (moveButtons != null) {
            for (JButton b : moveButtons) if (b != null) b.setEnabled(true);
        }
        if (pokemonButtons != null) {
            for (JButton b : pokemonButtons) if (b != null) b.setEnabled(true);
        }
    }

    public void disableAll() {
        enableMainButtons(false);
        for (JButton b : moveButtons) b.setEnabled(false);
        for (JButton b : pokemonButtons) b.setEnabled(false);
    }
    
    public void updateMovePanel(Pokemon pokemon) {
        movePanel.removeAll();
        moveButtons = new JButton[pokemon.getMoves().size()];
        
        for (int i = 0; i < moveButtons.length; i++) {
            final int idx = i;
            Move move = pokemon.getMoves().get(i);
            PixelCommandButton.Theme moveTheme = getTypeTheme(move.getType());
            String ppInfo = "PP:" + move.getPp() + "/" + move.getMaxPp();
            String powerInfo = String.valueOf(move.getPower());
            moveButtons[i] = new PixelCommandButton(move.getName(), ppInfo, powerInfo, moveTheme);
            moveButtons[i].setFont(new Font("Arial", Font.BOLD, 18));
            moveButtons[i].addActionListener(e -> {
                showMovePanel(false);
                enableMainButtons(true);
                controller.playerMove(idx);
            });
            movePanel.add(moveButtons[i]);
        }
        
        movePanel.revalidate();
        movePanel.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background với viền pixelated
        int pad = 4;
        int arc = 28;
        g2.setColor(new Color(70, 70, 70));
        g2.fillRoundRect(pad, pad, getWidth() - pad * 2, getHeight() - pad * 2, arc, arc);
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(pad + 2, pad + 2, getWidth() - (pad + 2) * 2, getHeight() - (pad + 2) * 2, arc - 6, arc - 6);
        g2.setColor(new Color(110, 110, 110));
        g2.drawRoundRect(pad + 3, pad + 3, getWidth() - (pad + 3) * 2, getHeight() - (pad + 3) * 2, arc - 6, arc - 6);

        g2.dispose();
    }

    // Lấy theme theo hệ Pokémon
    private PixelCommandButton.Theme getTypeTheme(PokemonType type) {
        switch (type) {
            case NORMAL:
                return PixelCommandButton.Theme.GRAY;
            case FIRE:
                return PixelCommandButton.Theme.ORANGE;
            case WATER:
                return PixelCommandButton.Theme.BLUE;
            case GRASS:
                return PixelCommandButton.Theme.GREEN;
            case ELECTRIC:
                return PixelCommandButton.Theme.YELLOW;
            case ICE:
                return PixelCommandButton.Theme.LIGHT_BLUE;
            case FIGHTING:
                return PixelCommandButton.Theme.DARK_RED;
            case POISON:
                return PixelCommandButton.Theme.PURPLE;
            case GROUND:
                return PixelCommandButton.Theme.BROWN;
            case FLYING:
                return PixelCommandButton.Theme.LIGHT_PURPLE;
            case PSYCHIC:
                return PixelCommandButton.Theme.PINK;
            case BUG:
                return PixelCommandButton.Theme.LIGHT_GREEN;
            case ROCK:
                return PixelCommandButton.Theme.DARK_BROWN;
            case GHOST:
                return PixelCommandButton.Theme.DARK_PURPLE;
            case DRAGON:
                return PixelCommandButton.Theme.BLUE;
            case DARK:
                return PixelCommandButton.Theme.DARK_BROWN;
            case STEEL:
                return PixelCommandButton.Theme.SILVER;
            case FAIRY:
                return PixelCommandButton.Theme.LIGHT_PURPLE;
            default:
                return PixelCommandButton.Theme.GRAY;
        }
    }

    // Custom retro-styled button
    static class PixelCommandButton extends JButton {
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

        PixelCommandButton(String text, Theme theme) {
            super(text);
            this.theme = theme;
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setOpaque(false);
            setRolloverEnabled(true);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        PixelCommandButton(String text, String ppText, Theme theme) {
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

        PixelCommandButton(String text, String ppText, String powerText, Theme theme) {
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

            // Outer bezel
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
}
