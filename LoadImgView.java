package view;

import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Pokemon;

public class LoadImgView extends JPanel {

    private Image backgroundImg;
    private Image playerImg;
    private Image aiImg;

    public LoadImgView() {
        try {
            backgroundImg = ImageIO.read(
                getClass().getResourceAsStream("/resources/background.png")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        setLayout(null);
    }

    public void loadPokemonImages(Pokemon player, Pokemon ai) {
        try {
            if (player != null) {
                String name = player.getName().toLowerCase();
                playerImg = ImageIO.read(
                    getClass().getResourceAsStream("/resources/" + name + "_back.png")
                );
            }

            if (ai != null) {
                String name = ai.getName().toLowerCase();
                aiImg = ImageIO.read(
                    getClass().getResourceAsStream("/resources/" + name + "_front.png")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImg != null)
            g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);

        if (playerImg != null)
            g.drawImage(playerImg, 200, 300, 300, 300, this);

        if (aiImg != null)
            g.drawImage(aiImg, 850, 100, 250, 250, this);
    }
}