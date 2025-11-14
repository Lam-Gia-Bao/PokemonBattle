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
                // Tất cả message trong hàng đợi đã hiển thị, cho phép chơi tiếp
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
    
    // Bắt đầu trận đấu - Player chọn pokemon
    public void showPlayerPokemonSelected(Pokemon pokemon) {
        setMessage("Player đã chọn " + pokemon.getName() + ".");
    }
    
    // Bắt đầu trận đấu - AI chọn pokemon
    public void showAIPokemonSelected(Pokemon pokemon) {
        setMessage("AI đã chọn " + pokemon.getName() + ".");
    }
    
    // Khi sẵn sàng chiến đấu
    public void showPokemonEnter(Pokemon pokemon) {
        setMessage("Tiến lên! " + pokemon.getName() + "!");
    }
    
    // Chờ hành động từ người chơi
    public void showWaitingForAction(Pokemon playerPokemon) {
        setMessage(playerPokemon.getName() + " sẽ làm gì?");
    }
    
    // Tấn công - sử dụng tuyệt chiêu
    public void showUsingMove(Pokemon attacker, String moveName) {
        setMessage(attacker.getName() + " đã sử dụng tuyệt chiêu " + moveName + ".");
    }
    
    // Gây sát thương
    public void showDamageDealt(int damage) {
        setMessage("Đã gây " + damage + " sát thương!");
    }
    
    // Pokemon bị hạ gục
    public void showPokemonFainted(Pokemon pokemon) {
        setMessage(pokemon.getName() + " đã bị hạ gục!");
    }
    
    // Đổi Pokemon
    public void showPokemonSwitched(Pokemon pokemon, boolean isPlayerSide) {
        String side = isPlayerSide ? "Player" : "AI";
        setMessage(side + " đã lựa chọn " + pokemon.getName() + ".");
    }
    
    // Đánh giá pokemon cũ khi đổi pokemon
    public void showPokemonPraise(Pokemon oldPokemon) {
        setMessage("Làm tốt lắm! " + oldPokemon.getName() + "!");
    }
    
    // Đổi pokemon mới
    public void showNewPokemonEnter(Pokemon newPokemon) {
        setMessage("Tiến lên! " + newPokemon.getName() + "!");
    }
    
    // Message lỗi khi không thể đổi pokemon
    public void showCannotSwitchMessage() {
        setMessage("Không thể đổi Pokémon này!");
    }
    
    // Message cho nút BAG
    public void showNoBagItemsMessage() {
        setMessage("Bạn không có vật phẩm nào!");
    }
    
    // Message cho nút RUN
    public void showCannotRunMessage() {
        setMessage("Không thể bỏ cuộc trước một huấn luyện viên!");
    }
    
    // Message Win
    public void showWinMessage() {
        setMessage("Bạn đã chiến thắng!");
    }
    
    // Message Lose
    public void showLoseMessage() {
        setMessage("Bạn đã thua...");
    }
    
    // Hàng đợi message cho tấn công
    public void queueUsingMoveMessage(Pokemon attacker, String moveName) {
        addMessageToQueue(attacker.getName() + " đã sử dụng tuyệt chiêu " + moveName  + ".");
    }
    
    public void queueDamageMessage(int damage, String moveName) {
        addMessageToQueue(moveName + " đã gây " + damage + " sát thương!");
    }
    
    public void queuePokemonFaintedMessage(Pokemon pokemon) {
        addMessageToQueue(pokemon.getName() + " đã bị hạ gục!");
    }
    
    public void queueAIPokemonSelectedMessage(Pokemon pokemon) {
        addMessageToQueue("AI đã chọn " + pokemon.getName() + ".");
    }
    
    public void queuePlayerPokemonSelectedMessage(Pokemon pokemon) {
        addMessageToQueue("Player đã lựa chọn " + pokemon.getName() + ".");
    }
    
    public void queuePokemonEnterMessage(Pokemon pokemon) {
        addMessageToQueue("Tiến lên! " + pokemon.getName() + "!");
    }
    
    public void queuePokemonPraiseMessage(Pokemon pokemon) {
        addMessageToQueue("Làm tốt lắm! " + pokemon.getName() + "!");
    }
    
    public void queueWinMessage() {
        addMessageToQueue("Bạn đã chiến thắng!");
    }
    
    public void queueLoseMessage() {
        addMessageToQueue("Bạn đã thua...");
    }
}
