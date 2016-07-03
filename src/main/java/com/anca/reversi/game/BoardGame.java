package com.anca.reversi.game;

import java.util.ArrayList;

import scala.Tuple2;

public class BoardGame {

	private byte gameplayer;
	private ArrayList<ArrayList<Byte>> matrix;
	private boolean notEndGame;

	public byte getGameplayer() {
		return gameplayer;
	}

	public ArrayList<ArrayList<Byte>> getMatrix() {
		return matrix;
	}

	public boolean isNotEndGame() {
		return notEndGame;
	}

	public void setGameplayer(byte gameplayer) {
		this.gameplayer = gameplayer;
	}

	public void setMatrix(ArrayList<ArrayList<Byte>> matrix) {
		this.matrix = matrix;
	}

	public void setNotEndGame(boolean notEndGame) {
		this.notEndGame = notEndGame;
	}

	public BoardGame(byte gameplayer, ArrayList<ArrayList<Byte>> matrix) {
		this.gameplayer = gameplayer;
		this.matrix = matrix;
		this.notEndGame = true;
	}

	public boolean returnValue(byte gameplayer, byte oppo, byte x, byte y, byte addx, byte addy) {
		if ((x + addx > -1) && (x + addx < 8) && (y + addy > -1) && (y + addy < 8)) {
			if (this.matrix.get(x + addx).get(y + addy) == oppo) {
				byte vtx = (byte) (x + addx);
				byte vty = (byte) (y + addy);
				while (this.matrix.get(vtx).get(vty) == oppo) {
					vtx += addx;
					vty += addy;
					if ((vtx <= -1) || (vtx >= 8) || (vty <= -1) || (vty >= 8)) {
						return false;
					}
				}
				if (this.matrix.get(vtx).get(vty) == gameplayer) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean checkChangeState(byte gameplayer, byte x, byte y) {
		if (this.matrix.get(x).get(y) != -1) {
			return false;
		}
		byte oppo = 0;
		if (gameplayer == 1) {
			oppo = 2;
		} else {
			oppo = 1;
		}
		if (this.returnValue(gameplayer, oppo, x, y, (byte) -1, (byte) 0)
				|| this.returnValue(gameplayer, oppo, x, y, (byte) 1, (byte) 0)
				|| this.returnValue(gameplayer, oppo, x, y, (byte) 0, (byte) -1)
				|| this.returnValue(gameplayer, oppo, x, y, (byte) 0, (byte) 1)
				|| this.returnValue(gameplayer, oppo, x, y, (byte) -1, (byte) -1)
				|| this.returnValue(gameplayer, oppo, x, y, (byte) -1, (byte) 1)
				|| this.returnValue(gameplayer, oppo, x, y, (byte) 1, (byte) -1)
				|| this.returnValue(gameplayer, oppo, x, y, (byte) 1, (byte) 1)) {
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<ArrayList<Byte>> reverseColorPoint(ArrayList<ArrayList<Byte>> matrix, byte gameplayer, byte x,
			byte y, byte addx, byte addy) {
		byte vtx = (byte) (x + addx);
		byte vty = (byte) (y + addy);
		while (matrix.get(vtx).get(vty) != gameplayer) {
			matrix.get(vtx).set(vty, gameplayer);
			vtx += addx;
			vty += addy;
		}
		return matrix;
	}

	public ArrayList<ArrayList<Byte>> newBoard(byte x, byte y) {
		ArrayList<ArrayList<Byte>> newMatrix = deepCopy(this.matrix);
		byte oppo = 0;
		if (this.gameplayer == 1) {
			oppo = 2;
		} else {
			oppo = 1;
		}
		if (this.returnValue(this.gameplayer, oppo, x, y, (byte) -1, (byte) 0))
			newMatrix = this.reverseColorPoint(newMatrix, this.gameplayer, x, y, (byte) -1, (byte) 0);
		if (this.returnValue(this.gameplayer, oppo, x, y, (byte) 1, (byte) 0))
			newMatrix = this.reverseColorPoint(newMatrix, this.gameplayer, x, y, (byte) 1, (byte) 0);
		if (this.returnValue(this.gameplayer, oppo, x, y, (byte) 0, (byte) -1))
			newMatrix = this.reverseColorPoint(newMatrix, this.gameplayer, x, y, (byte) 0, (byte) -1);
		if (this.returnValue(this.gameplayer, oppo, x, y, (byte) 0, (byte) 1))
			newMatrix = this.reverseColorPoint(newMatrix, this.gameplayer, x, y, (byte) 0, (byte) 1);
		if (this.returnValue(this.gameplayer, oppo, x, y, (byte) -1, (byte) 1))
			newMatrix = this.reverseColorPoint(newMatrix, this.gameplayer, x, y, (byte) -1, (byte) 1);
		if (this.returnValue(this.gameplayer, oppo, x, y, (byte) -1, (byte) -1))
			newMatrix = this.reverseColorPoint(newMatrix, this.gameplayer, x, y, (byte) -1, (byte) -1);
		if (this.returnValue(this.gameplayer, oppo, x, y, (byte) 1, (byte) 1))
			newMatrix = this.reverseColorPoint(newMatrix, this.gameplayer, x, y, (byte) 1, (byte) 1);
		if (this.returnValue(this.gameplayer, oppo, x, y, (byte) 1, (byte) -1))
			newMatrix = this.reverseColorPoint(newMatrix, this.gameplayer, x, y, (byte) 1, (byte) -1);

		newMatrix.get(x).set(y, this.gameplayer);
		return newMatrix;
	}

	public boolean checkNotEndGame() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (this.matrix.get(i).get(j) == -1) {
					return true;
				}
			}
		}
		this.notEndGame = false;
		return false;
	}

	public ArrayList<Tuple2<Byte, Byte>> generateChild() {
		byte gameplayer = this.gameplayer;
		ArrayList<Tuple2<Byte, Byte>> listStatus = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (this.checkChangeState(gameplayer, (byte) i, (byte) j)) {
					Tuple2<Byte, Byte> t = new Tuple2<Byte, Byte>((byte) i, (byte) j);
					listStatus.add(t);
				}
			}
		}
		return listStatus;
	}

	public Tuple2<Byte, Byte> count() {
		byte count1 = 0;
		byte count2 = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (this.matrix.get(i).get(j) == 2) {
					count2 += 1;
				} else if (this.matrix.get(i).get(j) == 1) {
					count1 += 1;
				}
			}
		}
		return new Tuple2<Byte, Byte>(count1, count2);
	}

	public void displayTerminal() {
		System.out.println("Turn: player" + this.gameplayer);
		System.out.println("  0 1 2 3 4 5 6 7");
		byte count = 0;
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			builder.append(count).append(" ");
			for (int j = 0; j < 8; j++) {
				if (this.matrix.get(i).get(j) == -1)
					builder.append("-");
				else
					builder.append(this.matrix.get(i).get(j).toString());
				builder.append(" ");
			}
			count += 1;
			builder.append("\n");
		}
		System.out.println(builder.toString());
	}

	private ArrayList<ArrayList<Byte>> deepCopy(ArrayList<ArrayList<Byte>> matrix) {
		ArrayList<ArrayList<Byte>> result = new ArrayList<>();
		for (ArrayList<Byte> arr : matrix) {
			ArrayList<Byte> resultArr = new ArrayList<>();
			for (Byte element : arr) {
				resultArr.add(new Byte(element.byteValue()));
			}
			result.add(resultArr);
		}
		return result;
	}
}
