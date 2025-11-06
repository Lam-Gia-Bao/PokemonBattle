package view;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class LoadImgView extends JPanel {
	private Image backgroundImg;
	private Image playerImg;
	private Image aiImg;
	
	public LoadImgView() {
		try {
			backgroundImg = ImageIO.read(new File("./resources/background.png"));
			playerImg = ImageIO.read(new File("./resources/pikachu_back.png"));
			aiImg = ImageIO.read(new File("./resources/pidgey_front.png"));
		} catch (Exception e) {
			System.out.println("Error loading images: " + e.getMessage());
		}
		setLayout(null);
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
