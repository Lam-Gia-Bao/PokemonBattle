package view;

import javax.swing.*;
import java.awt.*;
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
    private final BattleView battleView;

    public CommandView(BattleController controller, Pokemon player, PokemonTeam playerTeam, MessageView messageView, BattleView battleView) {
        this.controller = controller;
        this.message = messageView;
        this.battleView = battleView;
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

        // ============================================
        // UC4.1: NGƯỜI CHƠI CHỌN NƯỚC ĐI
        // ============================================
        for (int i = 0; i < moveButtons.length; i++) {
            final int idx = i;
            Move move = player.getMoves().get(i);
            PixelCommandButton.Theme moveTheme = getTypeTheme(move.getType());
            String ppInfo = "PP:" + move.getPp() + "/" + move.getMaxPp();
            String powerInfo = String.valueOf(move.getPower());
            moveButtons[i] = new PixelCommandButton(move.getName(), ppInfo, powerInfo, moveTheme);
            moveButtons[i].setFont(new Font("Arial", Font.BOLD, 18));
            
            // UC4.1.1: Xử lý sự kiện click nút nước đi
            moveButtons[i].addActionListener(e -> {
                showMovePanel(false);                   // Ẩn panel nước đi
                enableMainButtons(true);                // Hiển thị lại 4 nút chính
                
                // UC4.1.2: Gọi BattleController.playerMove(moveIndex)
                controller.playerMove(idx);
            });
            movePanel.add(moveButtons[i]);
        }

        add(movePanel);
        
        // ============================================================
        // TIẾN ĐỘ COMMIT 1: KHỞI TẠO PANEL POKÉMON MỚI
        // ============================================================
        pokemonPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        pokemonPanel.setBounds(10, 10, 400, 140);
        pokemonPanel.setOpaque(false);
        pokemonPanel.setVisible(false);
        add(pokemonPanel); // Đã nằm an toàn bên trong Constructor
        
        // Quét đội hình và dựng giao diện (HP Bar, Highlight) lần đầu tiên
        updatePokemonPanel(playerTeam);

        // Sự kiện nút chính
        fightBtn.addActionListener(e -> {
            showMovePanel(true);
            enableMainButtons(false);
        });
        bagBtn.addActionListener(e -> {
            disableAll();
            battleView.showTypeChart();
        });
        pokeBtn.addActionListener(e -> {
            // Cập nhật lại lượng HP và trạng thái mới nhất ngay khi ấn nút mở bảng
            updatePokemonPanel(playerTeam); 
            showPokemonPanel(true);
            enableMainButtons(false);
        });
        runBtn.addActionListener(e -> this.message.showCannotRunMessage());
    } // Kết thúc Constructor chính chuẩn xác

    // ============================================================
    // TIẾN ĐỘ COMMIT 1: HÀM CẬP NHẬT ĐỘNG (LIST 4 POKEMON + HIGHLIGHT + HP BAR)
    // ============================================================
    public void updatePokemonPanel(PokemonTeam playerTeam) {
        pokemonPanel.removeAll(); // Xóa các thành phần cũ để vẽ lại dữ liệu mới nhất
        pokemonButtons = new JButton[playerTeam.getTeamSize()];
        
        for (int i = 0; i < pokemonButtons.length; i++) {
            final int idx = i;
            Pokemon poke = playerTeam.getTeam().get(i);
            
            // 1. HIGHLIGHT STATUS (Sống / Hạ gục)
            String status = poke.isFainted() ? " [FAINTED]" : " [ALIVE]";
            PixelCommandButton.Theme pokeTheme = poke.isFainted() ? PixelCommandButton.Theme.GRAY : getTypeTheme(poke.getType());
            
            pokemonButtons[i] = new PixelCommandButton(poke.getName() + status, pokeTheme);
            pokemonButtons[i].setFont(new Font("Arial", Font.BOLD, 14)); // Resize nhẹ để khít với thanh HP
            pokemonButtons[i].setEnabled(!poke.isFainted() && idx != playerTeam.getCurrentIndex());
            
            pokemonButtons[i].addActionListener(e -> {
                showPokemonPanel(false);
                enableMainButtons(true);
                controller.switchPokemon(idx);
                updateMovePanel(controller.getPlayerTeam().getCurrentPokemon());
            });
            
            // 2. SHOW HP BAR CHO MỖI POKEMON (JProgressBar)
            JProgressBar hpBar = new JProgressBar(0, poke.getMaxHp());
            hpBar.setValue(poke.getHp());
            hpBar.setStringPainted(true);
            hpBar.setString(poke.getHp() + " / " + poke.getMaxHp());
            hpBar.setFont(new Font("Arial", Font.PLAIN, 10));
            hpBar.setPreferredSize(new Dimension(100, 14));
            
            // Đổi màu sắc thanh HP trực quan theo % máu hiện tại
            if (poke.isFainted()) {
                hpBar.setForeground(Color.DARK_GRAY);
            } else {
                double hpPercent = (double) poke.getHp() / poke.getMaxHp();
                if (hpPercent > 0.5) {
                    hpBar.setForeground(new Color(46, 204, 113)); // Xanh lá
                } else if (hpPercent > 0.2) {
                    hpBar.setForeground(new Color(241, 196, 15));  // Vàng
                } else {
                    hpBar.setForeground(new Color(231, 76, 60));   // Đỏ
                }
            }
            
            // 3. GỘP NÚT BẤM VÀ HP BAR THÀNH MỘT Ô LƯỚI TRỰC QUAN
            JPanel cellPanel = new JPanel(new BorderLayout(0, 2));
            cellPanel.setOpaque(false);
            cellPanel.add(pokemonButtons[i], BorderLayout.CENTER);
            cellPanel.add(hpBar, BorderLayout.SOUTH);
            
            pokemonPanel.add(cellPanel);
        }
        
        pokemonPanel.revalidate();
        pokemonPanel.repaint();
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
        for (JButton b : moveButtons) if (b != null) b.setEnabled(false);
        for (JButton b : pokemonButtons) if (b != null) b.setEnabled(false);
    }
    
    public void updateMoveButtons(Pokemon pokemon) {
        for (int i = 0; i < moveButtons.length && i < pokemon.getMoves().size(); i++) {
            Move move = pokemon.getMoves().get(i);
            PixelCommandButton.Theme moveTheme = getTypeTheme(move.getType());
            String ppInfo = "PP:" + move.getPp() + "/" + move.getMaxPp();
            String powerInfo = String.valueOf(move.getPower());
            
            final int idx = i;
            PixelCommandButton newBtn = new PixelCommandButton(move.getName(), ppInfo, powerInfo, moveTheme);
            newBtn.setFont(new Font("Arial", Font.BOLD, 18));
            newBtn.addActionListener(e -> {
                showMovePanel(false);
                enableMainButtons(true);
                controller.playerMove(idx);
            });
            
            movePanel.remove(moveButtons[i]);
            moveButtons[i] = newBtn;
            movePanel.add(newBtn, i);
        }
        movePanel.revalidate();
        movePanel.repaint();
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

    private PixelCommandButton.Theme getTypeTheme(PokemonType type) {
        switch (type) {
            case NORMAL: return PixelCommandButton.Theme.TYPE_NORMAL;
            case FIRE: return PixelCommandButton.Theme.TYPE_FIRE;
            case WATER: return PixelCommandButton.Theme.TYPE_WATER;
            case GRASS: return PixelCommandButton.Theme.TYPE_GRASS;
            case ELECTRIC: return PixelCommandButton.Theme.TYPE_ELECTRIC;
            case ICE: return PixelCommandButton.Theme.TYPE_ICE;
            case FIGHTING: return PixelCommandButton.Theme.TYPE_FIGHTING;
            case POISON: return PixelCommandButton.Theme.TYPE_POISON;
            case GROUND: return PixelCommandButton.Theme.TYPE_GROUND;
            case FLYING: return PixelCommandButton.Theme.TYPE_FLYING;
            case PSYCHIC: return PixelCommandButton.Theme.TYPE_PSYCHIC;
            case BUG: return PixelCommandButton.Theme.TYPE_BUG;
            case ROCK: return PixelCommandButton.Theme.TYPE_ROCK;
            case GHOST: return PixelCommandButton.Theme.TYPE_GHOST;
            case DRAGON: return PixelCommandButton.Theme.TYPE_DRAGON;
            case DARK: return PixelCommandButton.Theme.TYPE_DARK;
            case STEEL: return PixelCommandButton.Theme.TYPE_STEEL;
            case FAIRY: return PixelCommandButton.Theme.TYPE_FAIRY;
            default: return PixelCommandButton.Theme.GRAY;
        }
    }
}