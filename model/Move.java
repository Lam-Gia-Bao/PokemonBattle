package model;

public class Move {
	private final String name;
	private final PokemonType type;
	private final int power;
	private int pp;
	
	public Move(String name, PokemonType type, int power, int pp) {
		this.name = name;
		this.type = type;
		this.power = power;
		this.pp = pp;
	}

	public String getName() {
		return name;
	}

	public PokemonType getType() {
		return type;
	}

	public int getPower() {
		return power;
	}

	public int getPp() {
		return pp;
	}
	
	public void useMove() {
		if (pp > 0)
			pp--;
	}
	
	public boolean isUsable() {
		return pp > 0;
	}
}
