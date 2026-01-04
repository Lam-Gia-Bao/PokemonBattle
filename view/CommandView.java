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
            PixelCommandButton.Theme pokeTheme = getTypeTheme(poke.getType());
            pokemonButtons[i] = new PixelCommandButton(poke.getName() + status, pokeTheme);
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
            // Kích hoạt tất cả các nút chiêu thức
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
    
    // Cập nhật PP của các move buttons mà không tạo lại toàn bộ panel
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
                return PixelCommandButton.Theme.TYPE_NORMAL;
            case FIRE:
                return PixelCommandButton.Theme.TYPE_FIRE;
            case WATER:
                return PixelCommandButton.Theme.TYPE_WATER;
            case GRASS:
                return PixelCommandButton.Theme.TYPE_GRASS;
            case ELECTRIC:
                return PixelCommandButton.Theme.TYPE_ELECTRIC;
            case ICE:
                return PixelCommandButton.Theme.TYPE_ICE;
            case FIGHTING:
                return PixelCommandButton.Theme.TYPE_FIGHTING;
            case POISON:
                return PixelCommandButton.Theme.TYPE_POISON;
            case GROUND:
                return PixelCommandButton.Theme.TYPE_GROUND;
            case FLYING:
                return PixelCommandButton.Theme.TYPE_FLYING;
            case PSYCHIC:
                return PixelCommandButton.Theme.TYPE_PSYCHIC;
            case BUG:
                return PixelCommandButton.Theme.TYPE_BUG;
            case ROCK:
                return PixelCommandButton.Theme.TYPE_ROCK;
            case GHOST:
                return PixelCommandButton.Theme.TYPE_GHOST;
            case DRAGON:
                return PixelCommandButton.Theme.TYPE_DRAGON;
            case DARK:
                return PixelCommandButton.Theme.TYPE_DARK;
            case STEEL:
                return PixelCommandButton.Theme.TYPE_STEEL;
            case FAIRY:
                return PixelCommandButton.Theme.TYPE_FAIRY;
            default:
                return PixelCommandButton.Theme.GRAY;
        }
    }
}
