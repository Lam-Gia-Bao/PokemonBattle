package model;

import java.util.ArrayList;
import java.util.List;

public class Pokemon {
	private String name;
	private PokemonType type;
	private int hp;
	private int maxHp;
	private int atk;
	private int def;
	private int speed;
	private List<Move> moves;
	
	public Pokemon(String name, PokemonType type, int atk, int def, int speed, int hp) {
		this.name = name;
		this.hp = hp;
		this.maxHp = hp;
		this.atk = atk;
		this.def = def;
		this.speed = speed;
		this.type = type;
		this.moves = new ArrayList<>();
	}
	
	//Hàm thêm chiêu thức cho pokemon
	public void addMove(Move move) {
		moves.add(move);
	}

	public String getName() {
		return name;
	}

	public int getHp() {
		return hp;
	}

	public int getMaxHp() {
		return maxHp;
	}
	
	public List<Move> getMoves(){
		return moves;
	}
	
	//Hàm kiểm tra pokemon có bị hạ gục hay chưa
	public boolean isFainted() {
		return hp <= 0;
	}
	
	//Hàm khi pokemon nhận sát thương sẽ bị trừ máu
	public void receiveDmg(int damage) {
		hp -= damage;
		if (hp < 0)
			hp = 0;
	}
	
	//Hàm tính toán lượng sát thương khi pokemon tấn công
	public int attack(Pokemon target, Move move) {
		if (!move.isUsable())
			return 0;
		move.useMove();
		int damage = (int) (((atk - target.def / 2) * move.getPower()) / 10.0);
		if (damage < 1)
			damage = 1;
		target.receiveDmg(damage);
		return damage;
	}
}
