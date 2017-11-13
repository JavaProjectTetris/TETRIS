package javaproject_tetris;

import java.util.Random;
import java.lang.Math;

public class Shape {

	//블록 형태 정의 나열
	enum Piece{
		NoShape,IShape,Jshape,Lshape,Oshape,SShape,TShape,ZShape
	};
	
	private int coordinate[][];//실제 블록 형태(2차)의 좌표 ex){0.0},{0.0},{0.0},{0.0}
	private int [][][] coordsTable;//전체 블록(3차)의 형태 좌표
	
	public Shape(){
		coordinate = new int[4][4];
		setPiece(Piece.NoShape);//초기화
	}
	
	private Piece pieceShape;// 좌표 저장된 블록
	
	//블록 설정
	public void setPiece(Piece shape){
		coordsTable = new int[][][]{
			{{0,0},{0,0},{0,0},{0,0}}//NoShape 도형 위치 좌표 표현
		};
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				coordinate[i][j]=coordsTable[shape.ordinal()][i][j];//ordinal()은 enum의 상수 위치 반환.즉,모형을 가리킴
			}
		}
		pieceShape=shape;
	}
	//선택되어있는 블록의 이름을 반환
	public Piece getShape(){
		return pieceShape;
	}
	//블록 랜덤 설정
	public void setRandomShape(){
		Random r = new Random();
		int x=Math.abs(r.nextInt())%7+1;//abs는 절댓값. 1을 더하는 이유는 모르겠음.
	}
}
