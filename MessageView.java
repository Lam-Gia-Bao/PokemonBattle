package view;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import model.Pokemon;

public class MessageView extends JPanel {
    private JTextArea messageBox;
    private JPanel dialogPanel;
    private JLabel clickToContinueLabel;
    private Queue<String> messageQueue;
    private Runnable onQueueComplete;
    private boolean isWaiting;

    public MessageView(String initialMessage) {
        setLayout(null);
        setOpaque(false);
        setBounds(50, 600, 770, 80);

        // Background panel vẽ hộp thoại theo style retro
        dialogPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                int w = getWidth();
                int h = getHeight();

                // Tầng viền ngoài (đậm)
                g2.setColor(new Color(30, 30, 30));
                g2.fillRoundRect(0, 0, w - 1, h - 1, 12, 12);

                // Viền vàng cam
                g2.setColor(new Color(240, 176, 48));
                g2.fillRoundRect(4, 4, w - 8, h - 8, 10, 10);

                // Một lớp viền tối nhỏ bên trong để tạo độ sâu
                g2.setColor(new Color(70, 70, 70));
                g2.drawRoundRect(6, 6, w - 12, h - 12, 10, 10);

                // Nền xám nhạt bên trong
                g2.setColor(new Color(232, 232, 232));
                g2.fillRoundRect(8, 8, w - 16, h - 16, 8, 8);

                // Khung trong màu xám để giống UI classic
                g2.setColor(new Color(140, 140, 140));
                g2.drawRoundRect(10, 10, w - 20, h - 20, 8, 8);

                g2.dispose();
            }
        };
        dialogPanel.setOpaque(false);
        dialogPanel.setLayout(null);
        dialogPanel.setBounds(0, 0, 770, 80);

        messageBox = new JTextArea(initialMessage);
        messageBox.setEditable(false);
        messageBox.setOpaque(false); // để thấy nền vẽ bên dưới
        messageBox.setForeground(new Color(10, 10, 10));
        messageBox.setFont(new Font("Monospaced", Font.BOLD, 22));
        messageBox.setLineWrap(true);
        messageBox.setWrapStyleWord(true);
        // Padding bên trong để chữ không dính viền
        messageBox.setBounds(20, 14, 770 - 40, 80 - 28);

        clickToContinueLabel = new JLabel("Nhấn để tiếp tục..");
        clickToContinueLabel.setFont(new Font("Monospaced", Font.PLAIN, 13));
        clickToContinueLabel.setForeground(new Color(90, 90, 90));
        // Đặt trong vùng nội dung (viền trong cách 10px): bottom-right
        clickToContinueLabel.setBounds(770 - 20 - 150, 80 - 18 - 12, 150, 18);
        clickToContinueLabel.setVisible(false);

        messageQueue = new LinkedList<>();
        isWaiting = false;

        // Thứ tự: panel nền trước, messageBox nằm bên trong panel, sau đó là label
        add(dialogPanel);
        dialogPanel.add(messageBox);
        dialogPanel.add(clickToContinueLabel);

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
    public void showDamageDealt(Pokemon pokemon, int damage) {
        setMessage(pokemon.getName() + " đã gây " + damage + " sát thương!");
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
