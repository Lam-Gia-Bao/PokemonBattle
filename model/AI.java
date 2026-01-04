package model;

public class AI {
	private static final int MAX_DEPTH = 3; // Giới hạn độ sâu tìm kiếm, AI sẽ dự đoán truớc 3 lượt
	
	// Hàm chính: Chọn move dựa trên % máu
	public static Move chooseBestMove(Pokemon ai, Pokemon player) {
		double hpPercent = (double) ai.getHp() / ai.getMaxHp();
		
		// Dưới 40% máu: ưu tiên hồi máu
		if (hpPercent < 0.4) {
			return chooseBestMoveWithHealing(ai, player);
		}
		// Trên 70% máu: ưu tiên sát thương cao
		else if (hpPercent > 0.7) {
			return chooseBestMoveAggressive(ai, player);
		}
		// Từ 40% đến 70%: dùng minimax cân bằng
		else {
			return chooseBestMoveBalanced(ai, player);
		}
	}
	
	// Minimax cho % máu cao (>70%): Ưu tiên sát thương
	public static Move chooseBestMoveAggressive(Pokemon ai, Pokemon player) {
		Move bestMove = null;
		int maxDamage = Integer.MIN_VALUE;
		
		// Tìm move có sát thương cao nhất còn PP
		for (Move move : ai.getMoves()) {
			if (!move.isUsable() || move.isHealingMove()) continue;
			
			int damage = move.calculateDamage(ai, player);
			if (damage > maxDamage) {
				maxDamage = damage;
				bestMove = move;
			}
		}
		
		// Nếu không có move nào dùng được, dùng minimax thông thường
		if (bestMove == null) {
			return chooseBestMoveBalanced(ai, player);
		}
		
		return bestMove;
	}
	
	// Minimax cho % máu thấp (<40%): Ưu tiên hồi máu
	public static Move chooseBestMoveWithHealing(Pokemon ai, Pokemon player) {
		// Tìm skill hồi máu
		for (Move move : ai.getMoves()) {
			if (move.isUsable() && move.isHealingMove()) {
				return move;
			}
		}
		
		// Nếu không có skill hồi hoặc hết PP, dùng minimax phòng thủ
		return chooseBestMoveDefensive(ai, player);
	}
	
	// Minimax phòng thủ: tìm move gây ít thiệt hại nhất cho mình
	private static Move chooseBestMoveDefensive(Pokemon ai, Pokemon player) {
		Node root = new Node(ai, player, null);
		
		int bestValue = Integer.MIN_VALUE;
		Move bestMove = null;
		
		root.generateChildren(true);
		
		for (Node child : root.getChildren()) {
			// Đánh giá với trọng số cao hơn cho việc bảo toàn HP
			int value = minimaxDefensive(false, child, MAX_DEPTH - 1);
			if (value > bestValue) {
				bestValue = value;
				bestMove = child.getLastMove();
			}
		}
		
		return bestMove != null ? bestMove : ai.getMoves().get(0);
	}
	
	// Minimax cân bằng (dùng cho 40%-70% máu)
	public static Move chooseBestMoveBalanced(Pokemon ai, Pokemon player) {
		Node root = new Node(ai, player, null);
		
		int bestValue = Integer.MIN_VALUE;
		Move bestMove = null;
		
		// Tạo các state con là các move có thể của AI
		root.generateChildren(true);
		
		for (Node child : root.getChildren()) {
			int value = minimax(false, child, MAX_DEPTH - 1);
			if (value > bestValue) {
				bestValue = value;
				bestMove = child.getLastMove();
			}
		}
		
		return bestMove != null ? bestMove : ai.getMoves().get(0);
	}
	
	// Thuật toán minimax phòng thủ (tăng trọng số HP của AI)
	private static int minimaxDefensive(boolean maxMin, Node state, int depth) {
		if (depth == 0 || state.isOver()) {
			return heuristicDefensive(state);
		}
		
		if (maxMin) {
			int temp = Integer.MIN_VALUE;
			state.generateChildren(true);
			
			for (Node child : state.getChildren()) {
				int value = minimaxDefensive(false, child, depth - 1);
				if (value > temp) {
					temp = value;
				}
			}
			return temp;
		} else {
			int temp = Integer.MAX_VALUE;
			state.generateChildren(false);
			
			for (Node child : state.getChildren()) {
				int value = minimaxDefensive(true, child, depth - 1);
				if (value < temp) {
					temp = value;
				}
			}
			return temp;
		}
	}
	
	// Thuật toán minimax
	private static int minimax(boolean maxMin, Node state, int depth) {
		// Điều kiện dừng: đạt độ sâu tối đa hoặc trạng thái kết thúc
		if (depth == 0 || state.isOver()) {
			return heuristic(state);
		}
		
		// Nếu là lượt AI (max)
		if (maxMin) {
			int temp = Integer.MIN_VALUE;
			state.generateChildren(true);
			
			for (Node child : state.getChildren()) {
				int value = minimax(false, child, depth - 1);
				if (value > temp) {
					temp = value;
				}
			}
			return temp;
		}
		// Nếu là lượt Player (min)
		else {
			int temp = Integer.MAX_VALUE;
			state.generateChildren(false);
			
			for (Node child : state.getChildren()) {
				int value = minimax(true, child, depth - 1);
				if (value < temp) {
					temp = value;
				}
			}
			return temp;
		}
	}
	
	// Hàm heuristic đánh giá trạng thái
	private static int heuristic(Node state) {
		return state.point();
	}
	
	// Hàm heuristic phòng thủ: tăng trọng số HP của AI
	private static int heuristicDefensive(Node state) {
		// Trọng số cao hơn cho HP của AI
		int aiHp = state.getAiPokemon().getHp();
		int playerHp = state.getPlayerPokemon().getHp();
		
		// Điểm = (HP_AI * 2) - HP_Player
		// Ưu tiên giữ HP của AI cao
		return (aiHp * 2) - playerHp;
	}
}
