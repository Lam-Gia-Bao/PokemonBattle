package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import model.Pokemon;
import model.PokemonType;

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

		int currHp = pokemon.getHp();
		int maxHp = pokemon.getMaxHp();

		Graphics2D g2 = (Graphics2D) g.create();
		int w = getWidth();
		int h = getHeight();

		// Tầng viền ngoài (đậm)
		g2.setColor(new Color(30, 30, 30));
		g2.fillRoundRect(0, 0, w - 1, h - 1, 12, 12);

		// Viền vàng cam
		g2.setColor(new Color(240, 176, 48));
		g2.fillRoundRect(4, 4, w - 8, h - 8, 10, 10);

		// Lớp viền tối nhỏ bên trong
		g2.setColor(new Color(70, 70, 70));
		g2.drawRoundRect(6, 6, w - 12, h - 12, 10, 10);

		// Nền xám nhạt bên trong
		g2.setColor(new Color(232, 232, 232));
		g2.fillRoundRect(8, 8, w - 16, h - 16, 8, 8);

		// Khung trong màu xám
		g2.setColor(new Color(140, 140, 140));
		g2.drawRoundRect(10, 10, w - 20, h - 20, 8, 8);

		g2.dispose();

		// Vẽ hệ Pokémon ở góc phải trên
		Graphics2D g2d = (Graphics2D) g.create();
		drawTypeBadge(g2d, pokemon.getType1(), w - 100, 12);
		g2d.dispose();

		// Tên Pokémon
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.drawString(pokemon.getName(), 20, 28);

		// Vẽ thanh máu
		int barX = 20;
		int barY = 40;
		int barWidth = w - 40;
		int barHeight = 16;

		drawHealthBar(g, currHp, maxHp, barX, barY, barWidth, barHeight);

		// Hiển thị chữ HP
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 11));
		String hpText = currHp + " / " + maxHp;
		g.drawString(hpText, barX + barWidth - 45, barY + barHeight + 13);
	}

	//Thanh máu và đổi màu thanh máu theo lượng HP
	private void drawHealthBar(Graphics g, int currHp, int maxHp, int x, int y, int width, int height) {
		// Vẽ khung thanh máu (xám)
		g.setColor(new Color(200, 200, 200));
		g.fillRect(x, y, width, height);
		
		// Viền thanh máu
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);

		// Tính phần trăm HP
		double ratio = currHp / (double) maxHp;

		// Xác định màu máu dựa trên tỉ lệ HP
		Color hpColor = getHealthColor(ratio);

		// Thanh máu đổi màu
		int hpWidth = (int) (ratio * width);
		g.setColor(hpColor);
		g.fillRect(x + 1, y + 1, hpWidth - 1, height - 2);
	}

	// Vẽ badge hệ Pokémon
	private void drawTypeBadge(Graphics2D g2, PokemonType type, int x, int y) {
		int badgeWidth = 90;
		int badgeHeight = 24;

		// Lấy màu theo hệ
		Color typeColor = getTypeColor(type);

		// Vẽ nền badge
		g2.setColor(typeColor);
		g2.fillRoundRect(x, y, badgeWidth, badgeHeight, 6, 6);

		// Viền đen
		g2.setColor(Color.BLACK);
		g2.setStroke(new java.awt.BasicStroke(1.5f));
		g2.drawRoundRect(x, y, badgeWidth, badgeHeight, 6, 6);

		// Text hệ
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Arial", Font.BOLD, 12));
		String typeName = type.toString().substring(0, 1).toUpperCase() + type.toString().substring(1).toLowerCase();
		int textX = x + (badgeWidth - g2.getFontMetrics().stringWidth(typeName)) / 2;
		int textY = y + ((badgeHeight - g2.getFontMetrics().getHeight()) / 2) + g2.getFontMetrics().getAscent();
		g2.drawString(typeName, textX, textY);
	}

	// Lấy màu theo hệ Pokémon 
	private Color getTypeColor(PokemonType type) {
		switch (type) {
			case NORMAL:
				return new Color(168, 168, 120); // Xám
			case FIRE:
				return new Color(240, 128, 48); // Cam đỏ
			case WATER:
				return new Color(104, 144, 240); // Xanh nước biển
			case GRASS:
				return new Color(120, 200, 80); // Xanh lá
			case ELECTRIC:
				return new Color(248, 208, 48); // Vàng
			case ICE:
				return new Color(152, 216, 216); // Xanh nhạt
			case FIGHTING:
				return new Color(192, 48, 40); // Đỏ đậm
			case POISON:
				return new Color(160, 64, 160); // Tím
			case GROUND:
				return new Color(224, 192, 104); // Vàng đất
			case FLYING:
				return new Color(168, 144, 240); // Tím nhạt
			case PSYCHIC:
				return new Color(248, 88, 136); // Hồng
			case BUG:
				return new Color(168, 184, 32); // Xanh lục
			case ROCK:
				return new Color(184, 160, 56); // Nâu
			case GHOST:
				return new Color(112, 88, 152); // Tím đậm
			case DRAGON:
				return new Color(112, 56, 248); // Xanh dương đậm
			case DARK:
				return new Color(112, 88, 72); // Nâu tối
			case STEEL:
				return new Color(184, 184, 208); // Bạc
			case FAIRY:
				return new Color(238, 153, 172); // Hồng nhạt
			default:
				return new Color(128, 128, 128);
		}
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
