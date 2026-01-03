package view;

import javax.swing.*;
import java.awt.*;
import model.Move;
import model.Pokemon;
import model.PokemonType;

public class AIMoveView extends JPanel {
    private Pokemon aiPokemon;

    public AIMoveView(Pokemon aiPokemon) {
        this.aiPokemon = aiPokemon;
        setLayout(new GridLayout(2, 2, 8, 8));
        setOpaque(false);
        setBounds(10, 10, 400, 140);
        updateMoveDisplay(); // Hiển thị các move ngay khi khởi tạo
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int pad = 4;
        int arc = 28;

        // Tầng ngoài (đen)
        g2.setColor(new Color(70, 70, 70));
        g2.fillRoundRect(pad, pad, w - pad * 2, h - pad * 2, arc, arc);

        // Tầng trắng
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(pad + 2, pad + 2, w - (pad + 2) * 2, h - (pad + 2) * 2, arc - 8, arc - 8);

        // Tầng xám
        g2.setColor(new Color(110, 110, 110));
        g2.fillRoundRect(pad + 3, pad + 3, w - (pad + 3) * 2, h - (pad + 3) * 2, arc - 10, arc - 10);

        g2.dispose();

        // Vẽ children
        super.paintComponent(g);
    }

    public void updateMoveDisplay() {
        removeAll();
        
        for (int i = 0; i < aiPokemon.getMoves().size(); i++) {
            Move move = aiPokemon.getMoves().get(i);
            PixelCommandButton.Theme moveTheme = getTypeTheme(move.getType());
            String ppInfo = "PP:" + move.getPp() + "/" + move.getMaxPp();
            String powerInfo = String.valueOf(move.getPower());
            
            PixelCommandButton moveBtn = new PixelCommandButton(move.getName(), ppInfo, powerInfo, moveTheme);
            moveBtn.setFont(new Font("Arial", Font.BOLD, 18));
            moveBtn.setEnabled(false); // AI moves không clickable
            add(moveBtn);
        }
        
        revalidate();
        repaint();
    }

    private PixelCommandButton.Theme getTypeTheme(PokemonType type) {
        switch (type) {
            case NORMAL:
                return PixelCommandButton.Theme.GRAY;
            case FIRE:
                return PixelCommandButton.Theme.ORANGE;
            case WATER:
                return PixelCommandButton.Theme.BLUE;
            case GRASS:
                return PixelCommandButton.Theme.GREEN;
            case ELECTRIC:
                return PixelCommandButton.Theme.YELLOW;
            case ICE:
                return PixelCommandButton.Theme.LIGHT_BLUE;
            case FIGHTING:
                return PixelCommandButton.Theme.DARK_RED;
            case POISON:
                return PixelCommandButton.Theme.PURPLE;
            case GROUND:
                return PixelCommandButton.Theme.BROWN;
            case FLYING:
                return PixelCommandButton.Theme.LIGHT_PURPLE;
            case PSYCHIC:
                return PixelCommandButton.Theme.PINK;
            case BUG:
                return PixelCommandButton.Theme.LIGHT_GREEN;
            case ROCK:
                return PixelCommandButton.Theme.DARK_BROWN;
            case GHOST:
                return PixelCommandButton.Theme.DARK_PURPLE;
            case DRAGON:
                return PixelCommandButton.Theme.BLUE;
            case DARK:
                return PixelCommandButton.Theme.DARK_BROWN;
            case STEEL:
                return PixelCommandButton.Theme.SILVER;
            case FAIRY:
                return PixelCommandButton.Theme.LIGHT_PURPLE;
            default:
                return PixelCommandButton.Theme.GRAY;
        }
    }

    public void setPokemon(Pokemon pokemon) {
        this.aiPokemon = pokemon;
        updateMoveDisplay();
    }

    public void updateMoves() {
        updateMoveDisplay();
    }
}
