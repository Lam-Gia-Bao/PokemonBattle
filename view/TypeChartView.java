package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import model.PokemonType;

public class TypeChartView extends JPanel {
    private BufferedImage chartImage;
    private BattleView battleView;
    private JButton closeBtn;

    public TypeChartView(Runnable onClose, BattleView battleView) {
        this.battleView = battleView;
        setOpaque(false);
        setLayout(null);
        
        // Load ảnh bảng khắc hệ
        try {
            chartImage = ImageIO.read(new File("resources/type_chart.png"));
        } catch (Exception e) {
            System.out.println("Error loading type chart image: " + e.getMessage());
        }
        
        // Kích thước màn hình game
        int screenWidth = 1280;
        int screenHeight = 720;
        
        if (chartImage != null) {
            // Phóng to ảnh lên 1.3 lần
            double scale = 1.3;
            int imgWidth = (int)(chartImage.getWidth() * scale);
            int imgHeight = (int)(chartImage.getHeight() * scale);
            
            // Căn giữa ảnh
            int x = (screenWidth - imgWidth) / 2;
            int y = (screenHeight - imgHeight) / 2;
            
            // Set bounds cho panel chứa ảnh + nút X
            setBounds(x - 25, y - 25, imgWidth + 50, imgHeight + 50);
            
            // Nút X ở góc trên phải
            closeBtn = new JButton("X");
            closeBtn.setBounds(imgWidth + 10, 0, 40, 40);
            closeBtn.setFont(new Font("Arial", Font.BOLD, 22));
            closeBtn.setFocusPainted(false);
            closeBtn.setBackground(new Color(220, 60, 60));
            closeBtn.setForeground(Color.WHITE);
            closeBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            closeBtn.addActionListener(e -> {
                setVisible(false);
                battleView.hideTypeChart();
                // Khôi phục toàn bộ nút
                battleView.getCommand().enablePlayerInteraction();
            });
            add(closeBtn);
        } else {
            setBounds(190, 60, 900, 600);
            
            closeBtn = new JButton("X");
            closeBtn.setBounds(910, 0, 40, 40);
            closeBtn.setFont(new Font("Arial", Font.BOLD, 22));
            closeBtn.setFocusPainted(false);
            closeBtn.setBackground(new Color(220, 60, 60));
            closeBtn.setForeground(Color.WHITE);
            closeBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            closeBtn.addActionListener(e -> {
                setVisible(false);
                battleView.hideTypeChart();
                battleView.getCommand().enablePlayerInteraction();
            });
            add(closeBtn);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (chartImage != null) {
            // Phóng to ảnh 1.3 lần và vẽ ở vị trí offset 25px
            double scale = 1.3;
            int scaledWidth = (int)(chartImage.getWidth() * scale);
            int scaledHeight = (int)(chartImage.getHeight() * scale);
            g.drawImage(chartImage, 25, 25, scaledWidth, scaledHeight, this);
        } else {
            // Fallback nếu ảnh không load được
            g.setColor(new Color(240, 240, 240));
            g.fillRect(25, 25, getWidth() - 50, getHeight() - 50);
            g.setColor(Color.BLACK);
            g.drawString("Type Chart Image not found", 35, 45);
        }
    }
}
