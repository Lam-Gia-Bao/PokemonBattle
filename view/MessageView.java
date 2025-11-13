package view;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import model.Pokemon;

public class MessageView extends JPanel {
    private JTextArea messageBox;
    private JLabel clickToContinueLabel;
    private Queue<String> messageQueue;
    private Runnable onQueueComplete;
    private boolean isWaiting;

    public MessageView(String initialMessage) {
        setLayout(null);
        setOpaque(false);
        setBounds(50, 600, 1180, 80);

        messageBox = new JTextArea(initialMessage);
        messageBox.setEditable(false);
        messageBox.setBackground(new Color(255, 255, 255, 230));
        messageBox.setFont(new Font("Monospaced", Font.BOLD, 22));
        messageBox.setLineWrap(true);
        messageBox.setWrapStyleWord(true);
        messageBox.setBounds(0, 0, 1180, 80);

        clickToContinueLabel = new JLabel("[Click to continue...]");
        clickToContinueLabel.setFont(new Font("Monospaced", Font.ITALIC, 14));
        clickToContinueLabel.setForeground(Color.GRAY);
        clickToContinueLabel.setBounds(1000, 50, 200, 20);
        clickToContinueLabel.setVisible(false);

        messageQueue = new LinkedList<>();
        isWaiting = false;

        add(messageBox);
        add(clickToContinueLabel);

        // Click để hiện message tiếp theo - trên JPanel
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (isWaiting) {
                    showNextMessage();
                }
            }
        });
        
        // Click để hiện message tiếp theo - trên JTextArea
        messageBox.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (isWaiting) {
                    showNextMessage();
                }
            }
        });
        
        // Click để hiện message tiếp theo - trên Label
        clickToContinueLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (isWaiting) {
                    showNextMessage();
                }
            }
        });
    }

    public void setMessage(String text) {
        messageBox.setText(text);
        clickToContinueLabel.setVisible(false);
        isWaiting = false;
    }
    
    public void addMessageToQueue(String message) {
        messageQueue.offer(message);
    }
    
    public void startMessageQueue(Runnable onComplete) {
        this.onQueueComplete = onComplete;
        if (!messageQueue.isEmpty()) {
            showNextMessage();
        } else if (onComplete != null) {
            onComplete.run();
        }
    }
    
    private void showNextMessage() {
        if (!messageQueue.isEmpty()) {
            String nextMessage = messageQueue.poll();
            setMessage(nextMessage);
            
            if (!messageQueue.isEmpty()) {
                isWaiting = true;
                clickToContinueLabel.setVisible(true);
            } else {
                // Tất cả message đã hiển thị, cho phép chơi tiếp
                clickToContinueLabel.setVisible(false);
                isWaiting = false;
                if (onQueueComplete != null) {
                    onQueueComplete.run();
                }
            }
        }
    }
    
    public void clearQueue() {
        messageQueue.clear();
        clickToContinueLabel.setVisible(false);
        isWaiting = false;
    }
    
    // Message liên quan đến chiêu thức tấn công
    public void showAttackMessage(Pokemon attacker, String moveName, int damage) {
        setMessage(attacker.getName() + " used " + moveName + "! It dealt " + damage + " damage!");
    }
    
    // Message khi pokemon bị hạ gục và đối thủ có pokemon khác
    public void showPokemonFaintedWithReplacement(Pokemon faintedPokemon, Pokemon nextPokemon, boolean isOpponent) {
        if (isOpponent) {
            setMessage(faintedPokemon.getName() + " fainted! Opponent sends out " + nextPokemon.getName() + "!");
        } else {
            setMessage(faintedPokemon.getName() + " fainted! Go " + nextPokemon.getName() + "!");
        }
    }
    
    // Message khi pokemon bị hạ gục và không còn pokemon khác
    public void showGameOverMessage(Pokemon faintedPokemon, boolean playerWon) {
        if (playerWon) {
            setMessage(faintedPokemon.getName() + " fainted! You win!");
        } else {
            setMessage(faintedPokemon.getName() + " fainted! You lost...");
        }
    }
    
    // Message khi đổi pokemon
    public void showSwitchPokemonMessage(Pokemon newPokemon) {
        setMessage("Go " + newPokemon.getName() + "!");
    }
    
    // Message lỗi khi không thể đổi pokemon
    public void showCannotSwitchMessage() {
        setMessage("Can't switch to that Pokémon!");
    }
    
    // Message cho nút BAG
    public void showNoBagItemsMessage() {
        setMessage("You have no items!");
    }
    
    // Message cho nút RUN
    public void showCannotRunMessage() {
        setMessage("Can't run from a trainer battle!");
    }
    
    // Message khởi đầu trận đấu
    public void showStartBattleMessage(Pokemon playerPokemon) {
        setMessage("What will " + playerPokemon.getName() + " do?");
    }
}
