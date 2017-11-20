package tetris;

import java.lang.Math;
import java.util.Random;

public class Shape {
	int coordinate[][];
	int angle;
	private Piece pieceShape;

	public Shape() {
		coordinate = new int[4][2]; // 좌표 설정을 위한 배열 정의
	}

	public void setX(int index, int x) {
		coordinate[index][0] = x; // x좌표
	}

	public void setY(int index, int y) {
		coordinate[index][1] = y; // y좌표
	}

	public int getX(int index) {
		return coordinate[index][0]; // x좌표 리턴
	}

	public int getY(int index) {
		return coordinate[index][1]; // y좌표 리턴
	}

	public int minX() {
		int m = coordinate[0][0]; // 첫번째x좌표
		for (int i = 0; i < 4; i++) {
			m = Math.min(m, coordinate[i][0]);// x좌표들 비교

		}
		return m;
	}

	public int minY() {
		int m = coordinate[0][1];
		for (int i = 0; i < 4; i++) {
			m = Math.min(m, coordinate[i][1]); // y좌표중 가장 작은 숫자
		}
		return m;
	}

	public Shape rotate() {
		if (pieceShape == Piece.OShape) { // 네모모양이면 회전할 필요 없이 그냥 리턴
			return this;
		}

		Shape result = new Shape();
		result.pieceShape = pieceShape;

		for (int i = 0; i < 4; i++) { // x,y 좌표 바꿔주기
			result.setX(i, -getY(i)); // 반시계 방향으로 회전 하면 (x,y)였던 좌표가 (-y, x)로 변함
			result.setY(i, getX(i));

		}
		return result;
	}

	public Piece getPiece() { // 랜덤으로 숫자를 받아 테트리스 도형 설정
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

	class NoShape extends Shape {

		private int[][] coordsTable = new int[][] { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } };

	}

	class IShape extends Shape {// 일자

		private int[][] coordsTable = new int[][] { { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } };

	}

	class JShape extends Shape {// ㄱ모양

		private int[][] coordsTable = new int[][] { { -1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } };

	}

	class LShape extends Shape {// ㄱ반대로
		private int[][] coordsTable = new int[][] { { 1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } };

	}

	class OShape extends Shape {// 네모
		private int[][] coordsTable = new int[][] { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } };

	}

	class SShape extends Shape {// z모양 반대로
		private int[][] coordsTable = new int[][] { { 0, -1 }, { 0, 0 }, { -1, 0 }, { -1, 1 } };

	}

	class TShape extends Shape {// ㅗ모양
		private int[][] coordsTable = new int[][] { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } };

	}

	class ZShape extends Shape {// z모양
		private int[][] coordsTable = new int[][] { { 0, -1 }, { 0, 0 }, { 1, 0 }, { 1, 1 } };

	}

	enum Piece {

		NoShape, IShape, JShape, LShape, OShape, SShape, TShape, ZShape

	}

}
