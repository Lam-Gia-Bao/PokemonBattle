package view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

import model.Pokemon;
import model.PokemonData;
import model.PokemonType;

public class PokemonSelectionView extends JFrame {
    private JPanel selectedPanel;
    private JPanel availablePanel;
    private JButton startButton;
    private JLabel selectedImageLabel;
    private JLabel typeLabel;
    private JButton selectButton;
    private JButton deselectButton;
    private List<Pokemon> selectedPokemons;
    private List<JButton> pokemonButtons;
    private Pokemon currentPreviewPokemon;
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

        // Panel danh sách pokemon có sẵn (TRÁI)
        availablePanel = new JPanel();
        availablePanel.setOpaque(false);
        availablePanel.setLayout(new GridLayout(0, 2, 10, 10));
        JScrollPane availableScroll = new JScrollPane(availablePanel);
        availableScroll.setOpaque(false);
        availableScroll.getViewport().setOpaque(false);
        availableScroll.setBorder(BorderFactory.createTitledBorder("Chọn Pokemon"));
        availableScroll.setPreferredSize(new Dimension(300, 600));

        // Panel ảnh Pokemon được chọn (GIỮA)
        JPanel centerImagePanel = new JPanel();
        centerImagePanel.setOpaque(false);
        centerImagePanel.setLayout(new BorderLayout());
        centerImagePanel.setBorder(BorderFactory.createTitledBorder("Pokemon được chọn"));
        
        JPanel imageAndTypePanel = new JPanel(new BorderLayout());
        imageAndTypePanel.setOpaque(false);
        
        selectedImageLabel = new JLabel();
        selectedImageLabel.setHorizontalAlignment(JLabel.CENTER);
        selectedImageLabel.setVerticalAlignment(JLabel.CENTER);
        imageAndTypePanel.add(selectedImageLabel, BorderLayout.CENTER);
        
        typeLabel = new JLabel("Hệ: -");
        typeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        typeLabel.setForeground(Color.WHITE);
        typeLabel.setHorizontalAlignment(JLabel.CENTER);
        imageAndTypePanel.add(typeLabel, BorderLayout.SOUTH);
        
        centerImagePanel.add(imageAndTypePanel, BorderLayout.CENTER);
        
        // Panel nút Chọn/Hủy Chọn
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        selectButton = new PixelCommandButton("Chọn", PixelCommandButton.Theme.GREEN);
        selectButton.setFont(new Font("Arial", Font.BOLD, 16));
        selectButton.setPreferredSize(new Dimension(100, 40));
        selectButton.setEnabled(false);
        selectButton.addActionListener(e -> confirmSelectPokemon());
        
        deselectButton = new PixelCommandButton("Hủy Chọn", PixelCommandButton.Theme.RED);
        deselectButton.setFont(new Font("Arial", Font.BOLD, 16));
        deselectButton.setPreferredSize(new Dimension(100, 40));
        deselectButton.setEnabled(false);
        deselectButton.addActionListener(e -> confirmDeselectPokemon());
        
        buttonPanel.add(selectButton);
        buttonPanel.add(deselectButton);
        
        centerImagePanel.add(buttonPanel, BorderLayout.SOUTH);

        // Panel pokemon đã chọn (PHẢI)
        selectedPanel = new JPanel();
        selectedPanel.setOpaque(false);
        selectedPanel.setLayout(new GridLayout(2, 2, 10, 10));
        selectedPanel.setBorder(BorderFactory.createTitledBorder("Pokemon đã chọn (" + selectedPokemons.size() + "/" + MAX_SELECTION + ")"));
        JScrollPane selectedScroll = new JScrollPane(selectedPanel);
        selectedScroll.setOpaque(false);
        selectedScroll.getViewport().setOpaque(false);
        selectedScroll.setPreferredSize(new Dimension(300, 600));

        // Panel dưới
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        startButton = new PixelCommandButton("Bắt Đầu", PixelCommandButton.Theme.GREEN);
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setPreferredSize(new Dimension(150, 50));
        startButton.setEnabled(false);
        bottomPanel.add(startButton);

        // Layout chính
        JPanel mainContent = new JPanel(new BorderLayout(10, 10));
        mainContent.setOpaque(false);
        mainContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainContent.add(availableScroll, BorderLayout.WEST);
        mainContent.add(centerImagePanel, BorderLayout.CENTER);
        mainContent.add(selectedScroll, BorderLayout.EAST);
        mainContent.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.add(mainContent, BorderLayout.CENTER);
        add(mainPanel);

        loadPokemonButtons();
        setVisible(true);
    }

    private void loadPokemonButtons() {
        // Danh sách tất cả pokemon
        Pokemon[] allPokemons = getAllPokemons();

        for (Pokemon pokemon : allPokemons) {
            PixelCommandButton.Theme theme = getTypeTheme(pokemon.getType());
            PixelCommandButton btn = new PixelCommandButton(pokemon.getName(), theme);
            btn.setFont(new Font("Arial", Font.PLAIN, 14));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btn.addActionListener(e -> selectPokemon(pokemon, btn));
            availablePanel.add(btn);
            pokemonButtons.add(btn);
        }
    }

    private void selectPokemon(Pokemon pokemon, JButton sourceButton) {
        // Lưu Pokemon được preview
        currentPreviewPokemon = pokemon;
        
        // Hiển thị ảnh Pokemon được chọn ở giữa
        displaySelectedPokemonImage(pokemon);
        
        // Cập nhật trạng thái các nút Chọn/Hủy Chọn
        if (selectedPokemons.contains(pokemon)) {
            selectButton.setEnabled(false);
            deselectButton.setEnabled(true);
        } else {
            selectButton.setEnabled(selectedPokemons.size() < MAX_SELECTION);
            deselectButton.setEnabled(false);
        }
    }

    private void displaySelectedPokemonImage(Pokemon pokemon) {
        try {
            String pokemonName = pokemon.getName().toLowerCase();
            File imageFile = new File("resources/" + pokemonName + "_front.png");
            
            if (imageFile.exists()) {
                BufferedImage img = ImageIO.read(imageFile);
                Image scaledImage = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                selectedImageLabel.setIcon(new ImageIcon(scaledImage));
            }
            
            // Hiển thị type của Pokemon
            typeLabel.setText("Hệ: " + pokemon.getType().toString());
        } catch (Exception e) {
            System.out.println("Error loading pokemon image: " + e.getMessage());
        }
    }
    
    private void confirmSelectPokemon() {
        if (currentPreviewPokemon != null && !selectedPokemons.contains(currentPreviewPokemon)) {
            selectedPokemons.add(currentPreviewPokemon);
            
            // Disable nút của Pokemon đó
            for (int i = 0; i < pokemonButtons.size(); i++) {
                Pokemon[] allPokemons = getAllPokemons();
                if (allPokemons[i].equals(currentPreviewPokemon)) {
                    pokemonButtons.get(i).setEnabled(false);
                }
            }
            
            // Update UI
            updateSelectedPanel();
            updateStartButton();
            selectButton.setEnabled(false);
            deselectButton.setEnabled(true);
        }
    }
    
    private void confirmDeselectPokemon() {
        if (currentPreviewPokemon != null && selectedPokemons.contains(currentPreviewPokemon)) {
            selectedPokemons.remove(currentPreviewPokemon);
            
            // Enable nút của Pokemon đó
            for (int i = 0; i < pokemonButtons.size(); i++) {
                Pokemon[] allPokemons = getAllPokemons();
                if (allPokemons[i].equals(currentPreviewPokemon)) {
                    pokemonButtons.get(i).setEnabled(true);
                }
            }
            
            // Update UI
            updateSelectedPanel();
            updateStartButton();
            selectButton.setEnabled(selectedPokemons.size() < MAX_SELECTION);
            deselectButton.setEnabled(false);
        }
    }

    private void updateSelectedPanel() {
        selectedPanel.removeAll();
        for (Pokemon pokemon : selectedPokemons) {
            JPanel pokemonCardPanel = new JPanel(new BorderLayout());
            pokemonCardPanel.setOpaque(false);
            pokemonCardPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            
            JPanel imagePanel = new JPanel(new BorderLayout());
            imagePanel.setOpaque(false);
            
            try {
                String pokemonName = pokemon.getName().toLowerCase();
                File imageFile = new File("resources/" + pokemonName + "_front.png");
                
                if (imageFile.exists()) {
                    BufferedImage img = ImageIO.read(imageFile);
                    Image scaledImage = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                    JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                    imageLabel.setHorizontalAlignment(JLabel.CENTER);
                    imagePanel.add(imageLabel, BorderLayout.CENTER);
                }
            } catch (Exception e) {
                System.out.println("Error loading pokemon thumbnail: " + e.getMessage());
            }
            
            pokemonCardPanel.add(imagePanel, BorderLayout.CENTER);
            
            JPanel infoPanel = new JPanel(new BorderLayout());
            infoPanel.setOpaque(false);
            
            JLabel nameLabel = new JLabel(pokemon.getName());
            nameLabel.setFont(new Font("Arial", Font.BOLD, 10));
            nameLabel.setForeground(Color.WHITE);
            nameLabel.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(nameLabel, BorderLayout.NORTH);
            
            JLabel typeInfo = new JLabel("Type: " + pokemon.getType().toString());
            typeInfo.setFont(new Font("Arial", Font.PLAIN, 9));
            typeInfo.setForeground(Color.WHITE);
            typeInfo.setHorizontalAlignment(JLabel.CENTER);
            infoPanel.add(typeInfo, BorderLayout.SOUTH);
            
            pokemonCardPanel.add(infoPanel, BorderLayout.SOUTH);
            
            selectedPanel.add(pokemonCardPanel);
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

    // Lấy theme theo hệ Pokémon
    private PixelCommandButton.Theme getTypeTheme(PokemonType type) {
        switch (type) {
            case NORMAL:
                return PixelCommandButton.Theme.TYPE_NORMAL;
            case FIRE:
                return PixelCommandButton.Theme.TYPE_FIRE;
            case WATER:
                return PixelCommandButton.Theme.TYPE_WATER;
            case GRASS:
                return PixelCommandButton.Theme.TYPE_GRASS;
            case ELECTRIC:
                return PixelCommandButton.Theme.TYPE_ELECTRIC;
            case ICE:
                return PixelCommandButton.Theme.TYPE_ICE;
            case FIGHTING:
                return PixelCommandButton.Theme.TYPE_FIGHTING;
            case POISON:
                return PixelCommandButton.Theme.TYPE_POISON;
            case GROUND:
                return PixelCommandButton.Theme.TYPE_GROUND;
            case FLYING:
                return PixelCommandButton.Theme.TYPE_FLYING;
            case PSYCHIC:
                return PixelCommandButton.Theme.TYPE_PSYCHIC;
            case BUG:
                return PixelCommandButton.Theme.TYPE_BUG;
            case ROCK:
                return PixelCommandButton.Theme.TYPE_ROCK;
            case GHOST:
                return PixelCommandButton.Theme.TYPE_GHOST;
            case DRAGON:
                return PixelCommandButton.Theme.TYPE_DRAGON;
            case DARK:
                return PixelCommandButton.Theme.TYPE_DARK;
            case STEEL:
                return PixelCommandButton.Theme.TYPE_STEEL;
            case FAIRY:
                return PixelCommandButton.Theme.TYPE_FAIRY;
            default:
                return PixelCommandButton.Theme.GRAY;
        }
    }
    
    private Pokemon[] getAllPokemons() {
        return new Pokemon[]{
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
    }
}

