package view;

import controller.BattleController;
import java.awt.*;
import javax.swing.*;
import model.Inventory;
import model.InventorySlot;
import model.Move;
import model.Pokemon;
import model.PokemonTeam;
import model.PokemonType;

public class CommandView extends JPanel {
    private JButton fightBtn, bagBtn, pokeBtn, runBtn;
    private JPanel movePanel;
    private JButton[] moveButtons;
    private JPanel pokemonPanel;
    private JButton[] pokemonButtons;
    private JPanel bagPanel;
    private JButton[] itemButtons;
    private final BattleController controller;
    private final Inventory inventory;
    private final MessageView message;
    private final BattleView battleView;

    public CommandView(BattleController controller, Pokemon player, PokemonTeam playerTeam, Inventory inventory, MessageView messageView, BattleView battleView) {
        this.controller = controller;
        this.inventory = inventory;
        this.message = messageView;
        this.battleView = battleView;
        
        setLayout(null);
        setOpaque(false);
        setBounds(820, 520, 410, 160);

        // Khởi tạo và định vị 4 nút hành động chính
        int btnW = 180, btnH = 50;
        fightBtn = createButton("FIGHT", 15, 15, btnW, btnH);
        bagBtn = createButton("BAG", 220, 15, btnW, btnH);
        pokeBtn = createButton("POKÉMON", 15, 75, btnW, btnH);
        runBtn = createButton("RUN", 220, 75, btnW, btnH);

        add(fightBtn);
        add(bagBtn);
        add(pokeBtn);
        add(runBtn);

        // Khởi tạo panel chứa danh sách chiêu thức (mặc định ẩn)
        movePanel = new JPanel(new GridLayout(2, 2, 8, 8));
        movePanel.setBounds(10, 10, 400, 140);
        movePanel.setOpaque(false);
        movePanel.setVisible(false);
        moveButtons = new JButton[player.getMoves().size()];

        // Đổ dữ liệu chiêu thức của Pokemon hiện tại lên các nút bấm
        for (int i = 0; i < moveButtons.length; i++) {
            final int idx = i;
            Move move = player.getMoves().get(i);
            PixelCommandButton.Theme moveTheme = getTypeTheme(move.getType());
            String ppInfo = "PP:" + move.getPp() + "/" + move.getMaxPp();
            String powerInfo = String.valueOf(move.getPower());
            
            moveButtons[i] = new PixelCommandButton(move.getName(), ppInfo, powerInfo, moveTheme);
            moveButtons[i].setFont(new Font("Arial", Font.BOLD, 18));
            
            moveButtons[i].addActionListener(e -> {
                showMovePanel(false);                   // Chọn chiêu xong thì ẩn bảng chiêu thức đi
                enableMainButtons(true);                // Bật lại tương tác cho 4 nút chính
                controller.playerMove(idx);             // Gửi index chiêu thức sang cho controller xử lý trận đấu
            });
            movePanel.add(moveButtons[i]);
        }
        add(movePanel);
        
        // Khởi tạo panel hiển thị đội hình Pokemon trong sub-menu
        pokemonPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        pokemonPanel.setBounds(10, 10, 400, 140);
        pokemonPanel.setOpaque(false);
        pokemonPanel.setVisible(false);
        add(pokemonPanel);
        
        // Vẽ giao diện danh sách Pokemon (máu, trạng thái) lần đầu tiên
        updatePokemonPanel(playerTeam);

        bagPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        bagPanel.setBounds(10, 10, 400, 140);
        bagPanel.setOpaque(false);
        bagPanel.setVisible(false);
        add(bagPanel);
        updateBagPanel();

        // Lắng nghe sự kiện click trên 4 nút điều hướng chính
        fightBtn.addActionListener(e -> {
            showMovePanel(true);
            enableMainButtons(false);
        });
        
        bagBtn.addActionListener(e -> {
            if (!inventory.hasUsableItems()) {
                battleView.showNoBagItemsMessage();
                return;
            }
            updateBagPanel();
            showBagPanel(true);
            enableMainButtons(false);
        });
        
        pokeBtn.addActionListener(e -> {
            updatePokemonPanel(playerTeam);     // Cập nhật lại HP và trạng thái mới nhất trước khi hiển thị bảng
            showPokemonPanel(true);
            enableMainButtons(false);
        });
        
        runBtn.addActionListener(e -> this.message.showCannotRunMessage());
    } 

    public void updateBagPanel() {
        bagPanel.removeAll();
        int itemCount = Math.min(4, inventory.getSlots().size());
        itemButtons = new JButton[itemCount];

        for (int i = 0; i < itemCount; i++) {
            final int idx = i;
            InventorySlot slot = inventory.getSlots().get(i);
            String qtyInfo = "x" + slot.getQuantity();

            itemButtons[i] = new PixelCommandButton(slot.getItem().getName(), qtyInfo, slot.getItem().getDescription(), PixelCommandButton.Theme.ORANGE);
            itemButtons[i].setFont(new Font("Arial", Font.BOLD, 16));
            itemButtons[i].setEnabled(slot.hasStock());

            itemButtons[i].addActionListener(e -> {
                showBagPanel(false);
                enableMainButtons(true);
                controller.playerUseItem(idx);
            });

            bagPanel.add(itemButtons[i]);
        }

        bagPanel.revalidate();
        bagPanel.repaint();
    }

    /**
     * Làm mới toàn bộ UI của bảng đổi Pokemon (Tên, Trạng thái sống/ngất, Thanh HP Bar)
     */
    public void updatePokemonPanel(PokemonTeam playerTeam) {
        pokemonPanel.removeAll(); // Xóa UI cũ để render lại dữ liệu real-time
        pokemonButtons = new JButton[playerTeam.getTeamSize()];
        
        for (int i = 0; i < pokemonButtons.length; i++) {
            final int idx = i;
            Pokemon poke = playerTeam.getTeam().get(i);
            
            // Xử lý nhãn trạng thái và giao diện nút dựa trên việc Pokemon còn khả năng chiến đấu hay không
            String status = poke.isFainted() ? " [FAINTED]" : " [ALIVE]";
            PixelCommandButton.Theme pokeTheme = poke.isFainted() ? PixelCommandButton.Theme.GRAY : getTypeTheme(poke.getType());
            
            pokemonButtons[i] = new PixelCommandButton(poke.getName() + status, pokeTheme);
            pokemonButtons[i].setFont(new Font("Arial", Font.BOLD, 14)); 
            
            // Khóa nút nếu Pokemon đã ngất hoặc chính là Pokemon đang tham chiến trên sân
            pokemonButtons[i].setEnabled(!poke.isFainted() && idx != playerTeam.getCurrentIndex());
            
            pokemonButtons[i].addActionListener(e -> {
                showPokemonPanel(false);
                enableMainButtons(true);
                controller.switchPokemon(idx);
                updateMovePanel(controller.getPlayerTeam().getCurrentPokemon());
            });
            
            // Khởi tạo thanh HP trực quan (JProgressBar) bên dưới nút bấm tương ứng
            JProgressBar hpBar = new JProgressBar(0, poke.getMaxHp());
            hpBar.setValue(poke.getHp());
            hpBar.setStringPainted(true);
            hpBar.setString(poke.getHp() + " / " + poke.getMaxHp());
            hpBar.setFont(new Font("Arial", Font.PLAIN, 10));
            hpBar.setPreferredSize(new Dimension(100, 14));
            
            // Phân loại màu sắc thanh HP theo lượng máu hiện tại (Xanh > 50%, Vàng > 20%, Đỏ < 20%)
            if (poke.isFainted()) {
                hpBar.setForeground(Color.DARK_GRAY);
            } else {
                double hpPercent = (double) poke.getHp() / poke.getMaxHp();
                if (hpPercent > 0.5) {
                    hpBar.setForeground(new Color(46, 204, 113));
                } else if (hpPercent > 0.2) {
                    hpBar.setForeground(new Color(241, 196, 15));
                } else {
                    hpBar.setForeground(new Color(231, 76, 60));
                }
            }
            
            // Đóng gói nút bấm và thanh HP của Pokemon vào một ô Grid chung
            JPanel cellPanel = new JPanel(new BorderLayout(0, 2));
            cellPanel.setOpaque(false);
            cellPanel.add(pokemonButtons[i], BorderLayout.CENTER);
            cellPanel.add(hpBar, BorderLayout.SOUTH);
            
            pokemonPanel.add(cellPanel);
        }
        
        pokemonPanel.revalidate();
        pokemonPanel.repaint();
    }

    /**
     * Helper tạo nhanh các nút chức năng chính với bảng màu chuẩn Pixel Theme
     */
    private JButton createButton(String text, int x, int y, int w, int h) {
        PixelCommandButton.Theme theme;
        switch (text) {
            case "FIGHT":   theme = PixelCommandButton.Theme.PINK; break;
            case "BAG":     theme = PixelCommandButton.Theme.ORANGE; break;
            case "POKÉMON": theme = PixelCommandButton.Theme.GREEN; break;
            case "RUN":     theme = PixelCommandButton.Theme.BLUE; break;
            default:        theme = PixelCommandButton.Theme.GRAY;
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
        if (bagPanel != null) bagPanel.setVisible(false);
        if (pokemonPanel != null) pokemonPanel.setVisible(false);
        if (show && moveButtons != null) {
            for (JButton b : moveButtons) {
                if (b != null) b.setEnabled(true);
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
        if (bagPanel != null) bagPanel.setVisible(false);
        if (movePanel != null) movePanel.setVisible(false);
        repaint();
    }

    public void showBagPanel(boolean show) {
        fightBtn.setVisible(!show);
        bagBtn.setVisible(!show);
        pokeBtn.setVisible(!show);
        runBtn.setVisible(!show);

        bagPanel.setVisible(show);
        if (movePanel != null) movePanel.setVisible(false);
        if (pokemonPanel != null) pokemonPanel.setVisible(false);
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
        if (itemButtons != null) {
            for (int i = 0; i < itemButtons.length; i++) {
                InventorySlot slot = inventory.getSlot(i);
                if (itemButtons[i] != null && slot != null) {
                    itemButtons[i].setEnabled(slot.hasStock());
                }
            }
        }
    }

    public void disableAll() {
        enableMainButtons(false);
        for (JButton b : moveButtons) if (b != null) b.setEnabled(false);
        for (JButton b : pokemonButtons) if (b != null) b.setEnabled(false);
        if (itemButtons != null) {
            for (JButton b : itemButtons) if (b != null) b.setEnabled(false);
        }
    }
    
    /**
     * Đồng bộ lại thông tin chiêu thức khi Pokemon hiện tại thay đổi chỉ số PP
     */
    public void updateMoveButtons(Pokemon pokemon) {
        Pokemon aiPokemon = controller.getAiTeam().getCurrentPokemon();

        for (int i = 0; i < moveButtons.length && i < pokemon.getMoves().size(); i++) {
            Move move = pokemon.getMoves().get(i);
            PixelCommandButton.Theme moveTheme = getTypeTheme(move.getType());
            String ppInfo = "PP:" + move.getPp() + "/" + move.getMaxPp();
            String powerInfo = String.valueOf(move.getPower());

            boolean isUsable = move.isUsable();
            if (!isUsable) moveTheme = PixelCommandButton.Theme.GRAY;

            final int idx = i;
            PixelCommandButton newBtn = new PixelCommandButton(move.getName(), ppInfo, powerInfo, moveTheme);
            newBtn.setFont(new Font("Arial", Font.BOLD, 18));
            newBtn.setEnabled(isUsable);

            if (!isUsable) {
                newBtn.setToolTipText("Chiêu thức này đã hết PP!");
            } else if (aiPokemon != null && move.getPower() > 0 && !move.isHealingMove()) {
                int estDamage = move.calculateDamage(pokemon, aiPokemon);
                double eff = model.TypeEffectiveness.getMultiplier(move.getType(), aiPokemon.getType1(), aiPokemon.getType2());
                String effText = "Bình thường";
                String color = "white";
                if (eff > 1.0) { effText = "Siêu hiệu quả!"; color = "#00FF00"; }
                else if (eff < 1.0 && eff > 0) { effText = "Không hiệu quả lắm..."; color = "#FF9900"; }
                else if (eff == 0) { effText = "Không có tác dụng!"; color = "#FF0000"; }

                newBtn.setToolTipText(String.format(
                        "<html><body style='background-color:#333; color:white; padding:5px; font-family:Arial;'>" +
                                "<b>Sát thương dự kiến:</b> %d HP<br>" +
                                "<b>Độ hiệu quả:</b> <span style='color:%s;'>%s (x%.2f)</span>" +
                                "</body></html>", estDamage, color, effText, eff));
            } else if (move.isHealingMove()) {
                newBtn.setToolTipText("<html><body style='background-color:#333; color:white; padding:5px;'>Hồi phục " + move.getHealAmount() + " HP</body></html>");
            }

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
    /**
     * Reset và dựng lại toàn bộ danh sách chiêu thức mới khi đổi sang Pokemon khác
     */
    public void updateMovePanel(Pokemon pokemon) {
        movePanel.removeAll();
        moveButtons = new JButton[pokemon.getMoves().size()];

        // Lấy Pokemon của AI hiện tại để tính toán sát thương dự tính
        Pokemon aiPokemon = controller.getAiTeam().getCurrentPokemon();

        for (int i = 0; i < moveButtons.length; i++) {
            final int idx = i;
            Move move = pokemon.getMoves().get(i);

            PixelCommandButton.Theme moveTheme = getTypeTheme(move.getType());
            String ppInfo = "PP:" + move.getPp() + "/" + move.getMaxPp();
            String powerInfo = String.valueOf(move.getPower());

            // Xử lý khi chiêu thức hết PP (Tô xám và khóa nút)
            boolean isUsable = move.isUsable();
            if (!isUsable) {
                moveTheme = PixelCommandButton.Theme.GRAY;
            }

            moveButtons[i] = new PixelCommandButton(move.getName(), ppInfo, powerInfo, moveTheme);
            moveButtons[i].setFont(new Font("Arial", Font.BOLD, 18));
            moveButtons[i].setEnabled(isUsable);

            // Cài đặt hiển thị Tooltip (Sát thương và độ hiệu quả)
            if (!isUsable) {
                moveButtons[i].setToolTipText("Chiêu thức này đã hết PP!");
            } else if (aiPokemon != null && move.getPower() > 0 && !move.isHealingMove()) {
                int estDamage = move.calculateDamage(pokemon, aiPokemon);
                double eff = model.TypeEffectiveness.getMultiplier(move.getType(), aiPokemon.getType1(), aiPokemon.getType2());

                String effText = "Bình thường";
                String color = "white";
                if (eff > 1.0) { effText = "Siêu hiệu quả!"; color = "#00FF00"; }
                else if (eff < 1.0 && eff > 0) { effText = "Không hiệu quả lắm..."; color = "#FF9900"; }
                else if (eff == 0) { effText = "Không có tác dụng!"; color = "#FF0000"; }

                // Dùng mã HTML để trang trí hộp Tooltip cho xịn xò
                String tooltip = String.format(
                        "<html><body style='background-color:#333; color:white; padding:5px; font-family:Arial;'>" +
                                "<b>Sát thương dự kiến:</b> %d HP<br>" +
                                "<b>Độ hiệu quả:</b> <span style='color:%s;'>%s (x%.2f)</span>" +
                                "</body></html>",
                        estDamage, color, effText, eff
                );
                moveButtons[i].setToolTipText(tooltip);
            } else if (move.isHealingMove()) {
                moveButtons[i].setToolTipText("<html><body style='background-color:#333; color:white; padding:5px;'>Hồi phục " + move.getHealAmount() + " HP</body></html>");
            }

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

        // Vẽ khung nền bo góc giả lập giao diện máy game cầm tay (Pixel/Retro Style)
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

    /**
     * Mapper chuyển đổi hệ thuộc tính của Pokemon sang bảng màu Theme tương ứng cho UI nút bấm
     */
    private PixelCommandButton.Theme getTypeTheme(PokemonType type) {
        switch (type) {
            case NORMAL:   return PixelCommandButton.Theme.TYPE_NORMAL;
            case FIRE:     return PixelCommandButton.Theme.TYPE_FIRE;
            case WATER:    return PixelCommandButton.Theme.TYPE_WATER;
            case GRASS:    return PixelCommandButton.Theme.TYPE_GRASS;
            case ELECTRIC: return PixelCommandButton.Theme.TYPE_ELECTRIC;
            case ICE:      return PixelCommandButton.Theme.TYPE_ICE;
            case FIGHTING: return PixelCommandButton.Theme.TYPE_FIGHTING;
            case POISON:   return PixelCommandButton.Theme.TYPE_POISON;
            case GROUND:   return PixelCommandButton.Theme.TYPE_GROUND;
            case FLYING:   return PixelCommandButton.Theme.TYPE_FLYING;
            case PSYCHIC:  return PixelCommandButton.Theme.TYPE_PSYCHIC;
            case BUG:      return PixelCommandButton.Theme.TYPE_BUG;
            case ROCK:     return PixelCommandButton.Theme.TYPE_ROCK;
            case GHOST:    return PixelCommandButton.Theme.TYPE_GHOST;
            case DRAGON:   return PixelCommandButton.Theme.TYPE_DRAGON;
            case DARK:     return PixelCommandButton.Theme.TYPE_DARK;
            case STEEL:    return PixelCommandButton.Theme.TYPE_STEEL;
            case FAIRY:    return PixelCommandButton.Theme.TYPE_FAIRY;
            default:       return PixelCommandButton.Theme.GRAY;
        }
    }
}
