package com.anca.reversi.algorithm;

import com.anca.reversi.game.BoardGame;

public class Evaluation {

	public static final int eval(BoardGame mystate, byte player) {
		return (int) (Math.random() * 1000);
	}
}
