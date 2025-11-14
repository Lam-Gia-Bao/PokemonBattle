package view;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Pokemon;

public class LoadImgView extends JPanel {
	private Image backgroundImg;
	private Image playerImg;
	private Image aiImg;
	
	public LoadImgView() {
		try {
			backgroundImg = ImageIO.read(new File("src/resources/background.png"));
		} catch (Exception e) {
			System.out.println("Error loading images: " + e.getMessage());
		}
		setLayout(null);
	}
	
	public void loadPokemonImages(Pokemon player, Pokemon ai) {
		try {
			// Load ảnh pokemon player theo định dạng tên_back.png
			if (player != null) {
				String playerName = player.getName().toLowerCase();
				playerImg = ImageIO.read(new File("src/resources/" + playerName + "_back.png"));
			}
			
			// Load ảnh pokemon AI theo định dạng tên_front.png
			if (ai != null) {
				String aiName = ai.getName().toLowerCase();
				aiImg = ImageIO.read(new File("src/resources/" + aiName + "_front.png"));
			}
		} catch (Exception e) {
			System.out.println("Error loading pokemon images: " + e.getMessage());
		}
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