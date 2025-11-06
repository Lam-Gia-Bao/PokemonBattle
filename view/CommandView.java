package view;

import javax.swing.*;
import java.awt.*;
import model.Move;
import model.Pokemon;
import controller.BattleController;

public class CommandView extends JPanel {
    private JButton fightBtn, bagBtn, pokeBtn, runBtn;
    private JPanel movePanel;
    private JButton[] moveButtons;
    private MessageView message;

    public CommandView(BattleController controller, Pokemon player) {
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

        // Sự kiện nút chính
        fightBtn.addActionListener(e -> {
            showMovePanel(true);
            enableMainButtons(false);
        });
        bagBtn.addActionListener(e -> message.setMessage("You have no items!"));
        pokeBtn.addActionListener(e -> message.setMessage("No other Pokémon available!"));
        runBtn.addActionListener(e -> message.setMessage("Can't run from a trainer battle!"));
    }

    private JButton createButton(String text, int x, int y, int w, int h) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setBounds(x, y, w, h);
        return btn;
    }

    public void showMovePanel(boolean show) {
        movePanel.setVisible(show);
    }

    public void enableMainButtons(boolean enable) {
        fightBtn.setEnabled(enable);
        bagBtn.setEnabled(enable);
        pokeBtn.setEnabled(enable);
        runBtn.setEnabled(enable);
    }

    public void disableAll() {
        enableMainButtons(false);
        for (JButton b : moveButtons) b.setEnabled(false);
    }
}
