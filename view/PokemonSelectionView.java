package view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

import model.Pokemon;
import model.PokemonData;

public class PokemonSelectionView extends JFrame {
    private JPanel selectedPanel;
    private JPanel availablePanel;
    private JButton startButton;
    private List<Pokemon> selectedPokemons;
    private List<JButton> pokemonButtons;
    private static final int MAX_SELECTION = 4;

    public PokemonSelectionView() {
        selectedPokemons = new ArrayList<>();
        pokemonButtons = new ArrayList<>();
        initUI();
    }

    private void initUI() {
        setTitle("Pokemon Battle - Chọn Pokemon");
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        // Main panel với background
        JPanel mainPanel = new JPanel() {
            private Image backgroundImage;

            {
                try {
                    backgroundImage = ImageIO.read(new File("resources/background.png"));
                } catch (Exception e) {
                    System.out.println("Error loading background: " + e.getMessage());
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

        mainPanel.setLayout(new BorderLayout());

        // Panel danh sách pokemon có sẵn
        availablePanel = new JPanel();
        availablePanel.setOpaque(false);
        availablePanel.setLayout(new GridLayout(0, 3, 10, 10));
        availablePanel.setBorder(new TitledBorder("Pokemon có sẵn"));
        JScrollPane availableScroll = new JScrollPane(availablePanel);
        availableScroll.setOpaque(false);
        availableScroll.getViewport().setOpaque(false);
        availableScroll.setBorder(BorderFactory.createTitledBorder("Chọn Pokemon"));

        // Panel pokemon đã chọn
        selectedPanel = new JPanel();
        selectedPanel.setOpaque(false);
        selectedPanel.setLayout(new GridLayout(2, 2, 10, 10));
        selectedPanel.setBorder(new TitledBorder("Pokemon đã chọn (" + selectedPokemons.size() + "/" + MAX_SELECTION + ")"));

        // Panel chứa danh sách và pokemon đã chọn
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(availableScroll, BorderLayout.CENTER);

        JPanel selectedWrapper = new JPanel(new BorderLayout());
        selectedWrapper.setOpaque(false);
        selectedWrapper.setBorder(BorderFactory.createTitledBorder("Pokemon đã chọn (" + selectedPokemons.size() + "/" + MAX_SELECTION + ")"));
        selectedWrapper.add(selectedPanel, BorderLayout.CENTER);

        // Panel dưới
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        startButton = new JButton("Bắt Đầu");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(0, 120, 0));
        startButton.setOpaque(true);
        startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        startButton.setPreferredSize(new Dimension(150, 50));
        startButton.setEnabled(false);
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bottomPanel.add(startButton);

        // Layout chính
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setOpaque(false);
        mainContent.add(centerPanel, BorderLayout.WEST);
        mainContent.add(selectedWrapper, BorderLayout.EAST);
        mainContent.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.add(mainContent, BorderLayout.CENTER);
        add(mainPanel);

        loadPokemonButtons();
        setVisible(true);
    }

    private void loadPokemonButtons() {
        // Danh sách tất cả pokemon
        Pokemon[] allPokemons = {
            PokemonData.pokemonPikachu(),
            PokemonData.pokemonPidgey(),
            PokemonData.pokemonCharizard(),
            PokemonData.pokemonSuicune(),
            PokemonData.pokemonGreninja(),
            PokemonData.pokemonHaxorus(),
            PokemonData.pokemonGiratina(),
            PokemonData.pokemonGengar(),
            PokemonData.pokemonSteelix(),
            PokemonData.pokemonMagearna(),
            PokemonData.pokemonSerperior(),
            PokemonData.pokemonSceptile(),
            PokemonData.pokemonVolcarona(),
            PokemonData.pokemonScizor(),
            PokemonData.pokemonVileplume(),
            PokemonData.pokemonNidoking(),
            PokemonData.pokemonMewtwo(),
            PokemonData.pokemonJirachi(),
            PokemonData.pokemonGyarados(),
            PokemonData.pokemonRayquaza(),
            PokemonData.pokemonZekrom(),
            PokemonData.pokemonReshiram(),
            PokemonData.pokemonKyurem(),
            PokemonData.pokemonSnorlax(),
            PokemonData.pokemonArceus(),
            PokemonData.pokemonLapras(),
            PokemonData.pokemonDrapion(),
            PokemonData.pokemonAbsol(),
            PokemonData.pokemonTyranitar(),
            PokemonData.pokemonArcheops(),
            PokemonData.pokemonMachamp(),
            PokemonData.pokemonLucario(),
            PokemonData.pokemonGolem(),
            PokemonData.pokemonRhydon(),
            PokemonData.pokemonGardevoir()
        };

        for (Pokemon pokemon : allPokemons) {
            JButton btn = new JButton(pokemon.getName());
            btn.setFont(new Font("Arial", Font.PLAIN, 12));
            btn.setBackground(new Color(100, 150, 200));
            btn.setForeground(Color.WHITE);
            btn.setOpaque(true);
            btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btn.addActionListener(e -> selectPokemon(pokemon, btn));
            availablePanel.add(btn);
            pokemonButtons.add(btn);
        }
    }

    private void selectPokemon(Pokemon pokemon, JButton sourceButton) {
        if (selectedPokemons.contains(pokemon)) {
            // Deselect
            selectedPokemons.remove(pokemon);
            sourceButton.setBackground(new Color(100, 150, 200));
        } else if (selectedPokemons.size() < MAX_SELECTION) {
            // Select
            selectedPokemons.add(pokemon);
            sourceButton.setBackground(new Color(0, 180, 0));

            // Cập nhật panel pokemon đã chọn
            updateSelectedPanel();
        } else {
            JOptionPane.showMessageDialog(this, "Bạn chỉ có thể chọn " + MAX_SELECTION + " con Pokemon!", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Update button
        updateSelectedPanel();
        updateStartButton();
    }

    private void updateSelectedPanel() {
        selectedPanel.removeAll();
        for (Pokemon pokemon : selectedPokemons) {
            JLabel label = new JLabel(pokemon.getName());
            label.setFont(new Font("Arial", Font.BOLD, 14));
            label.setForeground(Color.WHITE);
            label.setHorizontalAlignment(JLabel.CENTER);
            selectedPanel.add(label);
        }
        selectedPanel.revalidate();
        selectedPanel.repaint();
    }

    private void updateStartButton() {
        startButton.setEnabled(selectedPokemons.size() == MAX_SELECTION);
    }

    public List<Pokemon> getSelectedPokemons() {
        return selectedPokemons;
    }

    public void addStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    public void closeSelection() {
        dispose();
    }
}
