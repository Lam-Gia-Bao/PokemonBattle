package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;

public class MenuView extends JFrame {
    private JButton startButton;
    private JButton exitButton;
    private JPanel backgroundPanel;

    public MenuView() {
        initUI();
    }

    private void initUI() {
        setTitle("Pokemon Battle - Menu");
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        // Tạo panel với background
        backgroundPanel = new JPanel() {
            private Image backgroundImage;

            {
                try {
                    backgroundImage = ImageIO.read(new File("resources/background.png"));
                } catch (Exception e) {
                    System.out.println("Error loading menu background: " + e.getMessage());
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

        backgroundPanel.setLayout(null);

        // Tạo label tiêu đề "Pokemon Battle"
        JLabel titleLabel = new JLabel("POKÉMON BATTLE") {
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

                // Vẽ border vàng (Pikachu color)
                g2.setColor(new Color(255, 215, 0));
                g2.setStroke(new BasicStroke(4));
                for (int i = 0; i < 3; i++) {
                    g2.drawString(getText(), x - 2 + i, y);
                    g2.drawString(getText(), x, y - 2 + i);
                    g2.drawString(getText(), x + 2 + i, y);
                    g2.drawString(getText(), x, y + 2 + i);
                }

                // Vẽ chữ chính (đỏ và xanh gradient Pokemon)
                g2.setColor(new Color(255, 0, 0));
                g2.drawString(getText(), x, y);

                // Vẽ text bình thường
                super.paintComponent(g);
                g2.dispose();
            }
        };
        titleLabel.setFont(new Font("Arial", Font.BOLD, 80));
        titleLabel.setForeground(new Color(0, 0, 255));
        titleLabel.setBounds(200, 80, 880, 120);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setOpaque(false);

        // Tạo nút "Bắt Đầu"
        startButton = new PixelCommandButton("Bắt Đầu", PixelCommandButton.Theme.GREEN);
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setBounds(490, 280, 300, 80);
        startButton.setFocusPainted(false);

        // Tạo nút "Thoát"
        exitButton = new PixelCommandButton("Thoát", PixelCommandButton.Theme.RED);
        exitButton.setFont(new Font("Arial", Font.BOLD, 24));
        exitButton.setBounds(490, 400, 300, 80);
        exitButton.setFocusPainted(false);

        backgroundPanel.add(titleLabel);
        backgroundPanel.add(startButton);
        backgroundPanel.add(exitButton);

        add(backgroundPanel);
        setVisible(true);
    }

    public void addStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    public void addExitButtonListener(ActionListener listener) {
        exitButton.addActionListener(listener);
    }

    public void closeMenu() {
        dispose();
    }
}
