package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;

public class ResultView extends JFrame {
    private JButton replayButton;
    private JButton selectPokemonButton;
    private JButton menuButton;
    private JButton exitButton;
    private boolean isVictory;
    private Runnable onReplay;
    private Runnable onSelectPokemon;
    private Runnable onMenu;

    public ResultView(boolean isVictory) {
        this.isVictory = isVictory;
        initUI();
    }

    private void initUI() {
        setTitle("Pokemon Battle - Kết Quả");
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        // Main panel với background
        JPanel mainPanel = new JPanel() {
            private Image backgroundImage;

            {
                try {
                    backgroundImage = ImageIO.read(new File("resources/background.png"));
                } catch (Exception e) {
                    System.out.println("Error loading background: " + e.getMessage());
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        mainPanel.setLayout(null);

        // Tiêu đề kết quả
        JLabel resultLabel = new JLabel(isVictory ? "BẠN ĐÃ CHIẾN THẮNG!" : "BẠN ĐÃ THUA CUỘC!") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();

                // Vẽ shadow (đen)
                g2.setColor(new Color(0, 0, 0, 150));
                g2.setFont(new Font("Arial", Font.BOLD, 80));
                FontMetrics fm = g2.getFontMetrics();
                int x = (w - fm.stringWidth(getText())) / 2;
                int y = ((h - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(getText(), x + 4, y + 4);

                // Vẽ border vàng
                g2.setColor(new Color(255, 215, 0));
                for (int i = 0; i < 3; i++) {
                    g2.drawString(getText(), x - 2 + i, y);
                    g2.drawString(getText(), x, y - 2 + i);
                    g2.drawString(getText(), x + 2 + i, y);
                    g2.drawString(getText(), x, y + 2 + i);
                }

                // Vẽ chữ chính
                if (isVictory) {
                    g2.setColor(new Color(0, 180, 0)); // Xanh cho chiến thắng
                } else {
                    g2.setColor(new Color(255, 0, 0)); // Đỏ cho thua cuộc
                }
                g2.drawString(getText(), x, y);

                super.paintComponent(g);
                g2.dispose();
            }
        };
        resultLabel.setFont(new Font("Arial", Font.BOLD, 80));
        resultLabel.setForeground(isVictory ? new Color(0, 180, 0) : new Color(255, 0, 0));
        resultLabel.setBounds(100, 80, 1080, 150);
        resultLabel.setHorizontalAlignment(JLabel.CENTER);
        resultLabel.setOpaque(false);

        mainPanel.add(resultLabel);

        // Panel chứa các nút
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBounds(250, 350, 780, 250);

        // Nút "Chơi lại"
        replayButton = new PixelCommandButton("Chơi Lại", PixelCommandButton.Theme.BLUE);
        replayButton.setFont(new Font("Arial", Font.BOLD, 20));
        replayButton.addActionListener(e -> {
            if (onReplay != null) onReplay.run();
            closeResult();
        });
        buttonPanel.add(replayButton);

        // Nút "Chọn Đội Hình Lại"
        selectPokemonButton = new PixelCommandButton("Chọn Đội Hình Lại", PixelCommandButton.Theme.ORANGE);
        selectPokemonButton.setFont(new Font("Arial", Font.BOLD, 20));
        selectPokemonButton.addActionListener(e -> {
            if (onSelectPokemon != null) onSelectPokemon.run();
            closeResult();
        });
        buttonPanel.add(selectPokemonButton);

        // Nút "Quay về Menu"
        menuButton = new PixelCommandButton("Quay về Menu", PixelCommandButton.Theme.PINK);
        menuButton.setFont(new Font("Arial", Font.BOLD, 20));
        menuButton.addActionListener(e -> {
            if (onMenu != null) onMenu.run();
            closeResult();
        });
        buttonPanel.add(menuButton);

        // Nút "Thoát"
        exitButton = new PixelCommandButton("Thoát", PixelCommandButton.Theme.RED);
        exitButton.setFont(new Font("Arial", Font.BOLD, 20));
        exitButton.addActionListener(e -> System.exit(0));
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel);

        add(mainPanel);
        setVisible(true);
    }

    public void setOnReplayListener(Runnable runnable) {
        this.onReplay = runnable;
    }

    public void setOnSelectPokemonListener(Runnable runnable) {
        this.onSelectPokemon = runnable;
    }

    public void setOnMenuListener(Runnable runnable) {
        this.onMenu = runnable;
    }

    public void closeResult() {
        dispose();
    }
}
