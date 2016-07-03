package com.anca.reversi;

import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.anca.reversi.algorithm.Algorithm;
import com.anca.reversi.game.BoardGame;

import scala.Tuple2;

//@SpringBootApplication
public class ReversiOnlineApplication {

	public static void main(String[] args) {
		// SpringApplication.run(ReversiOnlineApplication.class, args);
		ArrayList<ArrayList<Byte>> matrix = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			ArrayList<Byte> row = new ArrayList<>();
			for (int j = 0; j < 8; j++) {
				row.add((byte) -1);
			}
			matrix.add(row);
		}
		matrix.get(3).set(3, (byte) 1);
		matrix.get(3).set(4, (byte) 2);
		matrix.get(4).set(3, (byte) 2);
		matrix.get(4).set(4, (byte) 1);

		BoardGame board = new BoardGame((byte) 1, matrix);
		board.displayTerminal();

		Algorithm algorithm = new Algorithm((byte) 2);

		Scanner scan = new Scanner(System.in);
		int x = 0;
		int y = 0;
		while (true) {
			System.out.println("Enter x: ");
			x = scan.nextInt();
			System.out.println("Enter y: ");
			y = scan.nextInt();
			BoardGame temp = new BoardGame((byte) 2, board.newBoard((byte) x, (byte) y));
			board = temp;
			board.displayTerminal();
			
			Tuple2<Byte, Byte> comMove = algorithm.miniMax(board);
			temp = new BoardGame((byte) 1, board.newBoard(comMove._1, comMove._2));
			board = temp;
			board.displayTerminal();
		}
	}
}
