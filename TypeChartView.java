package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class TypeChartView extends JPanel {

    private BufferedImage chartImage;
    private BattleView battleView;
    private JButton closeBtn;

    public TypeChartView(Runnable onClose, BattleView battleView) {
        this.battleView = battleView;
        setOpaque(false);
        setLayout(null);

        // ✅ LOAD ẢNH ĐÚNG CÁCH (CLASSPATH)
        try {
            chartImage = ImageIO.read(
                getClass().getResourceAsStream("/resources/type_chart.png")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        int screenWidth = 1280;
        int screenHeight = 720;

        if (chartImage != null) {
            double scale = 1.3;
            int imgWidth = (int) (chartImage.getWidth() * scale);
            int imgHeight = (int) (chartImage.getHeight() * scale);

            int x = (screenWidth - imgWidth) / 2;
            int y = (screenHeight - imgHeight) / 2;

            setBounds(x - 25, y - 25, imgWidth + 50, imgHeight + 50);

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
                battleView.getCommand().enableMainButtons(true);
            });
            add(closeBtn);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (chartImage != null) {
            double scale = 1.3;
            int w = (int) (chartImage.getWidth() * scale);
            int h = (int) (chartImage.getHeight() * scale);
            g.drawImage(chartImage, 25, 25, w, h, this);
        } else {
            g.setColor(new Color(240, 240, 240));
            g.fillRect(25, 25, getWidth() - 50, getHeight() - 50);
            g.setColor(Color.BLACK);
            g.drawString("Type Chart Image not found", 35, 45);
        }
    }
}