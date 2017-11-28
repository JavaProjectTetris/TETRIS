package main;

import java.util.Random;
import java.lang.Math;

public class Shape {
	private static Random random = new Random();

	private int shapeType;// 블록 형태 지정
	private int rotateIndex;// 블록 회전 상태 인덱스(0=0도,1=90도,2=180도,3=270도)
	private int x, y;// 블록의 기준좌표(0,0)이라 하자.
	private Coordinate[] coordinates = new Coordinate[4];// 블록 4칸의 x,y좌표표현.

	public Shape() {// 랜덤블록 생성
		this.shapeType = random.nextInt(7) + 1;// 1~7사이의 임의의 값으로 각각의 도형모양을 의미할
												// 것.
		this.rotateIndex = 0;
		calculateCoordinate();
	}

	public Shape(int shapeType) {// 선택된 블록의 정보
		this.shapeType = shapeType;
		this.rotateIndex = 0;
		calculateCoordinate();
	}

	public int minX() {// 블록의 작은 4개 블록의 좌표 중 가장 작은 x좌표
		int min = Integer.MAX_VALUE;// 최대 10자리 숫자를 받아야 최솟값이 바로 들어간다.int타입으로 하면
									// RuntimeError가 발생할수있다.
		for (int i = 0; i < 4; i++) {
			min = Math.min(min, coordinates[i].x);
		}
		return min;
	}

	public int minY() {// 블록의 작은 4개 블록의 좌표 중 가장 작은 y좌표
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < 4; i++) {
			min = Math.min(min, coordinates[i].y);
		}
		return min;
	}

	public void moveDown() {// 아래로 움직였을때 작은 4개 블록의 좌표
		--y;
		calculateCoordinate();
	}

	public void moveLeft() {// 왼쪽으로 움직였을때 작은 4개 블록의 좌표
		--x;
		calculateCoordinate();
	}

	public void moveRight() {// 오른쪽으로 움직였을때 작은 4개 블록의 좌표
		++x;
		calculateCoordinate();
	}

	public void rotate() {
		++rotateIndex;
		if (rotateIndex >= 4) {
			rotateIndex = 0;// 회전이 270도가 넘으면 즉,360도로 0도와 같다.
		}
		calculateCoordinate();
	}

	public Coordinate getCoordinate(int index) {// 블록 4칸중 원하는 1개 블록의 x,y좌표
		return coordinates[index];
	}

	/*
	 * 7개의 블록 모양표현
	 * 
	 * 1 2 3 4 5 6 7 # ## # # ## ## # # ## # # ## ## ### # ## ## #
	 */
	// 1번 IShape도형의 각도별 좌표
	private static Coordinate[][] IShape = new Coordinate[][] {
			// 0도
			{ new Coordinate(0, 1), new Coordinate(0, 0), new Coordinate(0, -1), new Coordinate(0, -2) },
			// 90도
			{ new Coordinate(1, 0), new Coordinate(0, 0), new Coordinate(-1, 0), new Coordinate(-2, 0) },
			// 180도
			{ new Coordinate(0, -1), new Coordinate(0, 0), new Coordinate(0, 1), new Coordinate(0, 2) },
			// 270도
			{ new Coordinate(-1, 0), new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(2, 0) } };
	// 2번 OShape도형의 각도별 좌표
	private static Coordinate[][] OShape = new Coordinate[][] {
			// 0도
			{ new Coordinate(0, 0), new Coordinate(0, 1), new Coordinate(1, 0), new Coordinate(1, 1) },
			// 90도
			{ new Coordinate(0, 0), new Coordinate(0, 1), new Coordinate(1, 0), new Coordinate(1, 1) },
			// 180도
			{ new Coordinate(0, 0), new Coordinate(0, 1), new Coordinate(1, 0), new Coordinate(1, 1) },
			// 270도
			{ new Coordinate(0, 0), new Coordinate(0, 1), new Coordinate(1, 0), new Coordinate(1, 1) } };
	// 3번 JShape도형의 각도별 좌표
	private static Coordinate[][] JShape = new Coordinate[][] {
			// 0도
			{ new Coordinate(0, 1), new Coordinate(0, 0), new Coordinate(-1, 0), new Coordinate(-1, -1) },
			// 90도
			{ new Coordinate(1, 0), new Coordinate(0, 0), new Coordinate(-1, 0), new Coordinate(-1, 1) },
			// 180도
			{ new Coordinate(0, -1), new Coordinate(0, 0), new Coordinate(0, 1), new Coordinate(1, 1) },
			// 270도
			{ new Coordinate(-1, 0), new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(1, -1) } };
	// 4번 LShape도형의 각도별 좌표
	private static Coordinate[][] LShape = new Coordinate[][] {
			// 0도
			{ new Coordinate(0, 1), new Coordinate(0, 0), new Coordinate(0, -1), new Coordinate(1, -1) },
			// 90도
			{ new Coordinate(1, 0), new Coordinate(0, 0), new Coordinate(-1, 0), new Coordinate(-1, -1) },
			// 180도
			{ new Coordinate(0, -1), new Coordinate(0, 0), new Coordinate(0, 1), new Coordinate(-1, 1) },
			// 270도
			{ new Coordinate(-1, 0), new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(1, 1) } };
	// 5번 SShape도형의 각도별 좌표
	private static Coordinate[][] SShape = new Coordinate[][] {
			// 0도
			{ new Coordinate(1, 0), new Coordinate(0, 0), new Coordinate(0, -1), new Coordinate(-1, -1) },
			// 90도
			{ new Coordinate(0, -1), new Coordinate(0, 0), new Coordinate(-1, 0), new Coordinate(-1, 1) },
			// 180도
			{ new Coordinate(-1, 0), new Coordinate(0, 0), new Coordinate(0, 1), new Coordinate(1, 1) },
			// 270도
			{ new Coordinate(0, 1), new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(1, -1) } };
	// 6번 ZShape도형의 각도별 좌표
	private static Coordinate[][] ZShape = new Coordinate[][] {
			// 0도
			{ new Coordinate(-1, 0), new Coordinate(0, 0), new Coordinate(0, -1), new Coordinate(1, -1) },
			// 90도
			{ new Coordinate(0, 1), new Coordinate(0, 0), new Coordinate(-1, 0), new Coordinate(-1, -1) },
			// 180도
			{ new Coordinate(1, 0), new Coordinate(0, 0), new Coordinate(0, 1), new Coordinate(-1, 1) },
			// 270도
			{ new Coordinate(0, -1), new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(1, 1) } };
	// 7번 TShape도형의 각도별 좌표
	private static Coordinate[][] TShape = new Coordinate[][] {
			// 0도
			{ new Coordinate(0, 1), new Coordinate(0, 0), new Coordinate(-1, 0), new Coordinate(1, 0) },
			// 90도
			{ new Coordinate(1, 0), new Coordinate(0, 0), new Coordinate(0, 1), new Coordinate(0, -1) },
			// 180도
			{ new Coordinate(0, -1), new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(-1, 0) },
			// 270도
			{ new Coordinate(-1, 0), new Coordinate(0, 0), new Coordinate(0, -1), new Coordinate(0, 1) } };

	private static Coordinate[][] GetShapeCor(int shapeType) {// 선택된 블록모양별 상대좌표.
		// 상대좌표란 블록 기준좌표에서 상대적인 거리.
		Coordinate[][] r = null;
		switch (shapeType) {
		case 1:
			r = IShape;
			break;
		case 2:
			r = OShape;
			break;
		case 3:
			r = JShape;
			break;
		case 4:
			r = LShape;
			break;
		case 5:
			r = SShape;
			break;
		case 6:
			r = ZShape;
			break;
		case 7:
			r = TShape;
			break;
		}
		return r;
	}

	private void calculateCoordinate() {// 작은 블록 4개의 좌표를 계산하여 결정하는 메소드.
		// 블록의 기준좌표+상대좌표=4칸의 좌표
		Coordinate[][] cor = GetShapeCor(this.shapeType);// 상대좌표
		for (int i = 0; i < 4; i++) {
			coordinates[i].x = x + cor[rotateIndex][i].x;
			coordinates[i].y = y + cor[rotateIndex][i].y;
		}
	}

	public int getX(int index){
		return this.coordinates[index].x;
	}
	
	public int getY(int index){
		return this.coordinates[index].y;
	}
}