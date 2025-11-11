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
			backgroundImg = ImageIO.read(new File("src/resources/background.png"));
			playerImg = ImageIO.read(new File("src/resources/pikachu_back.png"));
			playerImg = ImageIO.read(new File("src/resources/suicune.png"));
			playerImg = ImageIO.read(new File("src/resources/charizard.png"));
			playerImg = ImageIO.read(new File("src/resources/greninja.png"));
			playerImg = ImageIO.read(new File("src/resources/magearna.png"));
			playerImg = ImageIO.read(new File("src/resources/haxorus.png"));
			playerImg = ImageIO.read(new File("src/resources/sceptile.png"));
			playerImg = ImageIO.read(new File("src/resources/scizor.png"));
			playerImg = ImageIO.read(new File("src/resources/vileplume-f.png"));
			playerImg = ImageIO.read(new File("src/resources/nidoking.png"));
			playerImg = ImageIO.read(new File("src/resources/jirachi.png"));
			playerImg = ImageIO.read(new File("src/resources/gyarados.png"));
			playerImg = ImageIO.read(new File("src/resources/zekrom.png"));
			playerImg = ImageIO.read(new File("src/resources/reshiram.png"));
			playerImg = ImageIO.read(new File("src/resources/kyurem.png"));
			playerImg = ImageIO.read(new File("src/resources/snorlax.png"));
			playerImg = ImageIO.read(new File("src/resources/arceus-normal.png"));
			playerImg = ImageIO.read(new File("src/resources/lapras.png"));
			playerImg = ImageIO.read(new File("src/resources/drapion.png"));
			playerImg = ImageIO.read(new File("src/resources/absol.png"));
			playerImg = ImageIO.read(new File("src/resources/tyranitar.png"));
			playerImg = ImageIO.read(new File("src/resources/archeops.png"));
			playerImg = ImageIO.read(new File("src/resources/machamp.png"));
			playerImg = ImageIO.read(new File("src/resources/lucario.png"));
			playerImg = ImageIO.read(new File("src/resources/golem.png"));
			playerImg = ImageIO.read(new File("src/resources/rhydon.png"));
			playerImg = ImageIO.read(new File("src/resources/gardevoir.png"));
			playerImg = ImageIO.read(new File("src/resources/volcarona.png"));
			
			aiImg = ImageIO.read(new File("src/resources/pidgey_front.png"));
			aiImg = ImageIO.read(new File("src/resources/steelix.png"));
			aiImg = ImageIO.read(new File("src/resources/gengar.png"));
			aiImg = ImageIO.read(new File("src/resources/giratina-altered.png"));
			aiImg = ImageIO.read(new File("src/resources/serperior.png"));
			aiImg = ImageIO.read(new File("src/resources/diancie.png"));
			aiImg = ImageIO.read(new File("src/resources/mewtwo.png"));
			aiImg = ImageIO.read(new File("src/resources/rayquaza.png"));
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
