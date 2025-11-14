package model;

import java.util.ArrayList;
import java.util.List;

public class PokemonTeam {
    private final List<Pokemon> team;
    private int currentIndex;
    
    public PokemonTeam() {
        this.team = new ArrayList<>();
        this.currentIndex = 0;
    }
    
    public void addPokemon(Pokemon pokemon) {
        if (team.size() < 4) {
            team.add(pokemon);
        }
    }
    
    public Pokemon getCurrentPokemon() {
        if (currentIndex >= 0 && currentIndex < team.size()) {
            return team.get(currentIndex);
        }
        return null;
    }
    
    public boolean switchPokemon(int newIndex) {
        if (newIndex >= 0 && newIndex < team.size()) {
            Pokemon nextPokemon = team.get(newIndex);
            if (!nextPokemon.isFainted()) {
                currentIndex = newIndex;
                return true;
            }
        }
        return false;
    }
    
    public List<Pokemon> getTeam() {
        return team;
    }
    
    public int getCurrentIndex() {
        return currentIndex;
    }
    
    public int getTeamSize() {
        return team.size();
    }
    
    // Kiểm tra xem team còn pokemon sống không
    public boolean hasActivePokemon() {
        for (Pokemon pokemon : team) {
            if (!pokemon.isFainted()) {
                return true;
            }
        }
        return false;
    }
    
    // Kiểm tra team còn pokemon để đổi hay không
    public boolean switchToNextActivePokemon() {
        for (int i = 0; i < team.size(); i++) {
            if (i != currentIndex && !team.get(i).isFainted()) {
                currentIndex = i;
                return true;
            }
        }
        return false;
    }
}
