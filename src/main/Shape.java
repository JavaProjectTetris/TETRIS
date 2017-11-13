package javaproject_tetris;

import java.util.Random;
import java.lang.Math;

public class Shape {

	//��� ���� ���� ����
	enum Piece{
		NoShape,IShape,Jshape,Lshape,Oshape,SShape,TShape,ZShape
	};
	
	private int coordinate[][];//���� ��� ����(2��)�� ��ǥ ex){0.0},{0.0},{0.0},{0.0}
	private int [][][] coordsTable;//��ü ���(3��)�� ���� ��ǥ
	
	public Shape(){
		coordinate = new int[4][4];
		setPiece(Piece.NoShape);//�ʱ�ȭ
	}
	
	private Piece pieceShape;// ��ǥ ����� ���
	
	//��� ����
	public void setPiece(Piece shape){
		coordsTable = new int[][][]{
			{{0,0},{0,0},{0,0},{0,0}}//NoShape ���� ��ġ ��ǥ ǥ��
		};
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				coordinate[i][j]=coordsTable[shape.ordinal()][i][j];//ordinal()�� enum�� ��� ��ġ ��ȯ.��,������ ����Ŵ
			}
		}
		pieceShape=shape;
	}
	//���õǾ��ִ� ����� �̸��� ��ȯ
	public Piece getShape(){
		return pieceShape;
	}
	//��� ���� ����
	public void setRandomShape(){
		Random r = new Random();
		int x=Math.abs(r.nextInt())%7+1;//abs�� ����. 1�� ���ϴ� ������ �𸣰���.
	}
}
