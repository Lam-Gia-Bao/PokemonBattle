package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.Pokemon;

public class HealthBarView extends JPanel {
	private Pokemon pokemon;

	public HealthBarView(Pokemon pokemon, boolean isPlayer) {
		this.pokemon = pokemon;

		setLayout(null);
		setOpaque(false);

		//Đặt vị trí khung HP cho Player và AI
		if (isPlayer) {
			setBounds(120, 520, 300, 80);
		} else {
			setBounds(900, 60, 300, 80);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		//Hiển thị tên Pokémon
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		String label = pokemon.getName();
		g.drawString(label, 10, 20);

		//Hiển thị thanh máu
		int barX = 10;
		int barY = 35;
		int barWidth = 250;
		int barHeight = 20;

		int currHp = pokemon.getHp();
		int maxHp = pokemon.getMaxHp();

		drawHealthBar(g, currHp, maxHp, barX, barY, barWidth, barHeight);
	}

	//Thanh máu và đổi màu thanh máu theo lượng HP
	private void drawHealthBar(Graphics g, int currHp, int maxHp, int x, int y, int width, int height) {
		// Vẽ khung
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);

		// Tính phần trăm HP
		double ratio = currHp / (double) maxHp;

		// Xác định màu máu dựa trên tỉ lệ HP
		Color hpColor = getHealthColor(ratio);

		// Thanh máu đổi màu
		int hpWidth = (int) (ratio * width);
		g.setColor(hpColor);
		g.fillRect(x + 1, y + 1, hpWidth - 1, height - 1);

		// Hiển thị số HP
		g.setColor(Color.BLACK);
		g.setFont(new Font("Monospaced", Font.BOLD, 14));
		String hpText = currHp + " / " + maxHp;
		g.drawString(hpText, x + width / 2 - 30, y + height - 5);
	}
	// Hiện màu cho thanh máu (xanh → vàng → đỏ)
	private Color getHealthColor(double ratio) {
		if (ratio > 0.5) {
			// Từ xanh lá (100%) → vàng (50%)
			double t = (1.0 - ratio) * 2;
			return interpolate(Color.GREEN, Color.YELLOW, t);
		} else {
			// Từ vàng (50%) → đỏ (0%)
			double t = (0.5 - ratio) * 2; 
			return interpolate(Color.YELLOW, Color.RED, t);
		}
	}

	// Hàm pha màu cho thanh máu
	private Color interpolate(Color c1, Color c2, double t) {
		t = Math.max(0, Math.min(1, t));
		int r = (int) (c1.getRed() + (c2.getRed() - c1.getRed()) * t);
		int g = (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * t);
		int b = (int) (c1.getBlue() + (c2.getBlue() - c1.getBlue()) * t);
		return new Color(r, g, b);
	}

	// Cập nhật thanh máu
	public void updateHP() {
		repaint();
	}
	
	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
		repaint();
	}
}
