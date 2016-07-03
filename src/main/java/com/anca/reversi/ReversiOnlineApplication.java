package com.anca.reversi;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.anca.reversi.game.BoardGame;

//@SpringBootApplication
public class ReversiOnlineApplication {

	public static void main(String[] args) {
		// SpringApplication.run(ReversiOnlineApplication.class, args);
		ArrayList<ArrayList<Byte>> matrix = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			ArrayList<Byte> row = new ArrayList<>();
			for (int j = 0; j < 8; j++) {
				row.add((byte) 0);
			}
			matrix.add(row);
		}
		matrix.get(3).set(3, (byte) 1);
		matrix.get(3).set(4, (byte) 2);
		matrix.get(4).set(3, (byte) 2);
		matrix.get(4).set(4, (byte) 1);

		BoardGame board = new BoardGame((byte) 1, matrix);
		board.displayTerminal();
	}
}
