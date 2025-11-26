package model;

public class AI {
	private static final int MAX_DEPTH = 3; // Giới hạn độ sâu tìm kiếm
	
	public static Move chooseBestMove(Pokemon ai, Pokemon player) {
		Node root = new Node(ai, player, null);
		
		int bestValue = Integer.MIN_VALUE;
		Move bestMove = null;
		
		// Tạo các state con (các move có thể của AI)
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
}
