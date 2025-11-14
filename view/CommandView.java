package view;

import javax.swing.*;
import java.awt.*;
import model.Move;
import model.Pokemon;
import model.PokemonTeam;
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
        setBounds(820, 470, 440, 180);

        int btnW = 180, btnH = 50;
        fightBtn = createButton("FIGHT", 10, 10, btnW, btnH);
        bagBtn = createButton("BAG", 230, 10, btnW, btnH);
        pokeBtn = createButton("POKÉMON", 10, 70, btnW, btnH);
        runBtn = createButton("RUN", 230, 70, btnW, btnH);

        add(fightBtn);
        add(bagBtn);
        add(pokeBtn);
        add(runBtn);

        // Panel chiêu thức
        movePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        movePanel.setBounds(0, 0, 440, 130);
        movePanel.setVisible(false);
        moveButtons = new JButton[player.getMoves().size()];

        for (int i = 0; i < moveButtons.length; i++) {
            final int idx = i;
            Move move = player.getMoves().get(i);
            moveButtons[i] = new JButton(move.getName());
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
        pokemonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        pokemonPanel.setBounds(0, 0, 440, 130);
        pokemonPanel.setVisible(false);
        pokemonButtons = new JButton[playerTeam.getTeamSize()];
        
        for (int i = 0; i < pokemonButtons.length; i++) {
            final int idx = i;
            Pokemon poke = playerTeam.getTeam().get(i);
            String status = poke.isFainted() ? " (FAINTED)" : "";
            pokemonButtons[i] = new JButton(poke.getName() + status);
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
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setBounds(x, y, w, h);
        return btn;
    }

    public void showMovePanel(boolean show) {
        movePanel.setVisible(show);
        if (show && moveButtons != null) {
            // ensure move buttons are enabled when showing the panel
            for (JButton b : moveButtons) {
                if (b != null)
                    b.setEnabled(true);
            }
        }
    }
    
    public void showPokemonPanel(boolean show) {
        pokemonPanel.setVisible(show);
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
            moveButtons[i] = new JButton(move.getName());
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
}
