package tetris;

import java.util.Random;

class Shape {
	int coordinate[][];

	public Shape() {
		coordinate = new int[4][2];
	}

	public void setX(int index, int x) {
		coordinate[index][0] = x;
	}

	public void setY(int index, int y) {
		coordinate[index][1] = y;
	}

	public int getX(int index) {
		return coordinate[index][0];
	}

	public int getY(int index) {
		return coordinate[index][1];
	}

	public int minX() {
		int m = coordinate[0][0];

		for (int i = 0; i < 4; i++) {
			m = Math.min(m, coordinate[i][0]);
		}
		return m;
	}

	public int minY() {
		int m = coordinate[0][1];

		for (int i = 0; i < 4; i++) {
			m = Math.min(m, coordinate[i][1]);
		}
		return m;
	}

	// public Shape rotate() { //È¸Àü
	//
	// if (pieceShape == Tetrominoes.SquareShape)
	// return this;
	//
	// Shape result = new Shape();
	// result.pieceShape = pieceShape;
	//
	// for (int i = 0; i < 4; ++i) {
	//
	// result.setX(i, setX.y(i));
	// result.setY(i, -x(i));
	// }
	//
	// return result;
	// }
	//
	// public Shape rotateRight() {
	//
	// if (pieceShape == Tetrominoes.SquareShape)
	// return this;
	//
	// Shape result = new Shape();
	// result.pieceShape = pieceShape;
	//
	// for (int i = 0; i < 4; ++i) {
	//
	// result.setX(i, -y(i));
	// result.setY(i, x(i));
	// }
	//
	// return result;
	// }

	public Piece getPiece() {
		int random = (int) Math.random() * 7;

		switch (random) {
		case 0:
			return Piece.IShape;

		case 1:
			return Piece.JShape;

		case 2:
			return Piece.LShape;

		case 3:
			return Piece.OShape;

		case 4:
			return Piece.SShape;

		case 5:
			return Piece.TShape;

		case 6:
			return Piece.ZShape;
		}
		return null;
	}

	class IShape extends Shape {
		int[][] coordinate = new int[][] { { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } };
	}

	class JShape extends Shape {
		int[][] coordinate = new int[][] { { -1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } };
	}

	class LShape extends Shape {
		int[][] coordinate = new int[][] { { 1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } };
	}

	class NoShape extends Shape {
		int[][] coordinatee = new int[][] { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } };
	}

	class OShape extends Shape {
		int[][] coordinate = new int[][] { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } };
	}

	class SShape extends Shape {
		int[][] coordinate = new int[][] { { 0, -1 }, { 0, 0 }, { -1, 0 }, { -1, 1 } };
	}

	class TShape extends Shape {
		int[][] coordinate = new int[][] { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } };
	}

	class ZShape extends Shape {
		int[][] coordinate = new int[][] { { 0, -1 }, { 0, 0 }, { 1, 0 }, { 1, 1 } };
	}
}

enum Piece {
	IShape, JShape, LShape, NoShape, OShape, SShape, TShape, ZShape
}

public class Tetris {
	public static void main(String[] args) {

	}
}