package model;

public class AI {

	public static Move chooseBestMove(Pokemon ai, Pokemon player) {
		Move bestMove = null;
		double bestScore = Double.NEGATIVE_INFINITY;
		
		for (Move m : ai.getMoves()) {
			if (!m.isUsable())
				continue;
			
			//AI điều khiển pokemon xem tình trạng hp pokemon và chọn chiêu theo tiêu chí sát thương của move cao và pp vẫn còn.
			double hpFactor = (ai.getHp()) / (double) ai.getMaxHp();
			double score = m.getPower() * 0.8 + m.getPp() * 0.2 + (1 - hpFactor) * 10;
			
			if (score > bestScore) {
				bestScore = score;
				bestMove = m;
			}
		}
		return bestMove != null ? bestMove : ai.getMoves().get(0);
	}
}
