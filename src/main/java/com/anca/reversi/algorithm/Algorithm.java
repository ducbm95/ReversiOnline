package com.anca.reversi.algorithm;

import java.util.ArrayList;

import com.anca.reversi.game.BoardGame;

import scala.Tuple2;

public class Algorithm {

	private byte player;

	public Algorithm(byte player) {
		this.player = player;
	}

	public Tuple2<Byte, Byte> miniMax(BoardGame node) {
//		byte count = (byte) (node.count()._1 + node.count()._2);
		Tuple2<Byte, Byte> move = this.alphaBeta(node, (byte) 4, Integer.MIN_VALUE, Integer.MAX_VALUE, true)._1;
		return move;
	}

	private Tuple2<Tuple2<Byte, Byte>, Integer> alphaBeta(BoardGame node, byte depth, int alpha, int beta,
			boolean isMax) {
		if ((depth == 0) || (node.generateChild().size() == 0)) {
			return new Tuple2<Tuple2<Byte, Byte>, Integer>(null, Evaluation.eval(node, this.player));
		}
		Tuple2<Byte, Byte> bestMove = null;
		if (isMax) {
			int bestVal = Integer.MIN_VALUE;
			for (Tuple2<Byte, Byte> move : node.generateChild()) {
				BoardGame child = this.generate(move._1, move._2, node);
				int v = this.alphaBeta(child, (byte) (depth - 1), alpha, beta, false)._2;
				if (v > bestVal) {
					bestVal = v;
					bestMove = move;
					alpha = Math.max(alpha, bestVal);
				}
				if (beta <= alpha) {
					// """beta cut-off"""
					break;
				}
			}
			return new Tuple2<Tuple2<Byte, Byte>, Integer>(bestMove, bestVal);
		} else {
			int bestVal = Integer.MAX_VALUE;
			for (Tuple2<Byte, Byte> move : node.generateChild()) {
				BoardGame child = this.generate(move._1, move._2, node);
				int v = this.alphaBeta(child, (byte) (depth - 1), alpha, beta, true)._2;
				if (v < bestVal) {
					bestVal = v;
					bestMove = move;
					beta = Math.min(beta, bestVal);
				}
				if (beta < alpha) {
					// """alpha cut-off"""
					break;
				}
			}
			return new Tuple2<Tuple2<Byte, Byte>, Integer>(bestMove, bestVal);
		}
	}

	private BoardGame generate(byte x, byte y, BoardGame gameBoard) {
		byte player = gameBoard.getGameplayer();
		player = (byte) (3 - player);
		ArrayList<ArrayList<Byte>> matrix = gameBoard.newBoard(x, y);
		BoardGame state = new BoardGame(player, matrix);
		return state;
	}
}
